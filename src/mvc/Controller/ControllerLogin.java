package mvc.Controller;

import javax.swing.JOptionPane;
import mvc.DAO.DAOAdmin;
import mcv.DAOInterface.IAdmin;
import mvc.Model.Admin;
import mvc.View.FormAdmin;
import mvc.View.FormDaftarBuku;
import mvc.View.MenuUtama;

public class ControllerLogin {
    FormAdmin frameLogin;
    IAdmin implAdmin;

    public ControllerLogin(FormAdmin frame) {
        this.frameLogin = frame;
        implAdmin = new DAOAdmin();
       
        frameLogin.getButtonLogin().addActionListener(e -> {
            login();
        });
    }

    public void login() {
        String email = frameLogin.getTxtEmail().getText();
        String password = new String(frameLogin.getTxtPassword().getPassword());

        
        if (email.trim().isEmpty() || password.trim().isEmpty()) {
            JOptionPane.showMessageDialog(frameLogin, "Email dan Password tidak boleh kosong!");
            return;
        }

        Admin admin = implAdmin.login(email, password);

        if (admin != null) 
        {
            JOptionPane.showMessageDialog(frameLogin, "Login Berhasil, Selamat Datang " + admin.getNama());
            MenuUtama menuUtama = new MenuUtama();
            menuUtama.setVisible(true);

            frameLogin.dispose();
        } else {
            JOptionPane.showMessageDialog(frameLogin, "Login Gagal, Email atau Password salah!");
        }
    }
}