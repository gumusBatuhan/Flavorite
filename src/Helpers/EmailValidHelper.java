package Helpers;

public class EmailValidHelper {
    //E-mail'in validation kontrollerini yapan Helper.
    public static boolean isEmailValid(String mail) {
        if (mail == null || mail.trim().isEmpty()) return false;
        String emailRegex = "^[\\w-\\.]+@[\\w-\\.]+\\.[a-zA-Z]{2,}$";
        return mail.matches(emailRegex);
    }
}
