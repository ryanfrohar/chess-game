/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assign4.chess;
import java.util.List;
import javafx.scene.image.Image;
import javafx.application.Application;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.ArrayList;


/**
 *
 * @author ryanfrohar
 */
public class Assign4 extends Application implements ListChangeListener {
    private ChessBoard board;
    private Button whiteTakenSquare[] = new Button[16] ;
    private Button blackTakenSquare[] = new Button[16] ;
    Pane right= new VBox();
    Pane left = new VBox();
    int leftIndex;
    int rightIndex;
    @Override
    public void start(Stage primaryStage) {
        board = new ChessBoard();
        Button btn = new Button();
        int leftIndex = 0;
        int rightIndex = 0;
        BorderPane bpane = new BorderPane();
        Button buttons[]= new Button[16];
        GridPane gpane = new GridPane();
        EventHandler<ActionEvent> handler = new SquareEventHandler(board);

        for(int i =0; i<buttons.length;i++){
            buttons[i]= new Button ();
            buttons[i].setStyle("-fx-background-color: grey;" + "-fx-border-color: white;");
            buttons[i].setMinSize(90,45);
            right.getChildren().add(buttons[i]);
        }
        for(int i =0; i<buttons.length;i++){
            buttons[i]= new Button ();
            buttons[i].setStyle("-fx-background-color: grey;" + "-fx-border-color: white;");
            buttons[i].setMinSize(90,45);
            left.getChildren().add(buttons[i]);
        }
        for(int i =0; i<8;i++){
                
            for(int j=0; j<8;j++){
                Button centrebuttons = new Button(String.valueOf((char)(104 - (7-i))) + String.valueOf((7-j) + 1));
                GridPane.setRowIndex(centrebuttons,j);
                GridPane.setColumnIndex(centrebuttons,i);
                gpane.getChildren().add(centrebuttons);
                centrebuttons.setMinSize(90, 90);
                
                if((j+i)%2!=0){
                    centrebuttons.setStyle("-fx-background-color: white;" + "-fx-border-color: white");
                }
                else{centrebuttons.setStyle("-fx-background-color: grey;" + "-fx-border-color: white");
                }
                if(j <= 1 || j>=6){
                    Image icon = new Image("file:src/assign4/chess/images/" + board.getSquare(new Coordinate(centrebuttons.getText())).getPiece().getImageName());
                   
                    centrebuttons.setGraphic( new ImageView( icon ));                    
                }
                centrebuttons.setOnAction(handler);
                
            }            
        }
        
        board.addTakenObserver(this);
        bpane.setRight(right);
        bpane.setLeft(left);
        bpane.setCenter(gpane);
        
        Scene scene = new Scene(bpane, 900, 720);
        
        primaryStage.setTitle("Assignment 4");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
     public static void main(String[] args) {
        launch(args);
    }
    

    class SquareEventHandler implements EventHandler<ActionEvent> {
        private ChessBoard board;
        private boolean firstClick = true;
        private Button firstButton;
        private Button secondButton;
        
        public SquareEventHandler(ChessBoard board){
            this.board = board;
            
        }
        public void handle(ActionEvent e) {
            Button b = (Button) e.getSource();
            System.out.println("" + b.getText());
            if(firstClick) {
                firstButton = b;
                firstClick = false;
            }
            else {
                secondButton = b;
                if(board.move(new Coordinate(firstButton.getText()),new Coordinate(secondButton.getText()))){
                    Image tempImage = new Image("file:src/assign4/chess/images/" + board.getSquare(new Coordinate(secondButton.getText())).getPiece().getImageName());                
                    firstButton.setGraphic( null);
                    secondButton.setGraphic( new ImageView( tempImage ));
                }
                firstClick = true;
            }
        }
    }
    public void onChanged(Change c){
        while (c.next()) {    
            if (c.wasAdded()) {   

                int index = c.getFrom();

                List<Piece> pieces = c.getAddedSubList();
                for (Piece p: pieces) {
                    Image tempImage = new Image("file:src/assign4/chess/images/" + p.getImageName());
                    if(p.getColour() == ChessColour.WHITE) {
                        ( (Button)left.getChildren().get(leftIndex)).setGraphic( new ImageView( tempImage ));
                        leftIndex++;
                    }
                    else {
                        ( (Button)right.getChildren().get(rightIndex)).setGraphic( new ImageView( tempImage ));
                        rightIndex++;
                    }
                }
            }
        } 
 
 
    }
    
    
}

