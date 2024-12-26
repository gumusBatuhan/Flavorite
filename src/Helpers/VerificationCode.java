package Helpers;

public class VerificationCode {

    // Doğrulama kodu gönderme ve doğrulama işlemi (3 deneme hakkı)
    public static boolean sendVerificationCodeAndVerify(String email, String name, String surName) {
        MailBodyHelper mailBodyHelper = new MailBodyHelper();
        mailBodyHelper.sendVerificationCode(email, name, surName);

        int attempts = 0; // Deneme sayacı
        final int MAX_ATTEMPTS = 3; // Maksimum deneme hakkı

        while (attempts < MAX_ATTEMPTS) {
            String userInputCode = MessageHelper.showVerificationDialog(
                    "E-posta adresinize gönderilen doğrulama kodunu giriniz:\n" +
                            "(Kalan deneme hakkı: " + (MAX_ATTEMPTS - attempts) + ")"
            );

            if (userInputCode == null) {
                return false;
            }

            if (userInputCode.equals(mailBodyHelper.getVerificationCode())) {
                MessageHelper.showMessage("Doğrulama başarılı! Kayıt işleminiz başarıyla gerçekleştirilmiştir.");
                return true; // Doğrulama başarılıysa işlem devam eder
            } else {
                attempts++;
                MessageHelper.showMessage("Girdiğiniz doğrulama kodu yanlış. Lütfen tekrar deneyin.");
            }
        }

        return false; // Doğrulama başarısız oldu
    }
}