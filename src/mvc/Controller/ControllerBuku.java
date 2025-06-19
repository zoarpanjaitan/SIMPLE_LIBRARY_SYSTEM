package mvc.Controller;

import java.util.List;
import javax.swing.JOptionPane;
import mvc.DAO.DAOBuku;
import mcv.DAOInterface.IBuku;
import mvc.Model.Buku;
import mvc.Model.TabelModelBuku;
import mvc.View.FormDaftarBuku;
import mvc.View.FormInputBuku;

public class ControllerBuku {
    FormDaftarBuku frameDaftar;
    FormInputBuku frameInput;
    IBuku implBuku;
    List<Buku> listBuku;

    // Konstruktor akan menerima frame utama sebagai parameter
    public ControllerBuku(FormDaftarBuku frame) {
        this.frameDaftar = frame;
        implBuku = new DAOBuku();
        listBuku = implBuku.getAll();
    }
    
    // Method untuk menampilkan semua data ke tabel
    public void isiTabel() 
    {
        listBuku = implBuku.getAll();
        TabelModelBuku tmb = new TabelModelBuku(listBuku);
        frameDaftar.getTblDaftarBuku().setModel(tmb);
    }

    public void reset() 
    {
        frameInput.getTxtID().setText("");
        frameInput.getTxtJudulBuku().setText("");
        frameInput.getTxtPengarang().setText("");
        frameInput.getTxtPenerbit().setText("");
        frameInput.getTxtTahunTerbit().setText("");
        frameInput.getCmbBoxKategori().setSelectedItem("Fiksi");
        frameInput.getTxtStok().setText("");
    }
    // Method untuk mengambil data dari frame input dan menyimpan ke database
    public void insert() 
    {
        if (!frameInput.getTxtJudulBuku().getText().trim().isEmpty()) {
            try {
                Buku b = new Buku();
                b.setJudul_buku(frameInput.getTxtJudulBuku().getText());
                b.setPengarang(frameInput.getTxtPengarang().getText());
                b.setPenerbit(frameInput.getTxtPenerbit().getText());
                b.setTahun_terbit(Integer.parseInt(frameInput.getTxtTahunTerbit().getText()));
                b.setKategori(frameInput.getCmbBoxKategori().getSelectedItem().toString());
                b.setStok(Integer.parseInt(frameInput.getTxtStok().getText()));
                implBuku.insert(b);
                JOptionPane.showMessageDialog(null, "Simpan Data Sukses");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frameInput, "Tahun dan Stok harus diisi angka!");
            }
        } else {
            JOptionPane.showMessageDialog(frameInput, "Judul Buku Tidak Boleh Kosong");
        }
    }
    // Method untuk menampilkan frame input
    public void showInputForm() {
        frameInput = new FormInputBuku();
        reset();
        frameInput.setVisible(true);
        frameInput.getBtnSimpan().addActionListener(e -> {
            insert();
            isiTabel();
            frameInput.dispose();
        });
        frameInput.getBtnBatal().addActionListener(e -> {
            frameInput.dispose();
        });
    }
    
    // Method untuk menampilkan data yang dipilih dari tabel ke form input
    public void isiField(int row) 
    {
        // Mengisi textfield di frameInput dengan data dari listBuku sesuai baris yang dipilih
        frameInput.getTxtID().setText(listBuku.get(row).getId().toString());
        frameInput.getTxtJudulBuku().setText(listBuku.get(row).getJudul_buku());
        frameInput.getTxtPengarang().setText(listBuku.get(row).getPengarang());
        frameInput.getTxtPenerbit().setText(listBuku.get(row).getPenerbit());
        frameInput.getTxtTahunTerbit().setText(listBuku.get(row).getTahun_terbit().toString());
        frameInput.getCmbBoxKategori().setSelectedItem(listBuku.get(row).getKategori());
        frameInput.getTxtStok().setText(listBuku.get(row).getStok().toString());
    }
    
    // Method untuk mengambil data yang diubah dari form input dan meng-update database
    public void update() 
    {
        if (!frameInput.getTxtID().getText().trim().isEmpty()) {
            try {
                Buku b = new Buku();
                b.setJudul_buku(frameInput.getTxtJudulBuku().getText());
                b.setPengarang(frameInput.getTxtPengarang().getText());
                b.setPenerbit(frameInput.getTxtPenerbit().getText());
                b.setTahun_terbit(Integer.parseInt(frameInput.getTxtTahunTerbit().getText()));
                b.setKategori(frameInput.getCmbBoxKategori().getSelectedItem().toString());
                b.setStok(Integer.parseInt(frameInput.getTxtStok().getText()));
                b.setId(Integer.parseInt(frameInput.getTxtID().getText()));
                implBuku.update(b);
                JOptionPane.showMessageDialog(null, "Update Data Sukses");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frameInput, "Tahun dan Stok harus diisi angka!");
            }
        } else {
            JOptionPane.showMessageDialog(frameInput, "Silakan pilih data yang akan diubah");
        }
    }
    
    // Method untuk menampilkan frame input dalam mode update
    public void showUpdateForm() 
    {
        int selectedRow = frameDaftar.getTblDaftarBuku().getSelectedRow();
        if (selectedRow != -1) {
            frameInput = new FormInputBuku();
            isiField(selectedRow);

            frameInput.getTxtID().setEditable(false);
            frameInput.setVisible(true);

            frameInput.getBtnSimpan().addActionListener(e -> {
                update();
                isiTabel();
                frameInput.dispose();
            });
            frameInput.getBtnBatal().addActionListener(e -> {
                frameInput.dispose();
            });
        } else {
            JOptionPane.showMessageDialog(frameDaftar, "Silakan pilih baris data terlebih dahulu");
        }
    }
    
    // Method untuk menghapus data yang dipilih
    public void delete() 
    {
        int selectedRow = frameDaftar.getTblDaftarBuku().getSelectedRow();
        
        // Cek apakah ada baris yang dipilih
        if (selectedRow != -1) {
            // Tampilkan dialog konfirmasi sebelum menghapus
            int confirm = javax.swing.JOptionPane.showConfirmDialog(frameDaftar, 
                                                                    "Apakah Anda yakin ingin menghapus data ini?", 
                                                                    "Konfirmasi Hapus", 
                                                                    javax.swing.JOptionPane.YES_NO_OPTION);
            
            if (confirm == javax.swing.JOptionPane.YES_OPTION) {
                // Ambil ID dari baris yang dipilih
                int id = listBuku.get(selectedRow).getId();
                // Panggil DAO untuk menghapus data
                implBuku.delete(id);
                javax.swing.JOptionPane.showMessageDialog(null, "Hapus Data Sukses");
                // Refresh tabel
                isiTabel();
            }
        } else {
            javax.swing.JOptionPane.showMessageDialog(frameDaftar, "Silakan pilih baris data terlebih dahulu");
        }
    }
    
     // Method untuk mengisi tabel berdasarkan hasil pencarian
    public void isiTabelCari() 
    {
        // Mengambil teks pencarian dari textfield di frameDaftar
        String cari = frameDaftar.getTxtCariJudulBuku().getText();
        // Memanggil method DAO untuk mencari data berdasarkan judul
        listBuku = implBuku.getCariJudul(cari);
        // Membuat tabel model baru dengan data hasil pencarian
        TabelModelBuku tmb = new TabelModelBuku(listBuku);
        // Mengatur model tabel di frameDaftar
        frameDaftar.getTblDaftarBuku().setModel(tmb);
    }
    
    // Method yang akan dipanggil saat tombol "Cari" di-klik
    public void cariJudul() 
    {
        // Cek apakah field pencarian tidak kosong
        if (!frameDaftar.getTxtCariJudulBuku().getText().trim().isEmpty()) {
            // Jika tidak kosong, panggil method untuk mengisi tabel dengan hasil pencarian
            isiTabelCari();
        } else {
            // Jika kosong, tampilkan pesan atau muat ulang semua data
            javax.swing.JOptionPane.showMessageDialog(frameDaftar, "Silakan masukkan judul yang ingin dicari.");
            isiTabel(); // Muat ulang semua data jika field kosong
        }
    }
}