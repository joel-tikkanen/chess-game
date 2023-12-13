package com.chessgame;

import java.util.ArrayList;

import com.chessgame.chess.Board;
import com.chessgame.chess.Move;
import com.chessgame.chess.Piece;
import com.chessgame.chess.Color;

public class ChessController implements IControllerBtoV , IControllerVtoB {
    private IChessUI ui;
    private Board board;

    public ChessController(IChessUI ui){
        this.ui = ui;
     // rnb1kb1r/pppppppp/8/1B6/8/5N1P/PPPPPnPP/RNBqK2R w KQkq - 0 1

     // rnN2k1r/pp2bppp/2p5/8/2B5/8/PPP1NnPP/RNBqK2R w KQ - 0 9

     // rnQq1k1r/pp2bppp/2p5/8/2B5/8/PPP1NnPP/RNB1K2R b KQ - 0 8

     // rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1


        this.board = new Board("8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 w - - 0 0");
    }

    @Override
    public void applyFen(String fen) {
       board.setFen(fen);
       ui.updateBoard(board.getBoard());
    }

    @Override
    public void makeMove(Move m) {
        board.makeMove(m);
        ui.updateBoard(board.getBoard());
    }

    @Override
    public void getLegal() {
        board.genLegal();
        ui.setLegal(board.getLegalMoves());
        setStatus();
    }

    public void setBoard() {
      
        ui.updateBoard(board.getBoard());
    }

    public void setStatus(){
       
    }

}
