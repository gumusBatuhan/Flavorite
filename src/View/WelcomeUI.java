package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomeUI extends JFrame {
        private JPanel container;
    private JPanel pnl_bottom;
    private JButton btn_adminLogin;
    private JPanel pnl_title;
    private JLabel lbl_title;
    private JButton btn_login;
    private JButton btn_register;

    public WelcomeUI() {

        //Sayfanın Tema Özellikleri
        this.add(this.container);
        this.setTitle("Flavorite");
        this.setSize(400, 400);
        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getSize().width) / 2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getSize().height) / 2;
        this.setLocation(x, y);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Admin Giriş Yap sayfası
        adminLoginUIButton();
        //User Giriş Yap sayfası
        userLoginUIButton();
        //User Kayıt ol sayfası
        userSignUpButton();


    }

    public void adminLoginUIButton(){
        btn_adminLogin.addActionListener(e -> {
            dispose();
            AdminLoginUI adminLoginUI = new AdminLoginUI();
        });
    }

    public void userLoginUIButton(){
        btn_login.addActionListener(e -> {
            dispose();
            UserLoginUI userLoginUI = new UserLoginUI();
        });
    }

    public void userSignUpButton(){
        btn_register.addActionListener(e -> {
            dispose();
            UserSignUp userSignUp = new UserSignUp();
        });
    }

}

