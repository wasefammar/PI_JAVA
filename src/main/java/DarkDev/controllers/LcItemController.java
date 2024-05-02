package DarkDev.controllers;

import DarkDev.models.LigneCommande;
import DarkDev.models.Produit;
import DarkDev.services.G_ligneCommande;
import DarkDev.test.MainFX;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class LcItemController {

    G_ligneCommande glc= new G_ligneCommande();
    public ImageView imagelc;
    public Text titrelc;
    public Label pricelc;
    public Label idProduitID;
    public ImageView idLcDelete;


    public void delete(MouseEvent mouseEvent) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText("Are you sure you want to delete this Product from your cart?");
        alert.setContentText("This action cannot be undone.");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    glc.supprimer(Integer.parseInt(idProduitID.getText()));
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Panier.fxml"));
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

    public void setData(Produit produit, String currency){
        titrelc.setText(produit.getTitreProduit());
        if(currency.equals("DT")){
            pricelc.setText( produit.getPrix()+" "+currency );
        } else if (currency.equals("Euro €")) {
            pricelc.setText( String.format("%.2f",produit.getPrix()*0.3)+" "+"€" );
        }else if (currency.equals("Dollar $")){
            pricelc.setText( String.format("%.2f",produit.getPrix()*0.32)+" "+"$" );
        }
        idProduitID.setText(""+produit.getId());
        File imageFile= new File(produit.getPhoto());
        imagelc.setImage(new Image(imageFile.toURI().toString()));

    }
}
