package DarkDev.controllers;

import DarkDev.Enums.State;
import DarkDev.models.Produit;
import DarkDev.services.GProduit;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class UpdateProduit implements Initializable {

    public TextField title;
    public TextField city;
    public TextField price;
    public CheckBox exchange;
    public TextField idID;
    public Text reqstate;
    public Text reqimage;
    public Text reqdescription;
    public Text reqprice;
    public Text reqcategory;
    public Text reqcity;
    public Text reqexchange;
    public Text reqtitle;
    GProduit gp = new GProduit();
    public Button idUpdate;
    public ChoiceBox<String> state;
    public ChoiceBox<String> category;
    public TextField image;
    public TextArea description;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       load();
    }

    private void load()  {
        try {
            List<String> categories= gp.ListeCategories();
            for (String cat: categories) {
                category.getItems().add (cat);

            }
            for (State s : State.values()){
                state.getItems().add(s.name());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void setFields(int id,String titreProduit,String descriptionProduit,String photo,String city,int exchange,String category,String etat, double price){
        this.title.setText(titreProduit);
        this.image.setText(photo);
        this.description.setText(descriptionProduit);
        this.city.setText(city);
        this.exchange.setSelected(exchange != 0);
        this.category.setValue(category);
        this.state.setValue(etat);
        this.price.setText(String.valueOf(price));
        this.idID.setText(""+id);

    }


    public void updateProduct(ActionEvent actionEvent) {
        String titreProduit = title.getText();
        String descriptionProduit = description.getText();
        String photo = image.getText();
        String city = this.city.getText();
        float prix = 0;
        if(!price.getText().isEmpty() && price.getText().matches("([0-9]*[.])?[0-9]+")){
             prix = Float.parseFloat(price.getText());
        }

        boolean exchange = this.exchange.isSelected();
        String category = this.category.getValue();
        String etat = state.getValue();
        int idProduit= Integer.parseInt(idID.getText());
        try{
            int id = gp.getCategoryByName(category);
            if(id!=0){
                if (titreProduit.isEmpty() || descriptionProduit.isEmpty() || photo.isEmpty() || city.isEmpty() || price.getText().isEmpty() || category.isEmpty() || etat.isEmpty()){


                    reqtitle.setVisible(titreProduit.isEmpty());

                    reqcity.setVisible(city.isEmpty());

                    if(price.getText().isEmpty()){
                        reqprice.setVisible(true);

                    } else if (!price.getText().matches("([0-9]*[.])?[0-9]+")) {
                        reqprice.setText("this field can accept only numeric value");
                        reqprice.setVisible(true);
                    }

                    reqstate.setVisible( etat.isEmpty());

                    reqdescription.setVisible(descriptionProduit.isEmpty());

                    reqimage.setVisible(photo.isEmpty());

                    reqcategory.setVisible(false);
                }
                else {
                    reqcategory.setVisible(false);

                    if(price.getText().isEmpty()){
                        reqprice.setVisible(true);

                    } else if (!price.getText().matches("([0-9]*[.])?[0-9]+")) {
                        reqprice.setText("this field can accept only numeric value");
                        reqprice.setVisible(true);
                    }else{
                        Produit produit = new Produit(idProduit, id, 2, exchange ? 1 : 0, prix, titreProduit,
                                descriptionProduit, photo,  city, etat);
                    gp.modifier(produit);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("OK");
                    alert.setHeaderText("Modification effectue");
                    alert.setContentText("Item has been successfully updated.");
                    alert.showAndWait();
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherProduitsFX.fxml"));
                        Parent root = loader.load();
                        title.getScene().setRoot(root);
                    } catch (IOException e) {
                        System.err.println(e.getMessage());
                    }

                }}
            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Category error");
                alert.setHeaderText("Non existant category");
                alert.setContentText("Click Cancel to exit.");
                alert.showAndWait();

                reqtitle.setVisible(titreProduit.isEmpty());

                reqcity.setVisible(city.isEmpty());

                if(price.getText().isEmpty()){
                    reqprice.setVisible(true);

                } else if (!price.getText().matches("([0-9]*[.])?[0-9]+")) {
                    reqprice.setText("this field can accept only numeric value");
                    reqprice.setVisible(true);
                }

                reqstate.setVisible( etat.isEmpty());

                reqdescription.setVisible(descriptionProduit.isEmpty());

                reqimage.setVisible(photo.isEmpty());

                
                reqcategory.setVisible(true);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void returnBack(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherProduitsFX.fxml"));
            Parent root = loader.load();
            title.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
