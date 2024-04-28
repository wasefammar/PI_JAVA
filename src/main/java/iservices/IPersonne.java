package iservices;

import models.Personne;

import java.sql.SQLException;
import java.util.List;

public interface IPersonne <Personne>{
    void ajoutUser(Personne personne) throws SQLException;

    List<Personne> recuperer() throws SQLException;
}
