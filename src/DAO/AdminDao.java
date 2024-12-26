package DAO;

import Entity.Admin;
import Helpers.DbConnectionHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AdminDao {

    private Connection connection;

    //DB Bağlantısı oluşturuluyor.
    public AdminDao() {
        this.connection = DbConnectionHelper.getInstance();
    }

    public Admin findByLoginAdmin(String mail, String passWord) {
        Admin admin = null;
        String query = "SELECT * FROM admin WHERE mail = ? AND passWord = ?";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setString(1, mail);
            pr.setString(2, passWord);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                admin = this.match(rs);
            } else{

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return admin;
    }

    //Var olan bütün Adminleri bulma işlemi.
    public ArrayList<Admin> findAllAdmin() {
        ArrayList<Admin> admin = new ArrayList();
        try {
            ResultSet rs = this.connection.createStatement().executeQuery("SELECT * FROM admin");

            while(rs.next()) {
                admin.add(this.match(rs));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return admin;
    }

    //Database'deki verilerle eşitleme işlemi.
    public Admin match(ResultSet rs) throws SQLException {
        Admin admin = new Admin();
        admin.setId(rs.getInt("id"));
        admin.setMail(rs.getString("mail"));
        admin.setPassWord(rs.getString("passWord"));
        return admin;
    }

}
