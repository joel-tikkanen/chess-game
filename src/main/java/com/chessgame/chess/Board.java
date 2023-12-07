package com.chessgame.chess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;

public class Board extends Chess {

    public final String STARTING_FEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
    private String fen = STARTING_FEN;
    private Color turn = Color.WHITE;
    private Color notTurn = Color.BLACK;
    private Stack<String> allPositions = new Stack<>();
    private Stack<Move> playedMoves = new Stack<Move>();
    Stack<Piece[]> boardStates = new Stack<>();
    public int mates = 0;
    public int checks = 0;

    private ArrayList<Integer> solvePin = new ArrayList<>();

    

    int kingInCheck = -1;

    int[] mailbox = {
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, 0, 1, 2, 3, 4, 5, 6, 7, -1,
            -1, 8, 9, 10, 11, 12, 13, 14, 15, -1,
            -1, 16, 17, 18, 19, 20, 21, 22, 23, -1,
            -1, 24, 25, 26, 27, 28, 29, 30, 31, -1,
            -1, 32, 33, 34, 35, 36, 37, 38, 39, -1,
            -1, 40, 41, 42, 43, 44, 45, 46, 47, -1,
            -1, 48, 49, 50, 51, 52, 53, 54, 55, -1,
            -1, 56, 57, 58, 59, 60, 61, 62, 63, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1
    };

    int[] mailbox64 = {
            21, 22, 23, 24, 25, 26, 27, 28,
            31, 32, 33, 34, 35, 36, 37, 38,
            41, 42, 43, 44, 45, 46, 47, 48,
            51, 52, 53, 54, 55, 56, 57, 58,
            61, 62, 63, 64, 65, 66, 67, 68,
            71, 72, 73, 74, 75, 76, 77, 78,
            81, 82, 83, 84, 85, 86, 87, 88,
            91, 92, 93, 94, 95, 96, 97, 98
    };

    public Piece[] board = {
            new Piece(Color.BLACK, Pieces.ROOK), new Piece(Color.BLACK, Pieces.KNIGHT),
            new Piece(Color.BLACK, Pieces.BISHOP), new Piece(Color.BLACK, Pieces.QUEEN),
            new Piece(Color.BLACK, Pieces.KING), new Piece(Color.BLACK, Pieces.BISHOP),
            new Piece(Color.BLACK, Pieces.KNIGHT), new Piece(Color.BLACK, Pieces.ROOK),
            new Piece(Color.BLACK, Pieces.PAWN), new Piece(Color.BLACK, Pieces.PAWN),
            new Piece(Color.BLACK, Pieces.PAWN), new Piece(Color.BLACK, Pieces.PAWN),
            new Piece(Color.BLACK, Pieces.PAWN), new Piece(Color.BLACK, Pieces.PAWN),
            new Piece(Color.BLACK, Pieces.PAWN), new Piece(Color.BLACK, Pieces.PAWN),
            null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null,
            new Piece(Color.WHITE, Pieces.PAWN), new Piece(Color.WHITE, Pieces.PAWN),
            new Piece(Color.WHITE, Pieces.PAWN), new Piece(Color.WHITE, Pieces.PAWN),
            new Piece(Color.WHITE, Pieces.PAWN), new Piece(Color.WHITE, Pieces.PAWN),
            new Piece(Color.WHITE, Pieces.PAWN), new Piece(Color.WHITE, Pieces.PAWN),
            new Piece(Color.WHITE, Pieces.ROOK), new Piece(Color.WHITE, Pieces.KNIGHT),
            new Piece(Color.WHITE, Pieces.BISHOP), new Piece(Color.WHITE, Pieces.QUEEN),
            new Piece(Color.WHITE, Pieces.KING), new Piece(Color.WHITE, Pieces.BISHOP),
            new Piece(Color.WHITE, Pieces.KNIGHT), new Piece(Color.WHITE, Pieces.ROOK),

    };

    public Piece[] oldBoard = Arrays.copyOfRange(board, 0, 64);

    private boolean isCheckmate = false;
    private int moveNum = 1;

    private boolean isStalemate = false;
    private double halfmoveClock = 0;

    // Draw agreement
    private boolean whiteDraw = false;
    private boolean blackDraw = false;



    private Move lastMove = null;

    public Board() {

    }

