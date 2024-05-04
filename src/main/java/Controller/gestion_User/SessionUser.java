package Controller.gestion_User;

public class SessionUser {

    private static SessionUser instance;
    private String adresseEmail;

    private  String nom, prenom,adress, telphone;

    private  int id;


    public SessionUser(){
        nom = "";// or null
        prenom = "";
        telphone ="";
        adresseEmail="";
        adress ="";
        id= 0;

    }


    private SessionUser(String adresseEmail, String nom,String prenom, String adress, String telphone, int id) {
        this.id = id;
        this.adresseEmail= adresseEmail;
        this.nom =  nom;
        this.prenom= prenom;
        this.adress= adress;
        this.telphone=telphone;
    }



    public static SessionUser getInstance(String adresseEmail , String nom , String prenom, String adress , String telphone, int id ) {
        if (instance == null) {
            instance = new SessionUser(adresseEmail ,nom,prenom,adress,telphone,id);
        }
        return instance;
    }



    public static void resetSession() {
        if (instance != null) {
            instance.setAdresseEmail("");
            instance.setNom("");
            instance.setPrenom("");
            instance.setAdress("");
            instance.setTelphone("");
            instance.setId(0);

        }
    }














    public static  SessionUser getUser (){return  instance;}


    public void setAdresseEmail(String adresseEmail) {
        this.adresseEmail = adresseEmail;
    }

    public String getAdresseEmail() {
        return adresseEmail;
    }



    public String getNom() {return nom;}

    public void setNom(String nom) {this.nom = nom;}

    public String getPrenom() {return prenom;}

    public void setPrenom(String prenom) {this.prenom = prenom;}


    public String getAdress() {return adress;}

    public void setAdress(String adress) {this.adress = adress;}


    public String getTelphone() {return telphone;}

    public void setTelphone(String telphone) {this.telphone = telphone;}


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "SessionUser{" +
                "adresseEmail='" + adresseEmail + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", adress='" + adress + '\'' +
                ", telphone='" + telphone + '\'' +
                ", id=" + id +
                '}';
    }
}
