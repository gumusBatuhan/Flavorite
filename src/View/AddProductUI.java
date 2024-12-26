package View;

import Business.ProductController;
import Entity.Product;
import Helpers.FieldEmptyHelper;
import Helpers.MessageHelper;

import javax.swing.*;
import java.awt.*;

public class AddProductUI extends JFrame {
    private JPanel container;
    private JLabel lbl_title;
    private JLabel lbl_name;
    private JTextField fld_productName;
    private JLabel lbl_type;
    private JComboBox<Product.CATEGORY> cmb_Producttype;
    private JLabel lbl_materials;
    private JTextPane pane_Materials;
    private JLabel lbl_preparation;
    private JTextPane pane_preparation;
    private JButton kaydetButton;
    private JScrollPane scrl_preparation;
    private JScrollPane scrl_materials;
    private Product product;
    private ProductController productController;


    public AddProductUI(Product product){
        this.product = product;
        this.productController = new ProductController();

        // Sayfanın Tema Özellikleri
        this.add(container);
        this.setTitle("Ürün Ekle/Düzenle");
        this.setSize(600, 500);
        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getSize().width) / 2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getSize().height) / 2;
        this.setLocation(x, y);
        this.setVisible(true);

        //ScrollPane'lerin Özellikleri
        scrl_materials.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrl_preparation.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        //Kategorileri ComboBox'a ekleme işlemi
        this.cmb_Producttype.setModel(new DefaultComboBoxModel<>(Product.CATEGORY.values()));

        if (this.product.getId() == 0) {
            this.lbl_title.setText("Ürün Ekle");
        }else {
            this.lbl_title.setText("Ürün Düzenle");
            this.fld_productName.setText(this.product.getName());
            this.pane_Materials.setText(this.product.getMaterials());
            this.pane_preparation.setText(this.product.getPreparation());
            this.cmb_Producttype.setSelectedItem(this.product.getCategory());
        }

        //Tüm alanların dolu olduğundan emin olduktan sonra girilen yeni ürünün bilgileri DB'ye kaydedilir.
        kaydetButton.addActionListener(e -> {
            JTextField[] checkListName = {this.fld_productName};
            if (FieldEmptyHelper.isFieldListEmpty(checkListName)) {
                MessageHelper.showMessage("fill");
            } else {
                boolean result;
                this.product.setName(this.fld_productName.getText());
                this.product.setMaterials(this.pane_Materials.getText());
                this.product.setPreparation(this.pane_preparation.getText());
                this.product.setCategory((Product.CATEGORY) this.cmb_Producttype.getSelectedItem());

                if (this.product.getId() == 0) {
                    result = this.productController.saveProduct(this.product);
                } else {
                    result = this.productController.updateProduct(this.product); // Eğer düzenleme yapılacaksa update metodu çalışacaktır
                }

                if (result) {
                    MessageHelper.showMessage("done");
                    dispose();
                } else {
                    MessageHelper.showMessage("error");
                }
            }
        });
    }
}