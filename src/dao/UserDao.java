package dao;

import Core.DB_connect;
import entity.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDao
{
    private Connection connect;

    //Constructor ile database ile bağlantı sağlanıyor.
    public UserDao()
    {
        this.connect = DB_connect.getInstance();
    }

    //Kullanıcı database'de var mı kontrol işlemi
    public User findByLogin(String mail, String password)
    {
        User user = null;
        String query = "SELECT * FROM user WHERE mail = ? AND password = ?";
        try
        {
            PreparedStatement pr = this.connect.prepareStatement(query);
            pr.setString(1, mail);
            pr.setString(2,password);
            //Gelen sonucu executeQuery ile çalıştır ResultSet'a ata.
            ResultSet rs = pr.executeQuery();
            //rs boş değilse ilk elemanı döndür.
            if (rs.next())
            {
                user = this.match(rs);
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        return user;
    }

    //Kayıtlı Bütün Kullanıcıları Bulmamıza Yaramaktadır.
    public ArrayList<User> findAll()
    {
        ArrayList<User> users = new ArrayList<>();
        try
        {
            ResultSet rs = this.connect.createStatement().executeQuery("SELECT * FROM user");
            while(rs.next())
            {
                users.add(this.match(rs));
            }
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }
        return users;
    }

    //Gelen user verilerini database'de kayıtlı veriyle eşleme işlemi
    //İleriye dönük yeni bir DB kolonu eklendiğinde yalnızca burada değişiklik yapmak yeterli olacaktır.
    public User match(ResultSet rs) throws SQLException
    {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setName(rs.getString("name"));
        user.setSurName(rs.getString("surName"));
        user.setMail(rs.getString("mail"));
        user.setPassword(rs.getString("passWord"));
        return user;
    }

}
