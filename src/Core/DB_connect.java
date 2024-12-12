package Core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB_connect
{
    //->Singleton Desing Pattern Kullanılmaktadır.

    //static ile proje çalışmaya başladığı zaman üretilip, beklemede kalacaklardır.
    private static DB_connect conn = null;
    private Connection connect = null;

    //->final'lar ile DB'nin sabitleri oluşturulmuştur.
    private final String DB_URL = "jdbc:mysql://localhost:3307/Flavorite";
    private final String DB_USERNAME = "root";
    private final String DB_PASSWORD = "";

    //->Databse Bağlanma İşlemi.
    //->Private olmasının sebebi herkes tarafından DB'ye erişim sağlanmasını istememekteyiz.
    private DB_connect()
    {
        try
        {
            connect = DriverManager.getConnection( DB_URL, DB_USERNAME, DB_PASSWORD);
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    //->Dışarıdan erişebilmek için getter oluşturulmuştur.

    private Connection getConnect()
    {
        return connect;
    }

    //->Database'in birden fazla kez oluşturulmasını engellemek ve bağlantı kesilirse tekrar oluşturmak için çalışacaktır.
    public static Connection getInstance()
    {
        try
        {
            if (conn == null || conn.getConnect().isClosed())
            {
                conn = new DB_connect();
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        return conn.getConnect();
    }

}
