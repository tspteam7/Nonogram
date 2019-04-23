
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
    private int whereTo = 0;
    
    //Username
    private String username = "";
    private int id = 0;
    
    //Arrays to store the data about how pixels lay out on the grid
    private ArrayList<ArrayList<Integer>> rowInfoList = new ArrayList<ArrayList<Integer>>();
    private ArrayList<ArrayList<Integer>> colInfoList = new ArrayList<ArrayList<Integer>>();
    
    //Create array to hold colors   white    black    x-ed     blue     green    red      lime     brown    aqua     purple
    private String[] colorIndex = {"FEFEFE","010101","000000","0000FF","008000","FF0000","00FF00","A52A2A","00FFFF","800080"};
    
    //create variable to track current color
    private int curColor = 1;
    private Button curColorBut = null;
    
    //The master check array
    private ArrayList<ArrayList<Integer>> masterList = new ArrayList<ArrayList<Integer>>();
    
    //The board being used on the game, default is all 0
    private ArrayList<ArrayList<Integer>> boardList = new ArrayList<ArrayList<Integer>>();
    
    GridPane gridPane; 
    
	
    /**
     * Constructor that passes in a  username, and the username is only used if it is a player 
     * 
     * @param s is the username
     */
    public NonogramGUI(String s, int i) {
		username = s;
		id = i;
	}

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
        title.setFill(Color.rgb(0, 0, 0));
        title.setFont(Font.font ("Verdana", 100));
        title.setTextAlignment(TextAlignment.CENTER);
        
        //Create the grid the game is played on
        gridPane = buildPuzzle(masterCol,masterRow);
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
		    	switch(whereTo) {
		    	case 0:
		        	TuturiolGUI tutgui = new TuturiolGUI(username);
		        	try {
						tutgui.start(new Stage());
					} catch (Exception a) {
						a.printStackTrace();
					}
		        	break;
		        	
		    	case 1:
		    		Puzzle p = new Puzzle(id,username);
		    		p.update(boardList, id, username);
		    		
		    		/*ViewPuzzlesGUI viewGUI = new ViewPuzzlesGUI(username);
		        	try {
						viewGUI.start(new Stage());
					} catch (Exception a) {
						a.printStackTrace();
					}*/
		    	case 2:
		    		ViewPuzzlesGUI viewGUI = new ViewPuzzlesGUI(username);
		        	try {
						viewGUI.start(new Stage());
					} catch (Exception a) {
						a.printStackTrace();
					}
		    		break;
		    		
		    	}
		    }
		    
		});
        
        //Create a flag button
        Button flag = new Button("Flag");
        flag.setMinHeight(50);
        flag.setMinWidth(70);
        flag.setOnAction(e-> {
        	Connection conn = null;
        	PreparedStatement stmt = null;
    		try {
    			conn = DriverManager.getConnection(
    					"jdbc:mysql://classdb.it.mtu.edu/sjogden",
    					"sjogden",
    					"password");
    			stmt = conn.prepareStatement("UPDATE Puzzles SET flagged = true WHERE puzzle_id = ?");
    			stmt.setInt(1, id);
    			stmt.execute();
    			//disable all buttons
    			disable();
    			
    			//create popup stage
    			Stage popupwindow=new Stage();
    		      
    			//Set style
    			popupwindow.initModality(Modality.APPLICATION_MODAL);      
    			      
    			//Create the title
    			Label label1= new Label("An admin will take a look at this \npuzzle and take the necessary action.\nThank you for your help!");
    			      
    			//Create a close button the close the popup
    			Button button1= new Button("Close");   
    			button1.setOnAction(eee -> popupwindow.close());
    			     
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
    			
    		} catch (SQLException ee) {
    			System.out.println(ee.getMessage());
    			ee.printStackTrace();
    		} 
		});
        
        //Create the color picker
        VBox colorPicker = new VBox();
        colorPicker.setMaxSize(50, 500);
        for (int butCol = 1; butCol < 10; butCol++) {
        	if(butCol == 2)
        		continue;
        	final int buttonColor = butCol;
        	Button pickBut = new Button();
        	pickBut.setMinWidth(Math.min(50, 50));
			pickBut.setMinHeight(Math.min(50, 50));
			pickBut.setStyle("-fx-border-color:#D3D3D3;-fx-background-color:#" + colorIndex[butCol] + ";");
			pickBut.setOnAction(new EventHandler<ActionEvent>() {
			    @Override public void handle(ActionEvent e) {
			    	curColor = buttonColor;
			    	curColorBut.setStyle("-fx-border-color:#D3D3D3;-fx-background-color:#" + colorIndex[curColor] + ";");
			    }
			});
        	colorPicker.getChildren().add(pickBut);
        }

        //create current color indicator
        VBox curColorBox = new VBox();
        curColorBox.setMaxSize(100, 110);
        Label curColorLab = new Label("Current Color");
        curColorLab.setTextFill(Color.rgb(28, 191, 107));
        curColorBut = new Button();
        curColorBut.setMinSize(100,100);
        curColorBut.setStyle("-fx-border-color:#D3D3D3;-fx-background-color:#" + colorIndex[curColor] + ";");
        curColorBox.getChildren().addAll(curColorLab,curColorBut);
        
        //Set the alignment of title and grid
        StackPane.setAlignment(title, Pos.TOP_CENTER);
        StackPane.setAlignment(gridPane, Pos.CENTER);
        StackPane.setAlignment(close, Pos.BOTTOM_LEFT);
        StackPane.setAlignment(flag, Pos.BOTTOM_RIGHT);
        StackPane.setAlignment(colorPicker, Pos.CENTER_RIGHT);
        StackPane.setAlignment(curColorBox, Pos.TOP_RIGHT);
        
        //Add the title and grid to the root, then change the color of root
        root.getChildren().addAll(title,gridPane,close,flag,colorPicker,curColorBox);
        root.setBackground(new Background(new BackgroundFill(Color.ORANGE, CornerRadii.EMPTY, Insets.EMPTY)));
        
        //Create a new scene with the root and show it
        Scene scene = new Scene(root, 1000, 1000);
        stage.setScene(scene);
        stage.show();
        
        compare();
	}

	/**
	 * Set the info to be used when making the puzzles
	 * 
	 * @param m is the master board
	 * @param r is the row info
	 * @param c is the column info
	 * @param wb is the working board if it exists
	 * @param isTutorial is set true if this is to return to the tutorial screen
	 */
    public void setInfo(ArrayList<ArrayList<Integer>> m, ArrayList<ArrayList<Integer>> r, ArrayList<ArrayList<Integer>> c, ArrayList<ArrayList<Integer>> wb, int isTutorial) {
    	//Set the info
    	masterList = m;
    	rowInfoList = r;
    	colInfoList = c;
    	whereTo = isTutorial;
    	masterRow = m.size();
    	masterCol = m.get(0).size();
    	
    	//If tutorial is not true we want to set the working board
    	if (whereTo == 1) {
    		boardList = wb;
    	}else {
    		//else initailize the working board
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
		int squareSize = Math.min(500/i, 500/j);
		
		//Create the hint boxes for each Column at the top
		for (int c = 1 ; c < i+1; c++) {
			String t = "";
			VBox vbox = new VBox();
			for(int p = 0; p < colInfoList.get(c-1).size(); p+=2) {
				t = "" + colInfoList.get(c-1).get(p);
				Label label = new Label(t);
				String labelColor = "#" + colorIndex[colInfoList.get(c-1).get(p+1)];
				label.setTextFill(hexToRGB(labelColor));
				label.setFont(Font.font ("Verdana", Math.min(squareSize*.9, 20)));
				vbox.getChildren().add(label);
			}
			
			vbox.setAlignment(Pos.BOTTOM_CENTER);
			vbox.setStyle("-fx-border-color:#D3D3D3;-fx-background-color:#FEFEFE;");
			grid.add(vbox, c, 0);
			GridPane.setHalignment(vbox, HPos.CENTER);
			GridPane.setValignment(vbox, VPos.BOTTOM);
		}
		
		//Write info for each row
		for(int r = 1 ; r < j+1; r++) {
			//Create the hint boxes for each row on the left side of the puzzle
			String t = "";
			HBox hbox = new HBox();
			for(int p = 0; p < rowInfoList.get(r-1).size(); p+=2) {
				t = "" + rowInfoList.get(r-1).get(p);
				Label label = new Label(t);
				String labelColor = "#" + colorIndex[rowInfoList.get(r-1).get(p+1)];
				label.setTextFill(hexToRGB(labelColor));
				label.setFont(Font.font ("Verdana", Math.min(squareSize*.9, 20)));
				hbox.getChildren().add(label);
				
			}
			
			hbox.setAlignment(Pos.CENTER_RIGHT);
			hbox.setSpacing(5);
			hbox.setStyle("-fx-border-color:#D3D3D3;-fx-background-color:#FEFEFE;");
			grid.add(hbox, 0, r);
			GridPane.setHalignment(hbox, HPos.RIGHT);
			
			
			//For each grid square
			for(int c = 1; c < i+1; c++) {
				//Create the button,format size,and set default to be white
				Button b = new Button();
				b.setMinWidth(squareSize);
				b.setMinHeight(squareSize);
				b.setFont(Font.font ("Verdana", squareSize*0.8));
				b.setPadding(Insets.EMPTY);
	    		
	    		switch(boardList.get(r-1).get(c-1)) {
	    		case 0:
	    			b.setStyle("-fx-border-color:#D3D3D3;-fx-background-color:#FEFEFE;");
	    			b.setText("");
	    			break;
	    		case 2:
	    			b.setStyle("-fx-border-color:#D3D3D3;-fx-background-color:#FEFEFE;");
	    			b.setText("X");
	    			break;
	    		default:
	    			String color2 = colorIndex[boardList.get(r-1).get(c-1)];
		    		b.setStyle("-fx-border-color:#D3D3D3;-fx-background-color:#" + color2 + ";");
		    		b.setText("");
			    	break;
	    		}
	    		
	    		//get current coordinates
				int row = r -1;
				int col = c - 1;
				
				//When button is pressed
				b.setOnAction(new EventHandler<ActionEvent>() {
				    @Override public void handle(ActionEvent e) {
				    	//If current button is white
				    	int curSquare = boardList.get(row).get(col);
				    	if (curSquare == 2){
				    		b.setStyle("-fx-border-color:#D3D3D3;-fx-background-color:#FEFEFE;");
				    		boardList.get(row).set(col, 0);
				    		b.setText("");
				    	} else if(curSquare == 0 || curSquare != curColor) {
				    		//Change the button to current color and remove labels
				    		String color2 = colorIndex[curColor];
				    		b.setStyle("-fx-border-color:#D3D3D3;-fx-background-color:#" + color2 + ";");
				    		b.setText("");
				    		boardList.get(row).set(col, curColor);
				    	} else if (curSquare == curColor){
				    		b.setStyle("-fx-border-color:#D3D3D3;-fx-background-color:#FEFEFE;");
				    		boardList.get(row).set(col, 2);
				    		b.setText("X");
				    	} 
				    	
				    	//Update the board and compare to master
				    	compare();
				    }
				});
				/*b.setOnAction(new EventHandler<ActionEvent>() {
				    @Override public void handle(ActionEvent e) {
				    	//If current button is white
				    	if(boardList.get(row).get(column) == 0) {
				    		//Change the button to black and remove labels
				    		b.setStyle("-fx-border-color:#D3D3D3;-fx-background-color:#010101;");
				    		b.setText("");
				    		
				    		//Update the board and compare to master
				    		boardList.get(row).set(column, 1);
				    		compare();
				    	}else {
				    		if(boardList.get(row).get(column) == 1) {
					    		//Change the button to white and remove labels
					    		b.setStyle("-fx-border-color:#D3D3D3;-fx-background-color:#FEFEFE;");
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
				});*/
				
				//Add the button to the grid
				grid.add(b, c, r);
			}
		}
		
		//Return the grid
		return grid;
	}
	
	/**
	 * Compares the master board vs the player grid and sends a popup if
	 * the player has won
	 */
	public void compare() {
		boolean equals = true;
		
		//Compare each value in the array to see if they are equal
		out:
		for(int r = 0 ; r < masterRow ; r++) {
			for(int c = 0 ; c < masterCol ; c++) {
				if(masterList.get(r).get(c) != boardList.get(r).get(c) && !(masterList.get(r).get(c) == 0 && boardList.get(r).get(c) == 2) && !(masterList.get(r).get(c) == 2 && boardList.get(r).get(c) == 0)) {
					equals = false;
					break out;
				}
			}
		}
		
		
		//If they are equal send a popup
		if(equals) {
			
			//disable all buttons
			disable();
			
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

	private void disable() {
		Button b;
		for(Node n : gridPane.getChildren()) {
			try{
				b = (Button)n;
				if (b != null)
					b.setOnAction(e-> {});
			} catch (ClassCastException ex){
				
			}
			
		}
		
		
	}	
	
	public static Color hexToRGB(String colorStr) {
	    return Color.rgb(
	            Integer.valueOf( colorStr.substring( 1, 3 ), 16 ),
	            Integer.valueOf( colorStr.substring( 3, 5 ), 16 ),
	            Integer.valueOf( colorStr.substring( 5, 7 ), 16 ) );
	}
	
}
