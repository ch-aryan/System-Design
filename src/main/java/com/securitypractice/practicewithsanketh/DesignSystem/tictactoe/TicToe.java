package com.securitypractice.practicewithsanketh.DesignSystem.tictactoe;

import java.util.Scanner;

 enum Symbol {
    x,o, empty;
}
    class Position{
    public int row;
    public int col;
    public Position(int row, int col){
        this.row = row;
        this.col = col;
    }

    }

    class GameContext{
    private GameState curState;
    public GameContext(){
        this.curState = new xTurnState();
    }
    public void setState(GameState state){
        this.curState = state;
    }
    public void next(Player player, boolean hasWon){
        curState.next(this, player, hasWon);
    }
    public boolean isGameOver(){
        return curState.isGameOver();
    }
    public GameState getCurrentState(){
        return curState;
    }
    }
    interface GameState{
    void next(GameContext context, Player player, boolean hasWon);
    boolean isGameOver();
    }
    class xTurnState implements GameState {
        @Override
        public void next(GameContext context, Player player, boolean hasWon) {
            if (hasWon) {
                context.setState(player.getSymbol() == Symbol.x ? new xWonState() : new oWonState());
            } else {
                context.setState(new oTurnState());
            }
        }
            @Override
            public boolean isGameOver () {
                return false;
            }

    }
    class oTurnState implements GameState {
        @Override
        public void next(GameContext context, Player player, boolean hasWon) {
            if (hasWon) {
                context.setState(player.getSymbol() == Symbol.x ? new xWonState() : new oWonState());
            }else {
                context.setState(new xTurnState());
            }
        }
            @Override
            public boolean isGameOver () {
                return false;
            }
        }
        class xWonState implements GameState{
       @Override
            public void next(GameContext context, Player player, boolean hasWon) {
            }
            public boolean isGameOver() {
                return true;
            }
        }

        class oWonState implements GameState{
            @Override
            public void next(GameContext context, Player player, boolean hasWon) {
            }
            public boolean isGameOver() {
                return true;
            }
        }



    class Player{
    Symbol symbol;
    PlayerStrategy strategy;
    Player(Symbol symbol, PlayerStrategy strategy){
        this.symbol = symbol;
        this.strategy = strategy;
    }


        public PlayerStrategy getStrategy() {
            return strategy;
        }

        public Symbol getSymbol() {
            return symbol;
        }
    }
interface PlayerStrategy {
    Position makeMove(Board board);
}
    class HumanPlayer implements PlayerStrategy {
        private Scanner scanner;
        private String name;

        public HumanPlayer(String name) {
            this.name = name;
            scanner = new Scanner(System.in);
        }

        public Position makeMove(Board board) {
            while (true) {
                System.out.println("Enter row and column" + name);
                try {
                    int row = scanner.nextInt();
                    int col = scanner.nextInt();
                    Position move = new Position(row, col);
                    if (board.isValidMove(move)) {
                        return move;
                    }
                    System.out.println("Invalid move try again");
                } catch (Exception e) {
                    System.out.println("Invalid input");
                    scanner.nextLine(); //clear input buffer.
                }
            }
        }
    }
class Board {
    private final int rows;
    private final int cols;
    private Symbol grid[][];

    public Board(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        grid = new Symbol[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = Symbol.empty;
            }
        }
    }

    public boolean isValidMove(Position move) {
        return move.row >= 0 && move.row < rows && move.col >= 0 && move.col < cols
                && grid[move.row][move.col] == Symbol.empty;
    }

    public void makeMove(Position move, Symbol symbol) {
        grid[move.row][move.col] = symbol;
    }

    public void checkGameState(GameContext context) {
        for (int i = 0; i < rows; i++) {

            if (grid[i][0] != Symbol.empty && isWinningLine(grid[i])) {
                context.next(currPlayer, true);
                return;
            }
        }

        for (int i = 0; i < cols; i++) {
            Symbol[] column = new Symbol[rows];
            for (int j = 0; j < rows; j++) {
                column[j] = grid[j][i];
            }
            if (column[0] != Symbol.empty && isWinningLine(column)) {
                context.next(currPlayer, true);
                return;
            }
        }
        Symbol[] diagonal1 = new Symbol[Math.min(rows, cols)];
        Symbol[] diagonal2 = new Symbol[Math.min(rows, cols)];
        for (int i = 0; i < Math.min(rows, cols); i++) {
            diagonal1[i] = grid[i][i];
            diagonal2[i] = grid[i][cols - 1 - i];
        }
        if (diagonal1[0] != Symbol.empty && isWinningLine(diagonal1)) {
            context.next(currPlayer, true);
            return;
        }
        if (diagonal2[0] != Symbol.empty && isWinningLine(diagonal2)) {
            context.next(currPlayer, true);
            return;
        }
    }
    // Additional logic to handle a draw or continue in progress can be added
    // here

    private boolean isWinningLine(Symbol[] line) {
        Symbol first = line[0];
        for (Symbol s : line) {
            if (s != first) {
                return false;
            }
        }
        return true;
    }

    public void printBoard() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Symbol symbol = grid[i][j];
                switch (symbol) {
                    case x:
                        System.out.print(" X ");
                        break;
                    case o:
                        System.out.print(" O ");
                        break;
                    case empty:
                    default:
                        System.out.print(" . ");
                }
                if (j < cols - 1) {
                    System.out.print("|");
                }
            }
            System.out.println();
            if (i < rows - 1) {
                System.out.println("---+---+---");
            }
        }
        System.out.println();
    }
}

interface BoardGames {
    // This interface illustrates how a large game company can manage multiple
    // types of games, including board games and non-board games. Tic Tac Toe is
    // an example of a game that is a child of the BoardGames interface.
    void play();
}

class TicTacToe implements BoardGames {
    private Board board;
    private Player playerX;
    private Player playerO;
    private Player currentPlayer;
    private GameContext gameContext;

    // Initializes the game board and players with their respective strategies.
    // Sets the current player to playerX. can be set to playerO as well
    public TicTacToe(PlayerStrategy xStrategy, PlayerStrategy oStrategy,
                         int rows, int columns) {
        board = new Board(rows, columns);
        playerX = new Player(Symbol.x, xStrategy);
        playerO = new Player(Symbol.o, oStrategy);
        currentPlayer = playerX;
        gameContext = new GameContext();
    }

    @Override
    // Loop continues until the game state indicates that the game is over.
    public void play() {
        do {
            // print the current state of the game
            board.printBoard();
            // current player makes the move
            Position move = currentPlayer.getStrategy().makeMove(board);
            board.makeMove(move, currentPlayer.getSymbol());
            // checks game state for win/draw
            board.checkGameState(gameContext);
            switchPlayer();
        } while (!gameContext.isGameOver());
        announceResult();
    }

    // Alternates the current player after each move.
    // Ensures both players take turns
    private void switchPlayer() {
        currentPlayer = (currentPlayer == playerX) ? playerO : playerX;
    }

    // Displays the outcome of the game based on the final game state.
    private void announceResult() {
        GameState state = gameContext.getCurrentState();
        if (state instanceof xWonState) {
            System.out.println("Player X wins!");
        } else if (state instanceof oWonState) {
            System.out.println("Player O wins!");
        } else {
            System.out.println("It's a draw!");
        }
    }
}



public class TicToe {
    public static void main(String[] args) {

    }
}