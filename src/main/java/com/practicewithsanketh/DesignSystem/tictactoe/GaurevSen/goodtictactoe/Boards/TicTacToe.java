package com.practicewithsanketh.DesignSystem.tictactoe.GaurevSen.goodtictactoe.Boards;

import com.practicewithsanketh.DesignSystem.tictactoe.GaurevSen.goodtictactoe.game.Board;
import com.practicewithsanketh.DesignSystem.tictactoe.GaurevSen.goodtictactoe.game.Cell;

public class TicTacToe extends Board {
    String cells[][] = new String[3][3];

    public String getCells(int row, int col) {
        return cells[row][col];
    }

    public void setCell(Cell cell , String symbol) {
        cells[cell.getRow()][cell.getCol()] = symbol;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String val = (cells[i][j] == null) ? "-" : cells[i][j];
                result.append(" ").append(val).append(" ");
                if (j < 2) {
                    result.append("|");
                }
            }
            result.append("\n");
            if (i < 2) {
                result.append("---+---+---\n");
            }
        }
        return result.toString();
    }
}
