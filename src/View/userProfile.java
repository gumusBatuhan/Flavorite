package View;

import Business.UserController;
import Entity.User;

import javax.swing.*;
import java.awt.*;

public class userProfile extends JFrame {
    private JPanel container;
    private JTextField userName1;
    private JTextField userSurname1;
    private JTextField userMail1;
    private JTextField userPassword1;
    private JButton passwordChangeButton;
    private UserController UserController;
    private User user;


    public userProfile(User user){
        this.UserController = new UserController();
        this.user=user;

        this.add(container);
        this.setTitle("Kullanıcı Profili");
        this.setSize(600, 400);
        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getSize().width) / 2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getSize().height) / 2;
        this.setLocation(x, y);
        this.setVisible(true);
        this.userName1.setText(user.getName());
        this.userSurname1.setText(user.getSurName());
        this.userMail1.setText(user.getMail());
        this.userPassword1.setText(user.getPassWord());

        paswordButtonClick();


    }

    private void paswordButtonClick(){
        passwordChangeButton.addActionListener(e -> {
            UserChangePassword UserChangePassword = new UserChangePassword(user);
        });

    }

}
