package View;

import Business.UserController;
import Entity.User;
import Helpers.EmailValidHelper;
import Helpers.FieldEmptyHelper;
import Helpers.MessageHelper;

import javax.swing.*;
import java.awt.*;

public class UserLoginUI extends JFrame {
    private JPanel container;
    private JPanel pnl_top;
    private JPanel pnl_bottom;
    private JLabel lbl_userMail;
    private JTextField fld_userMail;
    private JLabel lbl_userPassword;
    private JPasswordField fld_userPassword;
    private JButton btn_login;
    private JButton btn_backPage;
    private UserController userController;


    public UserLoginUI(){
        this.userController = new UserController();

        this.add(this.container);
        this.setTitle("Flavorite");
        this.setSize(400, 400);
        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getSize().width) / 2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getSize().height) / 2;
        this.setLocation(x, y);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //User geri dön butonu
        userTurnBackButton();
        //User giriş yap butonu
        userLoginButton();

    }

    //User geri dön işlemleri
    public void userTurnBackButton(){
        btn_backPage.addActionListener(e -> {
            dispose();
            WelcomeUI welcomeUI = new WelcomeUI();
        });
    }

    //User giriş yapma işlemleri
    public void userLoginButton(){
        btn_login.addActionListener(e -> {
            //login alanlarını burada ki checklistte tutarak dolu olup olmadıkları kontrolünü yapıyoruz.
            JTextField[] checkList = {this.fld_userMail, this.fld_userPassword};
            String Usermail = fld_userMail.getText().trim();
            String userPassword = new String(fld_userPassword.getPassword());
            if (!EmailValidHelper.isEmailValid(Usermail)) {
                MessageHelper.showMessage("Lütfen Geçerli Bir E-Posta Adresi Giriniz");
            } else if (FieldEmptyHelper.isFieldListEmpty(checkList)) {
                MessageHelper.showMessage("fill");
            } else {
                User user = this.userController.findByLoginUser(Usermail, userPassword);
                if (user == null) {
                    MessageHelper.showMessage("Böyle Bir Kullanıcı Bulunamadı.");
                } else {
                    this.dispose();
                    UserPanelUI UserPanelUI = new UserPanelUI(user);

                }
            }
        });
    }

}
