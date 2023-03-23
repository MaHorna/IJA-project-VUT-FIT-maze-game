package com.example;


import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

public class game_controller {
    int cell_size = 0;
    @FXML private Canvas game_canvas;
    @FXML private Pane game_pane_hold;
    GraphicsContext gc;
    game game = null;	

    @FXML private void initialize() {
        gc = game_canvas.getGraphicsContext2D();
        game_holder game_singleton = game_holder.get_instance();
        game = game_singleton.get_game();
        cell_size = game.cell_size;
        canvas_manager.draw_board(gc, game.board, game.width, game.height);
        file_manager.add_map(game);
        draw_objects(game);
    }

    void draw_objects(game game) {
        if (game.start != null) {
            ImageView player_view = new ImageView(new Image(getClass().getResource("images/game/pacman.png").toString(), cell_size, cell_size, true, true));
            player_view.setLayoutX(game.start.x*cell_size);
            player_view.setLayoutY(game.start.y*cell_size);
            game.player.image_view = player_view;
            game_pane_hold.getChildren().addAll(player_view);
            file_manager.add_double(game, game.start.x, game.start.y);
            file_manager.add_section_delimeter(game);
        }
        else {
            System.out.println("Start not found");
        }
        if (game.end != null) {
            ImageView trophy_view = new ImageView(new Image(getClass().getResource("images/game/trophy.png").toString(), cell_size, cell_size, true, true));
            trophy_view.setLayoutX(game.end.x*cell_size);
            trophy_view.setLayoutY(game.end.y*cell_size);
            game.end.image_view = trophy_view;
            game_pane_hold.getChildren().addAll(trophy_view);
            file_manager.add_double(game, game.end.x, game.end.y);
            file_manager.add_section_delimeter(game);
        }
        else {
            System.out.println("End not found");
        }
        for (int i = 0; i < game.key_list.size(); i++) {
            triple key = game.key_list.get(i);
            ImageView key_view = new ImageView(new Image(getClass().getResource("images/game/key.png").toString(), cell_size, cell_size, true, true));
            key_view.setLayoutX(key.x*cell_size);
            key_view.setLayoutY(key.y*cell_size);
            key.image_view = key_view;
            game_pane_hold.getChildren().addAll(key_view);
            file_manager.add_triplet(game, key.x, key.y, true);
        }        
        file_manager.add_section_delimeter(game);
        for (int i = 0; i < game.ghost_list.size(); i++) {
            ghost ghost = game.ghost_list.get(i);
            ImageView ghost_view = new ImageView(new Image(getClass().getResource("images/game/ghost.png").toString(), cell_size, cell_size, true, true));
            ghost_view.setLayoutX(ghost.x*cell_size);
            ghost_view.setLayoutY(ghost.y*cell_size);
            ghost.image_view = ghost_view;
            game_pane_hold.getChildren().addAll(ghost_view);
            file_manager.add_double(game, ghost.x, ghost.y);
        }
        file_manager.add_section_delimeter(game);
    }
    
    @FXML private void key_pressed_event(KeyEvent event) throws IOException{
        player player = game.player;
        int next_dir = -1;
        KeyCode kc = event.getCode();
        if (kc == KeyCode.UP || kc == KeyCode.W) {
            
            next_dir = 0;
        }
        else if (kc == KeyCode.RIGHT || kc == KeyCode.D) {
            next_dir = 1;
        }
        else if (kc == KeyCode.DOWN || kc == KeyCode.S) {
            next_dir = 2;
        }
        else if (kc == KeyCode.LEFT || kc == KeyCode.A) {
            next_dir = 3;
        }

        if (next_dir != -1 && player.current_direction != next_dir) {
            System.out.println("changinf dir from" + player.next_direction + " to " + next_dir + "");
            player.next_direction = next_dir;
        }

        if (kc == KeyCode.SPACE) {
            if (game.state == 0) { //not started yet 
                game.state = 1; //start game
                game.timeline.play();
            }
            else if (game.state == 1) { //in progress
                System.out.println("pause");
                game.state = 2; //pause game
                game.timeline.pause();
            }
            else if (game.state == 2) { //paused
                System.out.println("resume");
                game.state = 1; //resume game
                game.timeline.play();
            }
            else if (game.state == 3) { //finished
                game.state = 0; //restart game
                game.timeline.stop();
                game_holder game_singleton = game_holder.get_instance();
                game_singleton.set_game(new game(game.file_path)); //loads game from file, creates game instance
                view_controller.load_view("game_pane.fxml"); //game controller sets up listens for game events
            }
        }
        System.out.println("Key pressed: ," + event.getCode() + ", ," + event.getText()+",");
    }
}
