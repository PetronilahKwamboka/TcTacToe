package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Cell;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class TicTacToe extends Application{

    // indicate which player has a turn , initially set to X player
    private char whoseTurn = 'X';

    //create and initialize cell
    private Cell[][] cell = new Cell[3][3];

    //create and initialize status label
    private Label statusLabel = new Label("X's turn to play");

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        // TODO Auto-generated method stub

        //create a pane to hold the cell
        GridPane pane = new GridPane();
        for(int i = 0; i<3; i++)
            for(int j = 0; j<3; j++)
                pane.add(cell[i][j] = new Cell(), i, j);

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(pane);
        borderPane.setBottom(statusLabel);

        Scene scene = new Scene(borderPane, 450, 170);
        primaryStage.setTitle("TicTacToe");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    //Determine if the cell are all occupied
    public boolean isFull(){
        for (int i = 0; i < 3; i++)
            for(int j = 0; j<3; j++)
                if (cell[i][j].getToken() == ' '){
                    return false;
                }
        return true;
    }


    //Determine if the player with the specified token wins
    public boolean isWon(char token){
        for (int i = 0; i<3; i++)
                if (cell[i][0].getToken() == token
                    && cell[i][1].getToken() == token
                    && cell[i][2].getToken()== token) {
                    return true;
                   }
        for (int j =0; j<3; j++)
            if (cell[j][0].getToken() == token
                && cell[j][1].getToken() == token
                && cell[j][2].getToken() == token){
                return true;
            }
            // check major diagonal for a win
            if (cell[0][0].getToken() == token
                && cell[1][1].getToken() == token
                && cell[2][2].getToken() == token){
                return true;
            }

            // checking the sub diagonal for a win
            if (cell[0][2].getToken() == token
                && cell[1][1].getToken()== token
                && cell[2][0].getToken() == token){
                return true;
            }
            return false;
    }

    // create an inner class for the cell
    public class Cell extends Pane {
        //token used for this cell

        private char token = ' ';

        public Cell(){
            setStyle("-fx-border-color: black");
            this.setPrefSize(2000, 2000);
            this.setOnMouseClicked(e -> handleMouseClick());
        }

        public char getToken() {
            return token;
        }

        public void setToken(char c) {
            token = c;
           //display X
            if (token == 'X' ){
                Line line1 = new Line(10, 10, this.getWidth() - 10, this.getHeight()- 10);
                line1.endXProperty().bind(this.widthProperty().subtract(10));
                line1.endYProperty().bind(this.heightProperty().subtract(10));

                Line line2 = new Line(10, this.getHeight() - 10, this.getWidth() - 10, 10);
                line2.startYProperty().bind(this.heightProperty().subtract(10));
                line2.endXProperty().bind(this.widthProperty().subtract(10));
                this.getChildren().addAll(line1, line2);
            }
            else if (token == 'O'){
                Ellipse ellipse = new Ellipse(this.getWidth() / 2,
                        this.getHeight() /2, this.getWidth()/2 - 10, this.getHeight() / 2 - 10);
                ellipse.centerXProperty().bind(this.widthProperty().divide(2));
                ellipse.centerYProperty().bind(this.heightProperty().divide(2));
                ellipse.radiusXProperty().bind(this.widthProperty().divide(2).subtract(10));
                ellipse.radiusYProperty().bind(this.heightProperty().divide(2).subtract(10));
                ellipse.setStroke(Color.BLACK);
                ellipse.setFill(Color.WHITE);

                getChildren().add(ellipse);
            }
        }

        //handle a mouse click event
        public void handleMouseClick(){
            // if cell is empty and game is not over
            if (token == ' ' && whoseTurn != ' '){
                setToken(whoseTurn); // set token in thw cell

                // check game status
                if (isWon(whoseTurn)){
                    statusLabel.setText(whoseTurn + " won! The game is over");
                    whoseTurn = ' '; //Game over
                }
                else if (isFull()){
                    statusLabel.setText("Draw! The game is over");
                    whoseTurn = ' '; //Game over
                }
                else {
                    //change the turn
                    whoseTurn = (whoseTurn == 'X' ? 'O': 'X'); //ternary equation
                    // display whose turn
                    statusLabel.setText(whoseTurn + " 's turn");
                }
            }
        }
    }
}
