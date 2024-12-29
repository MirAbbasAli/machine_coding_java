package org.tictactoe.app.service;

import org.tictactoe.app.repo.GameRepository;
import org.tictactoe.app.repo.PlayerRepository;
import org.tictactoe.app.repo.entity.Game;
import org.tictactoe.app.repo.entity.GameStatus;
import org.tictactoe.app.repo.entity.Piece;
import org.tictactoe.app.repo.entity.Player;
import org.tictactoe.app.util.TransformUtils;

import java.util.List;

public class GameService {
    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;

    public GameService(){
        this.gameRepository = new GameRepository();
        this.playerRepository = new PlayerRepository();
    }

    public void createPlayers(List<String> names){
        List<Player> players = names.stream()
                .map(name -> {
                    Player player = new Player();
                    player.setName(TransformUtils.toTitleCase(name));
                    return player;
                }).toList();
        players.get(0).setPiece(Piece.X);
        players.get(1).setPiece(Piece.O);
        playerRepository.saveAll(players);
    }

    public void createGame(){
        Game game = new Game();
        game.setPlayers(playerRepository.findAllPlayers());
        game.setGameStatus(GameStatus.IN_PROGRESS);
        gameRepository.save(game);
    }
    
    public void displayBoard(){
        Game game = gameRepository.getGame();
        Character[][] board = game.getBoard();
        for (Character[] row : board) {
            for (Character cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }
    
    public Boolean isMovePossible(Integer row, Integer col){
        Game game = gameRepository.getGame();
        Character[][] board = game.getBoard();
        return board[row][col] == '-';
    }
    
    public Boolean isMoveLeft(){
        Game game = gameRepository.getGame();
        return game.getNumberOfEmptyCells() > 0;
    }
    
    public void updateBoard(Integer row, Integer col, Integer playerNumber){
        Game game = gameRepository.getGame();
        Character[][] board = game.getBoard();
        board[row][col] = game.getPlayers().get(playerNumber).getPiece().getValue();
        game.setNumberOfEmptyCells(game.getNumberOfEmptyCells() - 1);
    }
    
    public GameStatus checkGameStatus(Integer row, Integer col, Integer playerNumber){
        Game game = gameRepository.getGame();
        Character value = game.getPlayers().get(playerNumber).getPiece().getValue();
        Character[][] board = game.getBoard();
        int rowSum = 0;
        for(int rowIndex = 0; rowIndex < 3; rowIndex++){
            rowSum += (board[rowIndex][col] == value) ? 1 : 0;
        }
        if(rowSum == 3){
            return GameStatus.WON;
        }
        int colSum = 0;
        for(int colIndex = 0; colIndex < 3; colIndex++){
            colSum += (board[row][colIndex] == value) ? 1 : 0;
        }
        if(colSum == 3){
            return GameStatus.WON;
        }
        int leftDiagonalSum = 0;
        for(int index = 0; index < 3; index++){
            leftDiagonalSum += (board[index][index] == value) ? 1 : 0;
        }
        if(leftDiagonalSum == 3){
            return GameStatus.WON;
        }
        int rightDiagonalSum = 0;
        for(int index = 0; index < 3; index++){
            rightDiagonalSum += (board[index][2 - index] == value) ? 1 : 0;
        }
        if(rightDiagonalSum == 3){
            return GameStatus.WON;
        }
        if(!isMoveLeft()){
            return GameStatus.DRAW;
        }
        return GameStatus.IN_PROGRESS;
    }

    public void displayWinner(Integer playerNumber){
        System.out.println("Player " + gameRepository.getGame().getPlayers().get(playerNumber).getName() + " won");
    }
    
    
}
