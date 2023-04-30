/**
 * @author Matej Horňanský
 * @author Dávid Kán
 *
 * Class for saving text into txt file
 */
package com.example;

public class file_manager {
    //saving
    static void add_map(game game) {
        game.recorded_game += game.board.length + " " + game.board[0].length + "\n";
        for (int i = 0; i < game.board.length; i++) {
            for (int j = 0; j < game.board[0].length; j++) {
                game.recorded_game += game.board[i][j];
            }
            game.recorded_game += "\n";
        }
        add_section_delimeter(game);
    }
    static void add_section_delimeter(game game) {
        game.recorded_game += "*\n";
    }
    static void add_update_delimeter(game game) {
        game.recorded_game += "!\n";
    }
    static void add_step(game game, int step) {
        game.recorded_game += step + "\n";
    }
    static void add_triplet(game game, int x, int y, boolean bool) {
        game.recorded_game += x + " " + y + " " + bool + "\n";
    }
    static void add_double(game game, int x, int y) {
        game.recorded_game += x + " " + y + "\n";
    }
    static void add_double(game game, double x, double y) {
        game.recorded_game += x + " " + y + "\n";
    }
    //saving 



    //loading

    //loading
}
