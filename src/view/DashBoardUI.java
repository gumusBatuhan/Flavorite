package view;

import Core.Helper;
import entity.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DashBoardUI extends JFrame
{
    private JPanel container;
    private JPanel pnl_top;
    private JLabel lbl_welcome;
    private JButton btn_logout;
    private User user;

    public DashBoardUI(User user)
    {
        this.user = user;
        if (user == null)
        {
            Helper.showMessage("error");
            dispose();
        }

        this.add(container);
        this.setTitle("Flavorite");
        this.setSize(1000,500);
        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getSize().width) / 2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getSize().height) / 2;
        this.setLocation(x,y);
        this.setVisible(true);
        System.out.println(this.user.toString());
        this.lbl_welcome.setText("Hoşgeldin " + this.user.getName());

        //Çıkış Yap butonu
        btn_logout.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                dispose();
                LoginUI loginUI = new LoginUI();
            }
        });
    }
}
