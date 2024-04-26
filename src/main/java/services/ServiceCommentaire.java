package services;

import models.Commentaire;
import models.Service;
import utils.DatabaseConnexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ServiceCommentaire implements IService<Commentaire> {

    Connection cnx;

    public  ServiceCommentaire(){
        cnx = DatabaseConnexion.getInstance().getConnection();
    }
    @Override
    public void ajouter(Commentaire comment) throws SQLException {
        String sql = "INSERT INTO commentaire ( utilisateur_id, service_id, description)"+
                " values ('"+comment.getIdUtilisateur()+"'," +"'"+comment.getIdService()+"',"+"'"+comment.getDescription()+"'"+")";
        Statement st= cnx.createStatement();
        st.executeUpdate(sql);
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String sql= "DELETE FROM commentaire where id = ?";
        PreparedStatement ps= cnx.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    @Override
    public void modifier(Commentaire commentaire) throws SQLException {
        String sql= "UPDATE commentaire set description = ? where id = ?";
        PreparedStatement ps= cnx.prepareStatement(sql);
        ps.setString(1, commentaire.getDescription());
        ps.setInt(2, commentaire.getId());
        ps.executeUpdate();
    }

    public Commentaire getCommentaireById(int id) throws SQLException {
        String sql= "SELECT * FROM commentaire where id = "+id;
        Statement st= cnx.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while(rs.next()){
            Commentaire comment = new Commentaire();
            comment.setId(rs.getInt("id"));
            comment.setIdUtilisateur(rs.getInt("utilisateur_id"));
            comment.setIdService(rs.getInt("service_id"));
            comment.setDescription(rs.getString("description"));

            return  comment;
        }
        return  null;
    }

    public List<Commentaire> recuperer(int idService) throws SQLException {
        List<Commentaire> commentaires = new ArrayList<>();
        String sql= "SELECT * FROM commentaire where service_id = "+idService;
        Statement st= cnx.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while(rs.next()){
            Commentaire comment = new Commentaire();
            comment.setId(rs.getInt("id"));
            comment.setIdUtilisateur(rs.getInt("utilisateur_id"));
            comment.setIdService(rs.getInt("service_id"));
            comment.setDescription(rs.getString("description"));

            commentaires.add(comment);
        }
        return commentaires.stream().sorted(Comparator.comparing(Commentaire::getId).reversed()).toList();
    }
}
