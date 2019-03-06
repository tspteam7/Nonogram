/**
 * Main class for gui of nonogram app
 */

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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


/**
 * Builds the play window for the GUI
 * 
 * @author Frank
 */
public class NonogramGUI extends Application {

	//Create the global stage variables
    private Stage stage = null;
    
    //Create public variable keeping track of number of rows and columns of the puzzle
    private int masterRow = 5;
    private int masterCol = 5;
    
    //Arrays to store the data about how pixels lay out on the grid
    private int[][] rowInfo = {{1},{},{1,1,1},{},{1}};
    private int[][] colInfo = {{1},{},{1,1,1},{},{1}};
    
    //The master check array
    private int[][] master = {
    		{0,0,1,0,0},
    		{0,0,0,0,0},
    		{1,0,1,0,1},
    		{0,0,0,0,0},
    		{0,0,1,0,0},
    		
    };
    
    //The board being used on the game, default is all 0
    private int[][] board = new int[masterRow][masterCol];
    
	
    @Override
    /**
     * Create the stage to play the game
     */
    public void start(Stage stage) {
        //Make the root to hold everything
    	stage = new Stage();
        StackPane root = new StackPane();
        
        //Title
        Text title = new Text();
        title.setText("Nonogram");
        title.setFill(Color.rgb(28, 191, 107));
        title.setFont(Font.font ("Verdana", 100));
        title.setTextAlignment(TextAlignment.CENTER);
        
        //Create the grid the game is played on
        GridPane gridPane = buildPuzzle(masterCol,masterRow);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setMaxHeight(500);
        gridPane.setMaxWidth(500);
    	
    	//Show the Page
        
        //Set the alignment of title and grid
        StackPane.setAlignment(title, Pos.TOP_CENTER);
        StackPane.setAlignment(gridPane, Pos.CENTER);
        
        //Add the title and grid to the root, then change the color of root
        root.getChildren().addAll(title,gridPane);
        root.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        
        //Create a new scene with the root and show it
        Scene scene = new Scene(root, 1000, 1000);
        stage.setScene(scene);
        stage.show();
	}
	
    /**
     * Creates the grid for the puzzle based on an input of number of rows and columns
     * given in an input
     * 
     * @param i is the number of columns
     * @param j is the number of rows
     * @return GridPane is a grid of buttons that can register change
     */
    private GridPane buildPuzzle(int i, int j) {
		GridPane grid = new GridPane();
		
		//Black out area in the top left
		TextField test = new TextField();
		test.setDisable(true);
		test.setMinWidth(500/i);
		test.setMinHeight(500/j);
		test.setStyle("-fx-border-color:#010101;-fx-background-color:#010101;");
		grid.add(test, 0, 0);
		
		//Create the hint boxes for each Column at the top
		for (int c = 1 ; c < j+1; c++) {
			TextField t = new TextField();
			t.setDisable(true);
			t.setMinWidth(500/i);
			t.setMinHeight(500/j + 30);
			t.setStyle("-fx-border-color:#010101;-fx-background-color:#FEFEFE;");
			grid.add(t, c, 0);
		}
		
		//Write info for each row
		for(int r = 1 ; r < j+1; r++) {
			//Create the hint boxes for each row on the left side of the puzzle
			TextField t = new TextField();
			t.setDisable(true);
			t.setMinWidth(500/i + 30);
			t.setMinHeight(500/j);
			t.setStyle("-fx-border-color:#010101;-fx-background-color:#FEFEFE;");
			grid.add(t, 0, r);
			
			//For each grid square
			for(int c = 1; c < i+1; c++) {
				//Create the button,format size,and set default to be white
				Button b = new Button();
				b.setMinWidth(500/i);
				b.setMinHeight(500/j);
	    		b.setStyle("-fx-border-color:#010101;-fx-background-color:#FEFEFE;");
	    		
	    		//get current coordinates
				int row = r;
				int column = c;
				
				//When button is pressed
				b.setOnAction(new EventHandler<ActionEvent>() {
				    @Override public void handle(ActionEvent e) {
				    	//If button is pressed and its black
				    	if(b.getStyle().equals("-fx-border-color:#010101;-fx-background-color:#010101;")) {
				    		//Change button to white
				    		b.setStyle("-fx-border-color:#010101;-fx-background-color:#FEFEFE;");
				    		
				    		//Update the board and compare to master
				    		board[row-1][column-1] = 0;
				    		compare();
				    	}
				    	//else change the button the black
				    	else {
				    		//Change the button to black
				    		b.setStyle("-fx-border-color:#010101;-fx-background-color:#010101;");
				    		
				    		//Update the board and compare again
				    		board[row-1][column-1] = 1;
				    		compare();
				    	}
				    }
				});
				
				//Add the button to the grid
				grid.add(b, c, r);
			}
		}
		
		//Return the grid
		return grid;
	}

    /**
     * Launches the application
     * 
     * @param args
     */
	public static void main(String[] args) {
        launch(args);
    }
	
	/**
	 * Compares the master board vs the player grid and sends a popup if
	 * the player has won
	 */
	public void compare() {
		boolean equals = true;
		
		//Compare each value in the array to see if they are equal
		for(int r = 0 ; r < masterRow ; r++) {
			for(int c = 0 ; c < masterCol ; c++) {
				if(master[r][c] != board[r][c]) {
					equals = false;
				}
			}
		}
		
		
		//If they are equal send a popup
		if(equals) {
			
			//create popup stage
			Stage popupwindow=new Stage();
		      
			//Set style
			popupwindow.initModality(Modality.APPLICATION_MODAL);      
			      
			//Create the title
			Label label1= new Label("You Win!!");
			      
			//Create a close button the close the popup
			Button button1= new Button("Close");   
			button1.setOnAction(e -> popupwindow.close());
			     
			//Create layout format
			VBox layout= new VBox(10);    
			      
			//Add the label and button 
			layout.getChildren().addAll(label1, button1);
			layout.setAlignment(Pos.CENTER);
			      
			//Create a scen with the layout
			Scene scene1= new Scene(layout, 300, 250);
			      
			//Show the popup
			popupwindow.setScene(scene1);   
			popupwindow.showAndWait();
		}
	}
}