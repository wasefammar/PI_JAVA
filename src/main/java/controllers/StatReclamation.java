package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import models.Reclamation;
import services.ServiceReclamation;
import services.ServiceReponse;

import java.util.List;
import java.util.Map;

public class StatReclamation {

    @FXML
    private PieChart piechart;
    ServiceReponse sr= new ServiceReponse();
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
}
