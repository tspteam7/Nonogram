/**
 * Main class for gui of nonogram app
 */




import java.util.ArrayList;
import java.util.Arrays;

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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class NonogramGUI extends Application {

    private Stage stage = null;
    private BorderPane borderPane = null;
    private int masterRow = 5;
    private int masterCol = 5;
    
    private int[][] rowInfo = {{1},{0},{1,1,1},{0},{1}};
    private int[][] colInfo = {{1},{0},{1,1,1},{0},{1}};
    
    private int[][] master = {
    		{0,0,1,0,0},
    		{0,0,0,0,0},
    		{1,0,1,0,1},
    		{0,0,0,0,0},
    		{0,0,1,0,0},
    		
    };
    private int[][] board = new int[masterRow][masterCol];
    
	
    @Override
    public void start(Stage stage) {
    	
    	//Make borderpane
        borderPane = new BorderPane();
        
        StackPane root = new StackPane();
        
        //Title
        Text title = new Text();
        title.setText("Nonogram");
        title.setFill(Color.rgb(28, 191, 107));
        title.setFont(Font.font ("Verdana", 100));
        title.setTextAlignment(TextAlignment.CENTER);
        
        GridPane gridPane = buildPuzzle(masterCol,masterRow);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setMaxHeight(500);
        gridPane.setMaxWidth(500);
    	
    	//Show the Page
        StackPane.setAlignment(title, Pos.TOP_CENTER);
        StackPane.setAlignment(gridPane, Pos.CENTER);
        root.getChildren().addAll(title,gridPane);
        root.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
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
		
		for (int c = 1 ; c < j+1; c++) {

			TextField t = new TextField();
			t.setDisable(true);
			t.setMinWidth((500/i) + 30);
			t.setMinHeight(500/j);
			t.setStyle("-fx-border-color:#010101;-fx-background-color:#FEFEFE;");
			grid.add(t, 0, c);
		}
		
		for(int r = 1 ; r < j+1; r++) {
			TextField t = new TextField();
			t.setDisable(true);
			t.setMinWidth(500/i);
			t.setMinHeight((500/j) + 30);
			t.setStyle("-fx-border-color:#010101;-fx-background-color:#FEFEFE;");
			grid.add(t, r, 0);
			
			for(int c = 1; c < i+1; c++) {
				Button b = new Button();
				b.setMinWidth(500/i);
				b.setMinHeight(500/j);
	    		b.setStyle("-fx-border-color:#010101;-fx-background-color:#FEFEFE;");
	    		
				int row = r;
				int column = c;
				b.setOnAction(new EventHandler<ActionEvent>() {
				    @Override public void handle(ActionEvent e) {
				    	if(b.getStyle().equals("-fx-border-color:#010101;-fx-background-color:#010101;")) {
				    		b.setStyle("-fx-border-color:#010101;-fx-background-color:#FEFEFE;");
				    		board[row-1][column-1] = 0;
				    		compare();
				    	}else {
				    		b.setStyle("-fx-border-color:#010101;-fx-background-color:#010101;");
				    		board[row-1][column-1] = 1;
				    		compare();
				    	}
				    }
				});
				grid.add(b, c, r);
			}
		}
		
		return grid;
	}

	public static void main(String[] args) {
        launch(args);
    }
	
	public void compare() {
		boolean equals = true;
		
		for(int r = 0 ; r < masterRow ; r++) {
			for(int c = 0 ; c < masterCol ; c++) {
				if(master[r][c] != board[r][c]) {
					equals = false;
				}
			}
		}
		
		if(equals) {
			Stage popupwindow=new Stage();
		      
			popupwindow.initModality(Modality.APPLICATION_MODAL);      
			      
			Label label1= new Label("You Win!!");
			      
			     
			Button button1= new Button("Close");   
			button1.setOnAction(e -> popupwindow.close());
			     
			VBox layout= new VBox(10);    
			      
			layout.getChildren().addAll(label1, button1);
			layout.setAlignment(Pos.CENTER);
			      
			Scene scene1= new Scene(layout, 300, 250);
			      
			popupwindow.setScene(scene1);   
			popupwindow.showAndWait();
		}
	}
}