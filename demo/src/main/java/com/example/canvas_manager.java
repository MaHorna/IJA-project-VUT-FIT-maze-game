/**
 * @author Matej Horňanský
 * @author Dávid Kán
 *
 * Class used for drawing board
 */
package com.example;

import javafx.scene.paint.Color;
import javafx.scene.canvas.GraphicsContext;

public class canvas_manager {
    static int cell_size = 40;
    public static void draw_board(GraphicsContext gc, char[][] board, int width, int height) {
        
        if (board == null) {
            return;
        }
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(2);
        for (int i = 1; i < width+3; i++) { //x
            for (int j = 1; j < height+3; j++) { //y
                if (board[j][i] == 'X') { //we are drawing a wall
                    double x_1;
                    double y_1;
                    double x_2;
                    double y_2;
                    char up = board[j-1][i];
                    char down = board[j+1][i];
                    char left = board[j][i-1];
                    char right = board[j][i+1];
                    double pixel_x_1 = 0;
                    double pixel_y_1 = 0;
                    double pixel_x_2 = 0;
                    double pixel_y_2 = 0;
                    double pixel_bezier_target_x = 0;
                    double pixel_bezier_target_y = 0;
                    //up right
                    if (up == 'X') {
                        x_1 = 0.75;
                        y_1 = 0;
                    }
                    else {
                        x_1 = 0.5;
                        y_1 = 0.25;
                    }
                    if (right == 'X') {
                        x_2 = 1;
                        y_2 = 0.25;
                    }
                    else {
                        x_2 = 0.75;
                        y_2 = 0.5;
                    }
                    pixel_x_1 = (i+x_1)*cell_size;
                    pixel_y_1 = (j+y_1)*cell_size;
                    pixel_x_2 = (i+x_2)*cell_size;
                    pixel_y_2 = (j+y_2)*cell_size;
                    pixel_bezier_target_x = (i+0.75)*cell_size;
                    pixel_bezier_target_y = (j+0.25)*cell_size;
                    gc.beginPath();
                    gc.moveTo(pixel_x_1, pixel_y_1);
                    gc.bezierCurveTo(pixel_bezier_target_x, pixel_bezier_target_y, pixel_bezier_target_x, pixel_bezier_target_y, pixel_x_2, pixel_y_2);
                    gc.stroke();
                    //up right

                    //right down
                    if (right == 'X') {
                        x_1 = 1;
                        y_1 = 0.75;
                    }
                    else {
                        x_1 = 0.75;
                        y_1 = 0.5;
                    }
                    if (down == 'X') {
                        x_2 = 0.75;
                        y_2 = 1;
                    }
                    else {
                        x_2 = 0.5;
                        y_2 = 0.75;
                    }
                    pixel_x_1 = (i+x_1)*cell_size;
                    pixel_y_1 = (j+y_1)*cell_size;
                    pixel_x_2 = (i+x_2)*cell_size;
                    pixel_y_2 = (j+y_2)*cell_size;
                    pixel_bezier_target_x = (i+0.75)*cell_size;
                    pixel_bezier_target_y = (j+0.75)*cell_size;
                    gc.beginPath();
                    gc.moveTo(pixel_x_1, pixel_y_1);
                    gc.bezierCurveTo(pixel_bezier_target_x, pixel_bezier_target_y, pixel_bezier_target_x, pixel_bezier_target_y, pixel_x_2, pixel_y_2);
                    gc.stroke();
                    //right down

                    //down left
                    if (down == 'X') {
                        x_1 = 0.25;
                        y_1 = 1;
                    }
                    else {
                        x_1 = 0.5;
                        y_1 = 0.75;
                    }
                    if (left == 'X') {
                        x_2 = 0;
                        y_2 = 0.75;
                    }
                    else {
                        x_2 = 0.25;
                        y_2 = 0.5;
                    }
                    pixel_x_1 = (i+x_1)*cell_size;
                    pixel_y_1 = (j+y_1)*cell_size;
                    pixel_x_2 = (i+x_2)*cell_size;
                    pixel_y_2 = (j+y_2)*cell_size;
                    pixel_bezier_target_x = (i+0.25)*cell_size;
                    pixel_bezier_target_y = (j+0.75)*cell_size;
                    gc.beginPath();
                    gc.moveTo(pixel_x_1, pixel_y_1);
                    gc.bezierCurveTo(pixel_bezier_target_x, pixel_bezier_target_y, pixel_bezier_target_x, pixel_bezier_target_y, pixel_x_2, pixel_y_2);
                    gc.stroke();
                    //down left

                    //left up
                    if (left == 'X') {
                        x_1 = 0;
                        y_1 = 0.25;
                    }
                    else {
                        x_1 = 0.25;
                        y_1 = 0.5;
                    }
                    if (up == 'X') {
                        x_2 = 0.25;
                        y_2 = 0;
                    }
                    else {
                        x_2 = 0.5;
                        y_2 = 0.25;
                    }
                    pixel_x_1 = (i+x_1)*cell_size;
                    pixel_y_1 = (j+y_1)*cell_size;
                    pixel_x_2 = (i+x_2)*cell_size;
                    pixel_y_2 = (j+y_2)*cell_size;
                    pixel_bezier_target_x = (i+0.25)*cell_size;
                    pixel_bezier_target_y = (j+0.25)*cell_size;
                    gc.beginPath();
                    gc.moveTo(pixel_x_1, pixel_y_1);
                    gc.bezierCurveTo(pixel_bezier_target_x, pixel_bezier_target_y, pixel_bezier_target_x, pixel_bezier_target_y, pixel_x_2, pixel_y_2);
                    gc.stroke();
                    //left up
                }
            }
        }
    }
}
