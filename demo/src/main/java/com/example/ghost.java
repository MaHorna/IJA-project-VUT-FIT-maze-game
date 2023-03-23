package com.example;

import javafx.scene.image.ImageView;
import java.util.Random;

public class ghost {
    public double x; //start of board is (0,0) , middle of first cell is (0.5, 0.5), first cell ends at (1,1) , this way we can have linear motion on board
    public double y;
    public int direction; // 0 = up, 1 = right, 2 = down, 3 = left
    public ImageView image_view;
    //public int ai_type;

    public ghost(double x, double y, int direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        //this.ai_type = ai_type;
    }
    public void ghost_move(game game) {
        boolean is_in_middle_of_tile = false;
        int col_index = (int) Math.round(x);
        int row_index = (int) Math.round(y);
        
        if(((x%1 < 0.02) || (x%1 > 0.98)) && ((y%1 < 0.02) || (y%1 > 0.98))) {
            is_in_middle_of_tile = true;
            if (player.check_if_wall_in_dir(row_index, col_index, direction, game)) { //player hit wall, no input to change direction present -> automatic movement to empty cell
                while (player.check_if_wall_in_dir(row_index, col_index, direction, game)) { //while there is a wall in current direction
                    Random rand = new Random();
                    direction = rand.nextInt(4); //randomly choose new direction
                }
            }
        }
        
        if (!player.check_if_wall_in_dir(row_index, col_index, direction, game) || !is_in_middle_of_tile) {
            int x_move = 0;
            int y_move = 0;
            if (direction == 0) {
                y_move -= 1;
            }
            if (direction == 1) {
                x_move += 1;
            }
            if (direction == 2) {
                y_move += 1;
            }
            if (direction == 3) {
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
}
