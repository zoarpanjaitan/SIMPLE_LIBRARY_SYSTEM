
package mvc.Controller;

import mvc.View.MenuUtama;
import mvc.View.FormInputBuku; 
import mvc.View.FormDaftarBuku; 
import mvc.View.FormPeminjaman; 
import mvc.View.FormDaftarPeminjaman;

public class ControllerMenuUtama 
{

    private MenuUtama frame;

    public ControllerMenuUtama(MenuUtama frame) {
        this.frame = frame;
    }

    public void openInputBukuForm() {
        FormInputBuku formBuku = new FormInputBuku();
        formBuku.setVisible(true);
        frame.dispose(); 
    }

    public void openDaftarBukuForm() {
        FormDaftarBuku formDaftarBuku = new FormDaftarBuku();
        formDaftarBuku.setVisible(true);
        frame.dispose();
    }

    public void openInputPeminjamanForm() {
        FormPeminjaman formPeminjaman = new FormPeminjaman();
        formPeminjaman.setVisible(true);
        frame.dispose();
    }

    public void openDaftarPeminjamanForm() {
        FormDaftarPeminjaman formDaftarPeminjaman = new FormDaftarPeminjaman();
        formDaftarPeminjaman.setVisible(true);
        frame.dispose(); 
    }
}