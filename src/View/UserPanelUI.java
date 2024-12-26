package View;

import Business.ProductController;
import Business.UserController;
import Entity.Product;
import Entity.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class UserPanelUI extends JFrame {
    private User user;
    private JPanel container;
    private JTextField textField1;
    private JButton araButton;
    private JComboBox comboBox1;
    private JButton temizleButton;
    private JTable table1;
    private JButton çıkışYapButton;
    private JButton profilButton;
    private JLabel lbl_titleUser;
    private JLabel lbl_message;


    private ProductController productController;
    private UserController userController;

    private DefaultTableModel productTableModel = new DefaultTableModel();

    public UserPanelUI(User user){
        this.user = user;
        this.productController = new ProductController();
        this.userController = new UserController();
        this.add(container);
        this.setTitle("Flavorite");
        this.setSize(1000, 500);
        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getSize().width) / 2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getSize().height) / 2;
        this.setLocation(x, y);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.comboBox1.setModel(new DefaultComboBoxModel<>(Product.CATEGORY.values()));

        //Filtremele işlemi
        loadProductTable(productController.findAll());

        setUserLabel();

        productResetFilter();

        filterButton();

        profileButtonOnClick();

        userExitButton();

        mouseClick();

    }

    //Çıkış Yap butonu
    private void userExitButton(){
        çıkışYapButton.addActionListener(e -> {
            dispose();
            WelcomeUI welcomeUI = new WelcomeUI();
        });
    }

    //Profilim Sayfası Butonu
    private void profileButtonOnClick(){
        profilButton.addActionListener(e -> {
            userProfile userProfile = new userProfile(user);
        });

    }

    //Filtre temizleme butonu
    private void productResetFilter(){
        temizleButton.addActionListener(e -> {
            this.comboBox1.setSelectedItem(null);
            loadProductTable(productController.findAll());
        });
    }

    //Ürün filtreleme butonu
    private void filterButton(){
        araButton.addActionListener(e -> {
            ArrayList<Product> filteredProducts = this.productController.filter(
                    this.textField1.getText(),
                    (Product.CATEGORY) this.comboBox1.getSelectedItem()
            );
            //Filtreleme işleminden sonra tablonun yeni halinin arayüzde gösterilmesi
            loadProductTable(filteredProducts);
        });
    }

    //Ürünleri ekrana yazdırma işlemi
    private void loadProductTable(ArrayList<Product> products) {
        Object[] columnProduct = {"ID", "Yemek Adı", "Kategori", "Malzemeler", "Hazırlanışı"};
        if (products == null) {
            products = this.productController.findAll();
        }

        //Tablo sıfırlama işlemi
        DefaultTableModel clearModel = (DefaultTableModel) this.table1.getModel();
        clearModel.setRowCount(0);

        this.productTableModel.setColumnIdentifiers(columnProduct);
        if (products != null) {
            for (Product product : products) {
                Object[] rowObject = {
                        product.getId(),
                        product.getName(),
                        product.getCategory(),
                        product.getMaterials(),
                        product.getPreparation()
                };
                this.productTableModel.addRow(rowObject);
            }
        }
        this.table1.setModel(productTableModel);
        this.table1.getTableHeader().setReorderingAllowed(false);
        this.table1.getColumnModel().getColumn(0).setMaxWidth(50);
        this.table1.setEnabled(false);
    }

    //Kullanıcı adını ekrana yazdırma işlemi
    private void setUserLabel() {
        String userName = userController.getUserNameByEmail(user.getMail());
        if (userName != null) {
            lbl_titleUser.setText("Hoşgeldin, " + userName);
        } else {
            lbl_titleUser.setText("Hoş Geldiniz!");
        }
    }

    //MouseListener ile çift tıklama ile ürünün detay sayfasını açıyoruz.
    private void mouseClick() {
        this.table1.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table1.rowAtPoint(evt.getPoint()); //Tıklanan satırın indexi
                if (row != -1) {
                    table1.setRowSelectionInterval(row, row); //Satırı seçili hale getir
                    if (evt.getClickCount() == 2) { //Çift tıklama kontrolü
                        int productId = (Integer) table1.getValueAt(row, 0); //ID al
                        Product product = productController.getProductById(productId); //Ürünü getir

                        //Ürün detaylarını içeren yeni bir pencere aç
                        if (product != null) {
                            new ProductDetailsUI(product);
                        }
                    }
                }
            }
        });
    }


}
