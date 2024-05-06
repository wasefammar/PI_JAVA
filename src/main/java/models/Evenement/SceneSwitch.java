package models.Evenement;

import services.ServicesEvenement.EventService;
import models.Evenement.Event;
import SwapNShare.sami.MainFX;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.Objects;

public class SceneSwitch {
    public SceneSwitch(AnchorPane currentAnchorPane, String fxml) throws IOException{
        AnchorPane nextAnchorPane = FXMLLoader.load(Objects.requireNonNull(MainFX.class.getResource(fxml)));
        currentAnchorPane.getChildren().removeAll();
        currentAnchorPane.getChildren().setAll(nextAnchorPane);
    }
    public SceneSwitch(BorderPane currentAnchorPane, String fxml) throws IOException{
        AnchorPane nextAnchorPane = FXMLLoader.load(Objects.requireNonNull(MainFX.class.getResource(fxml)));
        currentAnchorPane.getChildren().removeAll();
        currentAnchorPane.getChildren().setAll(nextAnchorPane);
    }
}
