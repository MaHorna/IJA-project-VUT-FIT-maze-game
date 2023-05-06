/**
 * @author Matej Horňanský
 * @author Dávid Kán
 *
 * Class for player object
 */

package com.pacman;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * Class for storing player object information and its manipulation.
 */
public class player {
    public double x; //start of board is (0,0) , middle of first cell is (0.5, 0.5), first cell ends at (1,1) , this way we can have linear motion on board
    public double y;
    public int current_direction; // 0 = up, 1 = right, 2 = down, 3 = left
    public int next_direction; // 0 = up, 1 = right, 2 = down, 3 = left
    public ImageView image_view;
    public Label score_label, time_label, step_label;
    private int score;
    private int step = 0;
    private double prev_x = 0, prev_y = 0;
    public Pane game_pane_hold;
    public HBox Hbox_main;

    public player(double x, double y, int current_direction, int next_direction) {
        this.x = x;
        this.y = y;
        this.current_direction = current_direction;
        this.next_direction = next_direction;
        score = 0;
    }

    /**
     * Counts player steps. Only in middle tile.
     * @param game current game
     */
    public void player_step(game game){
        if(((x%1 < 0.02) || (x%1 > 0.98)) && ((y%1 < 0.02) || (y%1 > 0.98))) {
            if (prev_x != x || prev_y != y){
                file_manager.add_step(game, step);
                step_label.setText("Step: " + step);
                step++;
            }
            prev_x = x;
            prev_y = y;
        }
    }

    /**
     * Sets time on game screen.
     * @param time current time
     */
    public void set_time(double time){
        time_label.setText("Time: " + time);
    }

    /**
     * Moves player object
     * @param game current game
     */
    public void player_move(game game) {
        boolean is_in_middle_of_tile = false;
        int col_index = (int) Math.round(x);
        int row_index = (int) Math.round(y);
        if(((x%1 < 0.02) || (x%1 > 0.98)) && ((y%1 < 0.02) || (y%1 > 0.98))) {
            is_in_middle_of_tile = true;
        }
        if (is_in_middle_of_tile) { //middle of the tile is only place to rotate
            if (next_direction != current_direction) { //there was input to change direction
                if (!check_if_wall_in_dir(row_index, col_index, next_direction, game)) { //
                    current_direction = next_direction;
                }
            }
        }
        if (!check_if_wall_in_dir(row_index, col_index, current_direction, game) || !is_in_middle_of_tile) {
            int x_move = 0;
            int y_move = 0;
            if (current_direction == 0) {
                y_move -= 1;
            }
            if (current_direction == 1) {
                x_move += 1;
            }
            if (current_direction == 2) {
                y_move += 1;
            }
            if (current_direction == 3) {
                x_move -= 1;
            }
            if(x<1.95 || y<1.95 || x>game.board[0].length-2.95 || y>game.board.length-2.95) {
                game.timeline.stop();
                System.out.println("player is out of bounds");
            }
            x += x_move*0.05;
            y += y_move*0.05;
            image_view.relocate(x*game.cell_size, y*game.cell_size);
            file_manager.add_double(game, x*game.cell_size, y*game.cell_size);
        }
    }

    /**
     * Checks if wall is in player/ghost direction
     * @param y position of wall
     * @param x position of wall
     * @param dir direction object is facing
     * @param game current game
     * @return
     */
    public static boolean check_if_wall_in_dir(int y, int x, int dir, game game) {
        if (dir == 0) {
            y -= 1;
        }
        if (dir == 1) {
            x += 1;
        }
        if (dir == 2) {
            y += 1;
        }
        if (dir == 3) {
            x -= 1;
        }
        return check_if_object_in_tile(y, x, game, 'X');
    }

    private static boolean check_if_object_in_tile(int y, int x, game game, char tile) {
        return game.board[y][x] == tile;
    }

    /**
     * Checks if player hit key object and removes it
     * @param game current game
     */
    public void player_check_keys(game game) {
        int col_index = (int) Math.round(x);
        int row_index = (int) Math.round(y);
        if (check_if_object_in_tile(row_index, col_index, game, 'K')) {
            for (int i = 0; i < game.key_list.size(); i++) {
                if (game.key_list.get(i).x == col_index && game.key_list.get(i).y == row_index && !game.key_list.get(i).key_is_taken) {
                    game.key_list.get(i).key_is_taken = true;
                    game.key_list.get(i).image_view.setVisible(false);
                    file_manager.add_double(game, i, -1); //key removal "command"
                }
            }
        }
    }

    /**
     *  Checks if player hit a cherry and removes it
     * @param game current game
     */
    public void player_check_cherries(game game) {
        int col_index = (int) Math.round(x);
        int row_index = (int) Math.round(y);
        if (check_if_object_in_tile(row_index, col_index, game, '.')) {
            for (int i = 0; i < game.cherry_list.size(); i++) {
                if (game.cherry_list.get(i).x == col_index && game.cherry_list.get(i).y == row_index && !game.cherry_list.get(i).key_is_taken) {
                    game.cherry_list.get(i).key_is_taken = true;
                    game.cherry_list.get(i).image_view.setVisible(false);
                    score++;
                    score_label.setText("Score: " + score);
                    file_manager.add_double(game, i, score); //key removal "command"
                }
            }
        }
    }

    /**
     * Checks if player hit goal and if game can be ended
     * @param game current game
     */
    public void player_check_end(game game) {
        int col_index = (int) Math.round(x);
        int row_index = (int) Math.round(y);
        if (check_if_object_in_tile(row_index, col_index, game, 'T')) {
            int keys_taken = 0;
            for (int i = 0; i < game.key_list.size(); i++) {
                if (game.key_list.get(i).key_is_taken) {
                    keys_taken++;
                }
            }     
            if (keys_taken == game.key_list.size()) {
                System.out.println("player won");
                game.state = 3;
                game.timeline.stop();
                draw_end_window("You won");
            }
            else {
                System.out.println("player not yet collected all keys");
            }
        }
    }

    /**
     * Checks if player object hit ghost object. If player hit ghost game ends
     * @param game current game
     */
    public void player_check_ghosts(game game) {
        int col_index = (int) Math.round(x);
        int row_index = (int) Math.round(y);
        for (int i = 0; i < game.ghost_list.size(); i++) {
            ghost tmp_ghost = game.ghost_list.get(i);
            if ((int) Math.round(tmp_ghost.x) == col_index && (int) Math.round(tmp_ghost.y) == row_index) {
                game.timeline.stop();
                game.state = 3;
                System.out.println("player hit a ghost");
                draw_end_window("You died");
            }
        }
    }

    private void draw_end_window(String text)
    {
        Label label = new Label(text);
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setMinWidth(150);
        vBox.setMinHeight(50);
        vBox.setLayoutX(Hbox_main.getWidth()/2 - 150);
        vBox.setLayoutY(Hbox_main.getHeight()/2 - 50);
        label.setFont(Font.font("System", 24));
        label.setTextFill(Color.YELLOW);
        vBox.setStyle("-fx-background-color: black");
        vBox.setBorder(new Border(new BorderStroke(Color.BLUE, BorderStrokeStyle.SOLID, new CornerRadii(5), BorderWidths.DEFAULT)));
        vBox.getChildren().add(label);
        game_pane_hold.getChildren().add(vBox);
    }
}
