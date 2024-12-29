package org.tictactoe.app;

import org.tictactoe.app.repo.entity.GameStatus;
import org.tictactoe.app.service.GameService;

import java.util.List;
import java.util.Scanner;

public class TicTacToeApplication {
    public static boolean sanitizePosition(Integer row, Integer col){
        return row >= 0 && row <= 2 && col >= 0 && col <= 2;
    }
    public static void main(String args[]) {
        GameService gameService = new GameService();

        System.out.println("Player name format: <name>\nEnter 2 players name separated by newline");
        Scanner in = new Scanner(System.in);
        String playerOne = in.nextLine();
        String playerTwo = in.nextLine();
        gameService.createPlayers(List.of(playerOne, playerTwo));
        gameService.createGame();
        System.out.println("Initial Board");
        gameService.displayBoard();
        System.out.println("valid moves format: <int> <int>, (range 1 to 3)");
        boolean running = true;
        int count = 0;
        while(running && gameService.isMoveLeft()){
            String move = in.nextLine();
            String[] moves = move.split(" ");
            if(moves[0].equalsIgnoreCase("exit")){
                running = false;
                System.out.println("Exiting game");
                continue;
            }
            if(moves.length != 2){
                System.out.println("Invalid move, try again");
                continue;
            }
            Integer row = Integer.parseInt(moves[0]) - 1;
            Integer col = Integer.parseInt(moves[1]) - 1;
            if(!sanitizePosition(row, col)){
                System.out.println("Invalid move, try again");
                continue;
            }
            if(!gameService.isMovePossible(row, col)){
                System.out.println("Invalid move, try again");
                continue;
            }
            int playerNumber = count%2;
            gameService.updateBoard(row, col, playerNumber);
            gameService.displayBoard();
            GameStatus gameStatus = gameService.checkGameStatus(row, col, playerNumber);
            running = switch (gameStatus) {
                case WON -> {
                    gameService.displayWinner(playerNumber);
                    yield false;
                }
                case DRAW -> {
                    System.out.println("Game is a draw");
                    yield false;
                }
                default -> running;
            };
            count++;
        }

    }
}
