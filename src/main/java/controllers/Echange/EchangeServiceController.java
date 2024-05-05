package controllers.Echange;

import com.mysql.cj.protocol.ServerSessionStateController;
import controllers.User.SessionUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.Echange.EchangeService;
import models.Services.Service;

import services.EchangeServices.ServiceEchangeService;
import services.GestionServices.GestionService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

public class EchangeServiceController implements Initializable {
    @FXML
    private TextField serviceInField;
    @FXML
    private ComboBox<String> serviceOutComboBox;
    @FXML
    private DatePicker dateExchangePicker;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    private ServiceEchangeService serviceEchangeService;
    private GestionService serviceService;
    private List<Service> userServices;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        serviceEchangeService = new ServiceEchangeService();
        serviceService = new GestionService();
        try {
            userServices = serviceService.getServiceByUserId(serviceService.getIdUtilisateurByEmail(SessionUser.getUser().getAdresseEmail()).getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for (Service service: userServices) {
            MenuItem menuItem = new MenuItem(service.getTitreService());
            serviceOutComboBox.getItems().add(menuItem);
            MenuItem menuItem1 = idCategories.getItems().get(idCategories.getItems().size() - 1);
            menuItem1.addEventHandler(ActionEvent.ACTION, e->{
                filtreByCategory(cat);
            });

        }
        // Populate the serviceInComboBox with the selected service from the Service page
        Service selectedService = (Service) getServiceFromServicePage(); // Implement this method
        serviceInField.setText(selectedService.getTitreService());
        // Populate the serviceOutComboBox with the user's own services
        serviceOutComboBox.getItems().addAll(userServices);
    }

    @FXML
    private void handleSaveExchange() {
        Service serviceIn = (Service) getServiceFromServicePage(); // Implement this method
        Service serviceOut = serviceOutComboBox.getSelectionModel().getSelectedItem();
        LocalDateTime dateExchange = LocalDateTime.of(dateExchangePicker.getValue(), LocalDateTime.now().toLocalTime());
        try {
            EchangeService exchange = serviceEchangeService.createExchange(serviceIn, serviceOut, dateExchange, true);
            // Display a success message or navigate to a different view
            System.out.println("Exchange created successfully!");
        } catch (SQLException e) {
            System.err.println("Error creating exchange: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }

    @FXML
    private void handleCancelExchange() {
        serviceInField.clear();
        serviceOutComboBox.getSelectionModel().clearSelection();
        dateExchangePicker.setValue(null);
    }

    @FXML
    private void Retour(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Object getServiceFromServicePage() {
        // Implement this method to retrieve the selected service from the Service page
        // This will depend on how you have implemented the Service page
        return null;
    }

    public void back1(MouseEvent mouseEvent) {
    }

    public void back(MouseEvent mouseEvent) {
    }
}