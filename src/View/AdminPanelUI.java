package View;

import Business.ProductController;
import Business.UserController;
import Entity.Admin;
import Entity.Product;
import Entity.User;
import Helpers.MessageHelper;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class AdminPanelUI extends JFrame {
    private JPanel container;
    private JTabbedPane tab_menu;
    private JLabel lbl_welcome;
    private Admin admin;
    private JButton btn_logout;
    private JScrollPane scrl_recipes;
    private JTable tbl_products;
    private JPanel pnl_productFilter;
    private JTextField fld_name;
    private JComboBox<Product.CATEGORY> cmb_Type;
    private JButton btn_filter;
    private JButton btn_filterReset;
    private JButton btn_add;
    private JLabel lbl_filterName;
    private JLabel lbl_filterType;
    private JPanel pnl_user;
    private JPanel pnl_product;
    private JScrollPane scrl_user;
    private JTable tbl_user;
    private JPanel pnl_userFilter;
    private JTextField fld_userName;
    private JComboBox<User.GENDER> cmb_gender;
    private JButton btn_userFilter;
    private JButton btn_clearFilter;
    private JButton btn_userAdd;
    private JLabel lbl_userName;
    private JLabel lbl_userGender;
    private JPanel pnl_adminMain;
    private ProductController productController;
    private UserController userController;
    private DefaultTableModel productTableModel = new DefaultTableModel();
    private DefaultTableModel userTableModel = new DefaultTableModel();
    private JPopupMenu productPopup = new JPopupMenu();
    private JPopupMenu userPopup = new JPopupMenu();


    public AdminPanelUI(Admin admin) {
        this.admin = admin;
        this.productController = new ProductController();
        this.userController = new UserController();
        if (admin == null) {
            MessageHelper.showMessage("error");
            dispose();
            return;
        }

        //Sayfanın Tema Özellikleri
        this.add(container);
        this.setTitle("Admin Panel");
        this.setSize(1000, 500);
        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getSize().width) / 2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getSize().height) / 2;
        this.setLocation(x, y);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Product tablounu doldurma işlemi
        loadProductTable(productController.findAll());
        //Ürünler için popup ve tıklama eventleri
        loadProductPopup();
        //Yeni ürün ekleme işlemi
        addNewProduct();
        //Filtremele işlemi
        filterButton();
        //Product category'leri combobox'da listeleme işlemi.
        this.cmb_Type.setModel(new DefaultComboBoxModel<>(Product.CATEGORY.values()));
        //category box'ın ilk başta boş gelmesini sağlar.
        this.cmb_Type.setSelectedItem(null);
        //User Gender'ları combobox'da listeleme işlemi
        this.cmb_gender.setModel(new DefaultComboBoxModel<>(User.GENDER.values()));
        //Gender box'ın ilk başta bol görüntülenmesini sağlar
        this.cmb_gender.setSelectedItem(null);

        //User tablosunu doldurma işlemi
        loadUserTable(userController.findAllUsers());
        //User tablosu için popup ve tıklama eventleri
        loadUserPopup();
        //Yeni kullanıcı ekleme işlemi
        addNewUser();
        //Admin Çıkış Yap işlemi
        adminLogOut();
        //User filtreleme butonu
        userFilterButton();
        //User filtre temizleme butonu
        userResetFilter();
        //Product filtre temizleme butonu
        productResetFilter();

    }

    //Filtrelenmiş Product tablosunu sıfırlama
    private void productResetFilter(){
        btn_filterReset.addActionListener(e -> {
            this.cmb_Type.setSelectedItem(null);
            loadProductTable(productController.findAll());
        });
    }

    //Filtrelenmiş User tablosunu sıfırlama
    private void userResetFilter(){
        btn_clearFilter.addActionListener(e -> {
            this.cmb_gender.setSelectedItem(null);
            loadUserTable(userController.findAllUsers());
        });
    }

    //User filtreleme butonu
    private void userFilterButton(){
        btn_userFilter.addActionListener(e -> {
            ArrayList<User> filteredUsers = this.userController.userFilter(
                    this.fld_userName.getText(),
                    (User.GENDER) this.cmb_gender.getSelectedItem()
            );
            loadUserTable(filteredUsers);
        });
    }

    //Admin Çıkş yapma işlemi
    private void adminLogOut(){
        //Çıkış yap butonu
        btn_logout.addActionListener(e -> {
            dispose();
            AdminLoginUI adminLoginUI = new AdminLoginUI();
        });

    }

    //Kullanıcılar sayfası mouse sağ tık menüsü
    private void loadUserPopup() {
        // Popup menüsüne seçenekler ekleniyor
        JMenuItem updateUser = new JMenuItem("Güncelle");
        JMenuItem deleteUser = new JMenuItem("Sil");

        // Sağ tık güncelleme butonu
        updateUser.addActionListener(e -> {
            int selectedRow = tbl_user.getSelectedRow();
            if (selectedRow != -1) {
                //Seçilen satırdaki kullanıcı ID'sini al
                int userId = (int) tbl_user.getValueAt(selectedRow, 0);
                //Kullanıcıyı veritabanından çekmek için ID'yi kullan
                User selectedUser = this.userController.getUserById(userId);
                if (selectedUser != null) {
                    //Kullanıcıya onay penceresi göster
                    String message = "Bu kullanıcıyı güncellemek istediğinizden emin misiniz?\n" +
                            "Kullanıcı Adı: " + selectedUser.getName();
                    String title = "Güncelleme Onayı";
                    //Türkçeleşmiş mesajı çağırma işlemi
                    boolean confirmUpdate = MessageHelper.askConfirmation(message, title);

                    if (confirmUpdate) {
                        // AddUserUI'yi güncelleme işlemi için aç
                        AddUserUI addUserUI = new AddUserUI(selectedUser);
                        // WindowListener ile ekran kapandığı an güncellenmiş veriyi ekrana yazdır
                        addUserUI.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosed(WindowEvent e) {
                                loadUserTable(userController.findAllUsers());
                            }
                        });
                    }
                } else {
                    // Kullanıcı bulunamadıysa hata mesajı göster
                    MessageHelper.showMessage("Kullanıcı bulunamadı!");
                }
            }
        });

        //Sağ tık silme butonu
        deleteUser.addActionListener(e -> {
            int selectedRow = tbl_user.getSelectedRow();
            if (selectedRow != -1) {
                // Seçilen satırdaki kullanıcı ID'sini al
                int userId = (int) tbl_user.getValueAt(selectedRow, 0);
                // Kullanıcıyı veritabanından çekmek için ID'yi kullan
                User selectedUser = this.userController.getUserById(userId);
                if (selectedUser != null) {
                    // Kullanıcıya onay penceresini göster
                    String message = "Bu kullanıcıyı silmek istediğinizden emin misiniz?\n" +
                            "Kullanıcı Adı: " + selectedUser.getName();
                    String title = "Silme Onayı";
                    boolean confirmDelete = MessageHelper.askConfirmation(message, title);
                    if (confirmDelete) {
                        // Silme işlemi
                        if (this.userController.deleteUser(userId)) {
                            // İşlem başarılıysa mesaj göster
                            MessageHelper.showMessage("done");
                            // Tabloyu yeni haliyle güncelle
                            loadUserTable(userController.findAllUsers());
                        } else {
                            // İşlem başarısızsa hata mesajı göster
                            MessageHelper.showMessage("error");
                        }
                    }
                } else {
                    //Kullanıcı bulunamadıysa hata mesajı göster
                    MessageHelper.showMessage("Kullanıcı bulunamadı!");
                }
            }
        });

        userPopup.add(updateUser);
        userPopup.add(deleteUser);



        //Tabloya MouseListener ekleme işlemi
        tbl_user.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                //Sağ tıklama kontrolü
                if (e.isPopupTrigger()) {
                    int row = tbl_user.rowAtPoint(e.getPoint());
                    if (row != -1) {
                        tbl_user.setRowSelectionInterval(row, row); // Tıklanan satırı seç
                    }
                    userPopup.show(tbl_user, e.getX(), e.getY());
                }
                //Sol tıklama kontrolü
                else if (SwingUtilities.isLeftMouseButton(e)) {
                    int row = tbl_user.rowAtPoint(e.getPoint());
                    if (row != -1) {
                        tbl_user.setRowSelectionInterval(row, row); // Tıklanan satırı seç
                        // Satır bilgilerini konsola yazdır
                        System.out.println("Tıklanan Satır: " + row);
                        for (int col = 0; col < tbl_user.getColumnCount(); col++) {
                            System.out.print(tbl_user.getColumnName(col) + ": " + tbl_user.getValueAt(row, col) + " | ");
                        }
                        System.out.println();
                    }
                }
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                // Sağ tıklama kontrolü
                if (e.isPopupTrigger()) {
                    int row = tbl_user.rowAtPoint(e.getPoint());
                    if (row != -1) {
                        tbl_user.setRowSelectionInterval(row, row); // Tıklanan satırı seç
                    }
                    userPopup.show(tbl_user, e.getX(), e.getY());
                }
            }
        });
    }

    //Ürün Filtreleme butonu
    private void filterButton(){
        btn_filter.addActionListener(e -> {
            ArrayList<Product> filteredProducts = this.productController.filter(
                    this.fld_name.getText(),
                    (Product.CATEGORY) this.cmb_Type.getSelectedItem()
            );
            //Filtreleme işleminden sonra tablonun yeni halinin arayüzde gösterilmesi
            loadProductTable(filteredProducts);
        });
    }

    //Yeni ürün ekleme butonu işlemi
    private void addNewProduct(){
        btn_add.addActionListener(e -> {
            AddProductUI addProductUI = new AddProductUI(new Product());
            addProductUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadProductTable(productController.findAll());
                }
            });
        });
    }

    //Yeni kullanıcı ekleme işlemleri
    private void addNewUser(){
        btn_userAdd.addActionListener(e -> {
            AddUserUI addUserUI = new AddUserUI(new User());
            addUserUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadUserTable(userController.findAllUsers());
                }
            });
        });
    }

    //Ürün Sayfası mause Sağ tık menüsü
    private void loadProductPopup() {
        //Popup menüsüne seçenekler ekleniyor
        JMenuItem updateItem = new JMenuItem("Güncelle");
        JMenuItem deleteItem = new JMenuItem("Sil");

        //Sağ tık güncelleme butonu
        updateItem.addActionListener(e -> {
            int selectedRow = tbl_products.getSelectedRow();
            if (selectedRow != -1) {
                //Seçilen satırdaki ürün ID'sini al
                int productId = (int) tbl_products.getValueAt(selectedRow, 0);
                //Ürünü veritabanından çekmek için ID'yi kullan
                Product selectedProduct = this.productController.getProductById(productId);
                if (selectedProduct != null) {
                    //Kullanıcıya onay penceresi göster
                    String message = "Bu ürünü güncellemek istediğinizden emin misiniz?\n" +
                            "Ürün Adı: " + selectedProduct.getName();
                    String title = "Güncelleme Onayı";
                    //Türkçeleşmiş mesajı çağırma işlemi
                    boolean confirmUpdate = MessageHelper.askConfirmation(message, title);

                    if (confirmUpdate) {
                        //AddProductUI'yi güncelleme işlemi için aç
                        AddProductUI addProductUI = new AddProductUI(selectedProduct);
                        //WindowListener ile ekran kapandığı an güncellenmiş veriyi ekrana yazdır.
                        addProductUI.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosed(WindowEvent e) {
                                loadProductTable(productController.findAll());
                            }
                        });
                    }
                } else {
                    //Ürün bulunamadıysa hata mesajı göster
                    MessageHelper.showMessage("Ürün bulunamadı!");
                }
            }
        });

        //Sağ tık silme butonu
        deleteItem.addActionListener(e -> {
            int selectedRow = tbl_products.getSelectedRow();
            if (selectedRow != -1) {
                //Seçilen satırdaki ürün ID'sini al
                int productId = (int) tbl_products.getValueAt(selectedRow, 0);
                //Ürünü veritabanından çekmek için ID'yi kullan
                Product selectedProduct = this.productController.getProductById(productId);
                //Kullanıcıya onay penceresini göster
                if (selectedProduct != null) {
                    String message = "Bu ürünü silmek istediğinizden emin misiniz?\n" +
                            "Ürün Adı: " + selectedProduct.getName();
                    String title = "Silme Onayı";
                    //Türkçeleşmiş mesajı çağırma işlemi
                    boolean confirmDelete = MessageHelper.askConfirmation(message, title);
                    //
                    if (confirmDelete)
                    {
                        //Silme işlemi
                        if (this.productController.deleteProduct(productId)) {
                            //İşlem başarılıysa mesaj göster
                            MessageHelper.showMessage("done");
                            //Tabloyu yeni haliyle güncelle
                            loadProductTable(productController.findAll());
                        } else {
                            //İşlem başarısızsa hata mesajı göster
                            MessageHelper.showMessage("error");
                        }
                    }
                } else {
                    // Ürün bulunamadıysa hata mesajı göster
                    MessageHelper.showMessage("Ürün bulunamadı!");
                }
            }
        });

        productPopup.add(updateItem);
        productPopup.add(deleteItem);

        // Tabloya MouseListener ekleme işlemi
        tbl_products.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                //Sağ tıklama kontrolü
                if (e.isPopupTrigger()) {
                    int row = tbl_products.rowAtPoint(e.getPoint());
                    if (row != -1) {
                        tbl_products.setRowSelectionInterval(row, row); // Tıklanan satırı seç
                    }
                    productPopup.show(tbl_products, e.getX(), e.getY());
                }
                //Sol tıklama kontrolü
                else if (SwingUtilities.isLeftMouseButton(e)) {
                    int row = tbl_products.rowAtPoint(e.getPoint());
                    if (row != -1) {
                        tbl_products.setRowSelectionInterval(row, row); // Tıklanan satırı seç
                        // Satır bilgilerini konsola yazdır
                        System.out.println("Tıklanan Satır: " + row);
                        for (int col = 0; col < tbl_products.getColumnCount(); col++) {
                            System.out.print(tbl_products.getColumnName(col) + ": " + tbl_products.getValueAt(row, col) + " | ");
                        }
                        System.out.println();
                    }
                }
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                //Sağ tıklama kontrolü
                if (e.isPopupTrigger()) {
                    int row = tbl_products.rowAtPoint(e.getPoint());
                    if (row != -1) {
                        tbl_products.setRowSelectionInterval(row, row); // Tıklanan satırı seç
                    }
                    productPopup.show(tbl_products, e.getX(), e.getY());
                }
            }
        });
    }

    //Ürünleri tabloya ekleme işlemi
    private void loadProductTable(ArrayList<Product> products) {
        Object[] columnProduct = {"ID", "Yemek Adı", "Kategori", "Malzemeler", "Hazırlanışı"};
        if (products == null){
            products = this.productController.findAll();
        }

        //Tablo sıfırlama işlemi
        DefaultTableModel clearModel = (DefaultTableModel) this.tbl_products.getModel();
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
        //Ürünlerin gösterildiği tablonun özellikleri
        this.tbl_products.setModel(productTableModel);
        this.tbl_products.getTableHeader().setReorderingAllowed(false);
        this.tbl_products.getColumnModel().getColumn(0).setMaxWidth(50);
        this.tbl_products.setEnabled(false);
    }

    //Kullanıcıları tabloya ekleme işlemi
    private void loadUserTable(ArrayList<User> users) {
        Object[] columnUser = {"ID", "İsim", "Soyisim", "Cinsiyet", "Mail Adresi", "Şifre"};
        if (users == null){
            users = this.userController.findAllUsers();
        }

        //Tablo sıfırlama işlemi
        DefaultTableModel clearModel = (DefaultTableModel) this.tbl_user.getModel();
        clearModel.setRowCount(0);

        this.userTableModel.setColumnIdentifiers(columnUser);
        if (users != null) {
            for (User user : users) {
                Object[] rowObject = {
                        user.getId(),
                        user.getName(),
                        user.getSurName(),
                        user.getGender(),
                        user.getMail(),
                        user.getPassWord()
                };
                this.userTableModel.addRow(rowObject);
            }
        }
        //Ürünlerin gösterildiği tablonun özellikleri
        this.tbl_user.setModel(userTableModel);
        this.tbl_user.getTableHeader().setReorderingAllowed(false);
        this.tbl_user.getColumnModel().getColumn(0).setMaxWidth(50);
        this.tbl_user.setEnabled(false);
    }

}
