/**
 * @author Matej Horňanský
 * @author Dávid Kán
 *
 * Controller class for drawing objects and listening for input
 */
package com.pacman;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * Controller class for drawing objects and listening for input
 */
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
        game.player.game_pane_hold = game_pane_hold;
        game.player.Hbox_main = view_controller.getHbox_main();
    }

    /**
     * Draws objects on pane
     * @param game current game
     */
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
        for (int i = 0; i < game.cherry_list.size(); i++) {
            triple cherry = game.cherry_list.get(i);
            ImageView cherry_view = new ImageView(new Image(getClass().getResource("images/game/cherry.png").toString(), cell_size/2, cell_size/2, true, true));
            cherry_view.setLayoutX(cherry.x*cell_size+10);
            cherry_view.setLayoutY(cherry.y*cell_size+10);
            cherry.image_view = cherry_view;
            game_pane_hold.getChildren().addAll(cherry_view);
            file_manager.add_triplet(game, cherry.x, cherry.y, true);
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
        Label score_label = new Label("Score: 0");
        score_label.setTextFill(Color.YELLOW);
        score_label.setLayoutX(50);
        score_label.setFont(Font.font("System", 16));
        game_pane_hold.getChildren().add(score_label);
        game.player.score_label = score_label;
        Label time_label = new Label("Time: 0.0");
        time_label.setTextFill(Color.YELLOW);
        time_label.setLayoutX(200);
        time_label.setFont(Font.font("System", 16));
        game_pane_hold.getChildren().add(time_label);
        game.player.time_label = time_label;
        Label step_label = new Label("Steps: 0");
        step_label.setTextFill(Color.YELLOW);
        step_label.setLayoutX(350);
        step_label.setFont(Font.font("System", 16));
        game_pane_hold.getChildren().add(step_label);
        game.player.step_label = step_label;
    }

    /**
     * Function for key input
     * @param event key input
     * @throws IOException
     */
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
