package services.ServicesProduit;

import java.sql.SQLException;

public interface iLigneCommande < T >{

    void ajouter(T t) throws SQLException;
    void supprimer(int idLc, int idPanier) throws SQLException;



}
