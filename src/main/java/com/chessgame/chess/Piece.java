package com.chessgame.chess;
public class Piece extends Chess{

    private Color color;
    private Pieces type;
    private int[] offset;
    private boolean isSliding = false;
    private int moveCount = 0;
    private boolean hasMoved = false;
    
    public Piece(Color color, Pieces type) {
        this.color = color;
        this.type = type;
        setOffset();
    }

    /* for fen parsing */
    public Piece(char symbol){
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
        setOffset();
    }

    private void setOffset(){
        switch (type) {
            case PAWN:
                offset = color == Color.WHITE ? new int[]{-10, -11, -9, -20, -19, -21} : new int[]{10, 11, 9, 20, 19, 21};
                break;
            case ROOK:
                offset = new int[]{ -10,  -1,  1, 10};
                isSliding = true;
                break;
            case BISHOP:
                offset = new int[]{ -11,  -9,  9, 11};
                isSliding = true;
                break;
            case KING:
                offset = new int[]{ -11, -10, -9, -1, 1,  9, 10, 11 };
                break;
            case QUEEN:
                offset = new int[]{ -11, -10, -9, -1, 1,  9, 10, 11 };
                isSliding = true;
                break;
            case KNIGHT:
                offset = new int[]{ -21, -19,-12, -8, 8, 12, 19, 21 };
                break;
        }
    }

    public Color getColor() {
        return color;
    }

    public Pieces getType() {
        return type;
    }

    public int[] getOffset() {
        return offset;
    }

    public boolean isSliding() {
        return isSliding;
    }

    public void setHasMoved(boolean hasMoved){
        this.hasMoved = hasMoved;
    }

    public boolean getHasMoved(){
        return moveCount>0;
    }

    public void incrementMoveCount(){
        moveCount++;
    }

    public void decrementMoveCount(){
        moveCount--;
    }

    public char getSymbol(){
        char symbol = 'b';
        switch (type) {
            case KNIGHT:
                symbol = 'n';
                break;
            case KING:
                symbol = 'k';
                break;
            case ROOK:
                symbol = 'r';
                break;
            case PAWN:
                symbol = 'p';
                break;
            case QUEEN:
                symbol = 'q';
                break;
            case BISHOP:    
                symbol = 'b';
                break;
        }
        return color == Color.WHITE ? Character.toUpperCase(symbol) : symbol;

    }


    public char getUnicodeSymbol(){
        char symbol = 'n';
        switch (type) {
            case KNIGHT:
                symbol = color == Color.WHITE ? '♘'  : '♞';
                break;
            case KING:
                symbol = color == Color.WHITE ? '♔'  : '♚';
                break;
            case ROOK:
                symbol = color == Color.WHITE ? '♖'  : '♜';
                break;
            case PAWN:
                symbol = color == Color.WHITE ? '♙'  : '♟';
                break;
            case QUEEN:
                symbol = color == Color.WHITE ? '♕'  : '♛';
                break;
            case BISHOP:
                symbol = color == Color.WHITE ? '♗'  : '♝';
                break;
        }
        return symbol;
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Piece)) {
            return false;
        }
        Piece other = (Piece) obj;
        return this.getType() == other.getType();
    }

    public String toString(){
        return Character.toString(getSymbol());
    }



    


    
    

}
