package models;


import java.sql.Date;

public class Reponse {
        private int id;
        private int reponse_id;
        private String titre_r;
        private String description_id;
        private Date date;

        public Reponse(int id, int reponse_id, String titre_r, String description_id, Date date) {
            this.id = id;
            this.reponse_id = reponse_id;
            this.titre_r = titre_r;
            this.description_id = description_id;
            this.date = date;
        }

    public Reponse(int id,  String titre_r, String description_id, Date date) {
        this.id = id;

        this.titre_r = titre_r;
        this.description_id = description_id;
        this.date = date;
    }

    public Reponse() {

    }

    public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getReponse_id() {
            return reponse_id;
        }

        public void setReponse_id(int reponse_id) {
            this.reponse_id = reponse_id;
        }

        public String getTitre_r() {
            return titre_r;
        }

        public void setTitre_r(String titre_r) {
            this.titre_r = titre_r;
        }

        public String getDescription_id() {
            return description_id;
        }

        public void setDescription_id(String description_id) {
            this.description_id = description_id;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }
    }

