package com.chessgame;

import java.util.ArrayList;

import com.chessgame.chess.Board;
import com.chessgame.chess.Move;
import com.chessgame.chess.Piece;
import com.chessgame.chess.Chess.Color;

public class ChessController implements IControllerBtoV , IControllerVtoB {
    private IChessUI ui;
    private Board board;

    public ChessController(IChessUI ui){
        this.ui = ui;
        this.board = new Board("rnQq1k1r/pp2bppp/2p5/8/2B5/8/PPP1NnPP/RNBQK2R b KQ - 0 8");
    }

    @Override
    public void applyFen(String fen) {
       board.applyFEN(fen);
       ui.updateBoard(board.getBoard());
    }

    @Override
    public void makeMove(Move m) {
        board.makeMove(m);
        System.out.println(board);
        ui.updateBoard(board.getBoard());
        System.out.println(board.isSquareAttacked(6, Color.WHITE));
    }

    @Override
    public void getLegal() {
        ui.setLegal(board.getLegalMoves());
        setStatus();
    }

    public void setBoard() {
        System.out.println(board);
        ui.updateBoard(board.getBoard());
    }

    public void setStatus(){
        Piece check = null;
        int ci = board.getKingInCheck();
        if (ci!=-1) check = board.getBoard()[ci];
        boolean checkmate = board.getIsCheckmate();
        boolean draw = board.isDraw();
        ui.setStatus(check, checkmate, draw);
    }

}
