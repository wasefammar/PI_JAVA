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
import models.Echange.EchangeProduit;
import models.Produit.Produit;

import services.EchangeServices.ServiceEchangeProduit;
import services.ServicesProduit.GProduit;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

public class EchangeProduitController {
    public Label idProduitIn;
    public Label idTitreProduit;
    @FXML
    private TextField produitInField;
    @FXML
    private ComboBox<String> produitOutComboBox;
    @FXML
    private DatePicker dateExchangePicker;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    private ServiceEchangeProduit serviceEchangeProduit=new ServiceEchangeProduit();;
    private GProduit serviceProduit = new GProduit(); ;
    private List<Produit> userProduits;

    public void setData(int idProduit) throws SQLException {
        idProduitIn.setText(""+idProduit);
        produitInField.setText(serviceProduit.getProduitById(Integer.parseInt(idProduitIn.getText())).getTitreProduit());
        try {
            userProduits = serviceProduit.getProduitByUserId(serviceProduit.getIdUtilisateurByEmail(SessionUser.getUser().getAdresseEmail()).getId());
            // userServices = serviceService.getServiceByUserId(3);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for (Produit produit: userProduits) {

            produitOutComboBox.getItems().add(produit.getTitreProduit());

        }
        // Populate the serviceInComboBox with the selected service from the Service page
        //Service selectedService = (Service) getServiceFromServicePage(); // Implement this method
        //serviceInField.setText(selectedService.getTitreService());
    }

/*    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        serviceEchangeService = new ServiceEchangeService();
        serviceService = new GestionService();
        try {
          //  userServices = serviceService.getServiceByUserId(serviceService.getIdUtilisateurByEmail(SessionUser.getUser().getAdresseEmail()).getId());
            userServices = serviceService.getServiceByUserId(3);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for (Service service: userServices) {
            MenuItem menuItem = new MenuItem(service.getTitreService());
            serviceOutComboBox.getItems().add(menuItem.getText());
            menuItem.addEventHandler(ActionEvent.ACTION, e->{
               serviceOutComboBox.setPromptText(menuItem.getText());
            });

        }
        // Populate the serviceInComboBox with the selected service from the Service page
        Service selectedService = (Service) getServiceFromServicePage(); // Implement this method
        serviceInField.setText(selectedService.getTitreService());
        // Populate the serviceOutComboBox with the user's own services
    }*/

    @FXML
    private void handleSaveExchange() throws SQLException {
        Produit produitIn = serviceProduit.getProduitById(Integer.parseInt(idProduitIn.getText()));
        System.out.println(idTitreProduit.getText());
        Produit produitOut = serviceProduit.getProduitByName(produitOutComboBox.getValue());
        LocalDateTime dateExchange = LocalDateTime.of(dateExchangePicker.getValue(), LocalDateTime.now().toLocalTime());
        try {

            EchangeProduit exchange = serviceEchangeProduit.createExchange(produitIn, produitOut, dateExchange, false);
            // Display a success message or navigate to a different view
            System.out.println("Exchange created successfully!");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Transaction2.fxml"));
            Parent root = loader.load();
            idProduitIn.getScene().setRoot(root);

        } catch (SQLException e) {
            System.err.println("Error creating exchange: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }

    @FXML
    private void handleCancelExchange() {
        produitInField.clear();
        produitOutComboBox.getSelectionModel().clearSelection();
        dateExchangePicker.setValue(null);
    }

    @FXML
    private void Retour(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShowService.fxml"));
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