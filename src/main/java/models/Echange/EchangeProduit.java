package models.Echange;
import models.Produit.Produit;

import java.time.LocalDateTime;
public class EchangeProduit {
    private static int nextId = 1;
    private int id;
    private Produit produitIn;
    private Produit produitOut;
    private LocalDateTime dateEchange;
    private Boolean valide;
    public EchangeProduit(Produit produitIn, Produit produitOut, LocalDateTime dateEchange, Boolean valide) {
        this.id = nextId++;
        this.produitIn = produitIn;
        this.produitOut = produitOut;
        this.dateEchange = dateEchange;
        this.valide = valide;
    }

    public int getId() {
        return id;
    }

    public Produit getProduitIn() {
        return produitIn;
    }

    public void setProduitIn(Produit produitIn) {
        this.produitIn = produitIn;
    }

    public Produit getProduitOut() {
        return produitOut;
    }

    public void setProduitOut(Produit produitOut) {
        this.produitOut = produitOut;
    }

    public LocalDateTime getDateEchange() {
        return dateEchange;
    }

    public void setDateEchange(LocalDateTime dateEchange) {
        this.dateEchange = dateEchange;
    }

    public Boolean isValide() {
        return valide;
    }

    public void setValide(Boolean valide) {
        this.valide = valide;
    }

    @Override
    public String toString() {
        return "EchangeProduit{" +
                "id=" + id +
                ", produitIn=" + produitIn +
                ", produitOut=" + produitOut +
                ", dateEchange=" + dateEchange +
                ", valide=" + valide +
                '}';
    }
}

