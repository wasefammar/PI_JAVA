package controllers.Echange;
import controllers.User.SessionUser;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.Echange.EchangeService;
import models.Services.Service;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import javafx.beans.property.SimpleStringProperty;
import services.EchangeServices.ServiceEchangeService;
import services.GestionServices.GestionService;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Transaction {
    public Button idValidate;
    public Button idWithdraw;
    public Button idRejectTransaction;
    public TableColumn<EchangeService, String>  id1;
    public TableColumn<EchangeService, String>  id2;
    private static final String ACCOUNT_SID = "AC7743f6f4c8958bef21c21cdef33596b4";
    private static final String AUTH_TOKEN = "309684c9743a37ce65bc15c6738962a9";
    static {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    public Label idUsername;

    public static void sendSMS(String recipientPhoneNumber, String messageBody) {
        String twilioPhoneNumber = "+12562426661";
        // Create and send the message
        Message message = Message.creator(
                        new PhoneNumber(recipientPhoneNumber),
                        new PhoneNumber(twilioPhoneNumber),
                        messageBody)
                .create();
        // Print the SID of the sent message
        System.out.println("SMS sent successfully. SID: " + message.getSid());
    }

    @FXML
    private TableView<EchangeService> serviceInTableView;
    @FXML
    private TableColumn<EchangeService, String> serviceInColumn1;
    @FXML
    private TableColumn<EchangeService, String> serviceOutColumn1;
    @FXML
    private TableView<EchangeService> serviceOutTableView;
    @FXML
    private TableColumn<EchangeService, String> serviceInColumn2;
    @FXML
    private TableColumn<EchangeService, String> serviceOutColumn2;
    GestionService userService = new GestionService();
    ServiceEchangeService serviceEchangeService = new ServiceEchangeService();

    @FXML
    private void initialize() throws SQLException {
        idUsername.setText(SessionUser.getUser().getNom()+" "+SessionUser.getUser().getPrenom());
        // Populate tables
        populateServiceInTable();
        populateServiceOutTable();
        /*populateProduitInTable();
        populateProduitOutTable();*/
    }
    // Populate the TableView with EchangeService records where ServiceIn belongs to the current user
    private void populateServiceInTable() throws SQLException {
        // Fetch EchangeService records where ServiceIn belongs to the current user

        SessionUser user = SessionUser.getUser();
        int sessionUserId = userService.getIdUtilisateurByEmail(user.getAdresseEmail()).getId();
        List<Service> services = userService.getServiceByUserId(sessionUserId);
        ServiceEchangeService echangeService = new ServiceEchangeService();
        List<EchangeService> echangeServices = echangeService.getExchangeByServicesIn(services);


        // Populate the table view with fetched records
        serviceInTableView.getItems().addAll(echangeServices);
        serviceInColumn1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getServiceName()));
        serviceOutColumn1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getServiceName()));

    }

    private void populateServiceOutTable() throws SQLException {
        // Fetch EchangeService records where ServiceIn belongs to the current user
        SessionUser user = SessionUser.getUser();
        int sessionUserId = user.getId();
        GestionService userService = new GestionService();
        List<Service> services = userService.getServiceByUserId(sessionUserId);
        ServiceEchangeService echangeService = new ServiceEchangeService();
        List<EchangeService> echangeServices = echangeService.getExchangeByServicesOut(services);
        // Populate the table view with fetched records
        serviceOutTableView.getItems().addAll(echangeServices);
        serviceInColumn2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getServiceName()));
        serviceOutColumn2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getServiceOut().getTitreService()));

    }

    public void validateTransaction(ActionEvent actionEvent) throws SQLException {
        EchangeService echangeService = serviceOutTableView.getSelectionModel().getSelectedItem();
        serviceEchangeService.validateExchange(echangeService);
        serviceOutTableView.getItems().clear();
        populateServiceOutTable();

        String recipientPhoneNumber = "+21623553387";
        String messageBody = "Validation Successfully Made " ;
        sendSMS(recipientPhoneNumber, messageBody);


        String recipientMail = "wassefammar17@gmail.com";

        sendEmail(recipientMail,"succeful transaction","succeful");
    }

    public void cancelTransaction(ActionEvent actionEvent) throws SQLException {
        EchangeService echangeService = serviceInTableView.getSelectionModel().getSelectedItem();
        serviceEchangeService.deleteExchange(echangeService);
        serviceInTableView.getItems().clear();
        populateServiceInTable();
        String recipientPhoneNumber = "+21623553387";
        String messageBody = "Validation Rejected " ;
        sendSMS(recipientPhoneNumber, messageBody);
    }

    public void rejectTransaction(ActionEvent actionEvent) throws SQLException {
        EchangeService echangeService = serviceOutTableView.getSelectionModel().getSelectedItem();
        serviceEchangeService.deleteExchange(echangeService);
        serviceOutTableView.getItems().clear();
        populateServiceOutTable();

    }

    public static void sendEmail(String recipient, String subject, String body) {
        // Set properties for the SMTP server
        Properties properties = new Properties();
        System.out.println("AAAAAAAAAAA"+recipient);
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // Replace these with your Gmail credentials
        String adresse_mail = "Bensalahons428@gmail.com";
        String mot_passe = "scvt toqa edhb fntg";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(adresse_mail, mot_passe);
            }
        });

        try {
            // Create a MimeMessage object
            MimeMessage message = new MimeMessage(session);

            // Set content type to "text/html"
            message.setContent(body, "text/html; charset=utf-8");
            message.setFrom(new InternetAddress(adresse_mail));

            // Set recipient
            message.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(recipient));

            // Set email subject
            message.setSubject(subject);

            // Send the email
            Transport.send(message);

            System.out.println("E-mail envoyé avec succès à " + recipient);
        } catch (MessagingException e) {
            System.err.println("Erreur d'envoi de l'e-mail : " + e.getMessage());
        }
    }

    public void retour(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Services.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) idValidate.getScene().getWindow(); // Obtenir la scène actuelle
        stage.setScene(new Scene(root));
        stage.setTitle("Page ");
        stage.centerOnScreen();
        stage.show();
    }
}
