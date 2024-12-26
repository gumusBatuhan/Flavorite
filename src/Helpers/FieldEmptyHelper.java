package Helpers;

import javax.swing.*;

public class FieldEmptyHelper {

    // Tek bir JTextField boş mu kontrolü
    public static boolean isFieldEmpty(JTextField field) {
        return field.getText().trim().isEmpty();
    }

    // Bir JTextField listesi boş mu kontrolü
    public static boolean isFieldListEmpty(JTextField[] fieldList) {
        for (JTextField field : fieldList) {
            if (isFieldEmpty(field)) return true;
        }
        return false;
    }

    // Şifre alanı boş mu kontrolü
    public static boolean isPasswordEmpty(JPasswordField passwordField) {
        return new String(passwordField.getPassword()).trim().isEmpty();
    }

}
