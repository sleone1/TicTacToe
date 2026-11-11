package com;

import java.util.Optional;

import com.models.Board;
import com.models.Player;

public class TicTacToe {

    public static Optional<Player> checkWinner(Board board) {
        for (int i = 0; i < board.getSize(); i++) {
            Optional<Player> rowWinner = getSetWinner(board.getRow(i));
            if (rowWinner.isPresent()) {
                return rowWinner;
            }
            
            Player[] column = {
                board.getCell(0, i),
                board.getCell(1, i),
                board.getCell(2, i),
                board.getCell(3, i)};
            Optional<Player> columnWinner = getSetWinner(column);
            if (columnWinner.isPresent()) {
                return columnWinner;
            }
        }

        Player[] downDiagonal = {
            board.getCell(0, 0),
            board.getCell(1, 1),
            board.getCell(2, 2),
            board.getCell(3, 3)};
        Optional<Player> downDiagonalWinner = getSetWinner(downDiagonal);
        if (downDiagonalWinner.isPresent()) {
            return downDiagonalWinner;
        }

        Player[] upDiagonal = {
            board.getCell(0, 3),
            board.getCell(1, 2),
            board.getCell(2, 1),
            board.getCell(3, 0)};
        Optional<Player> upDiagonalWinner = getSetWinner(upDiagonal);
        if (upDiagonalWinner.isPresent()) {
            return upDiagonalWinner;
        }

        Player[] fourCorners = {
            board.getCell(0, 0),
            board.getCell(0, board.getSize() - 1),
            board.getCell(board.getSize() - 1, 0),
            board.getCell(board.getSize() - 1, board.getSize() - 1)};
        Optional<Player> fourCornersWinner = getSetWinner(fourCorners);
        if (fourCornersWinner.isPresent()) {
            return fourCornersWinner;
        }

        for (int i = 0; i < board.getSize() - 1; i++) {
            for (int j = 0; j < board.getSize() - 1; j++) {
                Player[] box = {
                    board.getCell(i, j),
                    board.getCell(i, j + 1),
                    board.getCell(i + 1, j),
                    board.getCell(i + 1, j + 1)};
                Optional<Player> boxWinner = getSetWinner(box);
                if (boxWinner.isPresent()) {
                    return boxWinner;
                }
            }
        }

        return Optional.empty();
    }

    public static boolean anyMovesLeft(Board board) {
        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                if (board.getCell(row, col) == null) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isGameOver(Board board) {
        return checkWinner(board).isPresent() || !anyMovesLeft(board);
    }

    private static Optional<Player> getSetWinner(Player[] set) {
        Player first = set[0];
        if (first == null) {
            return Optional.empty();
        }
        for (Player p : set) {
            if (p != first) {
                return Optional.empty();
            }
        }
        return Optional.of(first);
    }
}
