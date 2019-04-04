/**
 * Main class for gui of nonogram app
 */

import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
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
    private int masterRow = 0;
    private int masterCol = 0;
    
    //If this puzzle is from tutorial
    boolean fromTutorial = false;
    
    //Arrays to store the data about how pixels lay out on the grid
    private ArrayList<ArrayList<Integer>> rowInfoList = new ArrayList<ArrayList<Integer>>();
    private ArrayList<ArrayList<Integer>> colInfoList = new ArrayList<ArrayList<Integer>>();
    
    //The master check array
    private ArrayList<ArrayList<Integer>> masterList = new ArrayList<ArrayList<Integer>>();
    
    //The board being used on the game, default is all 0
    private ArrayList<ArrayList<Integer>> boardList = new ArrayList<ArrayList<Integer>>();
    
	
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
    	
    	//Create a close button
        Button close = new Button("Close");
        close.setMinHeight(50);
        close.setMinWidth(70);
        close.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	Stage stage = (Stage) close.getScene().getWindow();
		    	stage.close();
		    	if(fromTutorial) {
		        	TuturiolGUI tutgui = new TuturiolGUI();
		        	try {
						tutgui.start(new Stage());
					} catch (Exception a) {
						a.printStackTrace();
					}
		        	
		    	}
		    }
		});
        
        //Set the alignment of title and grid
        StackPane.setAlignment(title, Pos.TOP_CENTER);
        StackPane.setAlignment(gridPane, Pos.CENTER);
        StackPane.setAlignment(close, Pos.BOTTOM_LEFT);
        
        //Add the title and grid to the root, then change the color of root
        root.getChildren().addAll(title,gridPane,close);
        root.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        
        //Create a new scene with the root and show it
        Scene scene = new Scene(root, 1000, 1000);
        stage.setScene(scene);
        stage.show();
	}
    
    public void setInfo(ArrayList<ArrayList<Integer>> m, ArrayList<ArrayList<Integer>> r, ArrayList<ArrayList<Integer>> c, ArrayList<ArrayList<Integer>> wb, boolean isTutorial) {
    	masterList = m;
    	rowInfoList = r;
    	colInfoList = c;
    	fromTutorial = isTutorial;
    	masterRow = m.size();
    	masterCol = m.get(0).size();
    	
    	if (!isTutorial) {
    		boardList = wb;
    	}else {
    		for(int i = 0; i < masterRow ; i++) {
    			boardList.add(new ArrayList<Integer>());
    			for(int j = 0; j < masterCol ; j++) {
    				boardList.get(i).add(0);
    			}
    		}
    	}
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
			String t = "";
			for(int p = 0; p < colInfoList.get(c-1).size(); p++) {
				t = t + colInfoList.get(c-1).get(p) + "\n";
			}
			Label label = new Label(t);
			label.setTextFill(Color.WHITE);
			grid.add(label, c, 0);
			GridPane.setHalignment(label, HPos.CENTER);
		}
		
		//Write info for each row
		for(int r = 1 ; r < j+1; r++) {
			//Create the hint boxes for each row on the left side of the puzzle
			String t = "";
			for(int p = 0; p < rowInfoList.get(r-1).size(); p++) {
				t = t + rowInfoList.get(r-1).get(p) + " ";
			}
			Label label = new Label(t);
			label.setTextFill(Color.WHITE);
			grid.add(label, 0, r);
			GridPane.setHalignment(label, HPos.CENTER);
			
			//For each grid square
			for(int c = 1; c < i+1; c++) {
				//Create the button,format size,and set default to be white
				Button b = new Button();
				b.setMinWidth(500/i);
				b.setMinHeight(500/j);
	    		b.setStyle("-fx-border-color:#010101;-fx-background-color:#FEFEFE;");
	    		
	    		//get current coordinates
				int row = r -1;
				int column = c - 1;
				
				//When button is pressed
				b.setOnAction(new EventHandler<ActionEvent>() {
				    @Override public void handle(ActionEvent e) {
				    	//If current button is white
				    	if(boardList.get(row).get(column) == 0) {
				    		//Change the button to black and remove labels
				    		b.setStyle("-fx-border-color:#010101;-fx-background-color:#010101;");
				    		b.setText("");
				    		
				    		//Update the board and compare to master
				    		boardList.get(row).set(column, 1);
				    		compare();
				    	}else {
				    		if(boardList.get(row).get(column) == 1) {
					    		//Change the button to white and remove labels
					    		b.setStyle("-fx-border-color:#010101;-fx-background-color:#FEFEFE;");
					    		b.setText("X");
					    		
					    		//Update the board and compare to master
					    		boardList.get(row).set(column, 2);
					    		compare();
				    		}else {
				    			if(boardList.get(row).get(column) == 2) {
				    				//remove labels
						    		b.setText("");
						    		
						    		//Update the board and compare to master
						    		boardList.get(row).set(column, 0);
						    		compare();
				    			}
				    		}
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
				if(masterList.get(r).get(c) != boardList.get(r).get(c)) {
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
			      
			//Create a scene with the layout
			Scene scene1= new Scene(layout, 300, 250);
			      
			//Show the popup
			popupwindow.setScene(scene1);   
			popupwindow.showAndWait();
		}
	}
	
	private void build(){
		masterList.add(new ArrayList<Integer>());
		masterList.add(new ArrayList<Integer>());
		masterList.add(new ArrayList<Integer>());
		masterList.add(new ArrayList<Integer>());
		masterList.add(new ArrayList<Integer>());
		
		rowInfoList.add(new ArrayList<Integer>());
		rowInfoList.add(new ArrayList<Integer>());
		rowInfoList.add(new ArrayList<Integer>());
		rowInfoList.add(new ArrayList<Integer>());
		rowInfoList.add(new ArrayList<Integer>());
		
		colInfoList.add(new ArrayList<Integer>());
		colInfoList.add(new ArrayList<Integer>());
		colInfoList.add(new ArrayList<Integer>());
		colInfoList.add(new ArrayList<Integer>());
		colInfoList.add(new ArrayList<Integer>());
		
		masterList.get(0).add(0);
		masterList.get(0).add(0);
		masterList.get(0).add(1);
		masterList.get(0).add(0);
		masterList.get(0).add(0);
		
		masterList.get(1).add(0);
		masterList.get(1).add(0);
		masterList.get(1).add(0);
		masterList.get(1).add(0);
		masterList.get(1).add(0);
		
		masterList.get(2).add(1);
		masterList.get(2).add(0);
		masterList.get(2).add(1);
		masterList.get(2).add(0);
		masterList.get(2).add(1);
		
		masterList.get(3).add(0);
		masterList.get(3).add(0);
		masterList.get(3).add(0);
		masterList.get(3).add(0);
		masterList.get(3).add(0);
		
		masterList.get(4).add(0);
		masterList.get(4).add(0);
		masterList.get(4).add(1);
		masterList.get(4).add(0);
		masterList.get(4).add(0);
		
		rowInfoList.get(0).add(1);
		
		rowInfoList.get(2).add(1);
		rowInfoList.get(2).add(1);
		rowInfoList.get(2).add(1);
		
		rowInfoList.get(4).add(1);
		
		colInfoList.get(0).add(1);
		
		colInfoList.get(2).add(1);
		colInfoList.get(2).add(1);
		colInfoList.get(2).add(1);
		
		colInfoList.get(4).add(1);
		
		for(int i = 0; i < masterRow ; i++) {
			boardList.add(new ArrayList<Integer>());
			for(int j = 0; j < masterCol ; j++) {
				boardList.get(i).add(0);
			}
		}
	}
}
