package org.example.models;

public class Commentaire {
    private int id, idUtilisateur, idService;
    private  String description;

    public Commentaire() {
    }

    public Commentaire(int idUtilisateur, int idService, String description) {
        this.idUtilisateur = idUtilisateur;
        this.idService = idService;
        this.description = description;
    }

    public Commentaire(int id, int idUtilisateur, int idService, String description) {
        this.id = id;
        this.idUtilisateur = idUtilisateur;
        this.idService = idService;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public int getIdService() {
        return idService;
    }

    public void setIdService(int idService) {
        this.idService = idService;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Commentaire{" +
                "id=" + id +
                ", idUtilisateur=" + idUtilisateur +
                ", idService=" + idService +
                ", description='" + description + '\'' +
                '}';
    }
}
