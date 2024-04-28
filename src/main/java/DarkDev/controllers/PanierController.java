package DarkDev.controllers;

import DarkDev.models.Produit;
import DarkDev.services.G_ligneCommande;
import DarkDev.test.MainFX;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class PanierController implements Initializable {

    public Label total;
    G_ligneCommande glc = new G_ligneCommande();
    public Button back;
    public Button payerb;
    public Label somme;
    public VBox idlignec;

    public void setFields() {
        try {
            Label empty = new Label();
            empty.setText("No items found !!");
            empty.setStyle("-fx-font-family: 'Corbel';" +      // Setting font family to Corbel\n" +
                    "-fx-font-size: 24px;" +            // Setting font size to 24px\n" +
                    "-fx-font-weight: bold;" +          // Making the font bold\n" +
                    "-fx-padding: 300px 0 0 220px;");
            idlignec.getChildren().add(empty);
            List<Produit> produits = glc.ListeProduits();
            if (!produits.isEmpty()) {

                idlignec.getChildren().remove(empty);
                double sum = produits.stream().mapToDouble(p -> p.getPrix()).sum();
                somme.setText(sum + " " + MainFX.CURRENCY);
                total.setText(sum + 10 + " " + MainFX.CURRENCY);
                for (int i = 0; i < produits.size(); i++) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/Lcitem.fxml"));
                    HBox box = fxmlLoader.load();
                    LcItemController lcItemController = fxmlLoader.getController();
                    lcItemController.setData(produits.get(i));
                    idlignec.getChildren().add(box);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    public void payer(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setFields();
    }

    public void continueshopping(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherProduits.fxml"));
            Parent root = loader.load();
            total.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void handlepayment(ActionEvent actionEvent) throws StripeException {

        try {
            if (Double.parseDouble(somme.getText().split("\\s+")[0])==0){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Action not allowed");
                alert.setHeaderText("Make sure to fill your cart before proceding to payement!");

                alert.showAndWait();
            }
            else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Payment.fxml"));
                Parent root = loader.load();
                Payment payment= loader.getController();
                payment.setFields(total.getText());
                total.getScene().setRoot(root);
            }


        } catch (IOException e) {
            System.err.println(e.getMessage());
        }


        }
    }




