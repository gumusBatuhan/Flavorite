package Business;

import DAO.UserDao;
import Entity.User;
import Helpers.EmailValidHelper;
import Helpers.MessageHelper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserController {
    private final UserDao userDao = new UserDao();

    //Bütün kullanıcıları bulma işlemleri
    public ArrayList<User> findAllUsers(){
        return this.userDao.findAllUsers();
    }

    //Giriş Yapan kullanıcıyı bulma işlemi
    public User findByLoginUser(String mail, String passWord){
        if (!EmailValidHelper.isEmailValid(mail)) {
            return null;
        }
        return this.userDao.findByLoginUser(mail.trim(), passWord);
    }

    //Kayıtlı kullanıcıları id ile bulma işlemleri
    public User getUserById(int id){
        return this.userDao.getUserById(id);
    }

    //Kullanıcı silme işlemleri
    public boolean deleteUser(int id){
        if (this.getUserById(id) == null){
            MessageHelper.showMessage(id + "ID'li Kullanıcı Bulunamadı!");
            return false;
        }
        return this.userDao.deleteUser(id);
    }

    //Kllanıcı kaydetme işlemleri
    public boolean saveUser(User user){
        return this.userDao.saveUser(user);
    }

    //Kullanıcı güncelleme işlemleri
    public boolean updateUser(User user){
        if (this.getUserById(user.getId()) == null){
            MessageHelper.showMessage(user.getId() + "ID'li Kullanıcı Bulunamadı!");
            return false;
        }
        return this.userDao.updateUser(user); //user.password diyecez yeni parametre yollayacağız yeni user nesnesini
    }

    //Şifre Değiştirme işlemleri
    public boolean updatePassword(int iD, String newPassword) {
        if (this.getUserById(iD) == null){
            MessageHelper.showMessage(iD + "ID'li Kullanıcı Bulunamadı!");
            return false;
        }
        return this.userDao.updatePassword(iD,newPassword);
    }

    //Filtreleme yaparak arama yapma işlemleri
    public ArrayList<User> userFilter(String name, User.GENDER gender){
        //Örnek queryler
        //SELECT * FROM user WHERE name = 'TEST' AND gender = 'TEST'
        //SELECT * FROM user WHERE name = 'TEST'
        //SELECT * FROM user WHERE gender = 'TEST'
        //SELECT * FROM user
        String query ="SELECT * FROM user";
        //Boş ArrayList
        ArrayList<String> whereList = new ArrayList<>();

        //İsimle arama
        if (name.length() > 0){
            whereList.add("name LIKE '%"+name+"%'");
        }
        //kategorilere göre arama
        if (gender != null) {
            whereList.add("gender = '"+gender+"'");
        }

        if (whereList.size() > 0 ){
            //Queryleri joinleme işlemi
            String whereQuery = String.join(" AND ", whereList);
            query += " WHERE " + whereQuery;

        }
        return this.userDao.query(query);
    }

    public User getUserByEmail(String mail) {
        return this.userDao.getUserByEmail(mail);
    }

    public String getUserNameByEmail(String mail) {
        return userDao.getUserNameByEmail(mail);
    }



}
