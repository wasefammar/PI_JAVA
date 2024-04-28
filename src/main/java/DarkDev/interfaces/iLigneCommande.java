package DarkDev.interfaces;

import java.sql.SQLException;

public interface iLigneCommande < T >{

    void ajouter(T t) throws SQLException;
    void supprimer(int id) throws SQLException;



}
