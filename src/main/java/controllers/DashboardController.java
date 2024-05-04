package controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class DashboardController {


    public JFXButton btn_rec;

    public void moveToComplaints(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader  = FXMLLoader.load(getClass().getResource("/afficher_reclamation1.fxml"));
        AnchorPane anchorPane = loader.load();
        btn_rec.getScene().setRoot(anchorPane);

    }

    public void users(ActionEvent actionEvent) {
    }

    public void service(ActionEvent actionEvent) {
    }

    public void product(ActionEvent actionEvent) {
    }

    public void transaction(ActionEvent actionEvent) {
    }

    public void events(ActionEvent actionEvent) {
    }

    public void complaints(ActionEvent actionEvent) {
    }

    public void logout(ActionEvent actionEvent) {
    }
}
