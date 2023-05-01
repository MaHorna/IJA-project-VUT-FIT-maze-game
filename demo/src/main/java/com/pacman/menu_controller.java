/**
 * @author Matej Horňanský
 * @author Dávid Kán
 *
 * Controller class, functions for buttons form side menu panel
 */
package com.pacman;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.application.Platform;

/**
 * Controller class, functions for buttons from side menu panel
 */
public class menu_controller {
    private game_holder game_singleton = game_holder.get_instance(); 
    private replay_holder replay_singleton = replay_holder.get_instance();
    @FXML private Button ham_button;
    @FXML private VBox side_menu_cont;
    boolean ham_buttonClicked = false;
    @FXML private void animate_hamb_button() throws IOException {
        if (ham_buttonClicked) {
            side_menu_cont.setPrefWidth(55);
            side_menu_cont.setMinWidth(55);
            side_menu_cont.setMaxWidth(55);
        }
        else {
            side_menu_cont.setPrefWidth(200);
            side_menu_cont.setMinWidth(200);
            side_menu_cont.setMaxWidth(200);
        }
        ham_buttonClicked = !ham_buttonClicked;
    }

    @FXML private void play_again_button() throws IOException { //game controller loads last game 
        game game =  game_singleton.get_game();
        if (game != null) {
            game.timeline.stop();
            game_singleton.set_game(new game(game.file_path)); //loads game from file, creates game instance
            view_controller.load_view("game_pane.fxml"); //game controller sets up listens for game events
        }
    }
    
    @FXML private void save_replay_button() throws IOException {
        game game =  game_singleton.get_game();
        if (game != null) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Replay");
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + System.getProperty("file.separator")+ "Documents"));
            fileChooser.getExtensionFilters().add(extFilter);
            Stage stage = (Stage) ham_button.getScene().getWindow();
            System.out.println("save replay");
            File file = fileChooser.showSaveDialog(stage);
            if (file != null) {
                PrintWriter writer;
                writer = new PrintWriter(file);
                writer.println(game.recorded_game);
                writer.close();
            }
        }
    }
    
    @FXML private void load_replay_button() throws IOException { //replay controller
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + System.getProperty("file.separator")+ "Documents"));
        fileChooser.setTitle("Open Replay");
        Stage stage = (Stage) ham_button.getScene().getWindow();
        File file_from_chooser = fileChooser.showOpenDialog(stage);
        if (file_from_chooser != null) {
            String path_to_file = file_from_chooser.getAbsolutePath();
            replay replay =  replay_singleton.get_replay();
            if (replay != null) {
                replay.timeline.stop();
            }
            replay_singleton.set_replay(new replay(path_to_file)); //loads game from file, creates game instance
            view_controller.load_view("replay_pane.fxml"); //game controller sets up listens for game events
        } else {
            System.out.println("Open command cancelled by user.");
        }
    }
    
    @FXML private void load_custom_map_button() throws IOException { //game controller loads game from file
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + System.getProperty("file.separator")+ "Documents"));
        fileChooser.setTitle("Open Game File");
        Stage stage = (Stage) ham_button.getScene().getWindow();
        File file_from_chooser = fileChooser.showOpenDialog(stage);
        if (file_from_chooser != null) {
            String path_to_file = file_from_chooser.getAbsolutePath();
            game game =  game_singleton.get_game();
            if (game != null) {
                game.timeline.stop();
            }
            game_singleton.set_game(new game(path_to_file)); //loads game from file, creates game instance

            view_controller.load_view("game_pane.fxml"); //game controller sets up listens for game events
        } else {
            System.out.println("Open command cancelled by user.");
        }
    }
    
    @FXML private void exit_button() throws IOException {
        Platform.exit();
    }
}
