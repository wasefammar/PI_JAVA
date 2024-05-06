package models.Evenement;

public class EventParticipation {
    private int id, evenement_id, utilisateur_id;
    private double offre;

    public EventParticipation() {
    }

    public EventParticipation(int evenement_id, int utilisateur_id, double offre) {
        this.evenement_id = evenement_id;
        this.utilisateur_id = utilisateur_id;
        this.offre = offre;
    }

    public EventParticipation(int id, int evenement_id, int utilisateur_id, double offre) {
        this.id = id;
        this.evenement_id = evenement_id;
        this.utilisateur_id = utilisateur_id;
        this.offre = offre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEvenement_id() {
        return evenement_id;
    }

    public void setEvenement_id(int evenement_id) {
        this.evenement_id = evenement_id;
    }

    public int getUtilisateur_id() {
        return utilisateur_id;
    }

    public void setUtilisateur_id(int utilisateur_id) {
        this.utilisateur_id = utilisateur_id;
    }

    public double getOffre() {
        return offre;
    }

    public void setOffre(double offre) {
        this.offre = offre;
    }

    @Override
    public String toString() {
        return "EventParticipation{" +
                "id=" + id +
                ", evenement_id=" + evenement_id +
                ", utilisateur_id=" + utilisateur_id +
                ", offre=" + offre +
                '}';
    }
}
