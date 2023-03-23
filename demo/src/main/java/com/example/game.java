package com.example;

import java.util.*;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;

public class game {
    int state; //0 = not started, 1 = in progress, 2 = paused, 3 = finished
    String file_path;
    String file_data;
    String recorded_game;
    int height;
    int width;
    char[][] board;
    triple start;    //stores start position
    triple end;      //stores end position
    player player;   //stores player info
    List<triple> key_list = new ArrayList<triple>(); //stores key info
    List<ghost> ghost_list = new ArrayList<ghost>(); //stores ghost info
    double simulation_speed = 10.0; //tick every x miliseconds
    int cell_size = 40;
    Timeline timeline;

    public game(String file_path) throws IOException {
        this.file_path = file_path;
        game_load_from_file(file_path);
        timeline = new Timeline(new KeyFrame(Duration.millis(simulation_speed), ae -> do_sim_step()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        recorded_game = "";
    }

    void add_row_to_board(int row_index, char[] row_data) { //making board and creating object for later use
        if (row_data.length != width) {
            System.out.println("Declared map size mismatch (columns)");
        }
        board[0][row_index] = '.'; //adds left border
        board[1][row_index] = 'X'; //adds left border
        for (int i = 2; i < width+2; i++) {
            char c = row_data[i-2];
            if (c == 'S') { //start
                start = new triple(i, row_index, false);
                player = new player(i, row_index, 0, 0);
            }
            if (c == 'K') { //key
                key_list.add(new triple(i, row_index, false));
            }
            if (c == 'G') { //ghost
                ghost_list.add(new ghost(i, row_index, 0));
            }
            if (c == 'T') { //end
                end = new triple(i, row_index, false);
            }
            board[row_index][i] = c; //content
        }
        board[width+2][row_index] = 'X'; //adds right border
        board[width+3][row_index] = '.'; //adds right border
    }

    void game_load_from_file(String file_path) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file_path));
        String file_data = reader.readLine();
        if (file_data != null) {
            String[] parts = file_data.split(" "); //get params
            height = Integer.parseInt(parts[0]);
            width = Integer.parseInt(parts[1]);
            if (parts.length != 2) { //bad params
                System.out.println("Invalid file format");
            }
            board = new char[height+4][width+4]; //init board
            for (int i = 0; i < width+4; i++) { //adds top and bottom border
                board[i][0] = '.';
                if (i == 0 || i == width+3) {
                    board[i][1] = '.';
                    board[i][height+2] = '.';
                }
                else {
                    board[i][1] = 'X';
                    board[i][height+2] = 'X';
                }
                board[i][height+3] = '.';
            }
            String tmp_string = ""; //tmp string for reading
            int row_index = 0;
            while (tmp_string != null){
                file_data += tmp_string + "\n";
                tmp_string = reader.readLine();
                if (tmp_string == null) {
                    break;   
                }
                add_row_to_board(row_index+2, tmp_string.toCharArray()); //+2 because of the border
                row_index++;
                if (row_index == height+1) { //more rows than declared
                    System.out.println("Declared map size mismatch (more rows)");
                }
            }
            if (row_index<height) { //less rows than declared
                System.out.println("Declared map size mismatch (less rows)");
            }
        }
        else {
            System.out.println("File is empty");
        }
        reader.close();
    }

    void do_sim_step() {
        player.player_move(this);
        file_manager.add_update_delimeter(this); //after player move update
        player.player_check_keys(this);
        file_manager.add_update_delimeter(this); ///after key pickup 
        player.player_check_end(this);
        for (ghost ghost : ghost_list) {
            ghost.ghost_move(this);
        }
        player.player_check_ghosts(this);
        file_manager.add_section_delimeter(this); //end update section
    }
}
