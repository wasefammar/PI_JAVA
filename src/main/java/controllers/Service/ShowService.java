package controllers.Service;

import controllers.Echange.EchangeServiceController;
import controllers.User.SessionUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import javafx.stage.Stage;
import models.Services.Commentaire;
import models.Services.Service;
import services.GestionServices.GestionService;
import services.GestionServices.ServiceCommentaire;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ShowService implements Initializable {

    public HBox idBACK;
    public ImageView idImage;
    public HBox idRertour;
    public Label idUsername;
    GestionService gs= new GestionService();
    ServiceCommentaire sc=new ServiceCommentaire();
    public ImageView idBack;
    public VBox idListComment;
    public Label idRequiredDescription;

    public Label idServiceId;
    public Label idServiceUsername;
    public Label idCity;
    public Label idTitle;
    public Label idCategory;
    public Text idDescription;
    public Button idUpdate;
    public Button idDelete;
    public Button idExchange;
    public TextArea idCommentDescription;
    public Button idSubmitComment;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    public void setFields(Service service) throws SQLException {
        idUsername.setText(SessionUser.getUser().getNom()+" "+SessionUser.getUser().getPrenom());
        idServiceId.setText(""+service.getId());
        idTitle.setText(service.getTitreService());
        idDescription.setText(service.getDescriptionService());
        idCategory.setText(gs.getCategoryById(service.getIdCategorie()));
        idServiceUsername.setText(gs.getUserById(service.getIdUtilisateur()));
        idCity.setText(service.getVille());
        int utilsateurId= gs.getIdUtilisateurByEmail(SessionUser.getUser().getAdresseEmail()).getId();
        if(service.getIdUtilisateur() != utilsateurId){
            idDelete.setVisible(false);
            idUpdate.setVisible(false);
            idExchange.setVisible(service.isChoixEchange()!=0);
        }else {
            idDelete.setVisible(true);
            idUpdate.setVisible(true);
            idExchange.setVisible(false);
        }


        File imageFile= new File(service.getPhoto());
        idImage.setImage(new Image(imageFile.toURI().toString()));
        try {
            List<Commentaire> list = sc.recuperer(service.getId());
            for (int i=0; i<list.size(); i++){
                FXMLLoader fxmlLoader= new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/SingleComment.fxml"));
                VBox box= fxmlLoader.load();
                SingleComment singleComment = fxmlLoader.getController();
                singleComment.setData(list.get(i));
                //idListComments.getItems().add(box);
                idListComment.getChildren().add(box);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void back(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Services.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) idServiceId.getScene().getWindow(); // Obtenir la scène actuelle
            stage.setScene(new Scene(root));
            stage.setTitle("Page ");
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void updateService(ActionEvent event) {
        int idService = Integer.parseInt(idServiceId.getText());
        try{

                Service service = gs.getServiceById(idService);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateService.fxml"));
                Parent root = loader.load();
                UpdateService upadateService = loader.getController();
                upadateService.setFields(service);
            Stage stage = (Stage) idCommentDescription.getScene().getWindow(); // Obtenir la scène actuelle
            stage.setScene(new Scene(root));
            stage.setTitle("Page ");
            stage.centerOnScreen();
            stage.show();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }


    public void deleteService(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText("Are you sure you want to delete this Service?");
        alert.setContentText("This action cannot be undone.");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    gs.supprimer(Integer.parseInt(idServiceId.getText()));
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Services.fxml"));
                    Parent root = loader.load();
                    Stage stage = (Stage) idServiceId.getScene().getWindow(); // Obtenir la scène actuelle
                    stage.setScene(new Scene(root));
                    stage.setTitle("Page ");
                    stage.centerOnScreen();
                    stage.show();

                } catch (SQLException e) {
                    System.err.println(e.getMessage());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void exchangeService(ActionEvent event) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/EchangeService.fxml"));
        Parent root = loader.load();
        EchangeServiceController echangeServiceController = loader.getController();
        echangeServiceController.setData(Integer.parseInt(idServiceId.getText()));
        Stage stage = (Stage) idDelete.getScene().getWindow(); // Obtenir la scène actuelle
        stage.setScene(new Scene(root));
        stage.setTitle("Page ");
        stage.centerOnScreen();
        stage.show();
    }

    public void addComment(ActionEvent event) {
        String descriptionComment = idCommentDescription.getText();
        int idService = Integer.parseInt(idServiceId.getText());
        try{
                if (descriptionComment.isEmpty()){
                    idRequiredDescription.setVisible(true);
                    idCommentDescription.setStyle("-fx-border-color: red;");
                }
                else {
                    Service service = gs.getServiceById(idService);
                    int utilsateurId= gs.getIdUtilisateurByEmail(SessionUser.getUser().getAdresseEmail()).getId();
                    Commentaire commentaire = new Commentaire(utilsateurId,idService, descriptionComment);
                    sc.ajouter(commentaire);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShowService.fxml"));
                    Parent root = loader.load();
                    ShowService showService = loader.getController();
                    showService.setFields(service);
                    Stage stage = (Stage) idServiceId.getScene().getWindow(); // Obtenir la scène actuelle
                    stage.setScene(new Scene(root));
                    stage.setTitle("Page ");
                    stage.centerOnScreen();
                    stage.show();
                }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void back1(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Services.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) idBACK.getScene().getWindow(); // Obtenir la scène actuelle
            stage.setScene(new Scene(root));
            stage.setTitle("Page ");
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void retour(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Services.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) idBACK.getScene().getWindow(); // Obtenir la scène actuelle
            stage.setScene(new Scene(root));
            stage.setTitle("Page ");
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void moveToProducts(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherProduits.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) idBack.getScene().getWindow(); // Obtenir la scène actuelle
        stage.setScene(new Scene(root));
        stage.setTitle("Page ");
        stage.centerOnScreen();
        stage.show();
    }

    public void moveToEvents(MouseEvent mouseEvent) {

    }

    public void moveToComplaints(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherReclamation.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) idBack.getScene().getWindow(); // Obtenir la scène actuelle
        stage.setScene(new Scene(root));
        stage.setTitle("Page ");
        stage.centerOnScreen();
        stage.show();
    }

    public void moveToLogout(MouseEvent mouseEvent) throws IOException {
        SessionUser user = SessionUser.getUser();
        System.out.println(user.toString());
        SessionUser.resetSession();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/Login.fxml"));
        Parent box = fxmlLoader.load();
        Stage stage = (Stage) idBack.getScene().getWindow(); // Obtenir la scène actuelle
        stage.setScene(new Scene(box));
        stage.setTitle("Page ");
        stage.centerOnScreen();
        stage.show();
    }
}
