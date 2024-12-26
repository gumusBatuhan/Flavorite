package Helpers;

import java.security.SecureRandom;

public class CodeHelper {

    public static String GenerateCode() {
        SecureRandom randomCode = new SecureRandom();
        StringBuilder code = new StringBuilder();

        //3 rakam ekle
        for (int i = 0; i < 3; i++) {
            code.append(randomCode.nextInt(10)); // 0-9 arasında bir rakam ekle
        }

        //3 harf ekle (büyük harf ve küçük harf)
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        for (int i = 0; i < 3; i++) {
            int randomIndex = randomCode.nextInt(characters.length());
            code.append(characters.charAt(randomIndex)); // Rastgele harf ekle
        }
        return code.toString();
    }
}
