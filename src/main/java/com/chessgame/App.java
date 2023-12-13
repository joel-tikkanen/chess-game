package com.chessgame;

import java.util.ArrayList;
import com.chessgame.chess.Board;
import com.chessgame.chess.Move;
import com.chessgame.chess.Piece;
import com.chessgame.chess.Color;



import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class App extends Application implements IChessUI {

    private static Scene scene;
    private GridPane gp;
    private StackPane root;
    private String statusString = "STATUS HERE: ";
    private Text statusText = new Text(statusString);


    private Image[] pieceImages = {
        new Image("https://upload.wikimedia.org/wikipedia/commons/5/5c/Chess_rlt60.png"),
        new Image("https://upload.wikimedia.org/wikipedia/commons/8/81/Chess_bdt60.png"),
        new Image("https://upload.wikimedia.org/wikipedia/commons/9/9b/Chess_blt60.png"),
        new Image("https://upload.wikimedia.org/wikipedia/commons/e/e3/Chess_kdt60.png"),
        new Image("https://upload.wikimedia.org/wikipedia/commons/3/3b/Chess_klt60.png"),
        new Image("https://upload.wikimedia.org/wikipedia/commons/f/f1/Chess_ndt60.png"),
        new Image("https://upload.wikimedia.org/wikipedia/commons/2/28/Chess_nlt60.png"),
        new Image("https://upload.wikimedia.org/wikipedia/commons/c/cd/Chess_pdt60.png"),
        new Image("https://upload.wikimedia.org/wikipedia/commons/0/04/Chess_plt60.png"),
        new Image("https://upload.wikimedia.org/wikipedia/commons/a/af/Chess_qdt60.png"),
        new Image("https://upload.wikimedia.org/wikipedia/commons/4/49/Chess_qlt60.png"),
        new Image("https://upload.wikimedia.org/wikipedia/commons/a/a0/Chess_rdt60.png"),
    };

    private int clickedSquare = -1;

    private ArrayList<Move> legalMoves = new ArrayList<Move>();
    private ChessController controller;

    private ArrayList<Integer> displayedMoves = new ArrayList<Integer>();

    @Override
    public void start(Stage stage) {
        this.root = new StackPane();
        scene = new Scene(root, 500, 480); // Use the class-level scene variable
        stage.setScene(scene);
        this.gp = new GridPane();
        this.controller = new ChessController(this);
        root.getChildren().add(statusText);
        controller.setBoard();
        stage.show();
    }

    @Override
    public void handleClick(int row, int column){
        int square = row*8+column;
        boolean canMove = false;
        if (clickedSquare != -1) {
            Move m = null;
            for (Integer dm : displayedMoves) {
                javafx.scene.Node p = gp.getChildren().get(dm);
                int r = dm / 8;
                int c = dm % 8;
                if ((r+c) % 2 == 0) {
                    p.setStyle("-fx-background-color: #F0D9B5");
                } else {
                    p.setStyle("-fx-background-color: #B58863");
                }
            }
            for (Move move : legalMoves) {
                if (clickedSquare == move.getFromSquare() && square == move.getToSquare()) {
                    canMove = true;
                    m = move;
                    break;
                }
            }
            if (canMove) controller.makeMove(m);
            clickedSquare = -1;

        } else {
            for (Move move : legalMoves) {
                if (move.getFromSquare() == square) {
                    displayedMoves.add(move.getToSquare());
                    gp.getChildren().get(move.getToSquare()).setStyle("-fx-background-color: red");
                }
            }
            clickedSquare = square;
        }
        
    }

    
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void setLegal(ArrayList<Move> legalMoves){
        this.legalMoves = legalMoves;
        System.out.println(legalMoves);
        System.out.println(legalMoves.size());
    }


    @Override
    public void updateBoard(Piece[] newBoard) {
   
        int imageIndex, i;
        this.gp = new GridPane();
        gp.setPadding(new Insets(50));
        for (i = 0; i < newBoard.length; i++){
            if (newBoard[i].getColor()!=Color.EMPTY){
                switch (newBoard[i].getType()){
                    case ROOK:
                        imageIndex = newBoard[i].getColor() == Color.WHITE ? 0: 11;
                        setCell(imageIndex, i);
                        break;
                    case BISHOP:
                        imageIndex = newBoard[i].getColor() == Color.WHITE ? 2 : 1;
                        setCell(imageIndex, i);
                        break;
                    case KING:
                        imageIndex = newBoard[i].getColor() == Color.WHITE ? 4 : 3;
                        setCell(imageIndex, i);
                        break;
                    case QUEEN:
                        imageIndex = newBoard[i].getColor() == Color.WHITE ? 10: 9;
                        setCell(imageIndex, i);
                        break;
                    case KNIGHT:
                        imageIndex = newBoard[i].getColor() == Color.WHITE ? 6 : 5;
                        setCell(imageIndex, i);
                        break;
                    case PAWN:
                        imageIndex = newBoard[i].getColor() == Color.WHITE ? 8: 7;
                        setCell(imageIndex, i);
                        break;
                }
            } else {
                setCell(-1, i);
            }
            
        }
        controller.getLegal();
        root.getChildren().remove(gp);
        root.getChildren().add(gp);
    }

    private void setCell(int imageIndex, int cellIndex){
        Pane pane = new Pane();

        int row = cellIndex / 8;
        int col = cellIndex % 8;

        pane.setPrefSize(50, 50); 
        if ((row+col) % 2 == 0) {
            pane.setStyle("-fx-background-color: #F0D9B5");
        } else {
            pane.setStyle("-fx-background-color: #B58863");
        }
       
        if (imageIndex != -1){
            ImageView imageView = new ImageView();
            Image image = pieceImages[imageIndex];
            imageView.setImage(image);
            imageView.setFitHeight(40); 
            imageView.setFitWidth(40); 
            imageView.setPreserveRatio(true);
            pane.getChildren().add(imageView);
        }

        Button button = new Button();
        button.setPrefSize(50, 50);
        button.setStyle("-fx-background-color: transparent;");
        button.setOnAction(e -> handleClick(row, col));
        pane.getChildren().add(button);
        gp.add(pane, col, row);
    }

    public void setStatus(Piece check, boolean checkmate, boolean draw){
        statusText.setText(statusString);
        if (check != null)  statusText.setText(String.format("Piece %s %s giving check", check.getColor(), check.getType()));
        if (checkmate)  statusText.setText(String.format("Checkmate by %s", check.getColor()));
        if (draw) statusText.setText( "Just a draw.");

    }
}
