package controllers.Reclamation;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import models.Reclamation.Reclamation;
import models.Reclamation.Reponse;
import services.ServicesReclamation.BadWordsFilter;
import services.ServicesReclamation.ServiceReclamation;
import services.ServicesReclamation.ServiceReponse;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class Response {

    public Label idcomplaint;
    String[] badWordsArray = {"5ra", "le", "non","fuck","putin","yezi","edara","esprit","asshole"};

    BadWordsFilter filter=new BadWordsFilter(badWordsArray);
    //ServicePersonne pr = new ServicePersonne();
    ServiceReponse sr= new ServiceReponse();
    ServiceReclamation serviceReclamation = new ServiceReclamation();
    public Button btnSend;
    public Button btnCancel;
    public Label idRequiredResponse;
    public TextArea description_id;

    public void cancelRec(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficher_reclamation1.fxml"));
        Parent root = loader.load();
        description_id.getScene().setRoot(root);
    }

    public void setData(Reclamation reclamation){
        idcomplaint.setText(""+reclamation.getId());
    }

    public void ajouter_reclamation1(ActionEvent actionEvent) {
        String descriptionService = description_id.getText();
        Date currentDate = Date.valueOf(LocalDate.now());

        try{


            if ( descriptionService.isEmpty()){


                idRequiredResponse.setVisible(true);

            }
            else {
                if ( filter.hasBadWord(description_id.getText()))
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ALERT !!");
                    alert.setContentText("BAD WORD");
                    alert.showAndWait();

                }else {
                    Reclamation reclamation = serviceReclamation.getReclamationById(Integer.parseInt(idcomplaint.getText()));
                    reclamation.setStatus("Treated");
                    serviceReclamation.modifier_reclamation(reclamation);
                    Reponse response = new Reponse(reclamation.getId(), reclamation.getTitre_r(), descriptionService,currentDate);

                    sr.ajouter_Reponse(response);
                    //List<Personne> personne = pr.listerPersonne();
                    //SendMailrec.SendMailrec("ines.jendoubi@esprit.tn","Your response has been sent successfully","A new response to a   "+response.getTitre_r());
                    System.out.println("Complaint added successfully");
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficher_reclamation1.fxml"));
                    Parent root = loader.load();
                    description_id.getScene().setRoot(root);
                }

            }


        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

    }
}
