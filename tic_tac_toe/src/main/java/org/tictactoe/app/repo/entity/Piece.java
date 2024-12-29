package org.tictactoe.app.repo.entity;

public enum Piece {
    X('X'),
    O('O');

    private final Character value;

    Piece(Character value) {
        this.value = value;
    }

    public Character getValue() {
        return value;
    }
}
