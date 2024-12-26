package Helpers;

import Entity.User;
import javax.swing.*;

public class UserInputValidator {

    public static boolean validateUserInput(String name, String surName, String email, JPasswordField passwordField, JTextField[] fields) {
        // Alanların boş olup olmadığını kontrol et
        if (FieldEmptyHelper.isFieldListEmpty(fields)) {
            MessageHelper.showMessage("Lütfen tüm alanları doldurun.");
            return false;
        }

        // Şifre boş mu kontrol et
        if (FieldEmptyHelper.isPasswordEmpty(passwordField)) {
            MessageHelper.showMessage("Şifre alanı boş bırakılamaz.");
            return false;
        }

        // E-posta geçerli mi kontrol et
        if (!EmailValidHelper.isEmailValid(email)) {
            MessageHelper.showMessage("Geçersiz e-posta adresi!");
            return false;
        }

        return true;
    }
}

