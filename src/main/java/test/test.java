package test;

import models.Reclamation;
import models.Reponse;
import services.ServiceReclamation;
import services.ServiceReponse;
import utils.MyDataBase;

public class test {

    public static void main(String[] args){
       // MyDataBase db = new MyDataBase();
        MyDataBase.getInstance();
        ServiceReclamation sr = new ServiceReclamation();
        java.sql.Date d = new java.sql.Date(System.currentTimeMillis());
        //Reclamation reclamation = new Reclamation(1, "test","test","test","test",d);
        //sr.ajouter_Reclamation(reclamation);
        //sr.modifier_reclamation(1,"tesst","sara",d,"ffff","fffff");


        /////////////////////////////////////////TEST LISTER RECLAMATION/////////////////////////////////


        Reclamation rec = sr.listerReclamation().get(0);
        System.out.print(sr.listerReclamation());

        ///////////////////////////////////////////////////////////////////TEST SUPPRIMER////////////////////////

        //sr.supprimer_Reclamation(1);
        //////////////////////////////////////////////////////TEST CRUD REPONSE//////////////////////////////

        //ServiceReponse rp = new ServiceReponse();
        //Reponse reponse = new Reponse(1,"test","test",d);

        ///////////////////////////////////////////////////////TEST AJOUTER///////////////////

        //rp.ajouter_Reponse(reponse);

        ////////////////////////////////////////////////////TEST LISTER REPONSE///////////////////////////////////
        //Reponse rep = rp.listerReponse().get(0);
        //System.out.print(rep.getDescription_id());
    }
}
