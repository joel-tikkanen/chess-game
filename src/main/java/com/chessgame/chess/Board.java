package com.chessgame.chess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;

public class Board {

    private final Piece EMPTY = new Piece();

    public static final String[] coordinates = {
            "a8", "b8", "c8", "d8", "e8", "f8", "g8", "h8",
            "a7", "b7", "c7", "d7", "e7", "f7", "g7", "h7",
            "a6", "b6", "c6", "d6", "e6", "f6", "g6", "h6",
            "a5", "b5", "c5", "d5", "e5", "f5", "g5", "h5",
            "a4", "b4", "c4", "d4", "e4", "f4", "g4", "h4",
            "a3", "b3", "c3", "d3", "e3", "f3", "g3", "h3",
            "a2", "b2", "c2", "d2", "e2", "f2", "g2", "h2",
            "a1", "b1", "c1", "d1", "e1", "f1", "g1", "h1",
    };

    private final int[] mailbox = {
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

    private final int[] mailbox64 = {
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
            EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY,
            EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY,
            EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY,
            EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY,
            new Piece(Color.WHITE, Pieces.PAWN), new Piece(Color.WHITE, Pieces.PAWN),
            new Piece(Color.WHITE, Pieces.PAWN), new Piece(Color.WHITE, Pieces.PAWN),
            new Piece(Color.WHITE, Pieces.PAWN), new Piece(Color.WHITE, Pieces.PAWN),
            new Piece(Color.WHITE, Pieces.PAWN), new Piece(Color.WHITE, Pieces.PAWN),
            new Piece(Color.WHITE, Pieces.ROOK), new Piece(Color.WHITE, Pieces.KNIGHT),
            new Piece(Color.WHITE, Pieces.BISHOP), new Piece(Color.WHITE, Pieces.QUEEN),
            new Piece(Color.WHITE, Pieces.KING), new Piece(Color.WHITE, Pieces.BISHOP),
            new Piece(Color.WHITE, Pieces.KNIGHT), new Piece(Color.WHITE, Pieces.ROOK),

    };

    private final Piece[] STARTING_BOARD = {
            new Piece(Color.BLACK, Pieces.ROOK), new Piece(Color.BLACK, Pieces.KNIGHT),
            new Piece(Color.BLACK, Pieces.BISHOP), new Piece(Color.BLACK, Pieces.QUEEN),
            new Piece(Color.BLACK, Pieces.KING), new Piece(Color.BLACK, Pieces.BISHOP),
            new Piece(Color.BLACK, Pieces.KNIGHT), new Piece(Color.BLACK, Pieces.ROOK),
            new Piece(Color.BLACK, Pieces.PAWN), new Piece(Color.BLACK, Pieces.PAWN),
            new Piece(Color.BLACK, Pieces.PAWN), new Piece(Color.BLACK, Pieces.PAWN),
            new Piece(Color.BLACK, Pieces.PAWN), new Piece(Color.BLACK, Pieces.PAWN),
            new Piece(Color.BLACK, Pieces.PAWN), new Piece(Color.BLACK, Pieces.PAWN),
            EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY,
            EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY,
            EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY,
            EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY,
            new Piece(Color.WHITE, Pieces.PAWN), new Piece(Color.WHITE, Pieces.PAWN),
            new Piece(Color.WHITE, Pieces.PAWN), new Piece(Color.WHITE, Pieces.PAWN),
            new Piece(Color.WHITE, Pieces.PAWN), new Piece(Color.WHITE, Pieces.PAWN),
            new Piece(Color.WHITE, Pieces.PAWN), new Piece(Color.WHITE, Pieces.PAWN),
            new Piece(Color.WHITE, Pieces.ROOK), new Piece(Color.WHITE, Pieces.KNIGHT),
            new Piece(Color.WHITE, Pieces.BISHOP), new Piece(Color.WHITE, Pieces.QUEEN),
            new Piece(Color.WHITE, Pieces.KING), new Piece(Color.WHITE, Pieces.BISHOP),
            new Piece(Color.WHITE, Pieces.KNIGHT), new Piece(Color.WHITE, Pieces.ROOK),
    };

    private final Piece[] EMPTY_BOARD = {
            EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY,
            EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY,
            EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY,
            EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY,
            EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY,
            EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY,
            EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY,
            EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY,
    };

    public Piece[] oldBoard = Arrays.copyOfRange(board, 0, 64);

    public final String STARTING_FEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
    private String fen = STARTING_FEN;

    private Color turn = Color.WHITE;
    private Color notTurn = Color.BLACK;

    private Color inCheck = Color.EMPTY;

    private boolean isStalemate = false;
    private double halfmoveClock = 0;
    private int fullMoveClock = 1;

    private boolean whiteDraw = false;
    private boolean blackDraw = false;

    private Stack<String> allPositions = new Stack<>();
    private Stack<Move> playedMoves = new Stack<Move>();
    Stack<Piece[]> boardStates = new Stack<>();

    private ArrayList<Move> legalMoves = new ArrayList<>();
    private ArrayList<Move> oldLegalMoves = new ArrayList<>();;

    private int epTarget = -1;
    private int ep = -1;
    private int eps = -1;

    private boolean draw = false;

    public Board() {
        fen = STARTING_FEN;
        genLegal();
        allPositions.add(fen);

    }

    public Board(String fen) {
        setFen(fen);
        genLegal();

        allPositions.add(fen);
    }

    public void genLegal() {
        legalMoves = new ArrayList<>();
        oldLegalMoves = new ArrayList<Move>(legalMoves);
        /* Get pseudo legal moves in the means of offsets */
        for (int i = 0; i < board.length; i++) {
            Piece p = board[i];
            if (p.getColor() == turn) {
                if (p.getType() != Pieces.PAWN) {
                    int[] po = p.getOffset();
                    for (int k = 0; k < p.getOffsets(); k++) {
                        for (int n = i;;) {
                            n = mailbox[mailbox64[n] + po[k]];
                            if (n == -1)
                                break;
                            if (board[n].getColor() != Color.EMPTY) {
                                if (board[n].getColor() == notTurn)
                                    validateMove(new Move(i, n, Flag.CAPTURE, p));
                                break;
                            }

                            validateMove(new Move(i, n, Flag.QUIET, p));
                            if (!p.isSliding())
                                break;
                        }
                    }
                } else {
                    // PAWN CODIUM
                    int m = new int[] { -1, 1 }[turn.ordinal()];

                    int n, u;
                    int[] c = { 9 * m, 11 * m };
                    int[] pp = { 10 * m, 20 * m };

                    for (u = 0; u < c.length; u++) {
                        n = mailbox[mailbox64[i] + c[u]];
                        if (n != -1) {
                            if (board[n].getColor() == notTurn) {
                                validateMove(new Move(i, n, Flag.CAPTURE, p));
                            }
                        }
                    }
                    int l = board[i].hasMoved() ? pp.length - 1 : pp.length;
                    for (u = 0; u < l; u++) {
                        n = mailbox[mailbox64[i] + pp[u]];
                        if (n == -1)
                            break;
                        if (board[n].getColor() != Color.EMPTY)
                            break;
                        validateMove(new Move(i, n, u == 1 ? Flag.PP : Flag.QUIET, p));
                    }
                }
            }
        }

        genSpecial();

        if (legalMoves.size() == 0 && !isCheckmate())
            isStalemate = true;
    }

    public Move[] availableCastling(Color color) {
        Move[] castles = { null, null };
        int[] pp = color == Color.WHITE ? new int[] { 56, 60, 63 } : new int[] { 0, 4, 7 };

        if (isThatPiece(color, Pieces.ROOK, board[pp[0]]) && isThatPiece(color, Pieces.KING, board[pp[1]])) {
            if (!board[pp[0]].hasMoved() && !board[pp[1]].hasMoved()) {
                castles[0] = new Move(pp[1], pp[1] - 2, Flag.CQ, board[pp[1]]);
            }
        }

        if (isThatPiece(color, Pieces.KING, board[pp[1]]) && isThatPiece(color, Pieces.ROOK, board[pp[2]])) {
            if (!board[pp[1]].hasMoved() && !board[pp[2]].hasMoved()) {
                castles[1] = new Move(pp[1], pp[1] + 2, Flag.CK, board[pp[1]]);
            }
        }
        return castles;
    }

    /*
     * index 0 = queen side, index 1 = king side
     */
    public Move[] castlingRights() {
        int kingPos = turn == Color.WHITE ? 60 : 4;
        Move[] castles = availableCastling(turn);
        if (kingInCheck(turn) != -1)
            return new Move[] { null, null };
        int[] qs = { kingPos - 1, kingPos - 2, kingPos - 3 };
        int[] ks = { kingPos + 1, kingPos + 2 };
        for (int square = 0; square < qs.length - 1; square++) {
            if (isSquareAttacked(qs[square], notTurn) != -1)
                castles[0] = null;
        }

        for (Integer square : ks)
            if (isSquareAttacked(square, notTurn) != -1)
                castles[1] = null;
        if (board[qs[0]] != EMPTY || board[qs[1]] != EMPTY || board[qs[2]] != EMPTY)
            castles[0] = null;
        if (board[ks[0]] != EMPTY || board[ks[1]] != EMPTY)
            castles[1] = null;
        return castles;
    }

    public void enPassant(Color color) {
        if (epTarget != -1) {
            // validate and finalize

            validateAndAdd(new Move(ep, eps, Flag.EP, board[ep]));
        }
    }

    // Castles and en passants
    private void genSpecial() {

        // add castles
        Move[] castling = castlingRights();
        if (castling[0] != null)
            legalMoves.add(castling[0]);
        if (castling[1] != null)
            legalMoves.add(castling[1]);
        // add en passant
        enPassant(turn);
    }

    private void validateMove(Move move) {
        switch (move.getPiece().getType()) {
            case PAWN:
                genPawn(move);
                break;
            default:
                validateAndAdd(move);
        }
    }

    private void genPawn(Move move) {
        // check promotions
        boolean canPromote = canPromote(move.getFromSquare(), move.getPiece().getColor());
        if (validate(move)) {
            if (canPromote) {
                legalMoves.add(new Move(move.getFromSquare(), move.getToSquare(), Flag.PRR, move.getPiece()));
                legalMoves.add(new Move(move.getFromSquare(), move.getToSquare(), Flag.PRN, move.getPiece()));
                legalMoves.add(new Move(move.getFromSquare(), move.getToSquare(), Flag.PRQ, move.getPiece()));
                legalMoves.add(new Move(move.getFromSquare(), move.getToSquare(), Flag.PRB, move.getPiece()));
            } else {
                legalMoves.add(move);
            }
        }
    }

    // Move making
    private void handleFlag(Move move, boolean test) {

        switch (move.getFlag()) {
            case EP:
                board[turn == Color.WHITE ? move.getToSquare() + 8 : move.getToSquare() - 8] = EMPTY;
                break;
            case CQ:
                handleCastle(Flag.CQ);
                break;
            case CK:
                handleCastle(Flag.CK);
                break;
            case PP:

                if (!test)
                    setEP(move);
                break;
            case PRB:
                handlePr(move, Pieces.BISHOP);
                break;
            case PRQ:
                handlePr(move, Pieces.QUEEN);
                break;
            case PRN:
                handlePr(move, Pieces.KNIGHT);
                break;
            case PRR:
                handlePr(move, Pieces.ROOK);
                break;
            default:
                break;
        }

        board[move.getToSquare()] = move.getPiece();
        board[move.getFromSquare()] = EMPTY;
    }

    private void handlePr(Move move, Pieces type) {
        move.getPiece().setType(type);
        move.getPiece().setAttributes();
    }

    private void handleCastle(Flag cType) {
        int rPos;
        switch (cType) {
            case CK:
                rPos = turn == Color.WHITE ? 63 : 7;
                board[rPos - 2] = board[rPos];
                board[rPos] = EMPTY;
                break;
            case CQ:
                rPos = turn == Color.WHITE ? 56 : 0;
                board[rPos + 3] = board[rPos];
                board[rPos] = EMPTY;
                break;
            default:
                break;
        }
    }

    private boolean validateAndAdd(Move move) {
        boolean v = validate(move);
        if (v)
            legalMoves.add(move);
        return v;
    }

    public void pushMove(Move move) {
        setOld();
        handleFlag(move, true);
    }

    public void popMove() {
        toOldBoard();
        inCheck = Color.EMPTY;

    }

    public void unmakeMove() {
        // restore board state
        toOldBoard();
        Move m = playedMoves.pop();
        if (m.isPromotion()) {
            m.getPiece().setType(Pieces.PAWN);
            m.getPiece().setColor(m.getPiece().getColor());
            m.getPiece().setAttributes();
        }
        m.getPiece().decCount();
        changeTurn();
        resetEP();
        legalMoves = oldLegalMoves;
    }

    public void makeMove(Move move) {
        oldLegalMoves = legalMoves;
        // Board states
        resetEP();
        setOld();
        Piece moving = move.getPiece();
        handleFlag(move, false);
        playedMoves.push(move);
        moving.incCount();
        changeTurn();
        setClocks(move);

        // TODO:
        // new fen
        // setFen(generateFEN());
        // allPositions.push(fen);
    }

    public void makeMove(String uci, Flag flag) {
        oldLegalMoves = legalMoves;
        int from = 0, to = 0, i = 0;
        for (i = 0; i < board.length; i++) {
            if (coordinates[i].equals(uci.substring(0, 2)))
                from = i;
            if (coordinates[i].equals(uci.substring(2, 4)))
                to = i;
        }
        resetEP();
        setOld();
        Piece moving = board[from];

        Move move = new Move(from, to, flag, moving);
        handleFlag(move, false);
        changeTurn();
        setClocks(move);

    }

    public boolean inCheck(Color color) {
        return inCheck == color;
    }

    private boolean canPromote(int square, Color color) {
        int[] promotionRanks = { 1, 6 };
        return Math.floorDiv(square, 8) == promotionRanks[color.ordinal()];
    }

    public int kingInCheck(Color color) {
        int kingPos = findKing(color);

        return isSquareAttacked(kingPos, oppColor(color));
    }

    public boolean isEmpty(int square) {
        return board[square].getType() == Pieces.EMPTY;
    }

    public int findKing(Color color) {
        for (int i = 0; i < board.length; i++) {
            if (board[i].getType() == Pieces.KING && board[i].getColor() == color)
                return i;
        }
        return -1;
    }

    public boolean isThatPiece(Color color, Pieces type, Piece piece) {
        return color == piece.getColor() && type == piece.getType();
    }

    public boolean isKing(int square) {
        if (square == -1)
            return false;
        return board[square].getType() == Pieces.KING;
    }

    public Color oppColor(Color color) {
        return Color.WHITE == color ? Color.BLACK : Color.WHITE;
    }

    public void changeTurn() {
        Color c = notTurn;
        notTurn = turn;
        turn = c;
    }

    public boolean isCheckmate() {
        return legalMoves.size() == 0 && !draw && playedMoves.size() > 0 && kingInCheck(oppColor(turn)) != -1;
    }

    public String generateFEN() {
        String fen = "";
        int k = 0;
        for (int i = 0; i < board.length; i++) {
            Piece p = board[i];
            if (p != EMPTY) {
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

        if (epTarget != -1) {
            int square = turn == Color.WHITE ? epTarget - 8 : epTarget + 8;
            fen += String.format(" %s", coordinates[square]);
        } else
            fen += " -";

        fen += String.format(" %d", (int) halfmoveClock);
        fen += String.format(" %d", fullMoveClock);
        return fen;
    }

    private void setEP(Move move) {

        int n1 = mailbox[mailbox64[move.getToSquare()] + 1];
        int n2 = mailbox[mailbox64[move.getToSquare()] - 1];
        if (n1 != -1) {
            if (isThatPiece(notTurn, Pieces.PAWN, board[n1])) {
                ep = n1;
                eps = turn == Color.WHITE ? move.getToSquare() + 8 : move.getToSquare() - 8;
                epTarget = move.getToSquare();
            }
        }
        if (n2 != -1) {
            if (isThatPiece(notTurn, Pieces.PAWN, board[n2])) {
                ep = n2;
                eps = turn == Color.WHITE ? move.getToSquare() + 8 : move.getToSquare() - 8;
                epTarget = move.getToSquare();
            }
        }
    }

    public void setFen(String fen) {

        this.fen = fen;

        String[] splitted = fen.split(" ");
        String board = splitted[0];
        String turn = splitted[1];

        String halfMove = splitted[4];
        String fullMove = splitted[5];

        int k = 0;
        Piece[] a = EMPTY_BOARD.clone();

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

        halfmoveClock = Integer.parseInt(halfMove);
        fullMoveClock = Integer.parseInt(fullMove);

        for (int i = 0; i < this.board.length; i++) {
            if (STARTING_BOARD[i] == EMPTY && this.board[i] != EMPTY) {
                this.board[i].incCount();
            } else if (STARTING_BOARD[i] != EMPTY && this.board[i] != EMPTY) {
                if (!STARTING_BOARD[i].equals(this.board[i]))
                    this.board[i].incCount();
            }
        }
    }

    private void resetEP() {
        ep = -1;
        epTarget = -1;
        eps = -1;
    }

    public String getFen() {
        return fen;
    }

    public Color getTurn() {
        return turn;
    }

    public Color getNotTurn() {
        return notTurn;
    }

    public Color isInCheck() {
        return inCheck;
    }

    public boolean isStalemate() {
        return isStalemate;
    }

    public double getHalfmoveClock() {
        return halfmoveClock;
    }

    public int getFullMoveClock() {
        return fullMoveClock;
    }

    public boolean isWhiteDraw() {
        return whiteDraw;
    }

    public boolean isBlackDraw() {
        return blackDraw;
    }

    public Stack<String> getAllPositions() {
        return allPositions;
    }

    public Stack<Move> getPlayedMoves() {
        return playedMoves;
    }

    public ArrayList<Move> getLegalMoves() {
        genLegal();
        return legalMoves;
    }

    public int getEpTarget() {
        return epTarget;
    }

    public int getEp() {
        return ep;
    }

    private void setClocks(Move move) {
        if (move.isCapture() || move.getPiece().getType() == Pieces.PAWN)
            halfmoveClock = 0;
        else
            halfmoveClock += 0.5;
        if (turn == Color.BLACK)
            fullMoveClock++;
    }

    private int isSquareAttacked(int square, Color color) {
        int[] dirs = { -11, -10, -9, -1, 1, 9, 10, 11 };
        int i, n;
        for (i = 0; i < dirs.length; i++) {
            n = mailbox[mailbox64[square] + dirs[i]];
            if (n == -1)
                continue;
            // king attacks
            if (isThatPiece(color, Pieces.KING, board[n]))
                return n;
            // bishop, quuen, rook attacks
            for (n = square;;) {
                n = mailbox[mailbox64[n] + dirs[i]];
                if (n == -1)
                    break;
                if (isEmpty(n))
                    continue;
                if (board[n].getColor() != color)
                    break;
                if (board[n].isSliding() && board[n].getColor() == color) {
                    for (int j = 0; j < board[n].getOffset().length; j++) {
                        if (board[n].getOffset()[j] == dirs[i] * -1) {
                            return n;
                        }
                    }
                }
                break;
            }
        }
        // knight attacks

        int[] nos = { -21, -19, -12, -8, 8, 12, 19, 21 };
        for (i = 0; i < nos.length; i++) {
            n = mailbox[mailbox64[square] + nos[i]];
            if (n == -1)
                continue;
            if (isThatPiece(color, Pieces.KNIGHT, board[n]))
                return n;
        }
        // pawn attacks
        int[] po = color == Color.WHITE ? new int[] { 11, 9 } : new int[] { -11, -9 };
        for (i = 0; i < po.length; i++) {
            n = mailbox[mailbox64[square] + po[i]];
            if (n == -1)
                continue;
            if (isThatPiece(color, Pieces.PAWN, board[n]))
                return n;
        }
        return -1;
    }

    public boolean isThreefoldRepetition() {
        return Collections.frequency(allPositions, getFen()) == 3;
    }

    public boolean fiftyMoveRule() {
        return halfmoveClock == 50.0;
    }

    public boolean getIsDrawByAgreement() {
        return whiteDraw && blackDraw;
    }

    public void setDraw() {
        draw = getIsDrawByAgreement() || isStalemate || fiftyMoveRule() || isThreefoldRepetition();
    }

    private boolean validate(Move move) {
        // moves piece and checks if it leaves own king in check.
        pushMove(move);
        // if not then move is added
        if (kingInCheck(turn) != -1) {
            popMove();
            return false;
        }
        popMove();
        return true;
    }

    public String toString() {
        String boardString = "";
        for (int i = 0; i < board.length; i++) {
            if (board[i] != EMPTY)
                boardString += String.format(" %c ", board[i].getSymbol());
            else
                boardString += " · ";

            if ((i + 1) % 8 == 0)
                boardString += "\n";
        }
        return boardString;
    }

    public Piece[] getBoard() {
        return board;
    }

    private void toOldBoard() {
        if (!boardStates.isEmpty()) {
            Piece[] oldBoardState = boardStates.pop();
            board = Arrays.copyOf(oldBoardState, 64);
        }
    }

    private void setOld() {
        oldBoard = Arrays.copyOfRange(board, 0, 64);
        Piece[] boardCopy = Arrays.copyOf(board, 64);
        boardStates.push(boardCopy);
    }

}
