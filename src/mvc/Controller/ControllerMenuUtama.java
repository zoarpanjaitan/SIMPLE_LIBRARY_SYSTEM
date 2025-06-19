/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
        frame.dispose(); // Opsional: tutup menu utama jika ingin menggantinya
    }

    public void openDaftarBukuForm() {
        FormDaftarBuku formDaftarBuku = new FormDaftarBuku();
        formDaftarBuku.setVisible(true);
        frame.dispose(); // Opsional
    }

    public void openInputPeminjamanForm() {
        // Ini membuka FormPeminjaman yang hanya untuk input ID Buku, Nama, No HP
        FormPeminjaman formPeminjaman = new FormPeminjaman();
        formPeminjaman.setVisible(true);
        frame.dispose(); // Opsional
    }

    public void openDaftarPeminjamanForm() {
        FormDaftarPeminjaman formDaftarPeminjaman = new FormDaftarPeminjaman();
        formDaftarPeminjaman.setVisible(true);
        frame.dispose(); // Opsional
    }
}