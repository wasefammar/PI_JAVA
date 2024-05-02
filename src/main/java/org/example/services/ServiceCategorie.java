package org.example.services;
import org.example.models.Categorie;
import org.example.utils.DatabaseConnexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class ServiceCategorie implements IService<Categorie>{
    Connection cnx;
    public ServiceCategorie() {
        cnx = DatabaseConnexion.getInstance().getConnection();
    }

    @Override
    public void ajouter(Categorie categorie) throws SQLException {
        String sql = "INSERT INTO Categorie (nomCategorie, type) VALUES (?, ?)";
        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setString(1, categorie.getNomCategorie());
        ps.setString(2, categorie.getType());
        ps.executeUpdate();
    }

    @Override
    public void modifier(Categorie Categorie) throws SQLException {
        String sql = "UPDATE Categorie set nomCategorie = ? , type = ? where id = ?";
        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setString(1,Categorie.getNomCategorie());
        ps.setString(2,Categorie.getType());
        ps.executeUpdate();
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String sql = "DELETE FROM Categorie where id = ?";
        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setInt(1,id);
        ps.executeUpdate();
    }

    public List<Categorie> recuperer() throws SQLException {
        List<Categorie> Categories = new ArrayList<>();
        String sql = "SELECT * FROM Categorie";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()){
            Categorie Categorie = new Categorie();
            Categorie.setNomCategorie(rs.getString("nomCategorie"));
            Categorie.setType(rs.getString("type"));
            Categories.add(Categorie);
        }
        return Categories;
    }

    public ArrayList<Categorie> sortBy(String nom_column, String Asc_Dsc) {
        List<Categorie> ListeCategTriee = new ArrayList<>();
        try {
            // Utilisez des requêtes préparées pour éviter les attaques SQL par injection
            String req = "SELECT * FROM categorie ORDER BY ? " + Asc_Dsc;
            PreparedStatement preparedStatement = cnx.prepareStatement(req);
            preparedStatement.setString(1, nom_column);
            ResultSet res = preparedStatement.executeQuery();
            while (res.next()) {
                Categorie c = new Categorie();
                c.setId(res.getInt(1));
                c.setNomCategorie(res.getString(2));
                c.setType(res.getString(3));
                ListeCategTriee.add(c);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return new ArrayList<>(ListeCategTriee);
    }

    public ArrayList<Categorie> chercher(String nom_column, String valeur) {
        List<Categorie> ListeCategCherchee=new ArrayList<>();
        try {
            String req="SELECT * FROM categorie WHERE "+nom_column+" = '"+valeur+"'" ;
            Statement ste = cnx.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet res=ste.executeQuery(req);
            while(res.next()){
                Categorie c=new Categorie();
                c.setId(res.getInt(1));
                c.setNomCategorie(res.getString(2));
                c.setType(res.getString(3));
                ListeCategCherchee.add(c);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return (ArrayList<Categorie>) ListeCategCherchee ;
    }

    public Categorie readById(int id) {
        Categorie c = new Categorie();
        try {
            String req="SELECT * FROM categorie WHERE `id`='"+id+"'";
            Statement st = cnx.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(req);
            rs.beforeFirst();
            rs.next();
            c.setId(rs.getInt(1));
            c.setNomCategorie(rs.getString(2));
            c.setType(rs.getString(3));
        } catch (SQLException ex) {
        }
        return  c;
    }
}
