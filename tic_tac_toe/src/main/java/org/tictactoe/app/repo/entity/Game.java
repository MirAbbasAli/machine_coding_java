package org.tictactoe.app.repo.entity;

import java.util.List;

public class Game {
    private Integer numberOfEmptyCells;
    private GameStatus gameStatus;
    private List<Player> players;
    private final Character[][] board;

    public Game(){
        this.gameStatus = GameStatus.IN_PROGRESS;
        this.numberOfEmptyCells = 9;
        this.board = new Character[][]{{'-', '-', '-'}, {'-', '-', '-'}, {'-', '-', '-'}};
    }

    public Integer getNumberOfEmptyCells() {
        return numberOfEmptyCells;
    }

    public void setNumberOfEmptyCells(Integer numberOfEmptyCells) {
        this.numberOfEmptyCells = numberOfEmptyCells;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Character[][] getBoard() {
        return board;
    }
}
