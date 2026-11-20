package com.practicewithsanketh.DesignSystem.tictactoe.GaurevSen.Badtictactoe;

import com.practicewithsanketh.DesignSystem.tictactoe.TicToe;

class Board{
    Board(){

    }

}
class tictactoe extends Board{
    String [][] cells = new String[3][3];
}
class Player{

}
class GameResult{
    boolean result;
    String winner;

    public GameResult(boolean result, String winner) {
        this.result = result;
        this.winner = winner;
    }
}

class Move{

}

public class main {
    public static void main(String[] args) {

    }
    public Board start(){
        return new Board();
    }
    public void move(Board board, Move move, Player player){

    }
    public GameResult isComplete(Board board){
        boolean rowComplete = false;
        boolean ColumnComplete = false;
        boolean firstDiagonal = true;
        boolean secDiagonal  = true;
        String firstChar = "";
       tictactoe obj = (tictactoe) board;
    if(board instanceof tictactoe){
        for(int i = 0; i<3;i++){
            rowComplete = true;
           firstChar =  obj.cells[i][0];
            for(int j =1; j<3; j++){
                if(!obj.cells[i][j].equals(firstChar)){
                    rowComplete = false;
                    break;
                }
            }
            if(rowComplete){
                return new GameResult(true, firstChar);
            }
        }
        for(int i = 0; i<3;i++){
            ColumnComplete = true;
             firstChar =  obj.cells[0][i];
            for(int j =1; j<3; j++){
                if(!obj.cells[j][i].equals(firstChar)){
                    ColumnComplete = false;
                    break;
                }
            }
            if(ColumnComplete){
                return new GameResult(true, firstChar);
            }
        }
        for(int i = 0; i<3; i++){
            firstDiagonal = true;
             firstChar = obj.cells[0][0];
            if(!obj.cells[i][i].equals(firstChar)){
                firstDiagonal = false;
                break;
            }
        }
        if(firstDiagonal){
            return new GameResult(true, firstChar);
        }

        for(int i = 0; i<3; i++){
            secDiagonal = true;
             firstChar = obj.cells[0][0];
            if(!obj.cells[2-i][i].equals(firstChar)){
                secDiagonal = false;
                break;
            }
        }
        if(secDiagonal){
            return new GameResult(true, firstChar);
        }
    int CountFilledCells = 0;
        for(int i =0; i<3; i++){
            for(int j =0; j<3; j++){
            if(obj.cells[i][j] != null ){
              CountFilledCells++;
            }
            }
        }
        if(CountFilledCells == 9){
            return new GameResult(true, "-");
        }

        if(rowComplete || ColumnComplete || firstDiagonal || secDiagonal){
            new GameResult(true, firstChar);
        }


    }
    return new GameResult(false, "-");
    }
}
