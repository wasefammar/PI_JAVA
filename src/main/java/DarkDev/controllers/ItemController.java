package DarkDev.controllers;

import DarkDev.models.Produit;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import DarkDev.test.MainFX;
import DarkDev.test.MyListener;
import javafx.scene.input.MouseEvent;

import java.io.File;

public class ItemController {

    @FXML
    private Label namepr;

    @FXML
    private Label pricepr;

    @FXML
    private ImageView imagepr;

    private MyListener myListener;

    private Produit produit;

    @FXML
    void click(MouseEvent event) {
        myListener.onClickListener(produit);
    }

    public void setData(Produit produit, MyListener myListener) {
        this.produit = produit;
        this.myListener = myListener;
        namepr.setText(produit.getTitreProduit());
        pricepr.setText( produit.getPrix()+" "+MainFX.CURRENCY );
        File imageFile= new File(produit.getPhoto());
        imagepr.setImage(new Image(imageFile.toURI().toString()));

    }


}
