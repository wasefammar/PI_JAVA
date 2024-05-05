package services.EchangeServices;

import models.Echange.EchangeService;
import models.Services.Service;

import services.GestionServices.GestionService;
import utils.DatabaseConnexion;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ServiceEchangeService {
    private Connection cnx;

    public ServiceEchangeService() {
        cnx = DatabaseConnexion.getInstance().getConnection();
    }

    public EchangeService createExchange(Service serviceIn, Service serviceOut, LocalDateTime dateEchange, Boolean valide) throws SQLException {
        String sql = "INSERT INTO echange_service (service_in_id, service_out_id, date_echange, valide) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, serviceIn.getId());
        ps.setInt(2, serviceOut.getId());
        ps.setTimestamp(3, Timestamp.valueOf(dateEchange));
        ps.setBoolean(4, valide);
        ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();
        int id = 0;
        if (rs.next()) {
            id = rs.getInt(1);
        }
        return new EchangeService(serviceIn, serviceOut, dateEchange, valide);
    }

    public List<EchangeService> getAllExchanges() throws SQLException {
        List<EchangeService> exchanges = new ArrayList<>();
        String sql = "SELECT * FROM echange_service";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            int id = rs.getInt("id");
            int serviceInId = rs.getInt("service_in");
            int serviceOutId = rs.getInt("service_out");
            LocalDateTime dateEchange = rs.getTimestamp("date_echange").toLocalDateTime();
            Boolean valide = rs.getBoolean("valide");
            Service serviceIn = new GestionService().getServiceById(serviceInId);
            Service serviceOut = new GestionService().getServiceById(serviceOutId);

            EchangeService exchange = new EchangeService(serviceIn, serviceOut, dateEchange, valide);
            exchanges.add(exchange);
        }

        return exchanges;
    }

    public EchangeService getExchangeById(int id) throws SQLException {
        String sql = "SELECT * FROM echange_service WHERE id = ?";
        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            int serviceInId = rs.getInt("service_in_id");
            int serviceOutId = rs.getInt("service_out_id");
            LocalDateTime dateEchange = rs.getTimestamp("date_echange").toLocalDateTime();
            Boolean valide = rs.getBoolean("valide");

            Service serviceIn = new GestionService().getServiceById(serviceInId);
            Service serviceOut = new GestionService().getServiceById(serviceOutId);

            return new EchangeService(serviceIn, serviceOut, dateEchange, valide);
        }
        return null;
    }



    public void updateExchange(EchangeService exchange) throws SQLException {
        String sql = "UPDATE echange_service SET service_in = ?, service_out = ?, date_echange = ?, valide = ? WHERE id = ?";
        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setInt(1, exchange.getServiceIn().getId());
        ps.setInt(2, exchange.getServiceOut().getId());
        ps.setTimestamp(3, Timestamp.valueOf(exchange.getDateEchange()));
        ps.setBoolean(4, exchange.isValide());
        ps.setInt(5, exchange.getId());
        ps.executeUpdate();
    }

    public void deleteExchange(EchangeService exchange) throws SQLException {
        String sql = "DELETE FROM echange_service WHERE id = ?";
        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setInt(1, exchange.getId());
        ps.executeUpdate();
    }

    public void validateExchange(EchangeService exchange) throws SQLException {
        String sql = "UPDATE echange_service SET valide="+1+" WHERE id=?";
        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setInt(1, exchange.getId());
        ps.executeUpdate();
    }

    private Service getService(int id) {
        // Implement service CRUD here
        return null;
    }

    public List<EchangeService> getExchangeByServicesIn(List<Service> services) throws SQLException {
        List<EchangeService> echangeServices = new ArrayList<>();
        for (Service service : services) {
            String sql = "SELECT * FROM echange_service WHERE service_in_id = ?";
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setInt(1, service.getId());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id= rs.getInt("id");
                System.out.println(id);
                int serviceInId = rs.getInt("service_in_id");
                int serviceOutId = rs.getInt("service_out_id");
                LocalDateTime dateEchange = rs.getTimestamp("date_echange").toLocalDateTime();
                Boolean valide = rs.getBoolean("valide");
                Service serviceIn = new GestionService().getServiceById(serviceInId);
                Service serviceOut = new GestionService().getServiceById(serviceOutId);
                EchangeService echangeService = new EchangeService(id, serviceIn, serviceOut, dateEchange, valide);
                echangeServices.add(echangeService);
            }
        }
        return echangeServices;
    }
    public List<EchangeService> getExchangeByServicesOut(List<Service> services) throws SQLException {
        List<EchangeService> echangeServices = new ArrayList<>();
        for (Service service : services) {
            String sql = "SELECT * FROM echange_service WHERE service_out_id = ?";
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setInt(1, service.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id= rs.getInt("id");
                int serviceInId = rs.getInt("service_in_id");
                int serviceOutId = rs.getInt("service_out_id");
                LocalDateTime dateEchange = rs.getTimestamp("date_echange").toLocalDateTime();
                Boolean valide = rs.getBoolean("valide");
                Service serviceIn = new GestionService().getServiceById(serviceInId);
                Service serviceOut = new GestionService().getServiceById(serviceOutId);
                EchangeService echangeService = new EchangeService(id, serviceIn, serviceOut, dateEchange, valide);
                echangeServices.add(echangeService);
            }
        }
        return echangeServices;
    }


}

