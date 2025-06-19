
package mvc.Koneksi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Koneksi 
{
    private static Connection koneksi; 

    public static Connection getKoneksi() {
        try {
            if (koneksi == null || koneksi.isClosed()) 
            {
                String url = "jdbc:mysql://localhost:3306/library";
                String user = "root";
                String pass = "";
                koneksi = DriverManager.getConnection(url, user, pass);
                System.out.println("Koneksi Berhasil");
            }
        } catch (SQLException e) {
            System.out.println("Koneksi gagal: " + e.getMessage());
            koneksi = null;
        }
        return koneksi;
    }
}


