package com.chessgame;

import com.chessgame.chess.*;

public class ChessController {
    private IChessUI ui;
    private Board board;

    public ChessController(IChessUI ui) {
        this.ui = ui;
        this.board = new Board();
    }

    public void applyFen(String fen) {
        board.setFen(fen);
        ui.updateBoard(board.getBoard());
    }

    public void makeMove(Move m) {
        board.makeMove(m);
        ui.updateBoard(board.getBoard());
        setStatus();
    }

    public void getLegal() {
        board.genLegal();
        ui.setLegal(board.getLegalMoves());
        setStatus();
    }

    public void setBoard() {

        ui.updateBoard(board.getBoard());
    }

    public void setStatus() {

    }

}
