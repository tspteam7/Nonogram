import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

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
import javafx.scene.layout.BorderPane;
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

public class PuzzleCreator extends Application{

	//Create string to store username
	String username;
    
    //Create the GridPane (must be global for use in listeners)
    private GridPane gridPane = null;
    
    private Button curColorBut = null;
    
    //Create public variable keeping track of number of rows and columns of the puzzle
    private int masterRow = 5;
    private int masterCol = 5;
    
    //Create array to hold colors   white    black    x-ed     blue     green    red      lime     brown    aqua     purple
    private String[] colorIndex = {"FEFEFE","010101","000000","0000FF","008000","FF0000","00FF00","A52A2A","00FFFF","800080"};
    
    //create variable to track current color
    private int curColor = 1;
    
    //If this puzzle is from tutorial
    boolean fromTutorial = false;
    
    //The board being used on the game, default is all 0
    private ArrayList<ArrayList<Integer>> boardList = new ArrayList<ArrayList<Integer>>();
    
    public PuzzleCreator(String user) {
    	username = user;
    }
    
	@Override
	public void start(Stage stage) throws Exception {
		//Make the root to hold everything
        StackPane root = new StackPane();
        
        //Title
        Text title = new Text();
        title.setText("Puzzle Creator");
        title.setStyle("-fx-fill: #1c1207");
        title.setFont(Font.font ("System", 100));
        title.setTextAlignment(TextAlignment.CENTER);
    	
        // Create the width and height fields
        VBox dimensionsBox = new VBox();
        TextField width = new TextField("5") {
        	@Override public void replaceText(int start, int end, String text) {
        		if (text.matches("[0-9]*"))
        			super.replaceText(start, end, text);
        	}
        	@Override public void replaceSelection(String text) {
        		if (text.matches("[0-9]*"))
        			super.replaceSelection(text);
        	}
        };
        width.textProperty().addListener((observable, oldValue, newValue) -> {
        	if (newValue.length() > 0) {
        		int w = Integer.parseInt(newValue);
        		if (w > 0 && w < 50) {
        			masterCol = w;
        			root.getChildren().remove(gridPane);
            		setInfo();
            		gridPane = buildPuzzle(masterCol,masterRow);
            		root.getChildren().add(gridPane);
            		gridPane.setMaxHeight(500);
                	gridPane.setMaxWidth(500);
        		}
        	}
        });
        TextField height = new TextField("5") {
        	@Override public void replaceText(int start, int end, String text) {
        		if (text.matches("[0-9]*"))
        			super.replaceText(start, end, text);
        	}
        	@Override public void replaceSelection(String text) {
        		if (text.matches("[0-9]*"))
        			super.replaceSelection(text);
        	}
        };
        height.textProperty().addListener((observable, oldValue, newValue) -> {
        	if (newValue.length() > 0) {
        		int h = Integer.parseInt(newValue);
        		if (h > 0 && h < 50) {
        			masterRow = h;
        			root.getChildren().remove(gridPane);
        			setInfo();
        			gridPane = buildPuzzle(masterCol,masterRow);
        			root.getChildren().add(gridPane);
        			gridPane.setMaxHeight(500);
                	gridPane.setMaxWidth(500);
        		}
        	}
        });
        Label widLabel = new Label("Width:");
        Label heiLabel = new Label("Height:");
        widLabel.setTextFill(Color.rgb(28,191,107));
        heiLabel.setTextFill(Color.rgb(28,191,107));
        dimensionsBox.getChildren().addAll(widLabel, width, heiLabel, height);
        dimensionsBox.setMaxSize(100, 100);
        dimensionsBox.setSpacing(5);
        
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
        
        //Set the puzzle values
        setInfo();
        
        //Create the grid the game is played on
        gridPane = buildPuzzle(masterCol,masterRow);
        gridPane.setMaxHeight(500);
        gridPane.setMaxWidth(500);
        
        //Create a close button
        Button close = new Button("Back");
        close.setMinHeight(50);
        close.setMinWidth(70);
        close.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	Menu menu = new Menu(username);
		    	menu.start(new Stage());
		    	stage.close();
		    }
		});
        
      //Create a Create button
        Button create = new Button("Upload Puzzle");
        create.setMinHeight(50);
        create.setMinWidth(70);
        create.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	createPuzzle();
		    }
		});
      
        
      //Create a Upload Image Button
      	Button browse = new Button("Browse");
      	browse.setMinHeight(70);
      	browse.setMaxHeight(50);
      	browse.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	JFileChooser fileSelector = new JFileChooser();

		    	FileNameExtensionFilter yaboi = new FileNameExtensionFilter("jpg", "png", "jpeg");
		    	fileSelector.setFileFilter(yaboi);
		    	
				int returnValue = fileSelector.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File uhoh = fileSelector.getSelectedFile();
					PuzzleImageLoader pp = new PuzzleImageLoader(uhoh.getAbsolutePath());
					ArrayList<ArrayList<Integer>> mario = pp.pOutput();
					if(mario.size() <=40 && mario.get(0).size()<=40) {
						Puzzle p = new Puzzle();
						p.upload(pp.pOutput());
						
						//gets puzzle number in database
						GridPane root = new GridPane();
						BorderPane bord = new BorderPane();
						Connection conn = null;
				    	PreparedStatement stmt = null;
						ResultSet rs = null;
						int numPuzzles = 0;
						try {
							conn = DriverManager.getConnection(
									"jdbc:mysql://classdb.it.mtu.edu/sjogden",
									"sjogden",
									"password");
							stmt = conn.prepareStatement("SELECT COUNT(*) FROM Puzzles");
							rs = stmt.executeQuery();
							rs.first();
							numPuzzles = rs.getInt("COUNT(*)");
							
						} catch (SQLException e1) {
							//e1.printStackTrace();
						}
						
						
						
						//create popup stage
		    			Stage popupwindow=new Stage();
		    			//Set style
		    			popupwindow.initModality(Modality.APPLICATION_MODAL);      
		    			//Create the title
		    			Label label1= new Label("You're puzzle successfully uploaded!\n Go to Play Puzzles to play you're"
		    					+ " own puzzle!\nYour puzzle number is " + numPuzzles);
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
					}
					else {
						//create popup stage
		    			Stage popupwindowSize=new Stage();
		    			//Set style
		    			popupwindowSize.initModality(Modality.APPLICATION_MODAL);      
		    			//Create the title
		    			Label labelSize= new Label("Error: The picture you uploaded is too big!\nTry uploading a picture that is at less that\n40x40 pixels");
		    			//Create a close button the close the popup
		    			Button buttonSize= new Button("Close");   
		    			buttonSize.setOnAction(eee -> popupwindowSize.close());
		    			//Create layout format
		    			VBox layout= new VBox(10);    
		    			      
		    			//Add the label and button 
		    			layout.getChildren().addAll(labelSize, buttonSize);
		    			layout.setAlignment(Pos.CENTER);
		    			      
		    			//Create a scene with the layout
		    			Scene scene1= new Scene(layout, 300, 250);
		    			scene1.getStylesheets().add("LoginCSS.css");
		    			      
		    			//Show the popup
		    			popupwindowSize.setScene(scene1);   
		    			popupwindowSize.showAndWait();
					}
				}
		    }
		});
        
        //Set the alignment of title and grid
        StackPane.setAlignment(title, Pos.TOP_CENTER);
        StackPane.setAlignment(gridPane, Pos.CENTER);
        StackPane.setAlignment(dimensionsBox, Pos.CENTER_LEFT);
        StackPane.setAlignment(create, Pos.BOTTOM_RIGHT);
        StackPane.setAlignment(close, Pos.BOTTOM_LEFT);
        StackPane.setAlignment(colorPicker, Pos.CENTER_RIGHT);
        StackPane.setAlignment(curColorBox, Pos.TOP_RIGHT);
        StackPane.setAlignment(browse, Pos.BOTTOM_CENTER);
        //Add the title and grid to the root, then change the color of root
        root.getChildren().addAll(title,dimensionsBox,gridPane,close,create,colorPicker,curColorBox, browse);
        root.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        
        //Create a new scene with the root and show it
        Scene scene = new Scene(root, 1000, 1000);
        scene.getStylesheets().add("LoginCSS.css");
        stage.setScene(scene);
        stage.show();
		
        
      
        
	}
	
	
		
	
	private GridPane buildPuzzle(int i, int j) {
		GridPane grid = new GridPane();
		
		//Write info for each row
		for(int r = 0 ; r < j; r++) {
			//For each grid square
			for(int c = 0; c < i; c++) {
				//Create the button,format size,and set default to be white
				Button b = new Button();
				b.setMinWidth(Math.min(500/i, 500/j));
				b.setMinHeight(Math.min(500/i, 500/j));
				String color = colorIndex[boardList.get(r).get(c)];
				b.setStyle("-fx-border-color:#D3D3D3;-fx-background-color:#" + color + ";");
						    		
	    		//get current grid position
	    		final int row = r;
	    		final int col = c;
	    		
				//When button is pressed
				b.setOnAction(new EventHandler<ActionEvent>() {
				    @Override public void handle(ActionEvent e) {
				    	//If current button is white
				    	int curSquare = boardList.get(row).get(col);
				    	if(curSquare == 0 || curSquare == 1 || curSquare != curColor) {
				    		//Change the button to black and remove labels
				    		String color2 = colorIndex[curColor];
				    		b.setStyle("-fx-border-color:#D3D3D3;-fx-background-color:#" + color2 + ";");
				    		b.setText("");
				    		
				    		//Update the board and compare to master
				    		boardList.get(row).set(col, curColor);
				    		//compare();
				    	}else {
				    		b.setStyle("-fx-border-color:#D3D3D3;-fx-background-color:#FEFEFE;");
				    		boardList.get(row).set(col, 0);
				    	}
				    }
				});
				
				//Add the button to the grid
				grid.add(b, c, r);
			}
		}
		
		//Return the grid
        grid.setAlignment(Pos.CENTER);
		return grid;
	}
	
	private void setInfo() {
		while (boardList.size() < masterRow)
			boardList.add(new ArrayList<Integer>());
		while (boardList.size() > masterRow)
			boardList.remove(boardList.size()-1);
		for (ArrayList<Integer> row : boardList) {
			while (row.size() < masterCol)
				row.add(0);
			while (row.size() > masterCol)
				row.remove(row.size()-1);
		}
	}
	
	private void createPuzzle() {
		Puzzle puzzle = new Puzzle();
		//puzzle.setMaster(boardList);
		//puzzle.setX(masterCol);
		//puzzle.setY(masterRow);
		puzzle.upload(boardList);
		
		//create popup stage
		Stage popupwindow=new Stage();
	      
		//Set style
		popupwindow.initModality(Modality.APPLICATION_MODAL);      
		      
		//Create the title
		Label label1= new Label("Successfully uploaded puzzle.");
		      
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
