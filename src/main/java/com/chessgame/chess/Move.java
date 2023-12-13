package com.chessgame.chess;

public class Move {

    private int fromSquare;
    private int toSquare;

    private Flag flag;

    private String uci;

    private Piece piece;


    public Move(int fromSquare, int toSquare, Flag flag, Piece piece){
        this.fromSquare = fromSquare;
        this.toSquare = toSquare;
        this.flag = flag;
        this.piece = piece;
        this.uci = Board.coordinates[fromSquare] + Board.coordinates[toSquare] + " " + flag +  " " + piece.getType();
    }


    public int getFromSquare() {
        return fromSquare;
    }


    public void setFromSquare(int fromSquare) {
        this.fromSquare = fromSquare;
    }


    public int getToSquare() {
        return toSquare;
    }


    public void setToSquare(int toSquare) {
        this.toSquare = toSquare;
    }


    public Flag getFlag() {
        return flag;
    }


    public void setFlag(Flag flag) {
        this.flag = flag;
    }


    public String getUci() {
        return uci;
    }


    public void setUci(String uci) {
        this.uci = uci;
    }


    public Piece getPiece() {
        return piece;
    }


    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public String toString(){
        return uci;
    }
    

    




}
