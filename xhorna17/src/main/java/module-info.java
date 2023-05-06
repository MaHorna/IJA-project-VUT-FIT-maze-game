module com.pacman {
    requires transitive javafx.controls;
    requires javafx.fxml;

    opens com.pacman to javafx.fxml;
    exports com.pacman;
}
