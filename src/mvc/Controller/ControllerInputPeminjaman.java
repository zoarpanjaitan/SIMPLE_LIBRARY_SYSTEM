package mvc.Controller;

import mvc.DAO.DAOPeminjaman;
import mvc.Model.Peminjaman;
import mvc.View.FormPeminjaman;

import javax.swing.JOptionPane;
import java.util.Calendar;
import java.util.Date;  
import java.sql.SQLException; 

public class ControllerInputPeminjaman 
{

    private FormPeminjaman frame; 
    private DAOPeminjaman daoPeminjaman;

    public ControllerInputPeminjaman(FormPeminjaman frame) {
        this.frame = frame;
        this.daoPeminjaman = new DAOPeminjaman();
    }

    public void insertPeminjaman() {
        Peminjaman p = new Peminjaman();

        if (frame.getTxtIdBuku().getText().isEmpty() ||
            frame.getTxtNama().getText().isEmpty() ||
            frame.getTxtNoHp().getText().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Semua field harus diisi!", "Validasi Input", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            p.setIdBuku(Integer.parseInt(frame.getTxtIdBuku().getText()));
            p.setNamaPeminjam(frame.getTxtNama().getText());
            p.setNoHp(frame.getTxtNoHp().getText());

            p.setTglPeminjaman(new Date());

            Calendar cal = Calendar.getInstance();
            cal.setTime(p.getTglPeminjaman());
            cal.add(Calendar.DAY_OF_YEAR, 7);
            p.setTglPengembalian(cal.getTime()); 
            p.setStatus("Dipinjam");

            daoPeminjaman.insert(p); 

            frame.clearForm(); 

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "ID Buku harus berupa angka!", "Input Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Terjadi kesalahan: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}