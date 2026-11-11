package com;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.models.Board;
import com.models.Player;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class TicTacToeTest {
    private static final int SIZE = 4;

    private static final Board BOX_NEAR_MISS = board(
        "XX..",
        "XO..",
        "....",
        "....");

    private static final Board CORNER_NEAR_MISS = board(
        "X..X",
        "....",
        "....",
        "X..O");

    private static final Board DIAGONAL_NEAR_MISS = board(
        "X...",
        ".X..",
        "..X.",
        "...O");

    private static final Board COL_NEAR_MISS = board(
        "X...",
        "X...",
        "X...",
        "O...");

    private static final Board ROW_NEAR_MISS = board(
        "XXXO",
        "....",
        "....",
        "....");

    private static final String[] FULL_NO_WIN = {
        "XXOO",
        "OOXX",
        "XXOO",
        "OOXX",
    };

    @Nested
    class CheckWinner {

        @ParameterizedTest(name = "row {0}")
        @MethodSource("com.TicTacToeTest#indices")
        void horizontalWin(int row) {
            Player winner = Player.O;
            Board b = boardWith(winner, new int[] {row, 0}, new int[] {row, 1}, new int[] {row, 2}, new int[] {row, 3});
            assertEquals(Optional.of(winner), TicTacToe.checkWinner(b));
        }

        @ParameterizedTest(name = "column {0}")
        @MethodSource("com.TicTacToeTest#indices")
        void verticalWin(int col) {
            Player winner = Player.X;
            Board b = boardWith(winner, new int[] {0, col}, new int[] {1, col}, new int[] {2, col}, new int[] {3, col});
            assertEquals(Optional.of(winner), TicTacToe.checkWinner(b));
        }

        @Test
        void mainDiagonalWin() {
            Board b = board(
                "X...",
                ".X..",
                "..X.",
                "...X");
            assertEquals(Optional.of(Player.X), TicTacToe.checkWinner(b));
        }

        @Test
        void antiDiagonalWin() {
            Board b = board(
                "...O",
                "..O.",
                ".O..",
                "O...");
            assertEquals(Optional.of(Player.O), TicTacToe.checkWinner(b));
        }

        @Test
        void fourCornersWin() {
            Board b = board(
                "X..X",
                "....",
                "....",
                "X..X");
            assertEquals(Optional.of(Player.X), TicTacToe.checkWinner(b));
        }

        @ParameterizedTest(name = "box at ({0},{1})")
        @MethodSource("com.TicTacToeTest#boxPositions")
        void boxWin(int row, int col) {
            Player winner = Player.O;
            Board b = boardWith(winner,
                new int[] {row, col}, new int[] {row, col + 1},
                new int[] {row + 1, col}, new int[] {row + 1, col + 1});
            assertEquals(Optional.of(winner), TicTacToe.checkWinner(b));
        }

        @Test
        void emptyBoardHasNoWinner() {
            assertEquals(Optional.empty(), TicTacToe.checkWinner(new Board()));
        }

        @Test
        void fullBoardWithoutWinHasNoWinner() {
            assertEquals(Optional.empty(), TicTacToe.checkWinner(board(FULL_NO_WIN)));
        }

        @ParameterizedTest(name = "{0}")
        @MethodSource("com.TicTacToeTest#nearMisses")
        void nearMissHasNoWinner(String name, Board b) {
            assertEquals(Optional.empty(), TicTacToe.checkWinner(b));
        }
    }


    @Nested
    class AnyMovesLeft {

        @Test
        void emptyBoardHasMoves() {
            assertTrue(TicTacToe.anyMovesLeft(new Board()));
        }

        @Test
        void onlyLastCellEmptyHasMoves() {
            Board b = board(
                "XXOO",
                "OOXX",
                "XXOO",
                "OOX.");
            assertTrue(TicTacToe.anyMovesLeft(b));
        }

        @Test
        void fullBoardHasNoMoves() {
            assertFalse(TicTacToe.anyMovesLeft(board(FULL_NO_WIN)));
        }
    }

    @Nested
    class IsGameOver {

        @Test
        void inProgressGameIsNotOver() {
            Board b = board(
                "XO..",
                "....",
                "....",
                "....");
            assertFalse(TicTacToe.isGameOver(b));
        }

        @Test
        void winnerWithMovesLeftIsOver() {
            Board b = board(
                "XXXX",
                "OO..",
                "O...",
                "....");
            assertTrue(TicTacToe.isGameOver(b));
        }

        @Test
        void drawIsOver() {
            assertTrue(TicTacToe.isGameOver(board(FULL_NO_WIN)));
        }
    }


    static IntStream indices() {
        return IntStream.range(0, SIZE);
    }

    static Stream<Arguments> boxPositions() {
        return IntStream.range(0, SIZE - 1).boxed()
            .flatMap(r -> IntStream.range(0, SIZE - 1).mapToObj(c -> Arguments.of(r, c)));
    }

    static Stream<Arguments> nearMisses() {
        return Stream.of(
            Arguments.of("row", ROW_NEAR_MISS),
            Arguments.of("column", COL_NEAR_MISS),
            Arguments.of("diagonal", DIAGONAL_NEAR_MISS),
            Arguments.of("corners", CORNER_NEAR_MISS),
            Arguments.of("box", BOX_NEAR_MISS));
    }

        private static Board board(String... rows) {
        Player[][] cells = new Player[SIZE][SIZE];
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                char ch = rows[r].charAt(c);
                cells[r][c] = switch (ch) {
                    case 'X' -> Player.X;
                    case 'O' -> Player.O;
                    case '.' -> null;
                    default -> throw new IllegalArgumentException("Bad cell: " + ch);
                };
            }
        }
        return new Board(cells);
    }

    private static Board boardWith(Player p, int[]... coords) {
        Player[][] cells = new Player[SIZE][SIZE];
        for (int[] rc : coords) {
            cells[rc[0]][rc[1]] = p;
        }
        return new Board(cells);
    }
}
