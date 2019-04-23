import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.sql.*;
import java.util.ArrayList;
public class FlaggedPuzzlesGUI extends Application {

	String username;

	public FlaggedPuzzlesGUI(String username) {
		this.username = username;
	}

	public FlaggedPuzzlesGUI() {
		username = null;
	}

	@Override
	public void start(Stage primaryStage) {


		// Panes
		BorderPane border = new BorderPane();
		GridPane root = new GridPane();

		// Sets the spacing between elements
		root.setAlignment(Pos.CENTER);
		root.setHgap(20);
		root.setVgap(20);

		// Buttons
		Button back = new Button();
		back.setText("Back to Menu");

		// Text

		// Adding the elements together
		border.setCenter(root);
		border.setBottom(back);

		// Setting the scene and stage
		Scene scene = new Scene(border, 600, 400);
		scene.getStylesheets().add("LoginCSS.css");

		primaryStage.setScene(scene);
		primaryStage.setTitle("Nonogram");
		primaryStage.show();

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int numPuzzles = 0;

		// find the number of puzzles in the database
		try {
			conn = DriverManager.getConnection("jdbc:mysql://classdb.it.mtu.edu/sjogden", "sjogden", "password");
			stmt = conn.prepareStatement("SELECT COUNT(*) FROM Puzzles where flagged = true");
			rs = stmt.executeQuery();
			rs.first();
			numPuzzles = rs.getInt("COUNT(*)");
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}


		try {
			conn = DriverManager.getConnection("jdbc:mysql://classdb.it.mtu.edu/sjogden", "sjogden", "password");
			stmt = conn.prepareStatement("SELECT * FROM Puzzles where flagged = true");
			rs = stmt.executeQuery();
			rs.first();
			int rowIndex = 0;
			// put in a button for each puzzle
			for (int i = 4; i < numPuzzles + 4; i++) {
				int tempInt[] = { rs.getInt("puzzle_id") };
				rs.next();
				Button temp = new Button();
				temp.setText(String.valueOf(i - 3));

				// create the action even for each button to open up NonogramGUI
				temp.setOnAction(e -> {
					Puzzle puzzle = new Puzzle(tempInt[0], username);
					ArrayList<ArrayList<Integer>> rowInfo = new ArrayList<>();
					ArrayList<ArrayList<Integer>> colInfo = new ArrayList<>();
					puzzleParser parseInfo = new puzzleParser();
					parseInfo.getClues(rowInfo, colInfo, puzzle.getMaster());
					FlaggedPuzzlesViewer openGUI = new FlaggedPuzzlesViewer(username, tempInt[0]);
					openGUI.setInfo(puzzle.getMaster(), rowInfo, colInfo, puzzle.getMaster(), 1);
					openGUI.start(new Stage());
					primaryStage.close();
				});

				if (i % 4 != 0 || i == 0) {
					root.add(temp, i % 4, rowIndex);
				} else {
					root.add(temp, 0, ++rowIndex);
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
