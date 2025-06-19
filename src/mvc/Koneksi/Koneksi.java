/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mvc.Koneksi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Koneksi 
{
    private static Connection koneksi; // Objek koneksi static

    public static Connection getKoneksi() {
        try {
            // Periksa jika koneksi belum ada ATAU koneksi sudah ditutup/tidak valid
            if (koneksi == null || koneksi.isClosed()) { // Tambahkan .isClosed()
                String url = "jdbc:mysql://localhost:3306/library";
                String user = "root";
                String pass = "";
                koneksi = DriverManager.getConnection(url, user, pass);
                System.out.println("Koneksi Berhasil");
            }
        } catch (SQLException e) {
            System.out.println("Koneksi gagal: " + e.getMessage());
            koneksi = null; // Set koneksi jadi null jika gagal agar bisa dicoba lagi
        }
        return koneksi;
    }
}
