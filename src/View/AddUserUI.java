package View;

import Business.UserController;
import Entity.Product;
import Entity.User;
import Helpers.EmailValidHelper;
import Helpers.FieldEmptyHelper;
import Helpers.MessageHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddUserUI extends JFrame {
    private JPanel container;
    private JLabel lbl_title;
    private JLabel lbl_name;
    private JTextField fld_userName;
    private JLabel lbl_surName;
    private JTextField fld_UserSurName;
    private JLabel lbl_mail;
    private JTextField fld_userMail;
    private JLabel lbl_passWord;
    private JTextField fld_UserPassWord;
    private JLabel lbl_gender;
    private JComboBox<User.GENDER> cmb_gender;
    private JButton btn_saveUser;
    private User user;
    private UserController userController;

    public AddUserUI(User user){
        this.user = user;
        this.userController = new UserController();

        // Sayfanın Tema Özellikleri
        this.add(container);
        this.setTitle("Kullanıcı Ekle/Düzenle");
        this.setSize(400, 500);
        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getSize().width) / 2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getSize().height) / 2;
        this.setLocation(x, y);
        this.setVisible(true);

        //Cinsiyetleri ComboBox'a ekleme işlemi
        this.cmb_gender.setModel(new DefaultComboBoxModel<>(User.GENDER.values()));

        //Kullanıcıyı eklerken veya düzenlerken UI'da çağıran metod
        initializeForm();

        //Kullanıcı kaydetme butonu
        saveUserButton();

    }

    //Kullanıcı düzenlenecek mi yoksa yeni mi oluşturulacak sorgusu
    private void initializeForm() {
        //Cinsiyetleri ComboBox'a ekleme işlemi
        this.cmb_gender.setModel(new DefaultComboBoxModel<>(User.GENDER.values()));

        //Kullanıcı var mı kontrol et, varsa düzenleme işlemi yapılacak
        if (this.user.getId() == 0) {
            this.lbl_title.setText("Kullanıcı Ekle");
        } else {
            this.lbl_title.setText("Kullanıcı Düzenle");
            this.fld_userName.setText(this.user.getName());
            this.fld_UserSurName.setText(this.user.getSurName());
            this.fld_userMail.setText(this.user.getMail());
            this.fld_UserPassWord.setText(this.user.getPassWord());
            this.cmb_gender.setSelectedItem(this.user.getGender());
        }
    }

    //Kullanıcıyı ekle butonu işlemleri
    private void saveUserButton(){
        btn_saveUser.addActionListener(e -> {
            JTextField[] checkListName = {this.fld_userName};
            String email = this.fld_userMail.getText().trim();
            if (FieldEmptyHelper.isFieldListEmpty(checkListName)) {
                MessageHelper.showMessage("fill");
            } else if (!EmailValidHelper.isEmailValid(email)) {
                MessageHelper.showMessage("Lütfen geçerli bir Mail adresi giriniz.");
            }
            else {
                boolean result;
                this.user.setName(this.fld_userName.getText());
                this.user.setSurName(this.fld_UserSurName.getText());
                this.user.setMail(this.fld_userMail.getText());
                this.user.setPassWord(this.fld_UserPassWord.getText());
                this.user.setGender((User.GENDER) this.cmb_gender.getSelectedItem());

                if (this.user.getId() == 0) {
                    result = this.userController.saveUser(this.user);
                } else {
                    result = this.userController.updateUser(this.user); // Eğer düzenleme yapılacaksa update metodu çalışacaktır
                }

                if (result) {
                    MessageHelper.showMessage("done");
                    dispose();
                } else {
                    MessageHelper.showMessage("error");
                }
            }
        });
    }
}
