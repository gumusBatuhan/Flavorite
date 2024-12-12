package business;

import Core.Helper;
import dao.UserDao;
import entity.User;

public class UserController
{
    //Erişim verme işlemi.
    private final UserDao userDao = new UserDao();

    //Kayıtlı User bulma işlemi.
    public User findByLogin(String mail, String password)
    {
        //E-Mail check, Güvenlik açısından tekrar kontrol ediliyor.
        if (!Helper.isEmailValid(mail)) return null;
        return this.userDao.findByLogin(mail, password);
    }

}
