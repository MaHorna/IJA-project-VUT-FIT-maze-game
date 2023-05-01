/**
 * @author Matej Horňanský
 *
 * Controller for main class
 */
package com.pacman;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 * Controller for main class
 */
public class main_controller {
    public HBox hbox_main;
    @FXML private Pane view_holder;
    public void set_view(Node node) {
        view_holder.getChildren().setAll(node);
    }
}
