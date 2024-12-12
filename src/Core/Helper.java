package Core;

import javax.swing.*;


public class Helper
{
    //Tema kullanımı için oluşturulan helper.
    public static void setLayout()
    {
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
        {
            if (info.getName().equals("Nimbus"))
            {
                try
                {
                    UIManager.setLookAndFeel(info.getClassName());
                }
                catch(ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e)
                {
                    throw new RuntimeException(e);
                }
                break;
            }
        }
    }

    //Boşluklarıda kapsayacak şekilde text alanlarının dolu olup olmadığı kontrolünü yapan helper.
    public static boolean isFieldEmpty(JTextField field)
    {
        return field.getText().trim().isEmpty();
    }

    //Textleri liste olarak alıp dolu olup olmadığı kontrolünü yapan helper.
    public static boolean isFieldListEmpty(JTextField[] fields)
    {
        for (JTextField field : fields)
        {
            if (isFieldEmpty(field)) return true;
        }
        return false;
    }

    //E-Mail formatını kontrol eden helper.
    public static boolean isEmailValid(String mail)
    {
        if (mail == null || mail.trim().isEmpty()) return false; //E-mail boşsa false döner.

        if (!mail.contains("@")) return false; //@ işareti yoksa false döner.

        String[] parts = mail.split("@"); //@ işaretinden itibaren E-maili ikiye döler.

        if (parts.length!=2) return false; //@ işaretinden sonra 2'ye bölünmüyorsa false döner.

        if (parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) return false; //@ işaretiyle bölündükten sonra her hangi bir parça boş işe false döner.

        if (!parts[1].contains(".")) return false; //@ işaretinden sonra "." işareti yoksa false döner.

        return true;
    }

    //Hata Mesajını Kullanıcıya Yazdıran helper.
    public static void showMessage(String message)
    {
        optionPaneDialogTR();
        String msg;
        String title = switch (message)
        {
            case "fill" ->
            {
                msg = "Lütfen Tüm Alanları Doldurunuz !";
                yield "HATA!";
            }
            case "done" ->
            {
                msg = "İşlem Başarıyla Gerçekleşmiştir !";
                yield "SONUÇ";
            }
            case "error" ->
            {
                msg = "Bir Hata Meydana Geldi !";
                yield "HATA!";
            }
            default ->
            {
                msg = message;
                yield "MESAJ";
            }
        };

        JOptionPane.showMessageDialog(null, msg, title, JOptionPane.INFORMATION_MESSAGE);
    }

    //Pop-up'larda ki "OK" butonunu "Tamam" olarak değiştirme.
    public static void optionPaneDialogTR()
    {
        UIManager.put("OptionPane.okButtonText", "Tamam");
    }



}
