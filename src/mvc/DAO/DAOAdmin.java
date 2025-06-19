package mvc.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import mcv.DAOInterface.IAdmin;
import mvc.Koneksi.Koneksi;
import mvc.Model.Admin;

public class DAOAdmin implements IAdmin {
    Connection connection;
    // Query untuk memeriksa email dan password
    final String select = "SELECT * FROM tbl_admin WHERE email=? AND password=?;";

    public DAOAdmin() {
        connection = Koneksi.getKoneksi();
    }

    @Override
    public Admin login(String email, String password) {
        PreparedStatement statement = null;
        ResultSet rs = null;
        Admin admin = null;

        try {
            statement = connection.prepareStatement(select);
            // Mengisi parameter '?' pertama dengan email
            statement.setString(1, email);
            // Mengisi parameter '?' kedua dengan password
            statement.setString(2, password);
            
            // Eksekusi query
            rs = statement.executeQuery();

            // Cek apakah ada baris data yang cocok (login berhasil)
            if (rs.next()) {
                admin = new Admin();
                admin.setId(rs.getInt("id"));
                admin.setNama(rs.getString("nama"));
                admin.setEmail(rs.getString("email"));
                admin.setPassword(rs.getString("password"));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            // Selalu tutup statement dan result set
            try {
                if (rs != null) rs.close();
                if (statement != null) statement.close();
            } catch (SQLException ex) {
                // abaikan
            }
        }
        // Akan mengembalikan objek Admin jika login berhasil, atau null jika gagal
        return admin;
    }
}