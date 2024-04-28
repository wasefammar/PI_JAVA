package models;


import java.sql.Date;

public class Reclamation {
    private int id;
    private int utilisateur_id;

    private String titre_r;
    private String description_r;
    private String status;
    private String urgence;
    private Date date;


    public Reclamation(int id, int utilisateur_id, String titre_r, String description_r, String status, String urgence, Date date) {
        this.id = id;
        this.utilisateur_id = utilisateur_id;
        this.titre_r = titre_r;
        this.description_r = description_r;
        this.status = status;
        this.urgence = urgence;
        this.date = date;
    }
    public Reclamation(int utilisateur_id, String titre_r, String description_r, String status, String urgence, Date date) {
       this.utilisateur_id= utilisateur_id;
        this.titre_r = titre_r;
        this.description_r = description_r;
        this.status = status;
        this.urgence = urgence;
        this.date = date;
    }



    public Reclamation() {
    }

    public int getId() {
        return id;
    }

    public int getUtilisateur_id() {
        return utilisateur_id;
    }

    public String getTitre_r() {
        return titre_r;
    }

    public String getDescription_r() {
        return description_r;
    }

    public String getStatus() {
        return status;
    }

    public String getUrgence() {
        return urgence;
    }

    public Date getDate() {
        return date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUtilisateur_id(int utilisateur_id) {
        this.utilisateur_id = utilisateur_id;
    }

    public void setTitre_r(String titre_r) {
        this.titre_r = titre_r;
    }

    public void setDescription_r(String description_r) {
        this.description_r = description_r;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setUrgence(String urgence) {
        this.urgence = urgence;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
