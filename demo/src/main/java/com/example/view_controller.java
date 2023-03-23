package com.example;

import javafx.fxml.FXMLLoader;
import java.io.IOException;

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
}
