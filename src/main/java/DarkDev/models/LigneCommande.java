package DarkDev.models;

public class LigneCommande {
    private int  idPanier , idProduit;

    public LigneCommande(int idPanier, int idProduit) {
        this.idPanier = idPanier;
        this.idProduit = idProduit;
    }

    public LigneCommande() {

    }

    public int getIdPanier() {
        return idPanier;
    }

    public void setIdPanier(int idPanier) {
        this.idPanier = idPanier;
    }

    public int getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(int idProduit) {
        this.idProduit = idProduit;
    }

    @Override
    public String toString() {
        return "LigneCommande{" +
                "idPanier=" + idPanier +
                ", idProduit=" + idProduit +
                '}';
    }
}
