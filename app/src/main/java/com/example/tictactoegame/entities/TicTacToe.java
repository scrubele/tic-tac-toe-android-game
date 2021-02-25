package com.example.tictactoegame.entities;

public class TicTacToe {
    private final String name;
    private final String gameType;
    private final String description;
    private final String picture;

    public TicTacToe(String name, String gameType, String description, String url) {
        this.name = name;
        this.gameType = gameType;
        this.description = description;
        this.picture = url;
    }

    public String getName() {
        return name;
    }

    public String getGameType() {
        return gameType;
    }

    public String getDescription() {
        return description;
    }

    public String getPicture() {
        return picture;
    }
}
