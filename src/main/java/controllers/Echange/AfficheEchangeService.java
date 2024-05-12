package controllers.Echange;

import controllers.User.SessionUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.Echange.EchangeService;
import services.EchangeServices.ServiceEchangeService;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class AfficheEchangeService {
    @FXML
    private TableView<EchangeService> exchangeTableView;
    @FXML
    private TableColumn<EchangeService, String> serviceInColumn;
    @FXML
    private TableColumn<EchangeService, String> serviceOutColumn;
    @FXML
    private TableColumn<EchangeService, LocalDateTime> dateEchangeColumn;
    @FXML
    private TableColumn<EchangeService, Boolean> valideColumn;
    @FXML
    private TableColumn<EchangeService, ?> actionsColumn;
    @FXML
    private Button updateButton;
    @FXML
    private AreaChart<Number, Number> EchangeChart;

    private Button deleteButton;
    private ServiceEchangeService serviceEchangeService;
    private ObservableList<EchangeService> exchangeServices;

    public Button users_btn;
    public Button Services_btn;
    public Button product_btn;
    public Button Transactions_btn;
    public Button events_btn;
    public Button complaints_btn;
    public Button logout_btn;


    public AfficheEchangeService() {
        serviceEchangeService = new ServiceEchangeService();
        exchangeServices = FXCollections.observableArrayList();
    }

    @FXML
    public void initialize() {
        serviceInColumn.setCellValueFactory(new PropertyValueFactory<>("serviceIn"));
        serviceOutColumn.setCellValueFactory(new PropertyValueFactory<>("serviceOut"));
        dateEchangeColumn.setCellValueFactory(new PropertyValueFactory<>("dateEchange"));
        valideColumn.setCellValueFactory(new PropertyValueFactory<>("valide"));
        actionsColumn.setCellValueFactory(new PropertyValueFactory<>("actions"));
        exchangeTableView.setItems(exchangeServices);

        // Populate the AreaChart from the database
        populateChartFromDatabase(EchangeChart);

        loadExchangeServices();
    }

    private void populateChartFromDatabase(AreaChart<Number, Number> chart) {
        try {
            // Fetch data from the database
            List<EchangeService> exchanges = serviceEchangeService.getAllExchanges();

            // Populate chart with fetched data
            XYChart.Series<Number, Number> series = new XYChart.Series<>();
            for (EchangeService exchange : exchanges) {
                // Assuming you have a method to convert LocalDateTime to a numerical value for x-axis
                int xValue = (int) exchange.getDateEchange().toEpochSecond(java.time.OffsetDateTime.now().getOffset());

                int yValue = exchange.getServiceIn().getId(); // Example: Using serviceIn ID as y-value
                series.getData().add(new XYChart.Data<>(xValue, yValue));
            }

            // Add series to chart
            chart.getData().add(series);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadExchangeServices() {
        try {
            exchangeServices.clear();
            exchangeServices.addAll(serviceEchangeService.getAllExchanges());
        } catch (SQLException e) {
            System.err.println("Error loading exchange services: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void updateExchange(ActionEvent event) {
        // Get the selected exchange service from the table view
        EchangeService selectedExchange = exchangeTableView.getSelectionModel().getSelectedItem();
        if (selectedExchange != null) {
            try {
                serviceEchangeService.updateExchange(selectedExchange);
                loadExchangeServices();
            } catch (SQLException e) {
                System.err.println("Error updating exchanged services: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void deleteExchange(ActionEvent event) {
        // Get the selected exchange service from the table view
        EchangeService selectedExchange = exchangeTableView.getSelectionModel().getSelectedItem();
        if (selectedExchange != null) {
            try {
                serviceEchangeService.deleteExchange(selectedExchange);
                exchangeServices.remove(selectedExchange);
            } catch (SQLException e) {
                System.err.println("Error deleting exchange : " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void addExchange(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("AfficheEchangeService.fxml"));
            Stage stage = (Stage) exchangeTableView.getScene().getWindow(); // Obtenir la scène actuelle
            stage.setScene(new Scene(root));
            stage.setTitle("Page ");
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void Retour(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AllServices.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) exchangeTableView.getScene().getWindow(); // Obtenir la scène actuelle
            stage.setScene(new Scene(root));
            stage.setTitle("Page ");
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            // Replace with a more robust logging mechanism
            System.out.println("An error occurred while loading the AllServices view: " + e.getMessage());
        }
    }

    public void users(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader  = new FXMLLoader(getClass().getResource("/Dashuser.fxml"));
        Parent anchorPane = loader.load();
        Stage stage = (Stage) users_btn.getScene().getWindow(); // Obtenir la scène actuelle
        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Page ");
        stage.centerOnScreen();
        stage.show();
        // users_btn.getScene().setRoot(anchorPane);
    }

    public void service(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader  = new  FXMLLoader(getClass().getResource("/ServicesAdmin.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) users_btn.getScene().getWindow(); // Obtenir la scène actuelle
        stage.setScene(new Scene(root));
        stage.setTitle("Page ");
        stage.centerOnScreen();
        stage.show();
    }

    public void product(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader  = new  FXMLLoader(getClass().getResource("/ProduitsAdmin.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) users_btn.getScene().getWindow(); // Obtenir la scène actuelle
        stage.setScene(new Scene(root));
        stage.setTitle("Page ");
        stage.centerOnScreen();
        stage.show();
    }

    public void transaction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader  = new  FXMLLoader(getClass().getResource("/AfficheEchangeService.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) users_btn.getScene().getWindow(); // Obtenir la scène actuelle
        stage.setScene(new Scene(root));
        stage.setTitle("Page ");
        stage.centerOnScreen();
        stage.show();
    }

    public void events(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader  = new  FXMLLoader(getClass().getResource("/afficher_reclamation1.fxml"));
        Parent anchorPane = loader.load();
        Stage stage = (Stage) users_btn.getScene().getWindow(); // Obtenir la scène actuelle
        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Page ");
        stage.centerOnScreen();
        stage.show();
    }


    public void logout(ActionEvent actionEvent) throws IOException {
        SessionUser.resetSession();
        FXMLLoader loader  = new FXMLLoader(getClass().getResource("/Login.fxml"));
        Parent anchorPane = loader.load();
        Stage stage = (Stage) users_btn.getScene().getWindow(); // Obtenir la scène actuelle
        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Page ");
        stage.centerOnScreen();
        stage.show();
    }

    public void complaint(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader  = new  FXMLLoader(getClass().getResource("/afficher_reclamation1.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) users_btn.getScene().getWindow(); // Obtenir la scène actuelle
        stage.setScene(new Scene(root));
        stage.setTitle("Page ");
        stage.centerOnScreen();
        stage.show();
    }

    public void returnToCategories(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader  = new FXMLLoader(getClass().getResource("/CategorieFX.fxml"));
        Parent anchorPane = loader.load();
        Stage stage = (Stage) users_btn.getScene().getWindow(); // Obtenir la scène actuelle
        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Page ");
        stage.centerOnScreen();
        stage.show();
    }

    public void returnToHome(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader  = new FXMLLoader(getClass().getResource("/dash_admin.fxml"));
        Parent anchorPane = loader.load();
        Stage stage = (Stage) users_btn.getScene().getWindow(); // Obtenir la scène actuelle
        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Page ");
        stage.centerOnScreen();
        stage.show();
    }
}
