package controllers.Produit;


import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import controllers.User.SessionUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Produit.Produit;
import services.GestionServices.GestionService;
import services.ServicesProduit.G_ligneCommande;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class PanierController{

    public Label total;
    public Label idShipping;
    G_ligneCommande glc = new G_ligneCommande();
    GestionService gs = new GestionService();
    public Button back;
    public Button payerb;
    public Label somme;
    public VBox idlignec;

    public void setFields(String currency) {
        try {
            Label empty = new Label();
            empty.setText("No items found !!");
            empty.setStyle("-fx-font-family: 'Corbel';" +      // Setting font family to Corbel\n" +
                    "-fx-font-size: 24px;" +            // Setting font size to 24px\n" +
                    "-fx-font-weight: bold;" +          // Making the font bold\n" +
                    "-fx-padding: 300px 0 0 220px;");
            idlignec.getChildren().add(empty);
            int idPanier = glc.getPanierByIdUtilisateur(gs.getIdUtilisateurByEmail(SessionUser.getUser().getAdresseEmail()).getId());
            List<Produit> produits = glc.ListeProduits(idPanier);
            if (!produits.isEmpty()) {
                double total1 = 0;

                idlignec.getChildren().remove(empty);
                double sum = produits.stream().mapToDouble(p -> p.getPrix()).sum();
                if(currency.equals("DT") || currency.equals("currency")){
                    sum = sum ;
                    total1 = sum + 10;

                }else if (currency.equals("Dollar $")){
                    sum = sum *0.32;
                    total1 = sum + 10*0.32;
                    idShipping.setText(10*0.32+" Dollar $");


                }else if (currency.equals("Euro €")){
                    sum = sum *0.3;
                    total1 = sum + 10*0.3;
                    idShipping.setText(10*0.3+" Euro €");

                }
                somme.setText(String.format("%.2f",sum) + " " + currency);
                total.setText(String.format("%.2f",total1)+ " " + currency);
                for (int i = 0; i < produits.size(); i++) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/Lcitem.fxml"));
                    HBox box = fxmlLoader.load();
                    LcItemController lcItemController = fxmlLoader.getController();
                    lcItemController.setData(produits.get(i), currency);
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



    public void continueshopping(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherProduits.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) total.getScene().getWindow(); // Obtenir la scène actuelle
            stage.setScene(new Scene(root));
            stage.setTitle("Page ");
            stage.centerOnScreen();
            stage.show();
            //total.getScene().setRoot(root);
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
                String currency = somme.getText().split("\\s+")[1];
                if(currency.equals("DT")){
                    payment.setFields(total.getText(), currency);
                }else if (currency.equals("Dollar")){
                    payment.setFields(total.getText(), currency+" $");
                } else if (currency.equals("Euro")) {
                    payment.setFields(total.getText(), currency+" €");
                }
                Stage stage = (Stage) total.getScene().getWindow(); // Obtenir la scène actuelle
                stage.setScene(new Scene(root));
                stage.setTitle("Page ");
                stage.centerOnScreen();
                stage.show();
                //total.getScene().setRoot(root);
            }


        } catch (IOException e) {
            System.err.println(e.getMessage());
        }


        }
    }




