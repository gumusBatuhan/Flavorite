import Business.AdminController;
import Entity.Admin;
import Helpers.DbConnectionHelper;
import Helpers.LayoutHelper;
import View.AdminPanelUI;
import View.WelcomeUI;

import java.sql.Connection;

public class App
{
    public static void main(String[] args) {
        LayoutHelper.Theme();
        Connection connect = DbConnectionHelper.getInstance();
        WelcomeUI welcomeUI = new WelcomeUI();
        //AdminLoginUI adminLoginUI = new AdminLoginUI();
        /*
        AdminController adminController = new AdminController();
        Admin admin = adminController.findByLoginAdmin("flavoriteapp@gmail.com", "flavorite123");
        AdminPanelUI adminPanelUI = new AdminPanelUI(admin);

         */
    }
}