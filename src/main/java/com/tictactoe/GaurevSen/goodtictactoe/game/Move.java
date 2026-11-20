package com.practicewithsanketh.DesignSystem.tictactoe.GaurevSen.goodtictactoe.game;

public class Move {

    private static Cell cell;

    public Move(Cell cell){
        this.cell = cell;
    }
    public static Cell getCell(){
        return cell;
    }


}
