package DAO;

import Entity.Admin;
import Entity.Product;
import Entity.User;
import Helpers.DbConnectionHelper;
import Helpers.MessageHelper;
import Helpers.MailBodyHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDao {
    //Database Bağlantı işlemi
    private Connection connection;
    public UserDao(){this.connection = DbConnectionHelper.getInstance();}

    //Kayıtlı Bütün kullanıcıları bulma işlemi
    public ArrayList<User> findAllUsers() {
        ArrayList<User> users = new ArrayList();
        try {
            ResultSet rs = this.connection.createStatement().executeQuery("SELECT * FROM user");

            while(rs.next()) {
                users.add(this.matchUser(rs));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return users;
    }

    //Kayıtlı kullanıcıları email ve şifre ile bulma işlemi
    public User findByLoginUser(String mail, String passWord) {
        User user = null;
        String query = "SELECT * FROM user WHERE mail = ? AND passWord = ?";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setString(1, mail);
            pr.setString(2, passWord);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                user = this.matchUser(rs);
            } else{

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    //Database'deki verilerle eşitleme işlemi.
    public User matchUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setName(rs.getString("name"));
        user.setSurName(rs.getString("surName"));
        user.setMail(rs.getString("mail"));
        user.setPassWord(rs.getString("passWord"));
        user.setGender(User.GENDER.valueOf(rs.getString("gender")));
        return user;
    }

    //Var olan kullanıcıları silme işlemleri
    public boolean deleteUser(int id){
        String query = "DELETE FROM user WHERE id = ?";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1,id);
            return pr.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Var olan kullanıcıları güncelleme işlemleri
    public boolean updateUser(User user){
        String query = "UPDATE user SET name = ?, surName = ?, mail = ?, passWord = ?, gender = ? WHERE id = ?";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setString(1, user.getName());
            pr.setString(2, user.getSurName());
            pr.setString(3, user.getMail());
            pr.setString(4, user.getPassWord());
            pr.setString(5, user.getGender().toString());
            pr.setInt(6, user.getId());
            return pr.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Kullanıcı kaydetme işlemi
    public boolean saveUser(User user){
        if (this.findByLoginUser(user.getMail(), user.getPassWord()) != null) {
            MessageHelper.showMessage("Bu kullanıcı zaten mevcut!");
            return false;
        }else {
            String query = "INSERT INTO user (name, surName, mail, passWord, gender) VALUES (?,?,?,?,?)";
            try {
                PreparedStatement pr = this.connection.prepareStatement(query);
                pr.setString(1, user.getName());
                pr.setString(2, user.getSurName());
                pr.setString(3, user.getMail());
                pr.setString(4, user.getPassWord());
                pr.setString(5, user.getGender().toString());
                return pr.executeUpdate() > 0;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    //Kayıtlı kullanıcıları id ile bulma
    public User getUserById(int id){
        User user = null;
        String query = "SELECT * FROM user WHERE id = ?";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1,id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()){
                user = this.matchUser(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    //Kullanıcıyı e-posta adresiyle veritabanında arama
    public User getUserByEmail(String mail) {
        User user = null;
        String query = "SELECT * FROM user WHERE mail = ?";
        try (PreparedStatement pr = this.connection.prepareStatement(query)) {
            pr.setString(1, mail);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                user = this.matchUser(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    //Filtreleme işlemleri
    public ArrayList<User> query(String query){
        //Boş bir arraylist oluşturuluyor.
        ArrayList<User> users = new ArrayList<>();
        try {
            //Query dışarıdan alınıyor.
            ResultSet rs = rs = this.connection.createStatement().executeQuery(query);
            while (rs.next()){
                users.add(this.matchUser(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    //Şifre değiştirme işlemi
    public boolean updatePassword(int iD,String newPassword){
        String query = "UPDATE user SET passWord = ? WHERE id = ?";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setString(1, newPassword);
            pr.setInt(2, iD);
            return pr.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Kullanıcı adını mail üzerinden alma işlemi
    public String getUserNameByEmail(String mail) {
        String userName = null;
        String query = "SELECT name FROM user WHERE mail = ?";
        try (PreparedStatement pr = this.connection.prepareStatement(query)) {
            pr.setString(1, mail);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                userName = rs.getString("name");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Kullanıcı adı alınırken bir hata oluştu!", e);
        }
        return userName;
    }



}
