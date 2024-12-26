package Helpers;

import javax.swing.*;
import java.awt.*;

public class MessageHelper {

    private static ImageIcon resizedIcon; // Özel ikon tanımı

    // Statik blok: Logo yüklenir ve boyutlandırılır
    static {
        try {
            ImageIcon originalIcon = new ImageIcon("C:\\Users\\batug\\Desktop\\NormalÖğretimGörsel\\FlavoriteV2\\src\\Assets\\menu.png"); // Logo dosya yolu
            Image img = originalIcon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH); // Boyutlandırma
            resizedIcon = new ImageIcon(img);
        } catch (Exception e) {
            System.err.println("Logo yüklenirken hata oluştu: " + e.getMessage());
            resizedIcon = null;
        }
    }

    // Özelleştirilmiş Mesajlar
    public static void showMessage(String message) {
        optionPaneDialogTR();
        String msg;
        String title;

        switch (message) {
            case "fill":
                msg = "Lütfen Tüm Alanları Doldurunuz!";
                title = "BOŞ!";
                break;
            case "done":
                msg = "İşlem Başarıyla Gerçekleşmiştir!";
                title = "SONUÇ!";
                break;
            case "error":
                msg = "Bir Hata Meydana Geldi!";
                title = "HATA!";
                break;
            default:
                msg = message;
                title = "MESAJ";
        }

        JOptionPane.showMessageDialog(
                null,
                msg,
                title,
                JOptionPane.INFORMATION_MESSAGE,
                resizedIcon // Özel ikon eklendi
        );
    }

    // Mesaj butonlarını Türkçeleştir
    public static void optionPaneDialogTR() {
        UIManager.put("OptionPane.okButtonText", "Tamam");
        UIManager.put("OptionPane.cancelButtonText", "İptal");
        UIManager.put("OptionPane.yesButtonText", "Evet");
        UIManager.put("OptionPane.noButtonText", "Hayır");
    }

    // Onay mesajları (YES/NO) butonlarıyla
    public static boolean askConfirmation(String message, String title) {
        optionPaneDialogTR();
        String[] options = {"Evet", "Hayır"};

        int result = JOptionPane.showOptionDialog(
                null,
                message,
                title,
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                resizedIcon, // Özel ikon eklendi
                options,
                options[0]
        );

        return result == 0; // "Evet" seçilirse true döner
    }

    // Manuel captcha doğrulama
    public static String showVerificationDialog(String verificationCode) {
        optionPaneDialogTR();

        JTextField fldVerificationCode = new JTextField();
        Object[] message = {
                "Doğrulama Kodu: " + verificationCode,
                "Lütfen doğrulama kodunu giriniz:",
                fldVerificationCode
        };

        int option = JOptionPane.showConfirmDialog(
                null,
                message,
                "Doğrulama Kodu",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                resizedIcon // Özel ikon eklendi
        );

        if (option == JOptionPane.OK_OPTION) {
            return fldVerificationCode.getText();
        } else {
            return null; // Kullanıcı iptal ettiyse null döndür
        }
    }
}