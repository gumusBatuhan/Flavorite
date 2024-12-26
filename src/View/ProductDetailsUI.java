package View;

import Entity.Product;

import javax.swing.*;
import java.awt.*;

public class ProductDetailsUI extends JFrame {

    private JPanel container;
    private JLabel lbl_prdDName;
    private JLabel lbl_prdDCategory;
    private JTextArea txt_prdDMaterials;
    private JTextArea txt_prdDPreparation;
    private JButton btn_close;
    private JLabel lbl_photo;

    public ProductDetailsUI(Product product) {
        // Pencereyi oluşturuyoruz
        this.setTitle("Ürün Detayları");
        this.setSize(600, 400);
        this.setLayout(new BorderLayout());
        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getSize().width) / 2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getSize().height) / 2;
        this.setLocation(x, y);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        lbl_photo.setVisible(true);

        // Panel oluştur
        container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Ürün Adı
        lbl_prdDName = new JLabel("Yemek Adı: " + product.getName());
        lbl_prdDName.setFont(new Font("Arial", Font.BOLD, 18));

        // Kategori
        lbl_prdDCategory = new JLabel("Kategori: " + product.getCategory());
        lbl_prdDCategory.setFont(new Font("Arial", Font.PLAIN, 14));

        // Malzemeler
        txt_prdDMaterials = new JTextArea("Malzemeler:\n" + product.getMaterials());
        txt_prdDMaterials.setLineWrap(true);
        txt_prdDMaterials.setWrapStyleWord(true);
        txt_prdDMaterials.setEditable(false);
        JScrollPane materialsScroll = new JScrollPane(txt_prdDMaterials);

        // Hazırlanışı
        txt_prdDPreparation = new JTextArea("Hazırlanışı:\n" + product.getPreparation());
        txt_prdDPreparation.setLineWrap(true);
        txt_prdDPreparation.setWrapStyleWord(true);
        txt_prdDPreparation.setEditable(false);
        JScrollPane preparationScroll = new JScrollPane(txt_prdDPreparation);

        // Kapat Butonu
        btn_close.addActionListener(e -> dispose());

        // Bileşenleri panele ekle
        container.add(lbl_prdDName);
        container.add(lbl_prdDCategory);
        container.add(new JLabel(" "));
        container.add(new JLabel("Malzemeler:"));
        container.add(materialsScroll);
        container.add(new JLabel(" "));
        container.add(new JLabel("Hazırlanışı:"));
        container.add(preparationScroll);
        container.add(new JLabel(" "));
        container.add(btn_close);

        this.add(container, BorderLayout.CENTER);
        this.setVisible(true);
    }
}