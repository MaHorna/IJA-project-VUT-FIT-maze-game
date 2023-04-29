package com.example;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class replay_controller {
    @FXML public ToggleButton replay_step;
    @FXML public ToggleButton replay_flow;
    @FXML private HBox replay_buttons;
    int cell_size = 40;
    @FXML private Canvas replay_canvas;
    @FXML private Pane replay_pane_hold;
    GraphicsContext gc;
    replay replay = null;	
    
    @FXML private void initialize() throws IOException {
        gc = replay_canvas.getGraphicsContext2D();
        replay_holder replay_singleton = replay_holder.get_instance();
        replay = replay_singleton.get_replay();
        cell_size = replay.cell_size;
        canvas_manager.draw_board(gc, com.example.replay.board, replay.width-4, replay.height-4);
        replay_buttons.setLayoutX(replay.width*cell_size/2);
        replay_buttons.setLayoutY(replay.height*cell_size + 10);
        draw_objects(replay);
    }

    void draw_objects(replay replay) throws IOException {
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
        for (int i = 0; i < replay.cherry_list.size(); i++) {
            triple cherry = replay.cherry_list.get(i);
            ImageView cherry_view = new ImageView(new Image(getClass().getResource("images/game/cherry.png").toString(), cell_size/2, cell_size/2, true, true));
            cherry_view.setLayoutX(cherry.x*cell_size+10);
            cherry_view.setLayoutY(cherry.y*cell_size+10);
            cherry.image_view = cherry_view;
            replay_pane_hold.getChildren().addAll(cherry_view);
        }
        for (int i = 0; i < replay.ghost_list.size(); i++) {
            ghost ghost = replay.ghost_list.get(i);
            ImageView ghost_view = new ImageView(new Image(getClass().getResource("images/game/ghost.png").toString(), cell_size, cell_size, true, true));
            ghost_view.setLayoutX(ghost.x*cell_size);
            ghost_view.setLayoutY(ghost.y*cell_size);
            ghost.image_view = ghost_view;
            replay_pane_hold.getChildren().addAll(ghost_view);
        }
        Label score_label = new Label("Score: 0");
        score_label.setTextFill(Color.YELLOW);
        replay_pane_hold.getChildren().add(score_label);
        replay.player.score_label = score_label;
        Label time_label = new Label("Time: 0.0");
        time_label.setTextFill(Color.YELLOW);
        time_label.setLayoutX(100);
        replay_pane_hold.getChildren().add(time_label);
        replay.player.time_label = time_label;
        Label step_label = new Label("Steps: 0");
        step_label.setTextFill(Color.YELLOW);
        step_label.setLayoutX(200);
        replay_pane_hold.getChildren().add(step_label);
        replay.player.step_label = step_label;
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
            else if (replay.state == 2 && replay.mod) { //paused
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
        } else if (kc == KeyCode.RIGHT) {
            if (replay.mod){
                replay.forward = true;
            }else {
                int i = 0;
                boolean tmp = replay.forward;
                replay.forward = true;
                for (i = replay.steps ;i < replay.steps+10; i++)
                {
                    replay.do_replay__step(i);
                    if (i == replay.step_list.size()-1){
                        break;
                    }
                }
                replay.forward = tmp;
                replay.steps = i;
            }
        } else if (kc == KeyCode.LEFT) {
            if (replay.mod){
                replay.forward = false;
            }else {
                int i = 0;
                boolean tmp = replay.forward;
                replay.forward = false;
                for (i = replay.steps ;i > replay.steps-10; i--)
                {
                    replay.do_replay__step(i);
                    if (i == 0){
                        break;
                    }
                }
                replay.forward = tmp;
                replay.steps = i;
            }
        } else if (kc == KeyCode.UP) {
            replay.mod = true;
            replay.timeline.pause();
            replay.state = 2;
        }else if (kc == KeyCode.DOWN) {
            replay.mod = false;
            replay.timeline.pause();
            replay.state = 2;
        } else if (kc == KeyCode.C) {
            replay.timeline.pause();
            replay.state = 2;
            int i = replay.steps;
            replay.forward = true;
            while (i < replay.step_list.size()) {
                replay.do_replay__step(i++);
            }
            replay.steps = replay.step_list.size()-1;
            replay.forward = false;
        } else if (kc == KeyCode.X) {
            replay.timeline.pause();
            replay.state = 2;
            int i = replay.steps;
            replay.forward = false;
            while (i > -1) {
                replay.do_replay__step(i--);
            }
            replay.steps = 0;
            replay.forward = true;
        }
        System.out.println("Key pressed: ," + event.getCode() + ", ," + event.getText()+",");
    }

    @FXML public void replay_start(ActionEvent actionEvent) throws IOException {
        replay.timeline.pause();
        replay.state = 2;
        int i = replay.steps;
        replay.forward = false;
        while (i > -1) {
            replay.do_replay__step(i--);
        }
        replay.steps = 0;
        replay.forward = true;
    }

    @FXML public void replay_back(ActionEvent actionEvent) throws IOException {
        if (replay.mod){
            replay.forward = false;
        }else {
            int i = 0;
            boolean tmp = replay.forward;
            replay.forward = false;
            for (i = replay.steps ;i > replay.steps-10; i--)
            {
                replay.do_replay__step(i);
                if (i == 0){
                    break;
                }
            }
            replay.forward = tmp;
            replay.steps = i;
        }
    }

    @FXML public void replay_pause(ActionEvent actionEvent) throws IOException {
        if (replay.state == 0) { //not started yet
            replay.state = 1; //start game
            replay.timeline.play();
        }
        else if (replay.state == 1) { //in progress
            System.out.println("pause");
            replay.state = 2; //pause game
            replay.timeline.pause();
        }
        else if (replay.state == 2 && replay.mod) { //paused
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

    @FXML public void replay_next(ActionEvent actionEvent) throws IOException {
        if (replay.mod){
            replay.forward = true;
        }else {
            int i = 0;
            boolean tmp = replay.forward;
            replay.forward = true;
            for (i = replay.steps ;i < replay.steps+10; i++)
            {
                replay.do_replay__step(i);
                if (i == replay.step_list.size()-1){
                    break;
                }
            }
            replay.forward = tmp;
            replay.steps = i;
        }
    }

    @FXML public void replay_end(ActionEvent actionEvent) throws IOException {
        replay.timeline.pause();
        replay.state = 2;
        int i = replay.steps;
        replay.forward = true;
        while (i < replay.step_list.size()) {
            replay.do_replay__step(i++);
        }
        replay.steps = replay.step_list.size()-1;
        replay.forward = false;
    }

    @FXML public void replay_step_toggle(ActionEvent actionEvent) {
        if (actionEvent.getSource() == replay_step){
            replay.mod = false;
        } else if (actionEvent.getSource() == replay_flow) {
            replay.mod = true;
        }
        replay.timeline.pause();
        replay.state = 2;
    }
}
