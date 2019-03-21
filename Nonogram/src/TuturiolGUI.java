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

public class TuturiolGUI extends Application{
	Stage tuturiolStage; 
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		//Make the root to hold everything
    	tuturiolStage = new Stage();
        StackPane root = new StackPane();
        
        //Title
        Text title = new Text();
        title.setText("Tutorial");
        title.setFill(Color.rgb(28, 191, 107));
        title.setFont(Font.font ("Verdana", 100));
        title.setTextAlignment(TextAlignment.CENTER);
        
        //Create the grid the game is played on
        GridPane gridPane = new GridPane();
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
		    }
		});
        
        for(int i = 1; i <= 7 ; i++) {
        	Button b = new Button("Tutorial " + i);
            b.setMinHeight(50);
            b.setMinWidth(70);
            b.setOnAction(new EventHandler<ActionEvent>() {
    		    @Override public void handle(ActionEvent e) {
    		    	NonogramGUI game = new NonogramGUI();
    		    }
    		});
            gridPane.add(b, 0, i-1);
        }
        
        //Set the alignment of title and grid
        StackPane.setAlignment(title, Pos.TOP_CENTER);
        StackPane.setAlignment(gridPane, Pos.CENTER);
        StackPane.setAlignment(close, Pos.BOTTOM_LEFT);
        
        //Add the title and grid to the root, then change the color of root
        root.getChildren().addAll(title,gridPane,close);
        root.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        
        //Create a new scene with the root and show it
        Scene scene = new Scene(root, 1000, 1000);
        tuturiolStage .setScene(scene);
        tuturiolStage .show();
	}

}
