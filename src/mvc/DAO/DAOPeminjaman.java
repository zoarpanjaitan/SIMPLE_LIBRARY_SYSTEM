package mvc.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import mcv.DAOInterface.IPeminjaman; // PASTIKAN INI mvc.DAOInterface
import mvc.Koneksi.Koneksi;
import mvc.Model.Peminjaman;

public class DAOPeminjaman implements IPeminjaman {
    
    Connection connection;
    final String insert = "INSERT INTO tbl_peminjaman (id_buku, nama_peminjam, no_hp, tgl_peminjaman, status) VALUES (?, ?, ?, ?, ?);";
    final String update = "UPDATE tbl_peminjaman SET id_buku=?, nama_peminjam=?, no_hp=?, tgl_peminjaman=?, tgl_pengembalian=?, status=? WHERE id_peminjaman=?;";
    final String updatePengembalian = "UPDATE tbl_peminjaman SET tgl_pengembalian=?, status=? WHERE id_peminjaman=?;";
    final String delete = "DELETE FROM tbl_peminjaman WHERE id_peminjaman=?;";
    final String selectAll = "SELECT pinjam.*, buku.judul_buku " +
                              "FROM tbl_peminjaman pinjam " +
                              "INNER JOIN tbl_buku buku ON pinjam.id_buku = buku.id " +
                              "ORDER BY pinjam.id_peminjaman DESC;";

    final String cekStok = "SELECT stok FROM tbl_buku WHERE id=?;";
    final String updateStokKurang = "UPDATE tbl_buku SET stok = stok - 1 WHERE id=?;";
    final String updateStokTambah = "UPDATE tbl_buku SET stok = stok + 1 WHERE id=?;";

    final String searchSQL = "SELECT pinjam.*, buku.judul_buku " +
                             "FROM tbl_peminjaman pinjam " +
                             "INNER JOIN tbl_buku buku ON pinjam.id_buku = buku.id " +
                             "WHERE pinjam.nama_peminjam LIKE ? OR buku.judul_buku LIKE ? " + // Bisa mencari berdasarkan nama peminjam ATAU judul buku
                             "ORDER BY pinjam.id_peminjaman DESC;";
    
    final String getByIdSQL = "SELECT pinjam.*, buku.judul_buku " +
                              "FROM tbl_peminjaman pinjam " +
                              "INNER JOIN tbl_buku buku ON pinjam.id_buku = buku.id " +
                              "WHERE pinjam.id_peminjaman = ?;";

    public DAOPeminjaman() {
        connection = Koneksi.getKoneksi();
    }
    
    // untuk insert
    @Override
    public void insert(Peminjaman p) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(cekStok);
            statement.setInt(1, p.getIdBuku());
            ResultSet rs = statement.executeQuery();
            
