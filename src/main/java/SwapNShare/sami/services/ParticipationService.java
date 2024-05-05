package SwapNShare.sami.services;

import SwapNShare.sami.interfaces.IService;
import SwapNShare.sami.models.EventParticipation;
import SwapNShare.sami.util.MaConnexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ParticipationService implements IService<EventParticipation> {

    Connection cnx = MaConnexion.getInstance().getCnx();
    @Override
    public void add(EventParticipation eventParticipation) {
        try {
            String query = "INSERT INTO participation_evenement (evenement_id, utilisateur_id, offre) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = cnx.prepareStatement(query);
            preparedStatement.setInt(1, eventParticipation.getEvenement_id());
            preparedStatement.setInt(2, eventParticipation.getUtilisateur_id());
            preparedStatement.setDouble(3, eventParticipation.getOffre());
            preparedStatement.executeUpdate();
            System.out.println("Event Participation added successfully!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void update(EventParticipation eventParticipation) {
        try {
            String query = "UPDATE participation_evenement SET evenement_id=?, utilisateur_id=?, offre=? WHERE id=?";
            PreparedStatement preparedStatement = cnx.prepareStatement(query);
            preparedStatement.setInt(1, eventParticipation.getEvenement_id());
            preparedStatement.setInt(2, eventParticipation.getUtilisateur_id());
            preparedStatement.setDouble(3, eventParticipation.getOffre());
            preparedStatement.setInt(4, eventParticipation.getId());
            preparedStatement.executeUpdate();
            System.out.println("Event Participation updated successfully!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void delete(EventParticipation eventParticipation) {
        try {
            String query = "DELETE FROM participation_evenement WHERE id=?";
            PreparedStatement preparedStatement = cnx.prepareStatement(query);
            preparedStatement.setInt(1, eventParticipation.getId());
            preparedStatement.executeUpdate();
            System.out.println("Event Participation deleted successfully!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<EventParticipation> getAll() {
        List<EventParticipation> participations = new ArrayList<>();
        try {
            String query = "SELECT * FROM participation_evenement";
            Statement statement = cnx.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int evenement_id = resultSet.getInt("evenement_id");
                int utilisateur_id = resultSet.getInt("utilisateur_id");
                double offre = resultSet.getDouble("offre");
                EventParticipation participation = new EventParticipation(id, evenement_id, utilisateur_id, offre);
                participations.add(participation);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return participations;
    }

    @Override
    public EventParticipation getOne(int id) {
        EventParticipation participation = null;
        try {
            String query = "SELECT * FROM participation_evenement WHERE id=?";
            PreparedStatement preparedStatement = cnx.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int evenement_id = resultSet.getInt("evenement_id");
                int utilisateur_id = resultSet.getInt("utilisateur_id");
                double offre = resultSet.getDouble("offre");
                participation = new EventParticipation(id, evenement_id, utilisateur_id, offre);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return participation;
    }
}
