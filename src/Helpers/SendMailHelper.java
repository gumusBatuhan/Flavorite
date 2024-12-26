package Helpers;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import jakarta.mail.internet.MimeBodyPart;

import java.util.Properties;

public class SendMailHelper {

    public static void sendEmail(String toEmail, String subject, String body) {
        String host = "smtp.gmail.com";
        String fromEmail = "flavoriteapp@gmail.com"; // Giden e-posta adresiniz
        String password = "ybyo ikzk mngb fnrx"; // E-posta şifreniz

        //SMTP server ayarları (Yalnızca Gmail için)
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");

        //Session oluşturma
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            // E-posta mesajı oluşturma
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject(subject);

            // E-posta içeriğini HTML olarak set etme
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setContent(body, "text/html; charset=utf-8");

            // Multipart içeriği ekle
            MimeMultipart multipart = new MimeMultipart();
            multipart.addBodyPart(textPart);

            message.setContent(multipart);

            // E-posta gönderme
            Transport.send(message);

            System.out.println("E-posta başarıyla gönderildi.");
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("E-posta gönderilemedi.");
        }
    }
}