    public Piece[] getBoard() {
        return board;
    }

    public Board(String fen) {
        applyFEN(fen);
    }

    public static String indexToCoord(int index) {
        String coord = null;

        return coord;
    }

    public String getFEN() {
        return fen;
    }

    public String generateFEN() {
        String fen = "";
        int k = 0;
        for (int i = 0; i < board.length; i++) {
            Piece p = board[i];
            if (p != null) {
                if (k > 0)
                    fen += Integer.toString(k);
                k = 0;
                fen += p.getSymbol();
            } else {
                k++;
            }
            if ((i + 1) % 8 == 0 & i != 63) {
                if (k > 1)
                    fen += Integer.toString(k);
                fen += "/";
                k = 0;
            }
        }
        fen += turn == Color.WHITE ? " w " : " b ";
        Move[] acw = availableCastling(Color.WHITE);
        Move[] acb = availableCastling(Color.BLACK);

        String cs = "";
        if (acw[0] != null)
            cs += "K";
        if (acw[1] != null)
            cs += "Q";
        if (acb[0] != null)
            cs += "k";
        if (acb[1] != null)
            cs += "q";

        if (cs == "")
            fen += " -";
        else
            fen += cs;

        Move ep = getEnPassant(turn);
        if (ep != null)
            fen += String.format(" %s", ep.getToCoordinates());
        else
            fen += " -";
        fen += String.format(" %d", (int) getHalfmoveClock());
        fen += String.format(" %d", moveNum);
        return fen;
    }

    public void applyFEN(String fen) {

        this.fen = fen;

        Piece[] bb = this.board;

        String[] splitted = fen.split(" ");
        String board = splitted[0];
        String turn = splitted[1];
        String enPassant = splitted[3];
        String halfMove = splitted[4];
        String fullMove = splitted[5];

        int k = 0;
        Piece[] a = new Piece[64];
        for (int i = 0; i < board.length(); i++) {
            char ci = board.charAt(i);
            if ("rnbqkp".indexOf(Character.toLowerCase(ci)) != -1) {
                a[k] = new Piece(ci);
                k += 1;
            } else if (ci != '/') {
                k += ci - '0';
            }
        }
        this.board = a;
        this.turn = turn.equals("w") ? Color.WHITE : Color.BLACK;
        this.notTurn = turn.equals("w") ? Color.BLACK : Color.WHITE;
        setHalfMove(Integer.parseInt(halfMove));
        setFullMove(Integer.parseInt(fullMove));
        for (int i = 0; i < this.board.length; i++) {

            if (bb[i] == null && this.board[i] != null) {
                this.board[i].incrementMoveCount();
            } else if (bb[i] != null && this.board[i] != null) {
                if (!bb[i].equals(this.board[i]))
                    this.board[i].incrementMoveCount();
            }
        }

        for (int i = 0; i < this.board.length; i++) {
            if (coords[i].equals(enPassant)) {

                k = this.turn == Color.WHITE ? -1 : 1;
                playedMoves.add(new Move(i + k * 8, i - k * 8, null, this.board[i - k * 8], null));
            }
        }

        this.kingInCheck = kingInCheck(this.turn);

    }

    // Psuedo Legal without castles and en passants
    // Algorithm:
    // https://www.chessprogramming.org/10x12_Board#Offset_Move_Generation

