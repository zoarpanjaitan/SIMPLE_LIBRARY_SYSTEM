package mcv.DAOInterface;

import java.util.List;
import mvc.Model.Peminjaman;

public interface IPeminjaman 
{
    public void insert(Peminjaman p);
    public void update(Peminjaman p);
    public void delete(int idPeminjaman);
    public List<Peminjaman> search(String keyword);
    public Peminjaman getById(int id);
}