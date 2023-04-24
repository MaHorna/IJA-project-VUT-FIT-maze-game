package com.example;

import java.util.List;

public class step {

   List<String> ghosts;
    String player;
    String key_changes;
    String cherry_changes;
    String player_step;
    public step(List<String> ghosts, String player, String key_changes, String cherry_changes, String player_step)
    {
        this.ghosts = ghosts;
        this.player = player;
        this.key_changes = key_changes;
        this.cherry_changes = cherry_changes;
        this.player_step = player_step;
    }
}
