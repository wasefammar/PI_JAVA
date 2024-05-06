package controllers.Service;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import models.Services.Service;
import services.GestionServices.GestionService;
import utils.EmailSender;
import utils.SendEmail;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class ServiceAdminItem {
    public Label idProduitID;
    public Text titrelc;
    public ImageView imagelc;

    public ImageView idDelete;
    public ImageView idValidate;
    GestionService gestionService = new GestionService();

    public void setData(Service service){
        idProduitID.setText(""+service.getId());
        titrelc.setText(service.getTitreService());
        File imageFile= new File(service.getPhoto());
        imagelc.setImage(new Image(imageFile.toURI().toString()));
        if(service.isValid()==1){
            idValidate.setVisible(false);
        }else {
            idValidate.setVisible(true);
        }
    }

    public void delete(MouseEvent mouseEvent) throws SQLException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText("Are you sure you want to delete this Service?");
        alert.setContentText("This action cannot be undone.");
        Service service = gestionService.getServiceById(Integer.parseInt(idProduitID.getText()));

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    gestionService.supprimer(Integer.parseInt(idProduitID.getText()));
                    SendEmail.sendEmail(gestionService.getUserEmail(service),"Your service has been deleted","Apparently you didn't respect our rules or you published misleading content ");
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ServicesAdmin.fxml"));
                    Parent root = loader.load();
                    titrelc.getScene().setRoot(root);

                } catch (SQLException e) {
                    System.err.println(e.getMessage());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void validate(MouseEvent mouseEvent) throws SQLException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText("Are you sure you want to validate this Service?");
        alert.setContentText("This action cannot be undone.");
        Service service = gestionService.getServiceById(Integer.parseInt(idProduitID.getText()));

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    gestionService.validerService(Integer.parseInt(idProduitID.getText()));
                    SendEmail.sendEmail(gestionService.getUserEmail(service),"Your service is approved","thanks for your trust ");
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ServicesAdmin.fxml"));
                    Parent root = loader.load();
                    titrelc.getScene().setRoot(root);

                } catch (SQLException e) {
                    System.err.println(e.getMessage());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
