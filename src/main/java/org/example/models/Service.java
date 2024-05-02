package org.example.models;

public class Service {
    private int id, idCategorie, idUtilisateur, choixEchange, valid;
    private  String titreService, descriptionService, photo, ville;


    public Service(int id, int idCategorie, int idUtilisateur, String titreService, String descriptionService,
                   String photo, String ville, int choixEchange, int valid) {
        this.id = id;
        this.idCategorie = idCategorie;
        this.idUtilisateur = idUtilisateur;
        this.titreService = titreService;
        this.descriptionService = descriptionService;
        this.photo = photo;
        this.ville = ville;
        this.choixEchange = choixEchange;
        this.valid = valid;
    }

    public Service(int idCategorie, int idUtilisateur, String titreService, String descriptionService,
                   String photo, String ville, int choixEchange, int valid) {
        this.idCategorie = idCategorie;
        this.idUtilisateur = idUtilisateur;
        this.titreService = titreService;
        this.descriptionService = descriptionService;
        this.photo = photo;
        this.ville = ville;
        this.choixEchange = choixEchange;
        this.valid = valid;
    }

    public Service() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCategorie() {
        return idCategorie;
    }

    public void setIdCategorie(int idCategorie) {
        this.idCategorie = idCategorie;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public String getTitreService() {
        return titreService;
    }

    public void setTitreService(String titreService) {
        this.titreService = titreService;
    }

    public String getDescriptionService() {
        return descriptionService;
    }

    public void setDescriptionService(String descriptionService) {
        this.descriptionService = descriptionService;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public int isChoixEchange() {
        return choixEchange;
    }

    public void setChoixEchange(int choixEchange) {
        this.choixEchange = choixEchange;
    }

    public int isValid() {
        return valid;
    }

    public void setValid(int valid) {
        this.valid = valid;
    }

    @Override
    public String toString() {
        return "Service{" +
                "id=" + id +
                ", idCategorie=" + idCategorie +
                ", idUtilisateur=" + idUtilisateur +
                ", titreService='" + titreService + '\'' +
                ", descriptionService='" + descriptionService + '\'' +
                ", photo='" + photo + '\'' +
                ", ville='" + ville + '\'' +
                ", choixEchange=" + choixEchange +
                ", valid=" + valid +
                '}';
    }
}
