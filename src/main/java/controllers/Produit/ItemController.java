package controllers.Produit;

import controllers.User.SessionUser;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import models.Produit.Produit;
import services.GestionServices.GestionService;
import utils.MyListener;

import java.io.File;
import java.sql.SQLException;

public class ItemController {

    @FXML
    private Label namepr;

    @FXML
    private Label pricepr;

    @FXML
    private ImageView imagepr;

    private MyListener myListener;

    private Produit produit;

    GestionService gs = new GestionService();


    @FXML
    void click(MouseEvent event) throws SQLException {
        myListener.onClickListener(produit);
    }

    public void setData(Produit produit, MyListener myListener, String currency) throws SQLException {
        this.produit = produit;
        this.myListener = myListener;
        namepr.setText(produit.getTitreProduit());
        if(currency.equals("DT")){
            pricepr.setText( produit.getPrix()+" "+currency );
        } else if (currency.equals("Euro €")) {
            pricepr.setText( String.format("%.2f",produit.getPrix()*0.3)+" "+"€" );
        }else if (currency.equals("Dollar $")){
            pricepr.setText( String.format("%.2f",produit.getPrix()*0.32)+" "+"$" );
        }



        File imageFile= new File("F:\\ESPRIT\\pidev-main (2)\\pidev-main\\public\\uploads\\produits\\"+produit.getPhoto());
        imagepr.setImage(new Image(imageFile.toURI().toString()));

    }


}
