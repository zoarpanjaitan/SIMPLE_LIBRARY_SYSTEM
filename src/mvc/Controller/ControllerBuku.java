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

    public ControllerBuku(FormDaftarBuku frame) {
        this.frameDaftar = frame;
        implBuku = new DAOBuku();
        listBuku = implBuku.getAll();
    }
    
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
    

    public void isiField(int row) 
    {
        frameInput.getTxtID().setText(listBuku.get(row).getId().toString());
        frameInput.getTxtJudulBuku().setText(listBuku.get(row).getJudul_buku());
        frameInput.getTxtPengarang().setText(listBuku.get(row).getPengarang());
        frameInput.getTxtPenerbit().setText(listBuku.get(row).getPenerbit());
        frameInput.getTxtTahunTerbit().setText(listBuku.get(row).getTahun_terbit().toString());
        frameInput.getCmbBoxKategori().setSelectedItem(listBuku.get(row).getKategori());
        frameInput.getTxtStok().setText(listBuku.get(row).getStok().toString());
    }
    
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
    
    public void delete() 
    {
        int selectedRow = frameDaftar.getTblDaftarBuku().getSelectedRow();
        
        if (selectedRow != -1) {
            int confirm = javax.swing.JOptionPane.showConfirmDialog(frameDaftar, "Apakah Anda yakin ingin menghapus data ini?",  "Konfirmasi Hapus", javax.swing.JOptionPane.YES_NO_OPTION);
            
            if (confirm == javax.swing.JOptionPane.YES_OPTION) 
            {
                int id = listBuku.get(selectedRow).getId();
                implBuku.delete(id);
                javax.swing.JOptionPane.showMessageDialog(null, "Hapus Data Sukses");
                isiTabel();
            }
        } else {
            javax.swing.JOptionPane.showMessageDialog(frameDaftar, "Silakan pilih baris data terlebih dahulu");
        }
    }
    
    public void isiTabelCari() 
    {
        String cari = frameDaftar.getTxtCariJudulBuku().getText();
        listBuku = implBuku.getCariJudul(cari);
        TabelModelBuku tmb = new TabelModelBuku(listBuku);
        frameDaftar.getTblDaftarBuku().setModel(tmb);
    }
    
    public void cariJudul() 
    {
        if (!frameDaftar.getTxtCariJudulBuku().getText().trim().isEmpty()) {
            isiTabelCari();
        } else {
            javax.swing.JOptionPane.showMessageDialog(frameDaftar, "Silakan masukkan judul yang ingin dicari.");
            isiTabel(); 
        }
    }
}