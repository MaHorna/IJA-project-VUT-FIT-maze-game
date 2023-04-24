package com.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Pac man");
        stage.setScene(create_scene(load_main_pane()));
        stage.show();
    }

    private Pane load_main_pane() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/main.fxml"));
        Pane main_pane = (Pane) loader.load();
        view_controller.set_main_controller(loader.getController());
        view_controller.load_view("home_pane.fxml");
        return main_pane;
    }

    private Scene create_scene(Pane main_pane) {
        Scene scene = new Scene(main_pane);
        return scene;
    }

    public static void main(String[] args) {
        launch();
    }
}
/*
 * model :          map     enum[][] cell_value, cell_size, key_number, ghosts[], player, simulation_time, simulation_speed(ticks per second)
 *                  ghost   xy_position, direction, speed, ai_type?
 *                  player  xy_position, current_direction, next_direction (cannot change dir. yet situation), speed, eat_ghosts_activated bool, level, key_got
 *                  editor  selected_tile, map_size_x, map_size_y, enum[][] cell_value, has_start_tile, has_end_tile
 * 
 * view :           home    classic game, starts when spacebar pressed
 *                  replay  opens windows input file system, starts replay when spacebar pressed
 *                  custom  opens windows input file system, starts custom game from file when spacebar pressed      
 *                  editor  creates map, saves map to file, starts custom game from file when spacebar pressed 
 *                          pallette of tiles, paint with mouse click/drag&click 
 *    
 * controller :     any sim if mouse pressed reset route variables, calc route, save route, use it to set direction during "simulation" 
 *                          when key pressed reset route variables
 *                          wsad keys to set direction , or mouse click to set direction
 *                          spacebar to start game/restart game
 *                          when key pressed and not yet able to move that direction (pressing up but not at tile that has up empty) then save direction to next_direction
 *                          generaly move in current_direction, if next_direction is possible, then change current_direction to next_direction
 *                          each tick move ghosts/player by their speed
 *                          player/ghost can only change direction when in middle of tile
 *                          if T tile hit , and keys_got == map key_number => win popup
 *                          if T tile hit , and keys_got != map key_number continue
 *                          if ghost hit, and eat_ghosts_activated => ghost die, player eat_ghosts_activated = false, player speed = normal
 *                          if ghost hit, and not eat_ghosts_activated => player die, popup
 *                          if eat_ghost_powerup tile hit, and player set player bool and speed to higher
 * 
 *          
 *                  replay  saving - each tick - positions of player/ghosts, map
 *                          loading - load map, load positions of player/ghosts, read next tick ...
 *                  custom  load map from file
 *                  editor  liston to mouse click/drag&click
 * 
 * 
 * need to decide on whether we doing small feature upgrades (eat_ghost_powerup) or custom map editor for the interactive part of assignment.          
 */