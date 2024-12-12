package view;

import Core.Helper;
import business.UserController;
import entity.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginUI  extends  JFrame
{
    private JPanel container;
    private JPanel pnl_top;
    private JLabel lbl_title;
    private JPanel pnl_bottom;
    private JTextField fld_mail;
    private JButton btn_login;
    private JLabel lbl_mail;
    private JLabel lbl_password;
    private JPasswordField flp_password;
    private UserController userController;

    public LoginUI()
    {
        //UI özellikleri
        this.userController = new UserController();
        this.add(container);
        this.setTitle("Flavorite");     //Sayfa Başlığı
        this.setSize(400,400); //Sayfa boyutu.

        //Ekran boyutunu almak oluşturduğumuz değişkenler.
        //Bu sayede program başlangıçta ekranın tam ortasında başlayacaktır.
        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getSize().width) / 2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getSize().height) / 2;
        this.setLocation(x,y);
        this.setVisible(true);


        //Giriş yap butonu.
        //Lambda Expression kullanılmıştır.
        this.btn_login.addActionListener(e ->
        {
            JTextField[] checkList = {this.flp_password, this.fld_mail};

            if (!Helper.isEmailValid(this.fld_mail.getText()))
            {
               Helper.showMessage("Geçerli Bir E-Posta Giriniz !");
            }
            else if (Helper.isFieldListEmpty(checkList))
            {
                Helper.showMessage("fill");
            }
            else
            {
                //Kullanıcı kayıtlı mı diye kontrol işlemi. Kayıtlı ise DashBoardUI'a yönlendirme.
                User user = this.userController.findByLogin(this.fld_mail.getText(), this.flp_password.getText());
                if (user == null )
                {
                    Helper.showMessage("Girdiğiniz Bilgilere Uygun Kullanıcı Bulunamamıştır!");
                }
                else
                {
                    this.dispose();
                    DashBoardUI DashBoardUI = new DashBoardUI(user);
                }
            }
        });
    }


}
