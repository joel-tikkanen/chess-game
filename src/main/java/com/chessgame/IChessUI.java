package com.chessgame;

import java.util.ArrayList;

import com.chessgame.chess.Board;
import com.chessgame.chess.Move;
import com.chessgame.chess.Piece;

import javafx.scene.image.Image;

public interface IChessUI {
    public void updateBoard(Piece[] board);
    void handleClick(int row, int column);
    public void setLegal(ArrayList<Move> legalMoves);
    public void setStatus(Piece check, boolean checkmate, boolean draw);
    

}
