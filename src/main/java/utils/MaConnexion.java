package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MaConnexion {
    //DB URL = "pidev_swapnshare"
    //URL = "pidev_swapnshare"
    final String URL = "jdbc:mysql://localhost:3306/pidev_swapnshare";
    final String USR = "root";
    final String PWD = "";

    //Att
    private Connection cnx;
    public static MaConnexion instance;

    //Constructor
    private MaConnexion() {
        try {
            cnx = DriverManager.getConnection(URL, USR, PWD);
            System.out.println("Connected successfully");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getCnx() {
        return cnx;
    }

    public static MaConnexion getInstance() {
        if (instance == null)
            instance = new MaConnexion();
        return instance;
    }
}
