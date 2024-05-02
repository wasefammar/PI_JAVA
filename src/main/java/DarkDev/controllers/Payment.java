package DarkDev.controllers;


import DarkDev.models.LigneCommande;
import DarkDev.models.Produit;
//import DarkDev.services.EmailSender;
import DarkDev.services.EmailSender;
import DarkDev.services.G_ligneCommande;
import DarkDev.services.GeneratePDF;
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

import javax.mail.MessagingException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.List;


public class Payment {


}
