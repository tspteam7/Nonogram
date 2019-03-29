import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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
    	Scene menuScene;
    	Scene createScene;
    	
    	VBox createPane = new VBox();
        Button createSceneButton = new Button("Main Menu");
        createPane.getChildren().add(createSceneButton);
        createScene = new Scene(createPane, 300, 250);
    	
        primaryStage.setTitle("Nonogram");
        Button selectBtn = new Button();
        Button createBtn = new Button();
        Button tutorialBtn = new Button();
        selectBtn.setText("Play a Puzzle");
        createBtn.setText("Create a new Puzzle");
        tutorialBtn.setText("Tutorial");
        
        StackPane menuPane = new StackPane();
        menuPane.setAlignment(Pos.CENTER);
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setFillWidth(true);
        vbox.setPadding(new Insets(5.0));
        
        menuPane.getChildren().add(vbox);
        vbox.getChildren().add(selectBtn);
        vbox.getChildren().add(createBtn);
        vbox.getChildren().add(tutorialBtn);
        menuScene = new Scene(menuPane, 300, 250);

        selectBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
	        	NonogramGUI gui = new NonogramGUI();
	        	gui.start(new Stage());
	        	primaryStage.close();
			}
        });
        
        tutorialBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
	        	TuturiolGUI tutgui = new TuturiolGUI();
	        	try {
					tutgui.start(new Stage());
				} catch (Exception e) {
					e.printStackTrace();
				}
	        	primaryStage.close();
			}
        });
        
        createBtn.setOnAction(e -> primaryStage.setScene(createScene));
        createSceneButton.setOnAction(e -> primaryStage.setScene(menuScene));
        
        primaryStage.setScene(menuScene);
        primaryStage.show();
        
    }
}
