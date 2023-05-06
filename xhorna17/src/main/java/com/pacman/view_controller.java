/**
 * @author Matej Horňanský
 * @author Dávid Kán
 *
 * Controller class for loading view
 */

package com.pacman;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;

import java.io.IOException;

/**
 * Class for loading view
 */
public class view_controller {
    private static main_controller main_controller;
    public static void set_main_controller(main_controller arg_main_cont) {
        view_controller.main_controller = arg_main_cont;
    }
    public static void load_view(String fxml) {
        try {
            if (view_controller.class.getResource(fxml) == null) {
                System.out.println("FXML file NOT found " + fxml);
            }
            else {
                System.out.println("FXML file found " + fxml);
            }
            main_controller.set_view(FXMLLoader.load(view_controller.class.getResource(fxml)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static HBox getHbox_main(){
        return main_controller.hbox_main;
    }
}
