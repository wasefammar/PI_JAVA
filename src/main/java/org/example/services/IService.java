package org.example.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface IService <T>{
    void ajouter(T t) throws SQLException;
    void supprimer(int id) throws SQLException;
    void modifier(T t) throws SQLException;

}
