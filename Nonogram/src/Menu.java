import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
 
public class Menu extends Application {
	
	private String username;
	
	public Menu() {
		username = "";
	}
	
	public Menu(String user) {
		username = user;
	}
	
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
    	
    	//Panes
    	BorderPane 	main 	   	= new BorderPane(); //The main pane
    	GridPane	rightGrid	= new GridPane();	//The gridpane set to the right of the borderpane
    	GridPane 	centerGrid 	= new GridPane();	//The gridpane set to the center of the borderpane
    	GridPane 	bottomGrid 	= new GridPane();	//The gridpane set to the bottom of the borderpane
    	VBox 		textBox	   	= new VBox();		//The pane put into the center gridpane and holds the text
    	VBox 		buttonBox 	= new VBox(35);		//The pane put into the center gridpane and holds the buttons 
    	
    	//Sets the spacing between the elements
    	rightGrid.setVgap(60);
    	rightGrid.setHgap(30);
    	centerGrid.setVgap(30);
    	centerGrid.setHgap(40);
    	bottomGrid.setVgap(8);
    	bottomGrid.setHgap(8);
    	
    	//Buttons
    	Button selectBtn = new Button();			//The button that when clicked goes to the puzzle select GUI
    	selectBtn.setText("Play a Puzzle");			//The select button text
    	selectBtn.setPrefSize(125, 35);
        Button createBtn = new Button();			//The button that when clicked goes to the create puzzle GUI
        createBtn.setText("Create a new Puzzle");	//The create button text
        createBtn.setPrefSize(125, 35);
        Button tutorialBtn = new Button();			//The button that when clicked goes to the tutorial puzzles
        tutorialBtn.setText("Tutorial");			//The tutorial button text
        tutorialBtn.setPrefSize(125, 35);
        Button flaggedBtn = new Button();			//The button that when clicked shows all the bad puzzles
        flaggedBtn.setText("Flagged Puzzles");		//The flagged button text
        flaggedBtn.setPrefSize(125, 35);
        Button logout = new Button();				//The button that when clicked logs the user out of the system
        logout.setText("Logout");					//The logout button text
        
    	//Text
    	Text welcomeText = new Text();				//Text that displays Welcome
    	welcomeText.setText("Welcome");
    	welcomeText.setFont(Font.font("System", 24));
    	welcomeText.setStyle("-fx-fill: #1c1207");
    	Text userText = new Text();					//Text that displays the user's name
    	userText.setText("   " + username);
    	userText.setFont(Font.font("System", 30));
    	userText.setStyle("-fx-fill: #1c1207");
    	
    	//Adding the elements together
    	textBox.getChildren().add(welcomeText);		//Adds welcome and username to the same box
    	textBox.getChildren().add(userText);
    	
    	buttonBox.getChildren().add(selectBtn);		//Adds the buttons to the same box
    	buttonBox.getChildren().add(createBtn);
    	buttonBox.getChildren().add(tutorialBtn);
    	
    	if( username.equals("admin") ) {				//Isacc please implement this				
    		buttonBox.getChildren().add(flaggedBtn);				
    	}															

    	rightGrid.add(buttonBox, 0, 1);				//Adds the buttons to the rightGrid of the borderpane
    	rightGrid.addColumn(1, new Text("           "));	//Padding
    	
    	centerGrid.add(textBox, 1, 3);				//Adds the welcome and username to the centerGrid of the borderpane
    	
    	bottomGrid.add(logout, 1, 0);				//Adds the logout button to the bottomGrid of the borderpane
    	bottomGrid.add(new HBox(8), 1, 1);			//Padding
    	
    	main.setRight(rightGrid);					//sets the rightGrid to the right of the borderpane
    	main.setCenter(centerGrid);					//sets the centerGrid to the center of the borderpane
    	main.setBottom(bottomGrid);					//sets the bottomGrid to the bottom of the borderpane
    	
    	//Setting the scene and stage
    	Scene scene = new Scene(main, 600, 400);
    	scene.getStylesheets().add("LoginCSS.css");
    	
    	primaryStage.setScene(scene);
        primaryStage.setTitle("Nonogram");
        primaryStage.show();
        
        //Button that shows the select puzzle GUI
        selectBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
	        	ViewPuzzlesGUI open = new ViewPuzzlesGUI(username);
	        	open.start(new Stage());
	        	primaryStage.close();
			}
        });
        
        //Button that shows the tutorial GUI
        tutorialBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
	        	TuturiolGUI tutgui = new TuturiolGUI(username);
	        	try {
					tutgui.start(new Stage());
				} catch (Exception e) {
					e.printStackTrace();
				}
	        	primaryStage.close();
			}
        });
        
        //Button that shows the create puzzle GUI
        createBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
	        	PuzzleCreator pc = new PuzzleCreator(username);
	        	try {
					pc.start(new Stage());
				} catch (Exception e) {
					e.printStackTrace();
				}
	        	primaryStage.close();
			}
        });
        
        //method to go back to menu
        logout.setOnAction(e->{
        	
      		Login login = new Login();
      		login.start(new Stage());
      		primaryStage.close();
      	
        });
        
        
    }
}
