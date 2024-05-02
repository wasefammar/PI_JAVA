package org.example.services;

import org.example.models.EchangeProduit;
import org.example.models.Produit;
import org.example.services.GestionProduit;
import org.example.utils.DatabaseConnexion;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ServiceEchangeProduit {
    private Connection cnx;
    private GestionProduit gestionProduit;

    public ServiceEchangeProduit() {
        cnx = DatabaseConnexion.getInstance().getConnection();
        gestionProduit = new GestionProduit();
    }

    public EchangeProduit createExchange(Produit produitIn, Produit produitOut, LocalDateTime dateEchange, Boolean valide) throws SQLException {
        String sql = "INSERT INTO echange_produit (produit_in_id, produit_out_id, date_echange, valide) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, produitIn.getId());
            ps.setInt(2, produitOut.getId());
            ps.setTimestamp(3, Timestamp.valueOf(dateEchange));
            ps.setBoolean(4, valide);
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    return new EchangeProduit( produitIn, produitOut, dateEchange, valide);
                }
            }
        }
        throw new SQLException("Creating exchange failed, no ID obtained.");
    }

    public List<EchangeProduit> getAllExchanges() throws SQLException {
        List<EchangeProduit> exchanges = new ArrayList<>();
        String sql = "SELECT * FROM echange_produit";
        try (Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                int produitInId = rs.getInt("produit_in_id");
                int produitOutId = rs.getInt("produit_out_id");
                LocalDateTime dateEchange = rs.getTimestamp("date_echange").toLocalDateTime();
                Boolean valide = rs.getBoolean("valide");
                Produit produitIn = gestionProduit.getProduitById(produitInId);
                Produit produitOut = gestionProduit.getProduitById(produitOutId);
                exchanges.add(new EchangeProduit(produitIn, produitOut, dateEchange, valide));
            }
        }
        return exchanges;
    }

    public EchangeProduit getExchangeById(int id) throws SQLException {
        String sql = "SELECT * FROM echange_produit WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int produitInId = rs.getInt("produit_in_id");
                    int produitOutId = rs.getInt("produit_out_id");
                    LocalDateTime dateEchange = rs.getTimestamp("date_echange").toLocalDateTime();
                    Boolean valide = rs.getBoolean("valide");
                    Produit produitIn = gestionProduit.getProduitById(produitInId);
                    Produit produitOut = gestionProduit.getProduitById(produitOutId);
                    return new EchangeProduit(produitIn, produitOut, dateEchange, valide);
                }
            }
        }
        return null;
    }

    public void updateExchange(EchangeProduit exchange) throws SQLException {
        String sql = "UPDATE echange_produit SET produit_in_id = ?, produit_out_id = ?, date_echange = ?, valide = ? WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(sql)) {
            ps.setInt(1, exchange.getProduitIn().getId());
            ps.setInt(2, exchange.getProduitOut().getId());
            ps.setTimestamp(3, Timestamp.valueOf(exchange.getDateEchange()));
            ps.setBoolean(4, exchange.isValide());
            ps.setInt(5, exchange.getId());
            ps.executeUpdate();
        }
    }

    public void deleteExchange(EchangeProduit exchange) throws SQLException {
        String sql = "DELETE FROM echange_produit WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(sql)) {
            ps.setInt(1, exchange.getId());
            ps.executeUpdate();
        }
    }

    public void validateExchange(EchangeProduit exchange) throws SQLException {
        String sql = "UPDATE echange_produit SET valide = true WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(sql)) {
            ps.setInt(1, exchange.getId());
            ps.executeUpdate();
            exchange.setValide(true); // Update the local instance as well
        }
    }



    // You may add other helper methods as needed
}
