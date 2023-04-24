package com.example;

import java.util.List;

public class step {

   List<String> ghosts;
    String player;
    String key_changes;
    public step(List<String> ghosts, String player, String key_changes)
    {
        this.ghosts = ghosts;
        this.player = player;
        this.key_changes = key_changes;
    }
}
