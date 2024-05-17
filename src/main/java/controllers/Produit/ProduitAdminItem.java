package controllers.Produit;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import models.Produit.Produit;
import services.ServicesProduit.GProduit;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class ProduitAdminItem {
    public Label idProduitID;
    public ImageView idLcDelete;
    public Label pricelc;
    public Text titrelc;
    public ImageView imagelc;

    GProduit gp= new GProduit();

    public void delete(MouseEvent mouseEvent) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText("Are you sure you want to delete this Product?");
        alert.setContentText("This action cannot be undone.");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    gp.supprimer(Integer.parseInt(idProduitID.getText()));
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ProduitsAdmin.fxml"));
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

    public void setData(Produit produit) throws SQLException {
        titrelc.setText(produit.getTitreProduit());
        pricelc.setText( produit.getPrix()+" DT" );

        idProduitID.setText(""+produit.getId());

        File imageFile= new File("F:\\ESPRIT\\pidev-main (2)\\pidev-main\\public\\uploads\\produits\\"+produit.getPhoto());;
        imagelc.setImage(new Image(imageFile.toURI().toString()));

    }
}