            if (rs.next()) {
                int stokTersedia = rs.getInt("stok");
                if (stokTersedia > 0) 
                {
                    statement = connection.prepareStatement(updateStokKurang);
                    statement.setInt(1, p.getIdBuku());
                    statement.executeUpdate();

                    statement = connection.prepareStatement(insert);
                    statement.setInt(1, p.getIdBuku());
                    statement.setString(2, p.getNamaPeminjam());
                    statement.setString(3, p.getNoHp());
                    statement.setDate(4, new java.sql.Date(p.getTglPeminjaman().getTime()));
                    statement.setString(5, "Dipinjam"); 
                    statement.executeUpdate();
                    
                    javax.swing.JOptionPane.showMessageDialog(null, "Peminjaman berhasil dicatat!");
                    
                } else {
                    javax.swing.JOptionPane.showMessageDialog(null, "Stok buku habis, tidak bisa dipinjam.");
                }
            } else {
                javax.swing.JOptionPane.showMessageDialog(null, "ID Buku tidak ditemukan.");
            }
            rs.close();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(null, "Gagal melakukan proses peminjaman.\nError: " + ex.getMessage());
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException ex) {
            }
        }
    }
    
    @Override
    public void update(Peminjaman p) {
        PreparedStatement statement = null;
        try {
            if (p.getStatus() != null && p.getStatus().equalsIgnoreCase("Kembali")) {
                statement = connection.prepareStatement(updatePengembalian);
                statement.setDate(1, new java.sql.Date(p.getTglPengembalian().getTime()));
                statement.setString(2, "Kembali");
                statement.setInt(3, p.getIdPeminjaman());
                statement.executeUpdate();
                statement = connection.prepareStatement(updateStokTambah);
                statement.setInt(1, p.getIdBuku());
                statement.executeUpdate();
                javax.swing.JOptionPane.showMessageDialog(null, "Proses pengembalian berhasil!");

            } else {
                statement = connection.prepareStatement(update);
                statement.setInt(1, p.getIdBuku());
                statement.setString(2, p.getNamaPeminjam());
                statement.setString(3, p.getNoHp());
                statement.setDate(4, new java.sql.Date(p.getTglPeminjaman().getTime()));
                statement.setDate(5, p.getTglPengembalian() != null ? new java.sql.Date(p.getTglPengembalian().getTime()) : null);
                statement.setString(6, p.getStatus());
                statement.setInt(7, p.getIdPeminjaman());
                statement.executeUpdate();
                javax.swing.JOptionPane.showMessageDialog(null, "Data peminjaman berhasil diupdate!");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(null, "Gagal melakukan proses update/pengembalian.\nError: " + ex.getMessage());
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (SQLException ex) {
            }
        }
    }
    
    @Override
    public void delete(int idPeminjaman) {    
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(delete);
            statement.setInt(1, idPeminjaman);
            statement.executeUpdate();
            javax.swing.JOptionPane.showMessageDialog(null, "Catatan peminjaman berhasil dihapus.");
        } catch (SQLException ex) {
            ex.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(null, "Gagal menghapus catatan peminjaman.\nError: " + ex.getMessage());
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (SQLException ex) {
 
            }
        }
    }
    
    public List<Peminjaman> getAll() {
        List<Peminjaman> listPeminjaman = new ArrayList<>();
        Statement st = null; 
        ResultSet rs = null;
        try {
            st = connection.createStatement();
            rs = st.executeQuery(selectAll); 
            while (rs.next()) {
                Peminjaman p = new Peminjaman();
                p.setIdPeminjaman(rs.getInt("id_peminjaman"));
                p.setIdBuku(rs.getInt("id_buku"));
                p.setNamaPeminjam(rs.getString("nama_peminjam"));
                p.setNoHp(rs.getString("no_hp"));
                p.setTglPeminjaman(rs.getDate("tgl_peminjaman"));
                p.setTglPengembalian(rs.getDate("tgl_pengembalian"));
                p.setStatus(rs.getString("status"));
                p.setJudulBuku(rs.getString("judul_buku"));
                listPeminjaman.add(p);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (st != null) st.close();
            } catch (SQLException ex) {
            }
        }
        return listPeminjaman;
    }

    @Override
    public List<Peminjaman> search(String keyword) {
        List<Peminjaman> listPeminjaman = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            statement = connection.prepareStatement(searchSQL);
            statement.setString(1, "%" + keyword + "%");
            statement.setString(2, "%" + keyword + "%"); 
            rs = statement.executeQuery();
            while (rs.next()) {
                Peminjaman p = new Peminjaman();
                p.setIdPeminjaman(rs.getInt("id_peminjaman"));
                p.setIdBuku(rs.getInt("id_buku"));
                p.setNamaPeminjam(rs.getString("nama_peminjam"));
                p.setNoHp(rs.getString("no_hp"));
                p.setTglPeminjaman(rs.getDate("tgl_peminjaman"));
                p.setTglPengembalian(rs.getDate("tgl_pengembalian"));
                p.setStatus(rs.getString("status"));
                p.setJudulBuku(rs.getString("judul_buku"));
                listPeminjaman.add(p);
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
        return listPeminjaman;
    }

    @Override
    public Peminjaman getById(int id) {
        Peminjaman peminjaman = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            statement = connection.prepareStatement(getByIdSQL);
            statement.setInt(1, id);
            rs = statement.executeQuery();
            if (rs.next()) {
                peminjaman = new Peminjaman();
                peminjaman.setIdPeminjaman(rs.getInt("id_peminjaman"));
                peminjaman.setIdBuku(rs.getInt("id_buku"));
                peminjaman.setNamaPeminjam(rs.getString("nama_peminjam"));
                peminjaman.setNoHp(rs.getString("no_hp"));
                peminjaman.setTglPeminjaman(rs.getDate("tgl_peminjaman"));
                peminjaman.setTglPengembalian(rs.getDate("tgl_pengembalian"));
                peminjaman.setStatus(rs.getString("status"));
                peminjaman.setJudulBuku(rs.getString("judul_buku"));
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
        return peminjaman;
    }
}