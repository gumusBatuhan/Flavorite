package DAO;

import Entity.Product;
import Helpers.DbConnectionHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductDao {
    private Connection connection;
    public ProductDao(){this.connection = DbConnectionHelper.getInstance();}

    //Kayıtlı Bütün ürünleri bulma işlemi
    public ArrayList<Product> findAllProducts() {
        ArrayList<Product> products = new ArrayList();
        try {
            ResultSet rs = this.connection.createStatement().executeQuery("SELECT * FROM product");

            while(rs.next()) {
                products.add(this.matchProduct(rs));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return products;
    }

    //Kaydetme işlemi
    public boolean saveProduct(Product product){
        String query = "INSERT INTO product (name, materials, preparation, category) VALUES (?,?,?,?)";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setString(1, product.getName());
            pr.setString(2, product.getMaterials());
            pr.setString(3, product.getPreparation());
            pr.setString(4, product.getCategory().toString());
            return pr.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Var olan ürünlerin güncelleme işlemleri
    public boolean update(Product product){
        //String query = "UPDATE product (name, materials, preparation, category) VALUES (?,?,?,?)";
        String query = "UPDATE product SET name = ?, materials = ?, preparation = ?, category = ? WHERE id = ?";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setString(1, product.getName());
            pr.setString(2, product.getMaterials());
            pr.setString(3, product.getPreparation());
            pr.setString(4, product.getCategory().toString());
            pr.setInt(5, product.getId());
            return pr.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Var olan ürünleri silme işlemleri
    public boolean deleteProduct(int id){
        String query = "DELETE FROM product WHERE id = ?";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1,id);
            return pr.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Filtreleme işlemleri
    public ArrayList<Product> query(String query){
        //Boş bir arraylist oluşturuluyor.
        ArrayList<Product> products = new ArrayList<>();
        try {
            //Query dışarıdan alınıyor.
            ResultSet rs = rs = this.connection.createStatement().executeQuery(query);
            while (rs.next()){
                products.add(this.matchProduct(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return products;
    }

    //Database'deki verilerle eşitleme işlemi.
    public Product matchProduct(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setId(rs.getInt("id"));
        product.setName(rs.getString("name"));
        product.setMaterials(rs.getString("materials"));
        product.setPreparation(rs.getString("preparation"));
        product.setCategory(Product.CATEGORY.valueOf(rs.getString("category")));
        return product;
    }

    //Kayıtlı ürünleri id ile bulma
    public Product getProductById(int id){
        Product product = null;
        String query = "SELECT * FROM product WHERE id = ?";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1,id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()){
                product = this.matchProduct(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return product;
    }
}

