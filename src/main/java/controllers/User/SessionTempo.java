package controllers.User;


public class SessionTempo {




    private static SessionTempo instance;
    private  String adresseEmail;

    private   String nom, prenom,adress, telphone,password;




    private SessionTempo(String adresseEmail, String nom,String prenom, String adress, String telphone, String password ) {
        this.password=password;
        this.adresseEmail= adresseEmail;
        this.nom =  nom;
        this.prenom= prenom;
        this.adress= adress;
        this.telphone=telphone;
    }

    public static SessionTempo getInstance(String adresseEmail, String nom,String prenom, String adress, String telphone,String password) {
        if (instance == null) {
            instance = new SessionTempo(adresseEmail ,nom,prenom,adress,telphone,password);
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
            instance.setPassword("");
        }
    }







  public static  SessionTempo getUser (){return  instance;}






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


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String toString() {
        return "SessionTempo{" +
                "adresseEmail='" + adresseEmail + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", adress='" + adress + '\'' +
                ", telphone='" + telphone + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
