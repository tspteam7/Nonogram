import java.sql.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class Login extends Application {

	@Override
	public void start(Stage primaryStage) {
		
		//Panes
		BorderPane stack = new BorderPane();	//The top level pane
		GridPane top = new GridPane();			//The top of the Borderpane
		GridPane center = new GridPane(); 		//The center of the Borderpane
		GridPane btnusrpass = new GridPane();   //Within center, holds the buttons and userPass panes
		HBox buttons = new HBox(8);				//Holds the buttons
		VBox userPass = new VBox(8);			//Holds the username and password fields
		
		//Sets the spacing between elements
		btnusrpass.setVgap(8);					
		center.setVgap(8);
		top.setVgap(5);
		
		//Buttons
		Button btnLogin = new Button();					//Login button
		Button btnCreateAcc = new Button();				//Create account button
		btnLogin.setText("   Log In   ");				//text displayed on the button
		btnCreateAcc.setText("   Create Account   ");	//text displayed on the button
		
		//TextFields for username and password
		TextField username = new TextField();
		username.setPromptText("Username");				//username field prompt
		PasswordField password = new PasswordField();	
		password.setPromptText("Password");				//password field prompt

		//Title Text
		Text title = new Text();
		title.setText("Nonogram:\nA Logic Puzzle Game");			//What the title displays
		title.setFont(Font.font("System", FontWeight.BOLD, 30));	//Bolding the title and making it bigger
		title.setStyle("-fx-fill: #551400");						//Assign the color of the title
		title.setTextAlignment(TextAlignment.CENTER);
		
		//Error Text
		Text invalidLogin = new Text();								
		invalidLogin.setText("Invalid login information");			//What invalidLogin displays initially
		invalidLogin.setStyle("-fx-fill: #1c1207");							//Change the color of invalidLogin to red
		invalidLogin.setFont(Font.font("System", 18));							//Font size
		invalidLogin.setVisible(false);								//Only display when there is an error
		
		//Adding the elements together
		top.add( title, 0, 7);						//Title goes into the top of borderpane
		top.setAlignment(Pos.CENTER);				//And gets centered
		
		userPass.getChildren().add(username);		//userPass gets the username and password fields
		userPass.getChildren().add(password);
		userPass.setAlignment(Pos.CENTER);			//And centers them
		
		buttons.getChildren().add(btnLogin);		//buttons gets both buttons
		buttons.getChildren().add(btnCreateAcc);
		
		btnusrpass.add(userPass, 0, 0);				//the buttons and userPass are combined
		btnusrpass.add(buttons, 0, 1);
		btnusrpass.setAlignment(Pos.CENTER);		//And centered
		
		center.add(invalidLogin, 0, 0);				//The error message, buttons, username, and password
		center.add(btnusrpass, 0, 1);				//Are put into the center of the borderpane
		center.setAlignment(Pos.CENTER);			//And centered
		
		stack.setTop(top);							//top is set to the top borderpane
		stack.setCenter(center);					//center is set to the center borderpane
		
		//Setting the scene and stage
		Scene scene = new Scene(stack, 600, 400 );	//scene is created using the borderpane
		scene.getStylesheets().add("LoginCSS.css");	//scene uses "LoginCSS.css" to color the display
		
		primaryStage.setScene(scene);				//stage is set to scene
		primaryStage.setTitle("Login");				//title of the stage is set to "Login"
		primaryStage.setResizable(false);			//the stage is not resizable
		primaryStage.show();						//the stage is displayed
		
		//button event to log in
		btnLogin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	Connection conn = null;
            	PreparedStatement stmt = null;
        		ResultSet rs = null;
            	try {
        			conn = DriverManager.getConnection(					
        					"jdbc:mysql://classdb.it.mtu.edu/sjogden",
        					"sjogden",
        					"password");
        			stmt = conn.prepareStatement("SELECT password FROM NonogramUser WHERE username = ?");
        			stmt.setString(1, username.getText());
        			rs = stmt.executeQuery();
        			if(!rs.next()) {
        				invalidLogin.setText("Invalid login information");
        				invalidLogin.setVisible(true);
        				password.clear();
        				username.clear();
        			}
        			else {
        				rs.first();
        				if(password.getText().equals(rs.getString("password"))) {
        					//NonogramGUI n = new NonogramGUI();
        					Menu n = new Menu(username.getText()); // Testing opening the menu
        					n.start(primaryStage);
        				} else {
        					invalidLogin.setText("Invalid login information");
        					invalidLogin.setVisible(true);
        					password.clear();
        				}
        			}	
        		} catch (SQLException e) {
        			System.out.println(e.getMessage());
        			e.printStackTrace();
        		} 
            }
        });
		
		//keyboard event to login when 'enter' is pressed
		scene.setOnKeyPressed( ke -> {
			KeyCode keyCode = ke.getCode();
			if( keyCode.equals(KeyCode.ENTER)) {
				Connection conn = null;
            	PreparedStatement stmt = null;
        		ResultSet rs = null;
            	try {
        			conn = DriverManager.getConnection(
        					"jdbc:mysql://classdb.it.mtu.edu/sjogden",
        					"sjogden",
        					"password");
        			stmt = conn.prepareStatement("SELECT password FROM NonogramUser WHERE username = ?");
        			stmt.setString(1, username.getText());
        			rs = stmt.executeQuery();
        			if(!rs.next() ) {
        				invalidLogin.setText("Invalid login information");
        				invalidLogin.setVisible(true);
        				password.clear();
        			}
        			else {
        				rs.first();
        				if( password.getText().equals(rs.getString("password"))) {
        				  Menu n = new Menu(username.getText());
        				  n.start(primaryStage);
        				} else {
        					invalidLogin.setText("Invalid login information");
        					invalidLogin.setVisible(true);
        					password.clear();
        				}
        			}
        		} catch (SQLException e) {
        			System.out.println(e.getMessage());
        			e.printStackTrace();
        		} 
			}
		});

		//button event to create account
		btnCreateAcc.setOnAction( new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				Connection conn = null;
            	PreparedStatement stmt = null;
        		ResultSet rs = null;
            	try {
        			conn = DriverManager.getConnection(
        					"jdbc:mysql://classdb.it.mtu.edu/sjogden",
        					"sjogden",
        					"password");
        			stmt = conn.prepareStatement("SELECT password FROM NonogramUser WHERE username = ?");
        			stmt.setString(1, username.getText());
        			rs = stmt.executeQuery();
        			if( rs.next() || password.getText().equals("") ) { //If there is an account already or the password field is blank...
        				invalidLogin.setText("Username is taken or password is empty");
        				invalidLogin.setVisible(true);
        				password.clear();
        				username.clear();
        			}
        			else {
        				stmt = conn.prepareStatement("INSERT into NonogramUser (username, password, admin) values (?, ?, ?)");
        				stmt.setString(1, username.getText());
        				stmt.setString(2, password.getText());
        				stmt.setBoolean(3, false);
        				stmt.execute();
        				invalidLogin.setText("Account " + username.getText() + " created");
        				invalidLogin.setVisible(true);
        			}
        		} catch (SQLException e) {
        			invalidLogin.setText("Username and password must be less than 20 characters");
        			invalidLogin.setVisible(true);
        			password.clear();
        			username.clear();
        			System.out.println(e.getMessage());
        			e.printStackTrace();
        		}
			}
		});
	}

	public static void main(String[] args) {
		launch(args);
	}
}
