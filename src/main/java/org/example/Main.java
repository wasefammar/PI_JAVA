package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main  extends Application {


    @Override
    public void start(Stage stage) throws Exception {
/*
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Addusercontroller.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 467, 520);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();*/

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/DashUser.fxml"));

        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();

    }


    public static void main(String[] args) {
        launch(args);



    }

}