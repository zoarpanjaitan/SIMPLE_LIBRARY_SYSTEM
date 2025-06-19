package mvc.Controller;

import mvc.DAO.DAOPeminjaman;
import mvc.Model.Peminjaman;
import mvc.View.FormDaftarPeminjaman;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.sql.SQLException;

public class ControllerListPeminjaman {

    private FormDaftarPeminjaman frame; 
    private DAOPeminjaman daoPeminjaman;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public ControllerListPeminjaman(FormDaftarPeminjaman frame) {
        this.frame = frame;
        this.daoPeminjaman = new DAOPeminjaman();
        initializeTableModel(); 
        loadPeminjamanData();   
    }

    private void initializeTableModel() {
        DefaultTableModel tableModel = new DefaultTableModel(
            new Object[][]{},
            new String[]{"ID Peminjaman", "Nama Peminjam", "No HP", "Judul Buku", "Tgl Peminjaman", "Tgl Kembali", "Status"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        frame.getTblDaftarPeminjaman().setModel(tableModel);
    }

    public void loadPeminjamanData() {
        DefaultTableModel model = (DefaultTableModel) frame.getTblDaftarPeminjaman().getModel();
        model.setRowCount(0); 
        List<Peminjaman> peminjamans = daoPeminjaman.getAll();
        if (peminjamans != null) {
            for (Peminjaman p : peminjamans) {
                model.addRow(new Object[]{
                    p.getIdPeminjaman(),
                    p.getNamaPeminjam(),
                    p.getNoHp(),
                    p.getJudulBuku(),
                    p.getTglPeminjaman() != null ? dateFormat.format(p.getTglPeminjaman()) : "",
                    p.getTglPengembalian() != null ? dateFormat.format(p.getTglPengembalian()) : "",
                    p.getStatus()
                });
            }
        }
    }

    public void searchPeminjaman() {
        String searchKeyword = frame.getTxtCariNamaPeminjam().getText().trim();
        DefaultTableModel model = (DefaultTableModel) frame.getTblDaftarPeminjaman().getModel();
        model.setRowCount(0);

        List<Peminjaman> peminjamans = daoPeminjaman.search(searchKeyword);

        if (peminjamans != null) {
            if (peminjamans.isEmpty() && !searchKeyword.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Tidak ada catatan ditemukan untuk '" + searchKeyword + "'", "Hasil Pencarian", JOptionPane.INFORMATION_MESSAGE);
            }

            for (Peminjaman p : peminjamans) {
                model.addRow(new Object[]{
                    p.getIdPeminjaman(),
                    p.getNamaPeminjam(),
                    p.getNoHp(),
                    p.getJudulBuku(),
                    p.getTglPeminjaman() != null ? dateFormat.format(p.getTglPeminjaman()) : "",
                    p.getTglPengembalian() != null ? dateFormat.format(p.getTglPengembalian()) : "",
                    p.getStatus()
                });
            }
        }
    }

    public void deletePeminjaman() {
        int selectedRow = frame.getTblDaftarPeminjaman().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Mohon pilih catatan peminjaman yang ingin dihapus.", "Tidak Ada Pilihan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        DefaultTableModel model = (DefaultTableModel) frame.getTblDaftarPeminjaman().getModel();
        String statusSaatIni = (String) model.getValueAt(selectedRow, 6); 

        if ("Dipinjam".equalsIgnoreCase(statusSaatIni)) {
            JOptionPane.showMessageDialog(frame, "Tidak bisa menghapus peminjaman yang masih berstatus 'Dipinjam'.\nHarap lakukan pengembalian terlebih dahulu.", "Aksi Ditolak", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(frame, "Apakah Anda yakin ingin menghapus riwayat pengembalian ini?", "Konfirmasi Penghapusan", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                int idPeminjaman = (int) model.getValueAt(selectedRow, 0);
                daoPeminjaman.delete(idPeminjaman);
                loadPeminjamanData(); 
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Terjadi kesalahan saat menghapus data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void processReturn() {
        int selectedRow = frame.getTblDaftarPeminjaman().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Mohon pilih catatan peminjaman yang akan diproses pengembaliannya.", "Tidak Ada Pilihan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        DefaultTableModel model = (DefaultTableModel) frame.getTblDaftarPeminjaman().getModel();
        String statusSaatIni = (String) model.getValueAt(selectedRow, 6); 

        if ("Kembali".equalsIgnoreCase(statusSaatIni)) {
            JOptionPane.showMessageDialog(frame, "Buku ini sudah berstatus 'Kembali'.", "Informasi", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(frame, "Apakah Anda yakin ingin menandai buku ini sudah kembali?", "Konfirmasi Pengembalian", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                int idPeminjaman = (int) model.getValueAt(selectedRow, 0);
                
                Peminjaman peminjamanToReturn = daoPeminjaman.getById(idPeminjaman);

                if (peminjamanToReturn != null) {
                    peminjamanToReturn.setTglPengembalian(new Date()); 
                    peminjamanToReturn.setStatus("Kembali");
                    
                    daoPeminjaman.update(peminjamanToReturn); 
                    JOptionPane.showMessageDialog(frame, "Buku berhasil ditandai 'Sudah Kembali'. Stok telah diperbarui.", "Berhasil", JOptionPane.INFORMATION_MESSAGE);
                    loadPeminjamanData();
                } else {
                    JOptionPane.showMessageDialog(frame, "Catatan peminjaman tidak ditemukan di database.", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Terjadi kesalahan saat menandai buku kembali: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}