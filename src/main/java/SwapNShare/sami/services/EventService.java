package SwapNShare.sami.services;

import SwapNShare.sami.interfaces.IService;
import SwapNShare.sami.models.Event;
import SwapNShare.sami.util.MaConnexion;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class EventService implements IService<Event> {

    Connection cnx = MaConnexion.getInstance().getCnx();

    @Override
    public void add(Event event) {
        try {
            String query = "INSERT INTO evenement (produit_id, titre_evenement, description_evenement, status, date_debut, date_fin) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = MaConnexion.getInstance().getCnx().prepareStatement(query);
            preparedStatement.setInt(1, event.getProduit_id());
            preparedStatement.setString(2, event.getTitre_evenement());
            preparedStatement.setString(3, event.getDescription_evenement());
            preparedStatement.setString(4, event.getStatus());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(event.getDate_debut()));
            preparedStatement.setTimestamp(6, Timestamp.valueOf(event.getDate_fin()));
            preparedStatement.executeUpdate();
            System.out.println("Event added successfully!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void update(Event event) {
        try {
            String query = "UPDATE evenement SET produit_id=?, titre_evenement=?, description_evenement=?, status=?, date_debut=?, date_fin=? WHERE id=?";
            PreparedStatement preparedStatement = MaConnexion.getInstance().getCnx().prepareStatement(query);
            preparedStatement.setInt(1, event.getProduit_id());
            preparedStatement.setString(2, event.getTitre_evenement());
            preparedStatement.setString(3, event.getDescription_evenement());
            preparedStatement.setString(4, event.getStatus());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(event.getDate_debut()));
            preparedStatement.setTimestamp(6, Timestamp.valueOf(event.getDate_fin()));
            preparedStatement.setInt(7, event.getId());
            preparedStatement.executeUpdate();
            System.out.println("Event updated successfully!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void delete(Event event) {
        try {
            String query = "DELETE FROM evenement WHERE id=?";
            PreparedStatement preparedStatement = MaConnexion.getInstance().getCnx().prepareStatement(query);
            preparedStatement.setInt(1, event.getId());
            preparedStatement.executeUpdate();
            System.out.println("Event deleted successfully!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Event> getAll() {
        List<Event> events = new ArrayList<>();
        try {
            String query = "SELECT * FROM evenement";
            Statement statement = MaConnexion.getInstance().getCnx().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int produit_id = resultSet.getInt("produit_id");
                String titre_evenement = resultSet.getString("titre_evenement");
                String description_evenement = resultSet.getString("description_evenement");
                String status = resultSet.getString("status");
                LocalDateTime date_debut = resultSet.getTimestamp("date_debut").toLocalDateTime();
                LocalDateTime date_fin = resultSet.getTimestamp("date_fin").toLocalDateTime();
                Event event = new Event(id, produit_id, titre_evenement, description_evenement, status, date_debut, date_fin);
                events.add(event);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return events;
    }

    @Override
    public Event getOne(int id) {
        Event event = null;
        try {
            String query = "SELECT * FROM evenement WHERE id = ?";
            PreparedStatement preparedStatement = MaConnexion.getInstance().getCnx().prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int produit_id = resultSet.getInt("produit_id");
                String titre_evenement = resultSet.getString("titre_evenement");
                String description_evenement = resultSet.getString("description_evenement");
                String status = resultSet.getString("status");
                LocalDateTime date_debut = resultSet.getTimestamp("date_debut").toLocalDateTime();
                LocalDateTime date_fin = resultSet.getTimestamp("date_fin").toLocalDateTime();
                event = new Event(id, produit_id, titre_evenement, description_evenement, status, date_debut, date_fin);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return event;
    }

    public List<Event> fetchEvent() {
        List<Event> events = new ArrayList<>();
        String SQL = "SELECT * FROM evenement";

        try (Connection conn = MaConnexion.getInstance().getCnx();
             PreparedStatement pstmt = conn.prepareStatement(SQL);
             ResultSet resultSet = pstmt.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int produit_id = resultSet.getInt("produit_id");
                String titre_evenement = resultSet.getString("titre_evenement");
                String description_evenement = resultSet.getString("description_evenement");
                String status = resultSet.getString("status");
                LocalDateTime date_debut = resultSet.getTimestamp("date_debut").toLocalDateTime();
                LocalDateTime date_fin = resultSet.getTimestamp("date_fin").toLocalDateTime();
                Event event = new Event(id, produit_id, titre_evenement, description_evenement, status, date_debut, date_fin);
                events.add(event);

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return events;
    }
}
