package com.practicewithsanketh.DesignSystem.tictactoe.GaurevSen.goodtictactoe.game;

public class GameResult {
    boolean result;
    String message;

    public GameResult(boolean result, String message) {
        this.result = result;
        this.message = message;
    }

    public boolean isResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "isComplete=" + result + ", winner=" + message;
    }
}