    public ArrayList<Move> getPseudoLegalMoves() {
        if (isCheckmate) mates++;
        ArrayList<Move> moves = new ArrayList<Move>();

        for (int i = 0; i < 64; i++) {
            if (board[i] != null) {
                Piece p = board[i];
                if (p.getColor() == turn) {
                    int[] offset = p.getOffset();
                    if (p.getType() != Pieces.PAWN) {
                        for (int j = 0; j < offset.length; j++) {
                            for (int n = i;;) {
                                n = mailbox[mailbox64[n] + offset[j]];
                                if (n == -1)
                                    break;
                                if (board[n] != null) {
                                    if (board[n].getColor() == notTurn)
                                        moves.add(new Move(i, n, board[n], board[i], null));
                                    break;
                                }
                                moves.add(new Move(i, n, board[n], board[i], null));
                                if (!p.isSliding())
                                    break;
                            }
                        }
                    } else {
                        // pawn thingy
                        boolean canPromote;
                        // p pushes
                        int n = mailbox[mailbox64[i] + offset[0]];
                        if (n != -1) {
                           
                            if (board[n] == null) {
                                canPromote = canPromote(turn, i);
                                if (canPromote) {
                                    moves.addAll(addPromotions(i, n, p, p));
                                } else {
                                    moves.add(new Move(i, n, board[n], board[i], null));
                                }
                                n = mailbox[mailbox64[i] + offset[3]];
                                if (n != -1){
                                    if (board[n] == null && !board[i].getHasMoved())
                                    moves.add(new Move(i, n, board[n], board[i], null));
                                }
                                
                            }
                        }

                        // p caps
                        n = mailbox[mailbox64[i] + offset[1]];
                        canPromote = canPromote(turn, i);
                        if (n != -1) {
                            if (board[n] != null) {
                                if (board[n].getColor() == notTurn) {
                                    if (canPromote) {
                                        moves.addAll(addPromotions(i, n, p, p));
                                    } else {
                                        moves.add(new Move(i, n, board[n], board[i], null));
                                    }

                                }
                            }

                        }
                        n = mailbox[mailbox64[i] + offset[2]];
                        canPromote = canPromote(turn, i);
                        if (n != -1) {
                            if (board[n] != null) {
                                if (board[n].getColor() == notTurn) {
                                    if (canPromote) {
                                        moves.addAll(addPromotions(i, n, p, p));
                                    } else {
                                        moves.add(new Move(i, n, board[n], board[i], null));
                                    }
                                }
                            }

                        }

                    }
                }

            }

        }

        return moves;
    }

    /**
     * @return
     */
    public ArrayList<Move> getLegalMoves() {
        ArrayList<Move> pseudo = getPseudoLegalMoves();

        ArrayList<Move> legalMoves = new ArrayList<>();
        Move[] castling = castlingRights();
        Move enPassant = getEnPassant(turn);
        if (kingInCheck == -1) {
            if (castling[0] != null)
                legalMoves.add(castling[0]);
            if (castling[1] != null)
                legalMoves.add(castling[1]);
        }
        if (enPassant != null)
            legalMoves.add(enPassant);

        boolean checkmate = true;
       
        for (Move move : pseudo) {

            if (kingInCheck != -1) {
                if ((doubleCheck(kingInCheck) || move.getPiece().getType() == Pieces.KING)) {
                    if (isSquareAttacked(move.getToSquare(), notTurn) == -1 && move.getPiece().getType() == Pieces.KING) {
                        legalMoves.add(move);
                        checkmate = false;
                    }
                } else if (board[kingInCheck].getType() == Pieces.KNIGHT && move.getToSquare() == kingInCheck) {
                    legalMoves.add(move);
                    checkmate = false;
                } else if (getRaycast(findKing(turn)).contains(move.getToSquare())) {
                    legalMoves.add(move);
                    checkmate = false;
                }

            } else {
                checkmate = false;
                Piece isP = isPinned(move.getFromSquare(), turn);
                if (move.getPiece().getType() == Pieces.KING) {
                    if (isSquareAttacked(move.getToSquare(), notTurn) == -1) {
                        legalMoves.add(move);
                    }
                } else if (isP == null) {
                    legalMoves.add(move);
                } else {

                    ArrayList<Integer> ray = getRaycast(move.getFromSquare());
                    if (solvePin.contains(move.getToSquare()))
                        legalMoves.add(move);
                }
            }
        }
        if (checkmate){
                isCheckmate = true;
                
                mates++;
                return new ArrayList<>();
        }
        if (!checkmate && legalMoves.size() == 0 && kingInCheck == -1) {
            isStalemate = true;
            return new ArrayList<>();
        } 
        return legalMoves;
    }

