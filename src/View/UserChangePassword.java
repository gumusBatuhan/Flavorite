package View;

import Business.UserController;
import Entity.User;
import DAO.UserDao;
import Helpers.MessageHelper;

import javax.swing.*;
import java.awt.*;

public class UserChangePassword extends JFrame {
    private JPanel container;
    private JLabel lbl_title;
    private JTextField textField1;
    private JTextField textField2;
    private JButton changeButton;
    private UserController UserController;
    private User user;




    public UserChangePassword(User user){
        this.UserController = new UserController();
        this.user=user;

        this.add(container);
        this.setTitle("Kullanıcı Paneli");
        this.setSize(600, 300);
        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getSize().width) / 2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getSize().height) / 2;
        this.setLocation(x, y);
        this.setVisible(true);
        changeButtonClicOn();


    }
    private void changeButtonClicOn(){
        changeButton.addActionListener(e -> {

            String newPassword = textField1.getText();
            String confirmPassword = textField2.getText();

            if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Şifre alanları boş olamaz!", "Hata", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!newPassword.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(null, "Şifreler uyuşmuyor!", "Hata", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                // Kullanıcı ID'sine göre şifreyi güncelle
                boolean isUpdated = UserController.updatePassword(user.getId(), newPassword);
                if (isUpdated) {
                    JOptionPane.showMessageDialog(null, "Şifre başarıyla güncellendi!", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
                    dispose();


                } else {
                    JOptionPane.showMessageDialog(null, "Şifre güncellenemedi. Tekrar deneyin.", "Hata", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Bir hata oluştu: " + ex.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
            }
        });

    }


}
