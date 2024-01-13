package com.chessgame.chess;

public class Piece {

    private Color color = Color.EMPTY;
    private Pieces type;

    private boolean sliding;
    private int offsets;
    private int[] offset;

    private int moveCount = 0;

    private char symbol;
    private char unicodeSymbol;

    public Piece(Color color, Pieces type) {
        this.color = color;
        this.type = type;

        setAttributes();
        setSymbols();
    }

    public Piece() {
        color = Color.EMPTY;
        type = Pieces.EMPTY;
        sliding = false;
        offset = new int[1];
    }

    public Piece(char symbol) {
        color = Character.isUpperCase(symbol) ? Color.WHITE : Color.BLACK;
        switch (Character.toLowerCase(symbol)) {
            case 'n':
                type = Pieces.KNIGHT;
                break;
            case 'b':
                type = Pieces.BISHOP;
                break;
            case 'k':
                type = Pieces.KING;
                break;
            case 'q':
                type = Pieces.QUEEN;
                break;
            case 'p':
                type = Pieces.PAWN;
                break;
            case 'r':
                type = Pieces.ROOK;
                break;
        }
        setAttributes();
        setSymbols();
    }

    public void setAttributes() {
        boolean[] slide = { false, false, true, true, true, false };
        int[] offsets = { 0, 8, 4, 4, 8, 8 };
        int[][] offset = {
                { 0, 0, 0, 0, 0, 0, 0, 0 },
                { -21, -19, -12, -8, 8, 12, 19, 21 }, /* KNIGHT */
                { -11, -9, 9, 11, 0, 0, 0, 0 }, /* BISHOP */
                { -10, -1, 1, 10, 0, 0, 0, 0 }, /* ROOK */
                { -11, -10, -9, -1, 1, 9, 10, 11 }, /* QUEEN */
                { -11, -10, -9, -1, 1, 9, 10, 11 } /* KING */
        };
        this.sliding = slide[type.ordinal()];
        this.offset = offset[type.ordinal()];
        this.offsets = offsets[type.ordinal()];
    }

    public void incCount() {
        moveCount++;
    }

    public void decCount() {
        moveCount--;
    }

    public boolean hasMoved() {
        return moveCount > 0;
    }

    public int getOffsets() {
        return offsets;
    }

    public void setSymbols() {

        switch (type) {
            case KNIGHT:
                unicodeSymbol = color == Color.WHITE ? '♘' : '♞';
                symbol = 'n';
                break;
            case KING:
                unicodeSymbol = color == Color.WHITE ? '♔' : '♚';
                symbol = 'k';
                break;
            case ROOK:
                unicodeSymbol = color == Color.WHITE ? '♖' : '♜';
                symbol = 'r';
                break;
            case PAWN:
                unicodeSymbol = color == Color.WHITE ? '♙' : '♟';
                symbol = 'p';
                break;
            case QUEEN:
                unicodeSymbol = color == Color.WHITE ? '♕' : '♛';
                symbol = 'q';
                break;
            case BISHOP:
                unicodeSymbol = color == Color.WHITE ? '♗' : '♝';
                symbol = 'b';
                break;
            case EMPTY:
                unicodeSymbol = '·';
                symbol = '·';
        }

        if (color == Color.WHITE)
            symbol = Character.toUpperCase(symbol);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Pieces getType() {
        return type;
    }

    public void setType(Pieces type) {
        this.type = type;
    }

    public boolean isSliding() {
        return sliding;
    }

    public void setSliding(boolean sliding) {
        this.sliding = sliding;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public void setMoveCount(int moveCount) {
        this.moveCount = moveCount;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public char getUnicodeSymbol() {
        return unicodeSymbol;
    }

    public void setUnicodeSymbol(char unicodeSymbol) {
        this.unicodeSymbol = unicodeSymbol;
    }

    public int[] getOffset() {
        return offset;
    }

    public void setOffset(int[] offset) {
        this.offset = offset;
    }

    public String toString() {
        return Character.toString(symbol);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Piece)) {
            return false;
        }
        Piece other = (Piece) obj;

        return this.getType() == other.getType() && this.getMoveCount() == other.getMoveCount()
                && this.getColor() == other.getColor();
    }

}
