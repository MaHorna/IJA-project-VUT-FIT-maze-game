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

public class replay_controller {
    int cell_size = 40;
    @FXML private Canvas replay_canvas;
    @FXML private Pane replay_pane_hold;
    GraphicsContext gc;
    replay replay = null;	
    
    @FXML private void initialize() {
        gc = replay_canvas.getGraphicsContext2D();
        replay_holder replay_singleton = replay_holder.get_instance();
        replay = replay_singleton.get_replay();
        cell_size = replay.cell_size;
        canvas_manager.draw_board(gc, com.example.replay.board, replay.width-4, replay.height-4);
        draw_objects(replay);
    }

    void draw_objects(replay replay) {
        if (replay.player != null) {
            ImageView player_view = new ImageView(new Image(getClass().getResource("images/game/pacman.png").toString(), cell_size, cell_size, true, true));
            player_view.setLayoutX(replay.player.x*cell_size);
            player_view.setLayoutY(replay.player.y*cell_size);
            replay.player.image_view = player_view;
            replay_pane_hold.getChildren().addAll(player_view);
        }
        else {
            System.out.println("Start not found");
        }
        if (replay.end != null) {
            ImageView trophy_view = new ImageView(new Image(getClass().getResource("images/game/trophy.png").toString(), cell_size, cell_size, true, true));
            trophy_view.setLayoutX(replay.end.x*cell_size);
            trophy_view.setLayoutY(replay.end.y*cell_size);
            replay.end.image_view = trophy_view;
            replay_pane_hold.getChildren().addAll(trophy_view);
        }
        else {
            System.out.println("End not found");
        }
        for (int i = 0; i < replay.key_list.size(); i++) {
            triple key = replay.key_list.get(i);
            ImageView key_view = new ImageView(new Image(getClass().getResource("images/game/key.png").toString(), cell_size, cell_size, true, true));
            key_view.setLayoutX(key.x*cell_size);
            key_view.setLayoutY(key.y*cell_size);
            key.image_view = key_view;
            replay_pane_hold.getChildren().addAll(key_view);
        }        
        for (int i = 0; i < replay.ghost_list.size(); i++) {
            ghost ghost = replay.ghost_list.get(i);
            ImageView ghost_view = new ImageView(new Image(getClass().getResource("images/game/ghost.png").toString(), cell_size, cell_size, true, true));
            ghost_view.setLayoutX(ghost.x*cell_size);
            ghost_view.setLayoutY(ghost.y*cell_size);
            ghost.image_view = ghost_view;
            replay_pane_hold.getChildren().addAll(ghost_view);
        }
    }
    
    @FXML private void key_pressed_event(KeyEvent event) throws IOException{
        KeyCode kc = event.getCode();
        if (kc == KeyCode.SPACE) {
            if (replay.state == 0) { //not started yet 
                replay.state = 1; //start game
                replay.timeline.play();
            }
            else if (replay.state == 1) { //in progress
                System.out.println("pause");
                replay.state = 2; //pause game
                replay.timeline.pause();
            }
            else if (replay.state == 2) { //paused
                System.out.println("resume");
                replay.state = 1; //resume game
                replay.timeline.play();
            }
            else if (replay.state == 3) { //finished
                replay.state = 0; //restart game
                replay.timeline.stop();
                replay_holder replay_singleton = replay_holder.get_instance();
                replay_singleton.set_replay(new replay(replay.file_path)); //loads replay from file, creates replay instance
                view_controller.load_view("replay_pane.fxml"); //replay controller sets up listens for replay events
            }
        }
        System.out.println("Key pressed: ," + event.getCode() + ", ," + event.getText()+",");
    }
}
