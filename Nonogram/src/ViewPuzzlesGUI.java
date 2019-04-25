import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.control.ScrollBar;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.awt.image.BufferedImage;
import java.sql.*;
import java.util.ArrayList;

public class ViewPuzzlesGUI extends Application {

	String username;

	public ViewPuzzlesGUI(String username) {
		this.username = username;
	}

	public ViewPuzzlesGUI() {
		username = null;
	}

	@Override
	public void start(Stage primaryStage) {

		//Panes
		BorderPane border = new BorderPane();
		GridPane root = new GridPane();
		HBox randomBtns = new HBox(20);
		GridPane customBtns = new GridPane();
		GridPane top = new GridPane();
		BorderPane overall = new BorderPane();
		Pane barPane = new Pane();
		Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();

		//Sets the spacing between elements
		root.setAlignment(Pos.CENTER);
		root.setVgap(5);
		customBtns.setHgap(20);
		customBtns.setVgap(20);
		top.setHgap(8);
		top.setVgap(8);
		
		//Buttons
		Button back = new Button();
		back.setText("Back to Menu");

		//Text
		Text random = new Text("    Random Puzzles");
		random.setFont(Font.font("System", 25) );
		random.setStyle("-fx-fill: #551400");
		
		Text custom = new Text("    Custom Puzzles");
		custom.setFont(Font.font("System", 25));
		custom.setStyle("-fx-fill: #551400");
		
		//Line highlights
		Line line1 = new Line(0.0, 0.0, 500, 0.0);
		Line line2 = new Line(0.0, 0.0, 500, 0.0);
		
		//Scroll bar
		ScrollBar bar = new ScrollBar();
		bar.setOrientation(Orientation.VERTICAL);
		
		//Adding the elements together
		top.add(back, 1, 1);
		root.add(random, 0, 1);
		root.add(line1, 0, 2);
		
		
		//put a box in the gridpane for each one
		for (int i = 0; i < 4; i++) {
			final int i_ = i;
			Button temp = new Button();
			temp.setPrefSize(100, 20);
			temp.setText( 5 * (i + 1) + "x" + 5 * (i + 1) );
			temp.setOnAction(e -> {
				NonogramGUI game = new NonogramGUI(username, 0);
				Randomizer rand = new Randomizer();
				puzzleParser parse = new puzzleParser();
				ArrayList<ArrayList<Integer>> master = rand.randomizer(5 * (i_ + 1), 5 * (i_ + 1));
				ArrayList<ArrayList<Integer>> rowInfo = new ArrayList<>();
				ArrayList<ArrayList<Integer>> colInfo = new ArrayList<>();
				parse.getClues(rowInfo, colInfo, master);
				game.setInfo(master, rowInfo, colInfo, null, 2);
				game.start(primaryStage);
			});
			randomBtns.getChildren().add(temp);
		}
		
		root.add(randomBtns, 0, 4);
		root.add(custom, 0, 6);
		root.add(line2, 0, 7);
		root.add(customBtns, 0, 9);
		
		border.setCenter(root);
		border.setTop(top);

		overall.setCenter(border);
		overall.setRight(bar);

		//Setting the scene and stage
		Scene scene = new Scene(overall);
		scene.getStylesheets().add("LoginCSS.css");
		
		primaryStage.setX(bounds.getMinX());
		primaryStage.setY(bounds.getMinY());
		primaryStage.setWidth(bounds.getWidth());
		primaryStage.setHeight(bounds.getHeight());
		primaryStage.setScene(scene);
		primaryStage.setTitle("Nonogram");
		primaryStage.show();

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int numPuzzles = 0;

		bar.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
                    border.setLayoutY(-new_val.doubleValue());
            }
        });
		
		//find the number of puzzles in the database
		try {
			conn = DriverManager.getConnection("jdbc:mysql://classdb.it.mtu.edu/sjogden", "sjogden", "password");
			stmt = conn.prepareStatement("SELECT COUNT(*) FROM Puzzles");
			rs = stmt.executeQuery();
			rs.first();
			numPuzzles = rs.getInt("COUNT(*)");
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}

		// put a box in the gridpane for each one
		int rowIndex = 0;
		for (int i = 0; i < 4; i++) {
			final int i_ = i;
			Button temp = new Button();
			temp.setText("Random " + 5 * (i + 1) + "x" + 5 * (i + 1));
			temp.setOnAction(e -> {
				NonogramGUI game = new NonogramGUI(username, 0);
				Randomizer rand = new Randomizer();
				puzzleParser parse = new puzzleParser();
				ArrayList<ArrayList<Integer>> master = rand.randomizer(5 * (i_ + 1), 5 * (i_ + 1));
				ArrayList<ArrayList<Integer>> rowInfo = new ArrayList<>();
				ArrayList<ArrayList<Integer>> colInfo = new ArrayList<>();
				parse.getClues(rowInfo, colInfo, master);
				game.setInfo(master, rowInfo, colInfo, null, 2);
				game.start(new Stage());
				primaryStage.close();
			});
			root.add(temp, i % 4, rowIndex);
		}
		rowIndex++;

		try {
			conn = DriverManager.getConnection("jdbc:mysql://classdb.it.mtu.edu/sjogden", "sjogden", "password");
			stmt = conn.prepareStatement("SELECT * FROM Puzzles");
			rs = stmt.executeQuery();
			rs.first();

			// put in a button for each puzzle
			int rowIndex = 1;
			for (int i = 4; i < numPuzzles + 4; i++) {
				int tempInt[] = { rs.getInt("puzzle_id") };
				rs.next();
				Puzzle puzzle = new Puzzle(tempInt[0], username);
				Button temp = new Button();
				temp.setPrefSize(100, 20);
				temp.setText(String.valueOf(i - 3));

				// create the action even for each button to open up NonogramGUI
				temp.setOnAction(e -> {
					Puzzle tempPuzzle = new Puzzle(tempInt[0], username);
					ArrayList<ArrayList<Integer>> rowInfo = new ArrayList<>();
					ArrayList<ArrayList<Integer>> colInfo = new ArrayList<>();
					puzzleParser parseInfo = new puzzleParser();
					parseInfo.getClues(rowInfo, colInfo, puzzle.getMaster());
					NonogramGUI openGUI = new NonogramGUI(username, tempInt[0]);
					openGUI.setInfo(tempPuzzle.getMaster(), rowInfo, colInfo, tempPuzzle.getWorking(), 1);
					openGUI.start(primaryStage);
				});

				if (i % 4 != 0 || i == 4) {
//					customBtns.add(tempImage, i % 4, rowIndex);
					customBtns.add(temp, i % 4, rowIndex + 1);
				} else {
					rowIndex += 2;
//					customBtns.add(tempImage, 0, rowIndex);
					customBtns.add(temp, 0, rowIndex + 1);		
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}

		// method to go back to menu
		back.setOnAction(e -> {
			Menu menu = new Menu(username);
			menu.start(primaryStage);
		});

	}

	public static void main(String[] args) {
		launch(args);
	}
}
