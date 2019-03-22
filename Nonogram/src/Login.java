import java.sql.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
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
        		String user = username.getText();
        		String pass = password.getText();
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
        					Menu n = new Menu(); // Testing opening the menu
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
		
		//button event to create account
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}
