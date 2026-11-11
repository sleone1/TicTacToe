package com;

import com.models.Board;
import com.models.Player;

public class TicTacToe {

    public static Player checkWinner(Board board) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public static boolean anyMovesLeft(Board board) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public static boolean isGameOver(Board board) {
        return checkWinner(board) != null || !anyMovesLeft(board);
    }
}
