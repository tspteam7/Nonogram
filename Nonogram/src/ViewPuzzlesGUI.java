import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
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
	public void start( Stage primaryStage) {
		GridPane root = new GridPane();
		BorderPane bord = new BorderPane();
		Connection conn = null;
    	PreparedStatement stmt = null;
		ResultSet rs = null;
		int numPuzzles = 0;
		
		//find the number of puzzles in the database
		try {
			conn = DriverManager.getConnection(
					"jdbc:mysql://classdb.it.mtu.edu/sjogden",
					"sjogden",
					"password");
			stmt = conn.prepareStatement("SELECT COUNT(*) FROM Puzzles");
			rs = stmt.executeQuery();
			rs.first();
			numPuzzles = rs.getInt("COUNT(*)");
		} catch (SQLException e ) {
			e.printStackTrace();
			return;
		}
		
		//put a box in the gridpane for each one
		int rowIndex = 0;
		for(int i = 0; i < numPuzzles; i++) {
			int tempInt[] = {i + 1};
			Button temp = new Button();
			temp.setText(String.valueOf(i + 1));
			
			
			//create the action even for each button to open up NonogramGUI
			temp.setOnAction(e -> {
				Puzzle puzzle = new Puzzle(tempInt[0], username);
				ArrayList<ArrayList<Integer>> rowInfo = new ArrayList<>();
				ArrayList<ArrayList<Integer>> colInfo = new ArrayList<>();
				puzzleParser parseInfo = new puzzleParser();
				parseInfo.getClues(rowInfo, colInfo, puzzle.getMaster());
				NonogramGUI openGUI = new NonogramGUI(username,tempInt[0]);
				openGUI.setInfo(puzzle.getMaster(), rowInfo, colInfo, puzzle.getWorking(), 1);
				openGUI.start(new Stage());
				primaryStage.close();
			});
			
			
			if( i % 4 != 0 || i == 0) {
				root.add(temp, i % 4, rowIndex);
			}
			else {
				root.add(temp, 0, ++rowIndex);
			}
		}
		
		Button back = new Button();
		back.setText("Back to Menu");
		root.setAlignment(Pos.CENTER);
		root.setHgap(20);
		root.setVgap(20);
		bord.setCenter(root);
		bord.setBottom(back);
		Scene scene = new Scene(bord, 500, 500);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		//method to go back to menu
		back.setOnAction(e->{
			Menu menu = new Menu(username);
			menu.start(new Stage());
			primaryStage.close();
		});
		
	}

	public static void main( String [] args ) {
		launch(args);
	}
}
