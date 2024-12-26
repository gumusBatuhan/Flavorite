package Helpers;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.intellijthemes.FlatArcDarkIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatArcDarkOrangeIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatSolarizedDarkIJTheme;

import javax.swing.*;

public class LayoutHelper
{

    //Eski Tema
    /*
    public static void setLayout() {
        for(UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if (info.getName().equals("Nimbus")) {
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                } catch (InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
     */


    //Temayı çektiğimiz helper
    public static void Theme(){

        try {
            FlatArcDarkOrangeIJTheme.setup();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
