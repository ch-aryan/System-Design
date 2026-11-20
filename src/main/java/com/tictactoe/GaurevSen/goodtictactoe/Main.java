package com.practicewithsanketh.DesignSystem.tictactoe.GaurevSen.goodtictactoe;

import com.practicewithsanketh.DesignSystem.tictactoe.GaurevSen.goodtictactoe.game.Board;
import com.practicewithsanketh.DesignSystem.tictactoe.GaurevSen.goodtictactoe.game.Cell;
import com.practicewithsanketh.DesignSystem.tictactoe.GaurevSen.goodtictactoe.game.Move;
import com.practicewithsanketh.DesignSystem.tictactoe.GaurevSen.goodtictactoe.game.Player;
import com.practicewithsanketh.DesignSystem.tictactoe.GaurevSen.goodtictactoe.gameEngineAPI.GameEngine;

import java.util.Scanner;

public class Main {

    public static void main(String args[]){

        GameEngine engine = new GameEngine();
        Board tictacToe = engine.start("TictacToe");

        int row , col;
        Scanner scanner = new Scanner(System.in);
        while(!engine.isComplete(tictacToe).isResult()){


            Player computer = new Player("O");
            Player opponent = new Player("X");
            System.out.println("Make your move (row and col: 0 to 2):");

            row = scanner.nextInt();
            col = scanner.nextInt();
            Move oppMove = new Move(new Cell(row, col));
            engine.move(tictacToe, opponent, oppMove);
            System.out.println("Board after your move:");
            System.out.println(tictacToe);

            if(!engine.isComplete(tictacToe).isResult()){
                Move computerMove = engine.suggestMove(computer, tictacToe);
                engine.move(tictacToe, computer, computerMove);
                System.out.println("Board after computer move:");
                System.out.println(tictacToe);
            }


        }
        System.out.println("Game Over! Result: " + engine.isComplete(tictacToe));

    }

}
