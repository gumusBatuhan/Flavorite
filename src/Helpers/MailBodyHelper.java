package Helpers;

public class MailBodyHelper {
    public String verificationCode;

    // 1. Doğrulama kodu gönderme
    public void sendVerificationCode(String userEmail, String name, String surName) {
        this.verificationCode = CodeHelper.GenerateCode();

        String subject = "Flavorite - E-posta Doğrulama Kodu";
        String body = String.format("""
    <html>
        <head>
            <style>
                body {
                    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                    margin: 0;
                    padding: 0;
                    background-color: #f9fafc;
                }
                .container {
                    width: 100%%;
                    max-width: 600px;
                    margin: 20px auto;
                    background-color: #ffffff;
                    border-radius: 10px;
                    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                    overflow: hidden;
                }
                .header {
                    background-color: #ff7f50;
                    text-align: center;
                    padding: 30px;
                }
                .header img {
                    width: 120px;
                }
                .header h1 {
                    color: #ffffff;
                    margin: 10px 0;
                }
                .content {
                    padding: 30px;
                    text-align: center;
                    color: #333;
                }
                .content p {
                    margin: 10px 0;
                    font-size: 16px;
                }
                .code {
                    display: inline-block;
                    background: #ff7f50;
                    color: #ffffff;
                    font-weight: bold;
                    font-size: 24px;
                    padding: 10px 20px;
                    margin: 20px 0;
                    border-radius: 5px;
                }
                .footer {
                    background-color: #ff7f50;
                    text-align: center;
                    color: #ffffff;
                    padding: 10px;
                    font-size: 12px;
                }
            </style>
        </head>
        <body>
            <div class="container">
                <div class="header">
                    <img src="https://i.imgur.com/sLDWZXn.png" alt="Flavorite Logo">
                    <h1>Flavorite</h1>
                </div>
                <div class="content">
                    <p>Merhaba <strong>%s %s</strong>,</p>
                    <p>Hesabınızı doğrulamak için aşağıdaki kodu kullanabilirsiniz:</p>
                    <p class="code">%s</p>
                    <p>Bu kod 2 dakika boyunca geçerlidir.</p>
                    <p>Teşekkür ederiz!</p>
                </div>
                <div class="footer">
                    &copy; 2024 Flavorite. Tüm hakları saklıdır.
                </div>
            </div>
        </body>
    </html>
""", name, surName, this.verificationCode);

        SendMailHelper.sendEmail(userEmail, subject, body);
        System.out.println("Doğrulama kodu gönderildi: " + this.verificationCode);
    }

    public String getVerificationCode() {
        return this.verificationCode;
    }

    // 2. Hoş Geldiniz E-Posta Tasarımı
    public static void sendWelcomeEmail(String userEmail, String name, String surName) {
        String subject = "Flavorite Uygulamasına Hoş Geldiniz!";
        String body = String.format("""
    <html>
        <head>
            <style>
                body {
                    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                    margin: 0;
                    padding: 0;
                    background-color: #f9fafc;
                }
                .container {
                    width: 100%%;
                    max-width: 600px;
                    margin: 20px auto;
                    background-color: #ffffff;
                    border-radius: 10px;
                    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                    overflow: hidden;
                }
                .header {
                    background-color: #4caf50;
                    text-align: center;
                    padding: 30px;
                }
                .header img {
                    width: 120px;
                }
                .header h1 {
                    color: #ffffff;
                    margin: 10px 0;
                }
                .content {
                    padding: 30px;
                    text-align: center;
                    color: #333;
                }
                .content p {
                    margin: 10px 0;
                    font-size: 16px;
                }
                .footer {
                    background-color: #4caf50;
                    text-align: center;
                    color: #ffffff;
                    padding: 10px;
                    font-size: 12px;
                }
            </style>
        </head>
        <body>
            <div class="container">
                <div class="header">
                    <img src="https://i.imgur.com/sLDWZXn.png" alt="Flavorite Logo">
                    <h1>Flavorite</h1>
                </div>
                <div class="content">
                    <p>Merhaba <strong>%s %s</strong>,</p>
                    <p>Kayıt işleminiz başarıyla tamamlandı!</p>
                    <p>Artık Flavorite'ın sunduğu tüm özelliklerden yararlanabilirsiniz.</p>
                </div>
                <div class="footer">
                    &copy; 2024 Flavorite. Tüm hakları saklıdır.
                </div>
            </div>
        </body>
    </html>
""", name, surName);
        SendMailHelper.sendEmail(userEmail, subject, body);
    }
}
