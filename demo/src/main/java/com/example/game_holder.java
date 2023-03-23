package com.example;

public final class game_holder {
    public game game;

    private final static game_holder INSTANCE = new game_holder();

    private game_holder() {}

    public static game_holder get_instance() {
        return INSTANCE;
    }
    
    public void set_game(game g) {
        this.game = g;
    }
    
    public game get_game() {
        return this.game;
    }    
}
