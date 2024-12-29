package org.tictactoe.app.repo;

import org.tictactoe.app.repo.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerRepository {
    private final List<Player> players;

    public PlayerRepository(){
        players = new ArrayList<>();
    }

    public void saveAll(List<Player> players){
        this.players.addAll(players);
    }

    public List<Player> findAllPlayers(){
        return this.players;
    }
}
