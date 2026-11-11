package com.models;

public class Board {
    private Player[][] cells;

    public Board() {
        this.cells = new Player[4][4];
    }

    public Board(Player[][] cells) {
        this.cells = new Player[cells.length][];
        for (int i = 0; i < cells.length; i++) {
            this.cells[i] = cells[i].clone();
        }
    }

    public Player getCell(int row, int col) {
        return cells[row][col];
    }
}
