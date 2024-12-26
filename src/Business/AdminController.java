package Business;

import DAO.AdminDao;
import Entity.Admin;
import Helpers.EmailValidHelper;

public class AdminController {
    private final AdminDao adminDao = new AdminDao();

    public Admin findByLoginAdmin(String mail, String passWord) {
        if (!EmailValidHelper.isEmailValid(mail)) {
            return null;
        }
        return this.adminDao.findByLoginAdmin(mail.trim(), passWord);
    }


}
