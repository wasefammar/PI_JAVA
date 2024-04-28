package DarkDev.interfaces;

import java.sql.SQLException;
import java.util.List;

public interface iProduit <T>{

    void ajouter(T t) throws SQLException;
    void supprimer(int id) throws SQLException;

    void modifier(T t) throws SQLException;



}
