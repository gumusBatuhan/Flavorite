import Core.DB_connect;
import Core.Helper;
import view.LoginUI;
import java.sql.Connection;


public class App
{
    public static void main(String[] args)
    {
        Helper.setLayout(); //Temayı çağırma işlemi
        Connection connect1 = DB_connect.getInstance(); //Database ile bağlantı oluşturma işlemi
        LoginUI loginUI = new LoginUI(); //Başlangıç
    }
}
