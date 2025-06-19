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
            statement.setString(1, email);
            statement.setString(2, password);
            
            rs = statement.executeQuery();

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
            try {
                if (rs != null) rs.close();
                if (statement != null) statement.close();
            } catch (SQLException ex) {
            }
        }
        return admin;
    }
}