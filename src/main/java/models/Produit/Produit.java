package models.Produit;

public class Produit {
    private int id;
    private int idCategorie;
    private int idUtilisateur,choixEchange;
    
    private double prix;
    private  String titreProduit, descriptionProduit, photo, ville,etat;

    public Produit(int id, int idCategorie, int idUtilisateur, int choixEchange,double prix, String titreProduit,
                   String descriptionProduit, String photo, String ville,String etat) {
        this.id = id;
        this.idCategorie = idCategorie;
        this.idUtilisateur = idUtilisateur;
        this.choixEchange = choixEchange;
        this.prix = prix;
        this.titreProduit = titreProduit;
        this.descriptionProduit = descriptionProduit;
        this.photo = photo;
        this.ville = ville;
        this.etat=etat;


    }

    public Produit() {

    }

    public Produit(int idcategorie, int idUtilisateur, int exchange, double price, String title, String description, String image, String city, String state) {
        this.idCategorie = idcategorie;
        this.idUtilisateur = idUtilisateur;
        this.choixEchange = exchange;
        this.prix = price;
        this.titreProduit =title;
        this.descriptionProduit =  description;
        this.photo =  image;
        this.ville = city;
        this.etat=state;
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

    public int getChoixEchange() {
        return choixEchange;
    }

    public void setChoixEchange(int choixEchange) {
        this.choixEchange = choixEchange;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getTitreProduit() {
        return titreProduit;
    }

    public void setTitreProduit(String titreProduit) {
        this.titreProduit = titreProduit;
    }

    public String getDescriptionProduit() {
        return descriptionProduit;
    }

    public void setDescriptionProduit(String descriptionProduit) {
        this.descriptionProduit = descriptionProduit;
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

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }


    @Override
    public String toString() {
        return "Produit{" +
                ", idCategorie=" + idCategorie +
                ", choixEchange=" + choixEchange +
                ", prix=" + prix +
                ", titreProduit='" + titreProduit + '\'' +
                ", descriptionProduit='" + descriptionProduit + '\'' +
                ", photo='" + photo + '\'' +
                ", ville='" + ville + '\'' +
                ", etat='" + etat + '\'' +
                '}';
    }

    public void setChoixEchange(boolean selected) {
    }
}
