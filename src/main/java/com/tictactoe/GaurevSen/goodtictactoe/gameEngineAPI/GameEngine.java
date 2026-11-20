package com.practicewithsanketh.DesignSystem.tictactoe.GaurevSen.goodtictactoe.gameEngineAPI;


import com.practicewithsanketh.DesignSystem.tictactoe.GaurevSen.goodtictactoe.Boards.TicTacToe;
import com.practicewithsanketh.DesignSystem.tictactoe.GaurevSen.goodtictactoe.game.*;

public class GameEngine {
    public Board start(String type) {
        if (type.equalsIgnoreCase("TicTacToe")) {
            return new TicTacToe();
        } else {
            throw new IllegalArgumentException();
        }

    }

    public void move(Board board, Player player, Move move) {
        if (board instanceof TicTacToe) {
            TicTacToe board1 = (TicTacToe) board;
            board1.setCell(Move.getCell(), player.symbol());
        } else {
            throw new IllegalArgumentException();
        }
    }

    public GameResult isComplete(Board board1) {
        boolean rowComplete = false;
        boolean ColumnComplete = false;
        boolean firstDiagonal = false;
        boolean secDiagonal = false;
        String firstChar = "";

        if (board1 instanceof TicTacToe) {
            TicTacToe board = (TicTacToe) board1;
            for (int i = 0; i < 3; i++) {
                firstChar = board.getCells(i, 0);
                rowComplete = firstChar != null;
                if (firstChar != null) {
                    for (int j = 1; j < 3; j++) {
                        if (!firstChar.equals(board.getCells(i, j))) {
                            rowComplete = false;
                            break;
                        }
                    }
                    if (rowComplete) {
                        return new GameResult(true, firstChar);
                    }
                }
            }

            for (int i = 0; i < 3; i++) {
                firstChar = board.getCells(0, i);
                ColumnComplete = firstChar != null;
                if (firstChar != null) {
                    for (int j = 1; j < 3; j++) {
                        if (!firstChar.equals(board.getCells(j, i))) {
                            ColumnComplete = false;
                            break;
                        }
                    }
                    if (ColumnComplete) {
                        return new GameResult(true, firstChar);
                    }
                }
            }

            firstChar = board.getCells(0, 0);
            firstDiagonal = firstChar != null;
            if (firstChar != null) {
                for (int i = 1; i < 3; i++) {
                    if (!firstChar.equals(board.getCells(i, i))) {
                        firstDiagonal = false;
                        break;
                    }
                }
                if (firstDiagonal) {
                    return new GameResult(true, firstChar);
                }
            }

            firstChar = board.getCells(0, 2);
            secDiagonal = firstChar != null;
            if (firstChar != null) {
                for (int i = 1; i < 3; i++) {
                    if (!firstChar.equals(board.getCells(i, 2 - i))) {
                        secDiagonal = false;
                        break;
                    }
                }
                if (secDiagonal) {
                    return new GameResult(true, firstChar);
                }
            }

            int CountFilledCells = 0;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board.getCells(i, j) != null) {
                        CountFilledCells++;
                    }
                }
            }
            if (CountFilledCells == 9) {
                return new GameResult(true, "-");
            }
        }
        return new GameResult(false, "-");
    }

    public Move suggestMove(Player computer, Board board1) {
        if (board1 instanceof TicTacToe) {
            TicTacToe board = (TicTacToe) board1;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board.getCells(i, j) == null) {
                        return new Move(new Cell(i, j));
                    }
                }
            }
            throw new IllegalStateException();
        } else {
            throw new IllegalArgumentException();
        }
    }
}
