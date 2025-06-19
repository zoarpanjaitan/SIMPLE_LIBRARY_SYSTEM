package mcv.DAOInterface;

import java.util.List;
import mvc.Model.Buku;

public interface IBuku {
    public void insert(Buku b);
    public void update(Buku b);
    public void delete(int id);
    public List<Buku> getAll();
    public List<Buku> getCariJudul(String judul);
}