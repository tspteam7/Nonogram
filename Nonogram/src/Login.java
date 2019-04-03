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
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Login extends Application {

	@Override
	public void start(Stage primaryStage) {
		
		//set the stage
		BorderPane stack = new BorderPane();
		GridPane root = new GridPane();
		
		root.setVgap(8);
		
		Button btnLogin = new Button();
		Button btnCreateAcc = new Button();
		btnLogin.setText("Log In");
		btnCreateAcc.setText("Create Account");
		
		TextField username = new TextField();
		username.setPromptText("Username");
		PasswordField password = new PasswordField();
		password.setPromptText("Password");
		
		Text invalidLogin = new Text();
		invalidLogin.setText("Invalid login information");
		invalidLogin.setVisible(false);
		
		Text invalidNewAcc = new Text();
		invalidNewAcc.setText("This username is taken or password is empty");
		invalidNewAcc.setVisible(false);
		
		HBox buttons = new HBox(8);
		VBox userPass = new VBox(8);
		
		userPass.getChildren().add(username);
		userPass.getChildren().add(password);
		buttons.getChildren().add(btnLogin);
		buttons.getChildren().add(btnCreateAcc);
		
		root.add(userPass, 0, 0);
		root.add(buttons, 0, 1);
		root.add(invalidLogin, 0, 2);
		root.setAlignment(Pos.CENTER);
		
		
		
		stack.setCenter(root);
		Scene scene = new Scene(stack, 400, 300);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Login");
		primaryStage.show();
		
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
        				invalidLogin.setVisible(true);
        			}
        			else {
        				rs.first();
        				if(password.getText().equals(rs.getString("password"))) {
        					//NonogramGUI n = new NonogramGUI();
        					Menu n = new Menu(username.getText()); // Testing opening the menu
        					n.start(new Stage());
        					primaryStage.close();
        				}
        			}
        			
        		} catch (SQLException e) {
        			System.out.println(e.getMessage());
        			e.printStackTrace();
        		} 
            }
        });
		
		// keyboard event to login when 'enter' is pressed
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
        				invalidLogin.setVisible(true);
        			}
        			else {
        				Menu n = new Menu(username.getText()); // Testing opening the menu
        				n.start(new Stage());
        				primaryStage.close();
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
        			if(rs.next() || password.getText() == "") { //If there is an account already or the password field is blank...
        				invalidNewAcc.setVisible(true);
        			}
        			else {
        				stmt = conn.prepareStatement("INSERT into NonogramUser (username, password, admin) values (?, ?, ?)");
        				stmt.setString(1, username.getText());
        				stmt.setString(2, password.getText());
        				stmt.setBoolean(3, false);
        				stmt.execute();
        				
        				primaryStage.close();
        				primaryStage.show();
        			}
        			
        		} catch (SQLException e) {
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
