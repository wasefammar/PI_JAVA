package controllers.Produit;



import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import controllers.User.SessionUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import models.Produit.LigneCommande;
import services.GestionServices.GestionService;
import services.ServicesProduit.EmailSender;
import services.ServicesProduit.G_ligneCommande;
import services.ServicesProduit.GeneratePDF;

import javax.mail.MessagingException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.List;


public class Payment {

    public Label total;

    G_ligneCommande glc = new G_ligneCommande();
    GestionService gs = new GestionService();

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
                        .setCurrency("usd")

                        .build();
                System.out.println(params);

                PaymentIntent intent = null;
                try {
                    intent = PaymentIntent.create(params);
                    String currency = total.getText().split("\\s+")[1];
                    if(currency.equals("DT")){
                        imprimerPDF("DT");
                    }else if (currency.equals("Dollar")){
                        imprimerPDF("Dollar $");
                    } else if (currency.equals("Euro")) {
                        imprimerPDF("Euro €");
                    }
                    EmailSender.sendEmail(SessionUser.getUser().getAdresseEmail(),"Payement successful","Thanks for trusting us ","C:\\Users\\AGENT17\\Desktop\\Commande.pdf" );
                    List<LigneCommande> lcs= glc.Afficher();
                    int idPanier = glc.getPanierByIdUtilisateur(gs.getIdUtilisateurByEmail(SessionUser.getUser().getAdresseEmail()).getId());
                    for (LigneCommande lc : lcs){
                        glc.supprimer(lc.getIdProduit(), idPanier);
                    }
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherProduits.fxml"));
                    Parent root = loader.load();
                    Stage stage = (Stage) total.getScene().getWindow(); // Obtenir la scène actuelle
                    stage.setScene(new Scene(root));
                    stage.setTitle("Page ");
                    stage.centerOnScreen();
                    stage.show();
                    //total.getScene().setRoot(root);
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

    public  void setFields(String somme, String currency){
        this.total.setText(somme);
    }

    public void pay(ActionEvent actionEvent) throws MessagingException {
        processPayment();
        //sending email for the admin

    }

    public void backtoproductlist(ActionEvent actionEvent) {

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

    @FXML
    private void imprimerPDFButtonClicked(ActionEvent event) {

        try {
            GeneratePDF generatePDF = new GeneratePDF();
            generatePDF.generateCommandePDF("C:\\Users\\AGENT17\\Desktop\\Commande.pdf", "DT");
        } catch (FileNotFoundException | MalformedURLException | SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void imprimerPDF(String currency) {

        try {
            GeneratePDF generatePDF = new GeneratePDF();
            generatePDF.generateCommandePDF("C:\\Users\\AGENT17\\Desktop\\Commande.pdf", currency);
        } catch (FileNotFoundException | MalformedURLException | SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
