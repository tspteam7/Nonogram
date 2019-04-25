import java.util.ArrayList;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TuturiolGUI extends Application{
	
	private String username = "";
	
	@Override
	/**
	 * Starts the stage with primaryStage and displays the GUI.  It displays 7 tutorial
	 * levels that are built in that everyone has access to.
	 */
	public void start(Stage primaryStage) throws Exception {
		//Make the root to hold everything
        StackPane root = new StackPane();
        
        //Title
        Text title = new Text();
        title.setText("Tutorial");
        title.setStyle("-fx-fill: #1c1207");
        title.setFont(Font.font ("System", 100));
        title.setTextAlignment(TextAlignment.CENTER);
        
        //Create the grid the game is played on
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setMaxHeight(500);
        gridPane.setMaxWidth(500);
        gridPane.setVgap(20);
    	
    	//Create a close button
        Button close = new Button("Close");
        close.setMinHeight(50);
        close.setMinWidth(70);
        close.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	primaryStage.close();
		    	
		    	Menu menu = new Menu(username);
	        	try {
					menu.start(new Stage());
				} catch (Exception a) {
					a.printStackTrace();
				}
		    }
		});
        
    	//Create a close button
        Button hint = new Button("How to Play");
        hint.setMinHeight(50);
        hint.setMinWidth(70);
        hint.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	//create popup stage
				Stage popupwindow=new Stage();
			      
				//Set style
				popupwindow.initModality(Modality.APPLICATION_MODAL);      
				      
				//Create the title
				Label label1= new Label("Welcome to the game! To play you must fill in the grid in a predesigned \n"
										+ "pattern.  To do this you have hints on the side.  These hints tell you how many \n"
										+ "are filled in on that row or column(the sum of the numbers), and if there are \n"
										+ "multiple filled in blocks in a row(1 means 1 in a row, 2 means 2 in a row, etc.).  \n"
										+ "The blocks have three states: filled in, empty, or X'd.  The X'd state alows you to \n"
										+ "mark which blocks you have determined to be empty.  Once the puzzle is completed you \n"
										+ "recieve a notification and the puzzle will lock.  In the Play Puzzle section your \n"
										+ "progress will be saved, and you can reset your puzzle from the menu.  Good Luck!");
				      
				//Create a close button the close the popup
				Button button1= new Button("Close");   
				button1.setOnAction(a -> popupwindow.close());
				     
				//Create layout format
				VBox layout= new VBox(10);    
				      
				//Add the label and button 
				layout.getChildren().addAll(label1, button1);
				layout.setAlignment(Pos.CENTER);
				      
				//Create a scene with the layout
				Scene scene1= new Scene(layout, 500, 200);
				scene1.getStylesheets().add("LoginCSS.css");
				      
				//Show the popup
				popupwindow.setScene(scene1);   
				popupwindow.showAndWait();
		    }
		});
        
        for(int i = 1; i <= 7 ; i++) {
        	int temp = i;
        	
        	Button b = new Button("Tutorial " + i);
            b.setMinHeight(50);
            b.setMinWidth(70);
            b.setOnAction(new EventHandler<ActionEvent>() {
    		    @Override public void handle(ActionEvent e) {
    		    	String address = "TutorialPics\\Puzzle" + temp + ".png";
    		    	
    		    	NonogramGUI game = new NonogramGUI(username,0);
    		    	PuzzleImageLoader load = new PuzzleImageLoader(address);
    		    	puzzleParser parse = new puzzleParser();
    		    	
    		    	ArrayList<ArrayList<Integer>> master = load.pOutput();
    		    	ArrayList<ArrayList<Integer>> ri = new ArrayList<ArrayList<Integer>>();
    		    	ArrayList<ArrayList<Integer>> ci = new ArrayList<ArrayList<Integer>>();
    		    	ArrayList<ArrayList<Integer>> wb = null;
    		    	
    		    	parse.getClues(ri, ci, master);
    		    	
    		    	game.setInfo(master,ri,ci,wb,0);
    		    	
    		    	game.start(new Stage());
    		    	primaryStage.close();
    		    }
    		});
            gridPane.add(b, 0, i-1);
        }
        
        //Set the alignment of title and grid
        StackPane.setAlignment(title, Pos.TOP_CENTER);
        StackPane.setAlignment(gridPane, Pos.CENTER);
        StackPane.setAlignment(close, Pos.BOTTOM_LEFT);
        StackPane.setAlignment(hint, Pos.CENTER_LEFT);
        
        //Add the title and grid to the root, then change the color of root
        root.getChildren().addAll(title,gridPane,close,hint);
        root.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        
        //Create a new scene with the root and show it
        Scene scene = new Scene(root, 1000, 1000);
        scene.getStylesheets().add("LoginCSS.css");
        primaryStage.setScene(scene);
        primaryStage.show();
	}
	
	/**
	 * A constructor that passes in the users username
	 * 
	 * @param s is the username
	 */
	public TuturiolGUI(String s) {
		username = s;
	}

}
