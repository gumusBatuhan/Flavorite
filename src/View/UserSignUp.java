package View;

import Business.UserController;
import DAO.UserDao;
import Entity.User;
import Helpers.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class UserSignUp extends JFrame{
    private JLabel lbl_title;
    private JLabel lbl_userName;
    private JTextField fld_userName;
    private JLabel lbl_userSurName;
    private JTextField fld_userSurName;
    private JLabel lbl_userMail;
    private JTextField fld_userMail;
    private JLabel lbl_userPassWord;
    private JPasswordField fld_userPassWord;
    private JLabel lbl_userGender;
    private JComboBox<User.GENDER> cmb_userGender;
    private JButton btn_userSave;
    private JPanel pnl_top;
    private JPanel pnl_bottom;
    private User user;
    private JPanel container;
    private JButton btn_userBackPage;
    private JLabel lbl_userPasswordStrengt;
    private UserController userController;


    public UserSignUp(){

        this.userController = new UserController();

        this.add(this.container);
        this.setTitle("Flavorite");
        this.setSize(400, 500);
        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getSize().width) / 2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getSize().height) / 2;
        this.setLocation(x, y);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Cinsiyetlerin ComboBox'a yerleştirilmesi
        this.cmb_userGender.setModel(new DefaultComboBoxModel<>(User.GENDER.values()));

        //Kullanıcıya doğrulama kodu gönderme
        //SendCodeWithMail sendCodeWithMail = new SendCodeWithMail();


        //Kayıt ol geri dön butonu
        userTurnBackButton();
        //Şifre gücü için okuyucu
        userPasswordReading();
        //Kullanıcı kaydetme işlemi
        saveNewUser();

    }

    //User kayıt ol sayfası geri dön butonu işlemleri
    public void userTurnBackButton(){
        btn_userBackPage.addActionListener(e -> {
            dispose();
            WelcomeUI welcomeUI = new WelcomeUI();
        });
    }

    //Şifre güç kontrolünü kullanıcıya bildirme
    private void sendPasswordStrength() {
        String password = new String(fld_userPassWord.getPassword());
        String strength = PasswordStrength.checkPasswordStrength(password);
        lbl_userPasswordStrengt.setText("Şifre Gücü: " + strength);
        switch (strength) {
            case "Zayıf":
                lbl_userPasswordStrengt.setForeground(Color.RED);
                break;
            case "Orta":
                lbl_userPasswordStrengt.setForeground(Color.ORANGE);
                break;
            case "Güçlü":
                lbl_userPasswordStrengt.setForeground(Color.GREEN);
                break;
        }
    }

    //Şifre gücü kontrolü için okuyucu
    private void userPasswordReading(){
        fld_userPassWord.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                sendPasswordStrength();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                sendPasswordStrength();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                sendPasswordStrength();
            }
        });
    }


    private void saveNewUser() {
        btn_userSave.addActionListener(e -> {
            String name = fld_userName.getText();
            String surName = fld_userSurName.getText();
            String email = fld_userMail.getText();
            String password = new String(fld_userPassWord.getPassword());
            User.GENDER gender = User.GENDER.valueOf(cmb_userGender.getSelectedItem().toString());

            JTextField[] fields = {fld_userName, fld_userSurName, fld_userMail};

            if (UserInputValidator.validateUserInput(name, surName, email, fld_userPassWord, fields)) {
                UserDao userDao = new UserDao();
                User existingUser = userDao.getUserByEmail(email);

                if (existingUser != null) {
                    MessageHelper.showMessage("Bu e-posta adresi ile daha önce kayıt olunmuş.");
                } else {
                    // 📧 Doğrulama işlemi
                    boolean isVerified = VerificationCode.sendVerificationCodeAndVerify(email, name, surName);

                    if (isVerified) {
                        // Kullanıcı nesnesini oluştur
                        User newUser = new User();
                        newUser.setName(name);
                        newUser.setSurName(surName);
                        newUser.setMail(email);
                        newUser.setPassWord(password);
                        newUser.setGender(gender);

                        // Kullanıcıyı kaydet
                        if (userController.saveUser(newUser)) {
                            MailBodyHelper.sendWelcomeEmail(email, name, surName);
                            dispose();
                            new WelcomeUI();
                        }
                    } else {
                        MessageHelper.showMessage("Kayıt işleminiz iptal edildi.");
                    }
                }
            }
        });
    }

}
