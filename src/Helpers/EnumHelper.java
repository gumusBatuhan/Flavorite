package Helpers;

public class EnumHelper {

    //DB'de ki enumları JComboBox içerisinde daha düzgün görünmesine yardım eden helper
    public static String formatEnumName(String enumName) {
        return enumName.replaceAll("([a-z])([A-Z])", "$1 $2") // Küçük harf ile büyük harf arasına boşluk ekler
                .replace("yemeği", " Yemeği")
                .replace("işi", " İşi");
    }
}
