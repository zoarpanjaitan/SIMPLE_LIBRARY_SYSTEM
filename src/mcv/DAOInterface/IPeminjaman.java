package mcv.DAOInterface;

import java.util.List;
import mvc.Model.Peminjaman;

public interface IPeminjaman 
{
    // Method insert untuk mencatat peminjaman baru
    public void insert(Peminjaman p);

    // Method update untuk proses pengembalian buku
    public void update(Peminjaman p);

    // Method delete untuk menghapus riwayat peminjaman
    public void delete(int idPeminjaman);

    // Method untuk mencari peminjaman berdasarkan keyword 
    public List<Peminjaman> search(String keyword);

    // Method untuk mendapatkan satu objek Peminjaman berdasarkan ID
    public Peminjaman getById(int id);
}