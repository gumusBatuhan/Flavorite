package Business;

import DAO.ProductDao;
import Entity.Product;
import Helpers.MessageHelper;

import java.util.ArrayList;

public class ProductController {
    private final ProductDao productDao = new ProductDao();

    public ArrayList<Product> findAll(){
        return this.productDao.findAllProducts();
    }

    public boolean saveProduct(Product product){
        return this.productDao.saveProduct(product);
    }

    public Product getProductById(int id){
        return this.productDao.getProductById(id);
    }

    public boolean updateProduct(Product product){
        if (this.getProductById(product.getId()) == null){
            MessageHelper.showMessage(product.getId() + "ID'li Ürün Bulunamadı!");
            return false;
        }
        return this.productDao.update(product);
    }

    public boolean deleteProduct(int id){
        if (this.getProductById(id) == null){
            MessageHelper.showMessage(id + "ID'li Ürün Bulunamadı!");
            return false;
        }
        return this.productDao.deleteProduct(id);
    }

    //Filtreleme yaparak arama yapma işlemi
    public ArrayList<Product> filter(String name, Product.CATEGORY category){
        //Örnek queryler
        //SELECT * FROM product WHERE name = 'TEST' AND type = 'Aperatifler'
        //SELECT * FROM product WHERE name = 'TEST'
        //SELECT * FROM product WHERE type = 'Aperatifler'
        //SELECT * FROM product
        String query ="SELECT * FROM product";
        //Boş ArrayList
        ArrayList<String> whereList = new ArrayList<>();

        //İsimle arama
        if (name.length() > 0){
            whereList.add("name LIKE '%"+name+"%'");
        }
        //kategorilere göre arama
        if (category != null) {
            whereList.add("category = '"+category+"'");
        }

        if (whereList.size() > 0 ){
            //Queryleri joinleme işlemi
            String whereQuery = String.join(" AND ", whereList);
            query += " WHERE " + whereQuery;

        }
        return this.productDao.query(query);
    }

}