    // Checks if piece is pinned, color is color of the king that is potentially
    // pinned
    // Returns pinning piece
    public Piece isPinned(int square, Color color) {
        
        int[] dirs = { 1, 9, 10, 11 };
        ArrayList<Integer> solve;
        int i, j, d1, d2, k;
        Piece d1Piece, d2Piece;
        for (i = 0; i < dirs.length; i++) {
            solve = new ArrayList<>();
            k = 1;
            d1 = 0;
            d2 = 0;
            d1Piece = null;
            d2Piece = null;
            while (d1Piece == null || d2Piece == null) {
                if (d1Piece == null)
                    d1 = mailbox[mailbox64[square] + dirs[i] * k];
                    if (!isKing(d1)) solve.add(d1);
                if (d2Piece == null)
                    d2 = mailbox[mailbox64[square] + dirs[i] * -1 * k];
                    if (!isKing(d2)) solve.add(d2);
                if (d1 == -1 || d2 == -1)
                    break;
                if (board[d1] != null)
                    d1Piece = board[d1];
                if (board[d2] != null)
                    d2Piece = board[d2];
                k++;
            }
            if (d1Piece != null && d2Piece != null) {
                if (isThatPiece(color, Pieces.KING, d1Piece) && d2Piece.isSliding() && d2Piece.getColor() != color) {
                    for (j = 0; j < board[d2].getOffset().length; j++) {
                        if (d2Piece.getOffset()[j] == dirs[i]){
                            solvePin = solve;
                            return d2Piece;
                        }
                            
                    }
                }
                if (isThatPiece(color, Pieces.KING, d2Piece) && d1Piece.isSliding() && d1Piece.getColor() != color) {
                    for (j = 0; j < board[d1].getOffset().length; j++) {
                        if (d1Piece.getOffset()[j] == dirs[i]) {
                            solvePin = solve;
                            return d1Piece;
                        }
                    }
                }
            }

        }
        this.solvePin = new ArrayList<>();
        return null;
    }

    // Get square of piece that attacks certain square, returns -1 if no piece
    // attacks
    // If there are multiple attackers it returns the one it finds first
    public int isSquareAttacked(int square, Color color) {

        int i, s;
        // sliding piece detection + king
        int[] dirs = { -11, -10, -9, -1, 1, 9, 10, 11 };
        for (i = 0; i < dirs.length; i++) {
            boolean kf = false;
            s = mailbox[mailbox64[square] + dirs[i]];
            if (s == -1)
                continue;
            if (isThatPiece(color, Pieces.KING, board[s]))
                return s;
            for (s = square;;) {
                s = mailbox[mailbox64[s] + dirs[i]];
                if (s == -1)
                    break;
                if (isThatPiece(color == Color.WHITE ? Color.BLACK : Color.WHITE, Pieces.KING, board[s]))
                    kf = true;
                if (board[s] != null) {
                    if (board[s].isSliding() && board[s].getColor() == color) {
                        for (int o : board[s].getOffset())
                            if (o == dirs[i] * -1)
                                return s;
                    }
                    if (!kf || board[s].getColor() != color)
                        break;
                }
            }
        }

        // horse detection
        int[] nos = { -21, -19, -12, -8, 8, 12, 19, 21 };
        for (i = 0; i < nos.length; i++) {
            s = mailbox[mailbox64[square] + nos[i]];
            if (s == -1)
                continue;
            if (isThatPiece(color, Pieces.KNIGHT, board[s]))
                return s;
        }

        // pawn
        int[] po = color == Color.WHITE ? new int[] { 11, 9 } : new int[] { -11, -9 };
        for (i = 0; i < po.length; i++) {
            s = mailbox[mailbox64[square] + po[i]];
            if (s == -1)
                continue;
            if (isThatPiece(color, Pieces.PAWN, board[s]))
                return s;
        }
        return -1;
    }

    public ArrayList<Integer> getRaycast(int square) {
        ArrayList<Integer> raycast = new ArrayList<>();
        int[] offset = { -11, -10, -9, -1, 1,  9, 10, 11 };
        for (int i = 0; i < offset.length; i++) {
            ArrayList<Integer> r = new ArrayList<>();
            for (int n = square;;) {
                n = mailbox[mailbox64[n] + offset[i]];
                if (n == -1)
                    break;
                if (board[n] != null) {
                    if (!board[n].isSliding()) {
                        r.clear();
                    } else if (board[n].getColor() != turn) {
                        raycast.addAll(r);
                        raycast.add(n);
                    }
                    break;
                }
                r.add(n);
            }
        }
        return raycast;
    }

