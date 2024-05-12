package controllers.Reclamation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import javafx.stage.Stage;
import services.ServicesReclamation.ServiceReclamation;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class StatReclamation {

    @FXML
    private PieChart piechart;
    ServiceReclamation serviceReclamation = new ServiceReclamation();


    @FXML
    void initialize() {
        // Retrieve the list of specialties and their counts

        Map<String, Long> urgencyCounts =  serviceReclamation.getUrgenceDistribution();

        // Create ObservableList of PieChart.Data
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        urgencyCounts.forEach((specialty, count) -> pieChartData.add(new PieChart.Data(specialty, count)));

        // Set the data to the pie chart
        piechart.setData(pieChartData);
    }

    public void back(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/dash_admin.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) piechart.getScene().getWindow(); // Obtenir la scène actuelle
        stage.setScene(new Scene(root));
        stage.setTitle("Page ");
        stage.centerOnScreen();
        stage.show();
    }

    /*public void back(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficher_reclamation1.fxml"));

    }*/
}
