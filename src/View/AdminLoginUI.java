package View;

import Business.AdminController;
import Entity.Admin;
import Helpers.EmailValidHelper;
import Helpers.FieldEmptyHelper;
import Helpers.MessageHelper;

import javax.swing.*;
import java.awt.*;

public class AdminLoginUI extends JFrame {
    private JPanel container;
    private JPanel pnl_top;
    private JLabel lbl_title;
    private JPanel pln_bottom;
    private JTextField fld_mail;
    private JButton btn_login;
    private JPasswordField fld_password;
    private JLabel lbl_mail;
    private JLabel lbl_password;
    private JButton btn_AdminBack;
    private AdminController adminController;

    public AdminLoginUI()
    {
        this.adminController = new AdminController();
        //Sayfanın Tema Özellikleri
        this.add(this.container);
        this.setTitle("Flavorite");
        this.setSize(400, 400);
        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getSize().width) / 2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getSize().height) / 2;
        this.setLocation(x, y);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Admin Giriş Yap Butonu
        adminLogInButton();
        //Admin Geri Dön butonu
        adminTurnBackButton();

    }

    //Admin Giriş Yap butonu işlemleri
    private void adminLogInButton(){
        //Giriş yap butonu
        btn_login.addActionListener(e -> {
            //login alanlarını burada ki checklistte tutarak dolu olup olmadıkları kontrolünü yapıyoruz.
            JTextField[] checkList = {this.fld_mail, this.fld_password};
            String email = this.fld_mail.getText().trim();
            String password = new String(fld_password.getPassword());
            if (!EmailValidHelper.isEmailValid(email)) {
                MessageHelper.showMessage("Lütfen Geçerli Bir E-Posta Adresi Giriniz");
            } else if (FieldEmptyHelper.isFieldListEmpty(checkList)) {
                MessageHelper.showMessage("fill");
            } else {
                Admin admin = this.adminController.findByLoginAdmin(email, password);
                if (admin == null) {
                    MessageHelper.showMessage("Böyle Bir Admin Bulunamadı.");
                } else {
                    this.dispose();
                    AdminPanelUI adminPanelUI = new AdminPanelUI(admin);
                }
            }
        });
    }

    //Admin Geri Dön butonu işlemleri
    private void adminTurnBackButton(){
        btn_AdminBack.addActionListener(e -> {
            dispose();
            WelcomeUI welcomeUI = new WelcomeUI();
        });
    }


}