    /*
     * index 0 = queen side, index 1 = king side
     */
    public Move[] castlingRights() {
        int kingPos = turn == Color.WHITE ? 60 : 4;
        Move[] castles = availableCastling(turn);
        int[] qs = { kingPos - 1, kingPos - 2, kingPos - 3 };
        int[] ks = { kingPos + 1, kingPos + 2 };
        for (int square = 0; square < qs.length-1; square++) {
            if (isSquareAttacked(qs[square], notTurn) != -1) castles[0] = null;
        } 
        for (Integer square : ks)
            if (isSquareAttacked(square, notTurn) != -1)
                castles[1] = null;
        if (board[qs[0]] != null || board[qs[1]] != null || board[qs[2]] != null)
            castles[0] = null;
        if (board[ks[0]] != null || board[ks[1]] != null)
            castles[1] = null;
        return castles;
    }

    // Is castling available now or later in the game, returns castling moves for
    // color
    public Move[] availableCastling(Color color) {
        Move[] castles = { null, null };
        int[] pp = color == Color.WHITE ? new int[] { 56, 60, 63 } : new int[] { 0, 4, 7 };

        if (isThatPiece(color, Pieces.ROOK, board[pp[0]]) && isThatPiece(color, Pieces.KING, board[pp[1]])) {
            if (!board[pp[0]].getHasMoved() && !board[pp[1]].getHasMoved()) {
              
                castles[0] = new Move(pp[1], pp[1] - 2, 0, board[pp[1]]);
            }
        }

        if (isThatPiece(color, Pieces.KING, board[pp[1]]) && isThatPiece(color, Pieces.ROOK, board[pp[2]])) {
            if (!board[pp[1]].getHasMoved() && !board[pp[2]].getHasMoved()) {
               
                castles[1] = new Move(pp[1], pp[1] + 2, 1, board[pp[1]]);
            }
        }

        return castles;
    }

    // Color is color of the king being pinned
    public ArrayList<Integer> solvePin(int pinnedSquare, Color color){
        ArrayList<Integer> solve = new ArrayList<>();

        return solve;
    }

    public int kingInCheck(Color color) {
        int kingPos = findKing(color);
        return isSquareAttacked(kingPos, color == Color.WHITE ? Color.BLACK : Color.WHITE);
    }

    public void makeMove(Move move) {

        this.oldBoard = Arrays.copyOfRange(board, 0, 64);

        Piece[] boardCopy = Arrays.copyOf(board, 64);
        boardStates.push(boardCopy);


        Piece moving = board[move.getFromSquare()];
        allPositions.add(fen);
        int castle = move.getCastle();

        
        if (castle != -1) {
            // king side
            if (castle == 1) {

                board[move.getFromSquare() + 1] = board[move.getFromSquare() + 3];
                board[move.getFromSquare() + 3] = null;
            }
            // queen side
            if (castle == 0) {
                board[move.getFromSquare() - 1] = board[move.getFromSquare() - 4];
                board[move.getFromSquare() - 4] = null;
            }
        }

        board[move.getFromSquare()] = null;

        if (move.isPromotion()) {
            moving.setType(move.getPromotion());
            moving.setOffset();
            moving.setPromoted(true);
        }

        board[move.getToSquare()] = moving;
        if (move.isEnPassant()) {
            board[move.getEnPassantSquare()] = null;
        }
        playedMoves.push(move);
        if (move.getCapture() != null || move.getPiece().getType() == Pieces.PAWN)
            halfmoveClock = 0;
        else
            halfmoveClock += 0.5;
        if (turn == Color.BLACK)
            moveNum++;
        moving.incrementMoveCount();
        Color c = notTurn;
        notTurn = turn;
        turn = c;
        int kic = kingInCheck(turn);
        if (kic != -1) {
            checks++;
            kingInCheck = kic;
        } else {
            kingInCheck = -1;
        }
        setFen(generateFEN());
        lastMove = move;
        
    }

