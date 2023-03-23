package com.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class replay {
    int state; //0 = not started, 1 = in progress, 2 = paused, 3 = finished
    String recorded_game;
    String file_path;
    int height;
    int width;
    static char[][] board;
    triple start;    //stores start position
    triple end;      //stores end position
    player player;   //stores player info
    List<triple> key_list = new ArrayList<triple>(); //stores key info
    List<ghost> ghost_list = new ArrayList<ghost>(); //stores ghost info
    double simulation_speed = 10.0; //tick every x miliseconds
    int cell_size = 40;
    Timeline timeline;
    BufferedReader reader;

    public replay(String file_path) throws IOException {
        replay_load_from_file(file_path);
        timeline = new Timeline(new KeyFrame(Duration.millis(simulation_speed), ae -> {
            try {
                do_replay_step();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
    }
    
    void replay_load_from_file(String file_path) throws IOException {
        reader = new BufferedReader(new FileReader(file_path));
        String tmp_string = reader.readLine();
        if (tmp_string != null) {
            //get params
            System.out.println("get params: " + tmp_string);
            String[] parts1 = tmp_string.split(" "); 
            height = Integer.parseInt(parts1[0]);
            width = Integer.parseInt(parts1[1]);

            //get map
            board = new char[height][width]; //init board
            tmp_string = ""; //tmp string for reading
            int row_index = 0;
            while (tmp_string != null){ 
                tmp_string = reader.readLine();
                System.out.println("reading map: " + tmp_string);
                if (tmp_string == null || tmp_string.equals("*")) {
                    break;   
                }
                replay.board[row_index] = tmp_string.toCharArray();
                row_index++;
                if (row_index == height+1) { //more rows than declared
                    System.out.println("Declared map size mismatch (more rows)");
                }
            }
            if (row_index<height) { //less rows than declared
                System.out.println("Declared map size mismatch (less rows)");
            }

            //get player
            tmp_string = reader.readLine();
            System.out.println("get player: " + tmp_string);
            String[] parts2 = tmp_string.split(" "); 
            player = new player(Integer.parseInt(parts2[0]), Integer.parseInt(parts2[1]), 0,0);
            if (!reader.readLine().equals("*")) {
                System.out.println("Missing section delimeter, not well formed replay ? 1");
            }

            //get end
            tmp_string = reader.readLine();
            tmp_string = tmp_string.replaceAll("\\s+", " ");
            System.out.println("get end: " + tmp_string);
            String[] parts3 = tmp_string.split(" "); 
            end = new triple(Integer.parseInt(parts3[0]), Integer.parseInt(parts3[1]), false);
            if (!reader.readLine().equals("*")) {
                System.out.println("Missing section delimeter, not well formed replay ? 2");
            }

            //get keys
            tmp_string = reader.readLine(); //tmp string for reading
            while (!tmp_string.equals("*")) {
                String[] parts4 = tmp_string.split(" ");
                key_list.add(new triple(Integer.parseInt(parts4[0]), Integer.parseInt(parts4[1]), false));
                tmp_string = reader.readLine();
            }

            //get ghosts
            tmp_string = reader.readLine(); //tmp string for reading
            while (!tmp_string.equals("*"))  {
                String[] parts4 = tmp_string.split(" ");
                ghost_list.add(new ghost(Double.parseDouble(parts4[0]), Double.parseDouble(parts4[1]), 0));
                tmp_string = reader.readLine();
            }
        }
        else {
            System.out.println("File is empty");
        }
    }
    

    void do_replay_step() throws IOException{
        //player move
        String tmp_string = reader.readLine();
        if (tmp_string == null) {
            timeline.stop();
            System.out.println("replay finished");
            return;
        }
        while (!tmp_string.equals("!")) {
            String[] parts = tmp_string.split(" ");
            player.image_view.relocate(Double.parseDouble(parts[0]), Double.parseDouble(parts[1]));
            tmp_string = reader.readLine();
        }
        //key changes
        tmp_string = reader.readLine();
        while (!tmp_string.equals("!")) {
            String[] parts = tmp_string.split(" ");
            key_list.get(Integer.parseInt(parts[0])).image_view.setVisible(false);
            tmp_string = reader.readLine();
        }
        //ghosts move
        tmp_string = reader.readLine();
        int ghost_index = 0;
        while (!tmp_string.equals("*"))  {
            String[] parts = tmp_string.split(" ");
            ghost_list.get(ghost_index).image_view.relocate(Double.parseDouble(parts[0]), Double.parseDouble(parts[1]));
            tmp_string = reader.readLine();
            ghost_index++;
        }
        System.out.println("replay step");
    }
}
