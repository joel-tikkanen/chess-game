package com.chessgame;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {

    private static Scene scene;
    private GridPane gp;

    @Override
    public void start(Stage stage) {
        StackPane root = new StackPane();
        scene = new Scene(root, 500, 480); // Use the class-level scene variable
        stage.setScene(scene);

        this.gp = new GridPane();
        gp.setPadding(new Insets(50)); // Add 50 pixels of padding around the GridPane

        int size = 8; // Change the size of the checkerboard

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Pane pane = new Pane();
                pane.setPrefSize(50, 50); // Set a fixed size for the Pane
                if ((i + j) % 2 == 0) {
                    pane.setStyle("-fx-background-color: #F0D9B5");
                } else {
                    pane.setStyle("-fx-background-color: #B58863");
                }
                Button button = new Button();
                button.setPrefSize(50, 50);
                button.setStyle("-fx-background-color: transparent;");

                gp.add(pane, j, i);
            }
        }

        root.getChildren().add(gp);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