    // not up to date
    public void makeMove(String uci) {
        this.oldBoard = Arrays.copyOfRange(board, 0, 64);
        Piece[] boardCopy = Arrays.copyOf(board, 64);
        boardStates.push(boardCopy);
       
        int from = 0;
        int to = 0;
        for (int i = 0; i < board.length; i++) {
            if (coords[i].equals(uci.substring(0, 2)))
                from = i;
            if (coords[i].equals(uci.substring(2, 4)))
                to = i;
        }
        allPositions.add(fen);
        Move move = new Move(from, to, board[to], board[from], null);
        Piece moving = board[move.getFromSquare()];
        board[move.getFromSquare()] = null;

        if (move.isPromotion()) {
            moving.setType(move.getPromotion());
            moving.setOffset();
            moving.setPromoted(true);
        }

        board[move.getToSquare()] = moving;
        if (move.isEnPassant()) {
            board[move.getEnPassantSquare()] = null;
        }
        playedMoves.push(move);
        if (move.getCapture() != null || move.getPiece().getType() == Pieces.PAWN)
            halfmoveClock = 0;
        else
            halfmoveClock += 0.5;
        if (turn == Color.BLACK)
            moveNum++;
        moving.incrementMoveCount();
        Color c = notTurn;
        notTurn = turn;
        turn = c;
        int kic = kingInCheck(turn);
        if (kic != -1) {
            checks++;
            kingInCheck = kic;
        } else {
            kingInCheck = -1;
        }
        setFen(generateFEN());
        lastMove = move;
    }

    


    public ArrayList<Integer> getSolvePin() {
        return solvePin;
    }

    public Move newPop(){
        if (!boardStates.isEmpty()) {
            Piece[] oldBoardState = boardStates.pop();
            this.board = Arrays.copyOf(oldBoardState, 64);
        }
        allPositions.pop();
        Move m = playedMoves.pop();
        lastMove = m;
       
        if (m.isEnPassant()) m.getCapture().decrementMoveCount();
        if (m.isPromotion()) {
            m.getPiece().setType(Pieces.PAWN);
            m.getPiece().setOffset();
            m.getPiece().setPromoted(false);
        }
        
        m.getPiece().decrementMoveCount();
        Color c = notTurn;
        notTurn = turn;
        turn = c;
        kingInCheck = -1;
        return m;
    }

    public Move popMove() {
        allPositions.pop();
        Move m = playedMoves.pop();
        lastMove = m;
        Piece moved = board[m.getToSquare()];
        int castle = m.getCastle();

        if (m.isEnPassant()) {
            board[m.getEnPassantSquare()-16] = m.getCapture();
            m.getCapture().decrementMoveCount();
            board[m.getToSquare()] = null;
            board[m.getFromSquare()] = moved;
        } else {
            board[m.getToSquare()] = m.getCapture();
        }
        board[m.getFromSquare()] = moved;

       
        if (castle != -1) {
            // king side
            if (castle == 1) {
                board[m.getFromSquare() + 3] = board[m.getFromSquare() + 1];
            }
            // queen side
            if (castle == 0) {
                board[m.getFromSquare() - 4] = board[m.getFromSquare() - 1];
            }
        }

        kingInCheck = -1;
        moved.decrementMoveCount();
        Color c = notTurn;
        notTurn = turn;
        turn = c;

        return m;

    }

    public void setFen(String fen) {
        this.fen = fen;
    }

    public int findKing(Color color) {
        for (int i = 0; i < board.length; i++) {
            if (board[i] != null) {
                if (isThatPiece(color, Pieces.KING, board[i]))
                    return i;
            }
        }
        return 0;
    }

    public boolean isThreefoldRepetition() {
        return Collections.frequency(allPositions, getFEN()) == 3;
    }

    public boolean fiftyMoveRule() {
        return halfmoveClock == 50.0;
    }

    public Color getTurn() {
        return turn;
    }

    public Color getNotTurn() {
        return notTurn;
    }

    public Stack<Move> getPlayedMoves() {
        return playedMoves;
    }

    public int getKingInCheck(){
        return kingInCheck;
    }

    public Move getEnPassant(Color color) {
        Move elPassant = null;
        if (this.lastMove == null)
            return elPassant;
        Piece movedPiece = this.lastMove.getPiece();
        if (movedPiece.getType() == Pieces.PAWN && movedPiece.getColor() != color && !this.lastMove.isEnPassant()) {
            int pfr = this.lastMove.getFromSquare();
            int pto = this.lastMove.getToSquare();
            int n1 = mailbox[mailbox64[pto] - 1];
            int n2 = mailbox[mailbox64[pto] + 1];
            if (Math.abs(pto - pfr) == 16 && n1 != -1 && n2 != -1) {
                if (board[pto - 1] != null) {
                    Piece p = board[pto - 1];
                    if (p.getType() == Pieces.PAWN && board[pto - 1].getColor() == color) {
                        this.board[pto - 1] = null;
                        if (isPinned(pto, color) == null) {
                            elPassant = new Move(pto - 1, pto - Math.floorDiv(pto - pfr, 2), board[pto], p, pto);
                        }
                        this.board[pto - 1] = p;

                    }
                }
                if (board[pto + 1] != null) {
                    Piece p = board[pto + 1];
                    if (p.getType() == Pieces.PAWN && p.getColor() == color) {
                        this.board[pto + 1] = null;
                        if (isPinned(pto, color) == null) {
                            elPassant = new Move(pto + 1, pto - Math.floorDiv(pto - pfr, 2), board[pto], p, pto);
                        }
                        this.board[pto + 1] = p;
                    }
                }
            }
        }
        return elPassant;
    }

