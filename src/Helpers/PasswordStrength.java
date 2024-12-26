package Helpers;

public class PasswordStrength {
    public static String checkPasswordStrength(String password) {
        int strengthPoints = 0;

        //Uzunluk kontrolü
        if (password.length() >= 8) {
            strengthPoints++;
        }

        //Büyük harf kontrolü
        if (password.matches(".*[A-Z].*")) {
            strengthPoints++;
        }

        //Küçük harf kontrolü
        if (password.matches(".*[a-z].*")) {
            strengthPoints++;
        }

        //Rakam kontrolü
        if (password.matches(".*[0-9].*")) {
            strengthPoints++;
        }

        //Özel karakter kontrolü
        if (password.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
            strengthPoints++;
        }

        //Şifre gücünü belirleme
        if (strengthPoints <= 2) {
            return "Zayıf";
        } else if (strengthPoints == 3 || strengthPoints == 4) {
            return "Orta";
        } else {
            return "Güçlü";
        }
    }
}