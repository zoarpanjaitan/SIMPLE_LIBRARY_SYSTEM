package mvc.DAO;

import mvc.Koneksi.Koneksi;
import mvc.Model.Buku;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mcv.DAOInterface.IBuku;

/**
 *
 * @author (Namamu)
 */
public class DAOBuku implements IBuku 
{
    
    Connection connection;
    final String insert = "INSERT INTO tbl_buku (judul_buku, pengarang, penerbit, tahun_terbit, kategori, stok) VALUES (?,?,?,?,?,?);";
    final String update = "UPDATE tbl_buku set judul_buku=?, pengarang=?, penerbit=?, tahun_terbit=?, kategori=?, stok=? where id=?;";
    final String delete = "DELETE FROM tbl_buku where id=?;";
    final String select = "SELECT * FROM tbl_buku;";
    final String cariJudul = "SELECT * FROM tbl_buku where judul_buku like ?;";
    
    public DAOBuku() {
        connection = Koneksi.getKoneksi(); // 
    }
    
    // METHOD-METHOD LAINNYA AKAN KITA TAMBAHKAN DI SINI

    @Override
    public void insert(Buku b) 
    {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(insert);
            statement.setString(1, b.getJudul_buku());
            statement.setString(2, b.getPengarang());
            statement.setString(3, b.getPenerbit());
            statement.setInt(4, b.getTahun_terbit());
            statement.setString(5, b.getKategori());
            statement.setInt(6, b.getStok());
            statement.executeUpdate();

            // Kode ini untuk mengambil ID yang baru saja di-generate oleh database
            ResultSet rs = statement.getGeneratedKeys();
            while (rs.next()) {
                b.setId(rs.getInt(1));
            } 
        } catch (SQLException ex) {
            System.out.println("Gagal Input");
            ex.printStackTrace(); // Tampilkan detail error
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException ex) {
                // Abaikan
            }
        }
    }

    @Override
    public void update(Buku b) 
    {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(update);
            statement.setString(1, b.getJudul_buku());
            statement.setString(2, b.getPengarang());
            statement.setString(3, b.getPenerbit());
            statement.setInt(4, b.getTahun_terbit());
            statement.setString(5, b.getKategori());
            statement.setInt(6, b.getStok());
            statement.setInt(7, b.getId()); // Ini untuk mengisi ID di klausa WHERE
            statement.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Gagal Update");
            ex.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException ex) {
                // Abaikan
            }
        }
    }

    @Override
    public void delete(int id) 
    {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(delete);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Gagal Delete");
            ex.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException ex) {
                // Abaikan
            }
        }
    }

    @Override
    public List<Buku> getAll() {
        List<Buku> lb = null;
        try {
            lb = new ArrayList<Buku>();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(select); // Menjalankan query "SELECT * FROM tbl_buku;"
            while (rs.next()) {
                // Membuat objek buku baru untuk setiap baris data
                Buku b = new Buku();

                // Mengisi objek buku dengan data dari database
                b.setId(rs.getInt("id"));
                b.setJudul_buku(rs.getString("judul_buku"));
                b.setPengarang(rs.getString("pengarang"));
                b.setPenerbit(rs.getString("penerbit"));
                b.setTahun_terbit(rs.getInt("tahun_terbit"));
                b.setKategori(rs.getString("kategori"));
                b.setStok(rs.getInt("stok"));

                // Menambahkan objek buku ke dalam list
                lb.add(b);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOBuku.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lb;
    }

    @Override
    public List<Buku> getCariJudul(String judul) {
        List<Buku> lb = null;
        try {
            lb = new ArrayList<Buku>();
            PreparedStatement st = connection.prepareStatement(cariJudul);

            // Mengatur parameter untuk klausa 'LIKE' dengan wildcard '%'
            st.setString(1, "%" + judul + "%");

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Buku b = new Buku();
                b.setId(rs.getInt("id"));
                b.setJudul_buku(rs.getString("judul_buku"));
                b.setPengarang(rs.getString("pengarang"));
                b.setPenerbit(rs.getString("penerbit"));
                b.setTahun_terbit(rs.getInt("tahun_terbit"));
                b.setKategori(rs.getString("kategori"));
                b.setStok(rs.getInt("stok"));
                lb.add(b);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOBuku.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lb;
    }

}