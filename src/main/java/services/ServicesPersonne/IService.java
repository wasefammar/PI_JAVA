package services.ServicesPersonne;

import controllers.User.SessionTempo;

import java.sql.SQLException;
import java.util.List;

public interface IService<T,F>{

    void ajoutUser(F t) throws SQLException;


    void ajoutUser(SessionTempo personne) throws SQLException;

    void modifier(T t) throws SQLException;
    void supprimer(int id) throws SQLException;
    List<T> recuperer() throws SQLException;



}
