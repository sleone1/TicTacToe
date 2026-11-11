package com;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.models.Board;
import com.models.Player;

public class TicTacToe {

    private static final List<int[][]> WIN_LINES = buildWinLines(Board.getSize());

    public static Optional<Player> checkWinner(Board board) {
        for (int[][] line : WIN_LINES) {
            Optional<Player> winner = getLineWinner(board, line);
            if (winner.isPresent()) {
                return winner;
            }
        }
        return Optional.empty();
    }

    public static boolean anyMovesLeft(Board board) {
        for (int row = 0; row < Board.getSize(); row++) {
            for (int col = 0; col < Board.getSize(); col++) {
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

    private static Optional<Player> getLineWinner(Board board, int[][] line) {
        Player first = board.getCell(line[0][0], line[0][1]);
        if (first == null) {
            return Optional.empty();
        }
        for (int[] cell : line) {
            if (board.getCell(cell[0], cell[1]) != first) {
                return Optional.empty();
            }
        }
        return Optional.of(first);
    }

    private static List<int[][]> buildWinLines(int size) {
        List<int[][]> lines = new ArrayList<>();
        int last = size - 1;

        for (int i = 0; i < size; i++) {
            int[][] row = new int[size][];
            int[][] column = new int[size][];
            for (int k = 0; k < size; k++) {
                row[k] = new int[] {i, k};
                column[k] = new int[] {k, i};
            }
            lines.add(row);
            lines.add(column);
        }

        int[][] downDiagonal = new int[size][];
        int[][] upDiagonal = new int[size][];
        for (int k = 0; k < size; k++) {
            downDiagonal[k] = new int[] {k, k};
            upDiagonal[k] = new int[] {k, last - k};
        }
        lines.add(downDiagonal);
        lines.add(upDiagonal);

        int[][] corners = new int[][] {{0, 0}, {0, last}, {last, 0}, {last, last}};
        lines.add(corners);

        for (int i = 0; i < last; i++) {
            for (int j = 0; j < last; j++) {
                int[][] box = new int[][] {{i, j}, {i, j + 1}, {i + 1, j}, {i + 1, j + 1}};
                lines.add(box);
            }
        }

        return List.copyOf(lines);
    }
}
