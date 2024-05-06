package controllers.Service;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import models.Services.Service;
import services.GestionServices.GestionService;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class SingleService {

    public VBox idService;
    public Label idServiceID;
    GestionService gs= new GestionService();
    public ImageView idPhoto;
    public Label idTitle;
    public Label idUserName;
    public Label idCategory;
    public Label idCity;

    public void setData(Service service) throws SQLException {
      idServiceID.setText(""+service.getId());
      idTitle.setText(service.getTitreService());
      idUserName.setText(gs.getUserById(service.getIdUtilisateur()));
      idCategory.setText(gs.getCategoryById(service.getIdCategorie()));
      idCity.setText(service.getVille());

        File imageFile= new File(service.getPhoto());
        idPhoto.setImage(new Image(imageFile.toURI().toString()));

    }

    public void showService(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShowService.fxml"));
            Parent root = loader.load();
            ShowService showService = loader.getController();
            showService.setFields(gs.getServiceById(Integer.parseInt(idServiceID.getText())));
            idServiceID.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