    public int getMoveNum() {
        return moveNum;
    }

    public Move getLastMove() {
        if (playedMoves.size() > 0)
            return playedMoves.peek();
        return null;

    }

    public boolean isThatPiece(Color color, Pieces type, Piece piece) {
        if (piece == null)
            return false;
        return piece.getColor() == color && piece.getType() == type;
    }

    public double getHalfmoveClock() {
        return halfmoveClock;
    }

    public void setHalfMove(int clock) {
        halfmoveClock = clock;
    }

    public void setFullMove(int c) {
        moveNum = c;
    }

    public boolean getIsCheckmate() {
        getLegalMoves();
        return isCheckmate;
    }

    public boolean getIsStalemate() {
        return isStalemate;
    }

    public boolean isDraw() {
        getLegalMoves();
        return getIsDrawByAgreement() || getIsStalemate() || fiftyMoveRule() || isThreefoldRepetition();
    }

    public boolean getIsDrawByAgreement() {
        return whiteDraw && blackDraw;
    }

    public int[] getPieceCount(Color color) {
        int[] pCounts = new int[6];
        for (int i = 0; i < board.length; i++) {
            if (board[i] != null) {
                if (board[i].getColor() == color)
                    pCounts[board[i].getType().ordinal()]++;
            }
        }
        return pCounts;
    }

    public boolean isInsufficientMaterial() {
        int[] wpc = getPieceCount(Color.WHITE);
        int[] bpc = getPieceCount(Color.BLACK);
        if (wpc[0] + bpc[0] == 0 || wpc[3] + bpc[3] + wpc[4] + bpc[4] == 0) {
            if (wpc[1] + bpc[1] + wpc[2] + bpc[2] == 0)
                return true;
            if (wpc[1] + bpc[1] > 0 && wpc[2] + bpc[2] == 0)
                return true;
            if (wpc[1] + bpc[1] == 0 && wpc[2] + bpc[2] > 0)
                return true;
        }
        return false;
    }

    public boolean canPromote(Color color, int square) {
        if (square == -1)
            return false;
        char row = coords[square].charAt(1);

        if (row == '7' && color == Color.WHITE) {
            return true;
        }
        if (row == '2' && color == Color.BLACK)
            return true;
        return false;
    }

    public ArrayList<Move> addPromotions(int from, int to, Piece capture, Piece piece) {
        ArrayList<Move> promotions = new ArrayList<>();
        promotions.add(new Move(from, to, board[to], piece, Pieces.QUEEN));
        promotions.add(new Move(from, to, board[to], piece, Pieces.BISHOP));
        promotions.add(new Move(from, to, board[to], piece, Pieces.ROOK));
        promotions.add(new Move(from, to, board[to], piece, Pieces.KNIGHT));
        return promotions;
    }


    private boolean doubleCheck(int attackerSquare){
        Piece attacker = board[attackerSquare];
        board[attackerSquare] = null;
        int a = kingInCheck(turn);
        board[attackerSquare] = attacker;
        if (a != -1) return true;
        return false;
    }

    public String toString() {
        String boardString = "";
        for (int i = 0; i < board.length; i++) {
            if (board[i] != null) {
                boardString += String.format(" %c ", board[i].getUnicodeSymbol());
            } else {
                boardString += " · ";
            }
            if ((i + 1) % 8 == 0)
                boardString += "\n";
        }
        return boardString;
    }

    public Piece[] getOld(){
        return oldBoard;
    }

    public boolean isKing(int square){
        if (square == -1) return false;
        if (board[square] == null) return false;
        return board[square].getType() == Pieces.KING;
    }

}
