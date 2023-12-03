package com.chessgame.chess;

public class Move extends Chess{

    private int fromSquare;
    private int toSquare;

    private Piece capture = null;
    private String uci;
    private Piece piece;

    private int castle = 0;


    public Move(int fromSquare, int toSquare, Piece capture, Piece piece){
        this.fromSquare = fromSquare;
        this.toSquare = toSquare;
        this.capture = capture;
        this.uci = coords[fromSquare] + coords[toSquare];
        this.piece = piece;
    }

    public Move(int fromSquare, int toSquare, int castle, Piece piece){
        this.fromSquare = fromSquare;
        this.toSquare = toSquare;
        this.castle = castle;
        this.uci = castle == 1 ? "O-O":"O-O-O";
        this.piece = piece;
    }

    

    public int getFromSquare() {
        return fromSquare;
    }




    public int getToSquare() {
        return toSquare;
    }




    public Piece getCapture() {
        return capture;
    }


    public String getUCI(){
        return uci;
    }

    public String getUci() {
        return uci;
    }

    public Piece getPiece(){
        return piece;
    }

    public int getCastle() {
        return castle;
    }

    public String getFromCoordinate(){
        return coords[fromSquare];
    }

    public String getToCoordinates(){
        return coords[toSquare];
    }


    public String toString(){
        return uci;
    }

    



}