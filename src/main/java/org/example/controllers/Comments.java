package org.example.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import org.example.models.Commentaire;
import org.example.services.ServiceCommentaire;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class Comments implements Initializable {
    @FXML
    private Button idAddComment;

    @FXML
    private TableColumn<Commentaire, Integer> idCommentaire;

    @FXML
    private TableView<Commentaire> idCommentsTable;

    @FXML
    private Button idDelete;

    @FXML
    private TableColumn<Commentaire, String> idDesc;

    @FXML
    private Text idNoComments;

    @FXML
    private Text idService;

    @FXML
    private Text idTitle;

    @FXML
    private Button idUpdate;


    ObservableList<Commentaire> commentaireObservableList;
    ServiceCommentaire sc= new ServiceCommentaire();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            List<Commentaire> list = sc.recuperer(Integer.parseInt(this.idService.getText()));
            commentaireObservableList = FXCollections.observableArrayList(list);

            idCommentsTable.setItems(commentaireObservableList);
            idCommentaire.setCellValueFactory(new PropertyValueFactory<>("id"));
            idDesc.setCellValueFactory(new PropertyValueFactory<>("description"));

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void setFields(String titreService, int idService){
        this.idService.setText(""+idService);
        this.idTitle.setText(this.idTitle.getText()+titreService);
    }

    public void addComment(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddComment.fxml"));
            Parent root = loader.load();
            idCommentsTable.getScene().setRoot(root);
        } catch ( IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void updateComment(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddComment.fxml"));
            Parent root = loader.load();
            idCommentsTable.getScene().setRoot(root);
        } catch ( IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void deleteComment(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddComment.fxml"));
            Parent root = loader.load();
            idCommentsTable.getScene().setRoot(root);
        } catch ( IOException e) {
            System.err.println(e.getMessage());
        }
    }


}
