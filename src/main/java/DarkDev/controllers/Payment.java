package DarkDev.controllers;


import DarkDev.models.LigneCommande;
import DarkDev.models.Produit;
import DarkDev.services.G_ligneCommande;
import DarkDev.test.MainFX;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


public class Payment {

    public Label total;
    /*   // Your secret key should ideally be stored securely and retrieved from a secure location
        private static final String SECRET_KEY = "sk_test_51P9AG603pY7kKWfgFkhBd8uhJ8D7EtmLS60m2a6skEZ48aTYaSxwztXPx4uWvn3VxhzpICFCf2QRY5ILApdjPgvG00xje0cKBF";

        public void processPayment() {
            try {
                // Set your secret key
                Stripe.apiKey = SECRET_KEY;

                // Create a PaymentIntent with payment details
                PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                        .setAmount(1000L) // Amount in cents (e.g., $10.00)
                        .setCurrency("usd")
                        .build();

                PaymentIntent intent = PaymentIntent.create(params);

                // If the payment was successful, display a success message
                System.out.println("Payment successful. PaymentIntent ID: " + intent.getId());
            } catch (StripeException e) {
                // If there was an error processing the payment, display the error message
                System.out.println("Payment failed. Error: " + e.getMessage());
            }
        }

        public static void main(String[] args) {
            Payment processor = new Payment();
            processor.processPayment();
        }*/
 G_ligneCommande glc = new G_ligneCommande();

    public static void main(String[] args) {
// Set your secret key here
        Stripe.apiKey = "sk_test_51P9AG603pY7kKWfgFkhBd8uhJ8D7EtmLS60m2a6skEZ48aTYaSxwztXPx4uWvn3VxhzpICFCf2QRY5ILApdjPgvG00xje0cKBF";

        try {
// Retrieve your account information
            Account account = Account.retrieve();
            System.out.println("Account ID: " + account.getId());
// Print other account information as needed
        } catch (StripeException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void processPayment() {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Payement");
            alert.setHeaderText("Are you sure you want to validate the payement?");
            alert.setContentText("This action cannot be undone.");

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    Stripe.apiKey = "sk_test_51P9AG603pY7kKWfgFkhBd8uhJ8D7EtmLS60m2a6skEZ48aTYaSxwztXPx4uWvn3VxhzpICFCf2QRY5ILApdjPgvG00xje0cKBF";
                    System.out.println(total.getText().split("\\s+")[0]);
                    PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                            .setAmount((long) (Double.parseDouble(total.getText().split("\\s+")[0])*32))// Amount in cents (e.g., $10.00)
                            //.setReceiptEmail("fouratchawech50@gmail.com")
                            .setCurrency("usd")

                            .build();

                    PaymentIntent intent = null;
                    try {
                        intent = PaymentIntent.create(params);
                        List<LigneCommande> lcs= glc.Afficher();
                        for (LigneCommande lc : lcs){
                            glc.supprimer(lc.getIdProduit());
                        }
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherProduits.fxml"));
                        Parent root = loader.load();
                        total.getScene().setRoot(root);
                        System.out.println("Payment successful. PaymentIntent ID: " + intent.getId());
                    } catch (StripeException e) {
                        System.out.println("Payment failed. Error: " + e.getMessage());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
            });


    }

    public  void setFields(String somme){
        this.total.setText(somme);
    }

    public void pay(ActionEvent actionEvent) {
        processPayment();
    }

    public void backtoproductlist(ActionEvent actionEvent) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherProduits.fxml"));
            Parent root = loader.load();
            total.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }
}
