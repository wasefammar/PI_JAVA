package Esprit.models;

public class Personne {




    private int id;
    private String nom, prenom, email,telephone, adresse,password;
    private String role;









    public Personne( int id, String email, String password , String nom, String prenom,String adresse ,String telephone,String role) {
        this.id = id;
        this.email = email;
        this.password= password;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse= adresse;
        this.telephone= telephone;
        this.role=role;

    }



    public Personne (int id,String email, String nom , String prenom, String adresse, String telephone){
        this.id=id;
        this.email=email;
        this.nom=nom;
        this.prenom=prenom;
        this.adresse=adresse;
        this.telephone=telephone;

    }



   public Personne(  String email ,  String password , String nom, String prenom  ,String adresse, String telephone) {

        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
        this.adresse = adresse;
        this.password = password;

    }




    public Personne(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Personne{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
                ", adresse='" + adresse + '\'' +
                ", password='" + password + '\'' +

                '}';
    }


    public String getNomComplet() {
        return nom + " " + prenom;
    }








}
