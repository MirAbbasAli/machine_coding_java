package org.tictactoe.app.repo;

import org.tictactoe.app.repo.entity.Game;

import java.util.ArrayList;
import java.util.List;

public class GameRepository {
    private final List<Game> games;

    public GameRepository(){
        games = new ArrayList<>();
    }

    public void save(Game game){
        games.add(game);
    }

    public Game getGame(){
        return this.games.get(0);
    }
}
