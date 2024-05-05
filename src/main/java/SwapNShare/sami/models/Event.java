package SwapNShare.sami.models;

import java.time.LocalDateTime;

public class Event {
    private int id, produit_id;
    private String titre_evenement, description_evenement, status;
    private LocalDateTime date_debut, date_fin;


    public Event() {
    }
    public Event(int produit_id, String titre_evenement, String description_evenement, String status, LocalDateTime date_debut, LocalDateTime date_fin) {
        this.produit_id = produit_id;
        this.titre_evenement = titre_evenement;
        this.description_evenement = description_evenement;
        this.status = status;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
    }
    public Event(int id, int produit_id, String titre_evenement, String description_evenement, String status, LocalDateTime date_debut, LocalDateTime date_fin) {
        this.id = id;
        this.produit_id = produit_id;
        this.titre_evenement = titre_evenement;
        this.description_evenement = description_evenement;
        this.status = status;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
    }

    public Event(int id, int produit_id, String titre_evenement, String description_evenement, LocalDateTime date_debut, LocalDateTime date_fin) {
        this.id = id;
        this.produit_id = produit_id;
        this.titre_evenement = titre_evenement;
        this.description_evenement = description_evenement;
        this.status = "Waitlisted";
        this.date_debut = date_debut;
        this.date_fin = date_fin;
    }

    public Event(int produit_id, String titre_evenement, String description_evenement, LocalDateTime date_debut, LocalDateTime date_fin) {
        this.produit_id = produit_id;
        this.titre_evenement = titre_evenement;
        this.description_evenement = description_evenement;
        this.status = "Waitlisted";
        this.date_debut = date_debut;
        this.date_fin = date_fin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProduit_id() {
        return produit_id;
    }

    public void setProduit_id(int produit_id) {
        this.produit_id = produit_id;
    }

    public String getTitre_evenement() {
        return titre_evenement;
    }

    public void setTitre_evenement(String titre_evenement) {
        this.titre_evenement = titre_evenement;
    }

    public String getDescription_evenement() {
        return description_evenement;
    }

    public void setDescription_evenement(String description_evenement) {
        this.description_evenement = description_evenement;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(LocalDateTime date_debut) {
        this.date_debut = date_debut;
    }

    public LocalDateTime getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(LocalDateTime date_fin) {
        this.date_fin = date_fin;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", produit_id=" + produit_id +
                ", titre_evenement='" + titre_evenement + '\'' +
                ", description_evenement='" + description_evenement + '\'' +
                ", status='" + status + '\'' +
                ", date_debut=" + date_debut +
                ", date_fin=" + date_fin +
                '}';
    }
}
