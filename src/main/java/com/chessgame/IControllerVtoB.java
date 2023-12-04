package com.chessgame;

import com.chessgame.chess.Move;

public interface IControllerVtoB { 
    void applyFen(String fen);
    void makeMove(Move m);

} 