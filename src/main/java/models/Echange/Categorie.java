package models.Echange;
public class Categorie {
    private int id;
    private String nomCategorie;
    private String type;

    public Categorie() {
        // Default constructor
    }

    public Categorie( String nomCategorie, String type) {
        this.nomCategorie = nomCategorie;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomCategorie() {
        return nomCategorie;
    }

    public void setNomCategorie(String nomCategorie) {
        if (nomCategorie != null && !nomCategorie.isEmpty() && nomCategorie.length() <= 20 && !nomCategorie.matches(".*\\d.*")) {
            this.nomCategorie = nomCategorie;
        } else {
            throw new IllegalArgumentException("Invalid category name: " + nomCategorie);
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
            this.type = type;
    }

    @Override
    public String toString() {
        return this.nomCategorie;
    }
}
