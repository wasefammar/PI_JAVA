package DarkDev.util;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnexion {

    private  final  String URL = "jdbc:mysql://localhost:3306/pidev_swapnshare";
    private final  String USER = "root";
    private final String PASSWORD = "";
    private Connection connection;

    private static DatabaseConnexion instance;

    private DatabaseConnexion(){
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public static DatabaseConnexion getInstance() {
        if (instance == null){
            instance = new DatabaseConnexion();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}

