package com.models;

public class Board {
    private static final int SIZE = 4;

    private Player[][] cells;

    public Board() {
        this.cells = new Player[SIZE][SIZE];
    }

    public Board(Player[][] cells) {
        if (cells.length != SIZE) {
            throw new IllegalArgumentException(String.format("Board must have %s rows, but has %s", SIZE, cells.length));
        }
        this.cells = new Player[cells.length][];
        
        for (int i = 0; i < cells.length; i++) {
            if (cells[i].length != SIZE) {
                throw new IllegalArgumentException(String.format("Board must have %s columns, but row %s has %s", SIZE, i, cells[i].length));
            }
            this.cells[i] = cells[i].clone();
        }
    }

    public Player getCell(int row, int col) {
        return cells[row][col];
    }

    public static int getSize() {
        return SIZE;
    }
}
