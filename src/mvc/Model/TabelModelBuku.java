package mvc.Model;

import java.util.List;
import javax.swing.table.AbstractTableModel;

public class TabelModelBuku extends AbstractTableModel {
    
    List<Buku> lb;
    
    public TabelModelBuku(List<Buku> lb) {
        this.lb = lb;
    }
    
    @Override
    public int getColumnCount() {
        return 7; // Jumlah kolom: ID, Judul, Pengarang, Penerbit, Tahun, Kategori, Stok
    }
    
    @Override
    public int getRowCount() {
        return lb.size();
    }
    
    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "ID";
            case 1:
                return "Judul Buku";
            case 2:
                return "Pengarang";
            case 3:
                return "Penerbit";
            case 4:
                return "Tahun";
            case 5:
                return "Kategori";
            case 6:
                return "Stok";
            default:
                return null;
        }
    }
    
    @Override
    public Object getValueAt(int row, int column) {
        switch (column) {
            case 0:
                return lb.get(row).getId();
            case 1:
                return lb.get(row).getJudul_buku(); // Pastikan nama getter ini sesuai dengan yang ada di kelas Buku.java
            case 2:
                return lb.get(row).getPengarang();
            case 3:
                return lb.get(row).getPenerbit();
            case 4:
                return lb.get(row).getTahun_terbit();
            case 5:
                return lb.get(row).getKategori();
            case 6:
                return lb.get(row).getStok();
            default:
                return null;
        }
    }
}