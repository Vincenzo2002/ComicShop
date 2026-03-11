package model.dao;

import manager.ConPool;
import model.bean.Category;
import model.bean.Product;
import model.interf.IModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ProductDAO implements IModel<Product> {

    @Override
    public void doSave(Product product) throws SQLException {
        // Inserimento di un nuovo prodotto
        String sql;
        try (Connection con = ConPool.getConnection()) {
            if (product.getIdProduct() <= 0) {
                sql = "INSERT INTO Product (Name, Quantity, Price, Description" + (product.getUrlImage() != null ? ", Image_url" : "") + ") VALUES (?, ?, ?, ?" + (product.getUrlImage() != null ? ", ?" : "") + ")";

                try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, product.getName());
                    ps.setInt(2, product.getQuantity());
                    ps.setDouble(3, product.getPrice());
                    ps.setString(4, product.getDescription());
                    if (product.getUrlImage() != null) {
                        ps.setString(5, product.getUrlImage());
                    }

                    ps.executeUpdate();

                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            int id = rs.getInt(1);
                            product.setIdProduct(id);
                        }
                    }

                    String sql2 = "INSERT INTO CategoryProduct (ID_Category, ID_Product) " +
                            "VALUES ((SELECT ID_Category FROM Category WHERE Name = ?), ?)";
                    try (PreparedStatement ps1 = con.prepareStatement(sql2)) {
                        ps1.setString(1, product.getCategory());
                        ps1.setInt(2, product.getIdProduct());
                        ps1.executeUpdate();
                    }
                }
            } else {
                // Aggiornamento di un prodotto esistente
                String imageUrl = product.getUrlImage();
                String img = imageUrl != null ? ", Image_url = ?" : "";
                sql = "UPDATE Product SET Name = ?, Quantity = ?, Price = ?, Description = ?" +
                        img +
                        " WHERE ID_Product = ?";

                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setString(1, product.getName());
                    ps.setInt(2, product.getQuantity());
                    ps.setDouble(3, product.getPrice());
                    ps.setString(4, product.getDescription());
                    if (imageUrl != null) {
                        ps.setString(5, imageUrl);
                        ps.setInt(6, product.getIdProduct());
                    } else {
                        ps.setInt(5, product.getIdProduct());
                    }
                    ps.executeUpdate();

                    String query2 = "UPDATE CategoryProduct SET ID_Category = (SELECT ID_Category FROM Category WHERE Name = ?) WHERE ID_Product = ?";
                    try (PreparedStatement ps1 = con.prepareStatement(query2)) {
                        ps1.setString(1, product.getCategory());
                        ps1.setInt(2, product.getIdProduct());
                        ps1.executeUpdate();
                    }
                }
            }
        }
    }


    @Override
    public boolean doDelete(int idProduct) throws SQLException {
        //Elimina un prodotto
        String sql = "DELETE FROM Product WHERE ID_Product = ?";

        try(Connection con = ConPool.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)){

            ps.setInt(1, idProduct);
            int result = ps.executeUpdate();

            if(result == 1)
                return true;
            return false;
        }
    }

    @Override
    public Product doRetrieveByKey(int idProduct) throws SQLException {
        //Restituisce un prodotto sul id
        String sql = "SELECT * FROM Product WHERE ID_Product = ?";

        try(Connection con = ConPool.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)){

            ps.setInt(1, idProduct);

            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    Product product = new Product();
                    product.setIdProduct(rs.getInt("ID_Product"));
                    product.setName(rs.getString("Name"));
                    product.setQuantity(rs.getInt("Quantity"));
                    product.setUrlImage(rs.getString("Image_url"));
                    product.setPrice(rs.getDouble("Price"));
                    product.setDescription(rs.getString("Description"));
                    return product;
                }
            }
        }
        return null;
    }

    public List<Product> doRetrieveByCategory(String nameCategory, int skip, int limit) throws SQLException {
        //Restituisci i prodotti che fanno parte di una determinata categoria
        String sql = "SELECT p.* " +
                "FROM Product p " +
                "JOIN CategoryProduct cp ON p.ID_Product = cp.ID_Product " +
                "WHERE cp.ID_Category = ? " +
                "LIMIT ?, ?";

        List<Product> products = new ArrayList<>();
        Category category = new CategoryDAO().doRetrieveByName(nameCategory);

        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, category.getIdCategory());
            ps.setInt(2, skip);
            ps.setInt(3, limit);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Product product = new Product();
                    product.setIdProduct(rs.getInt("ID_Product"));
                    product.setName(rs.getString("Name"));
                    product.setQuantity(rs.getInt("Quantity"));
                    product.setUrlImage(rs.getString("Image_url"));
                    product.setPrice(rs.getDouble("Price"));
                    product.setDescription(rs.getString("Description"));
                    products.add(product);
                }
            }
        }
        return products;
    }



    @Override
    public Collection<Product> doRetrieveAll(String order) throws SQLException {
        //Restituisce tutti i prodotti presenti
        String sql = "SELECT * FROM Product";
        List<Product> products = new ArrayList<>();

        if(order != null && !order.equals("")){
            sql +=" ORDER BY " + order;
        }

        try(Connection con = ConPool.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){

                while(rs.next()){
                    Product product = new Product();
                    product.setIdProduct(rs.getInt("ID_Product"));
                    product.setName(rs.getString("Name"));
                    product.setQuantity(rs.getInt("Quantity"));
                    product.setUrlImage(rs.getString("Image_url"));
                    product.setPrice(rs.getDouble("Price"));
                    product.setDescription(rs.getString("Description"));
                    products.add(product);
                }
            }
        return products;
    }

    public Collection<Product> doRetrieveAll(String order, int skip, int limit) throws SQLException {
        //Restituisce tutti i prodotti, con skip e limit per la paginazione
        String sql = "SELECT * FROM Product";
        List<Product> products = new ArrayList<>();

        if(order != null && !order.equals("")){
            sql +=" ORDER BY " + order;
        }

        sql += " LIMIT ?, ?";

        try(Connection con = ConPool.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)){

            ps.setInt(2, skip);
            ps.setInt(3, limit);

            try(ResultSet rs = ps.executeQuery(sql)) {
                while (rs.next()) {
                    Product product = new Product();
                    product.setIdProduct(rs.getInt("ID_Product"));
                    product.setName(rs.getString("Name"));
                    product.setQuantity(rs.getInt("Quantity"));
                    product.setUrlImage(rs.getString("Image_url"));
                    product.setPrice(rs.getDouble("Price"));
                    product.setDescription(rs.getString("Description"));
                    products.add(product);
                }
            }
        }
        return products;
    }


    public Collection<Product> searchByName(String name) throws SQLException {
        //Ricerca prodotti in base al nome
        String sql = "SELECT * FROM Product WHERE Name LIKE ?";
        List<Product> products = new ArrayList<>();


        try(Connection con = ConPool.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)){

            ps.setString(1, name + "%");

            try(ResultSet rs = ps.executeQuery()){
                while (rs.next()){
                    Product product = new Product();
                    product.setIdProduct(rs.getInt("ID_Product"));
                    product.setName(rs.getString("Name"));
                    product.setQuantity(rs.getInt("Quantity"));
                    product.setUrlImage(rs.getString("Image_url"));
                    product.setPrice(rs.getDouble("Price"));
                    product.setDescription(rs.getString("Description"));
                    products.add(product);
                }
            }
        }
        return products;
    }

    public int getNumProductByCategory(String nameCategory) throws SQLException {
        //Restituisce il numero di prodotti di una categoria
        String sql = "SELECT COUNT(*) " +
                "FROM Product p " +
                "JOIN CategoryProduct cp ON p.ID_Product = cp.ID_Product " +
                "WHERE cp.ID_Category = ?";

        Category category = new CategoryDAO().doRetrieveByName(nameCategory);

        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, category.getIdCategory());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    public int getNumProduct() throws SQLException {
        //Restiruisce il numero totale dei prodotti
        String sql = "SELECT COUNT(*) FROM Product";

        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    public void decreaseQuantity(Product product, int quantity) throws SQLException {
        //Decrementa la quantita di un prodotto
        String sql = "UPDATE Product SET quantity = quantity - ? WHERE ID_Product = ?";

        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, quantity);
            ps.setInt(2, product.getIdProduct());

            ps.executeUpdate();
        }
    }

    public void incrementQuantity(Product product, int quantity) throws SQLException {
        //Incrementa la quantita di un prodotto
        String sql = "UPDATE Product SET quantity = quantity + ? WHERE ID_Product = ?";

        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, quantity);
            ps.setInt(2, product.getIdProduct());

            ps.executeUpdate();
        }
    }

    public String getCategoryProduct(Product product) throws SQLException {
        //Restituisce la gategoria di un prodotto
        String sql = "SELECT c.Name " +
                "FROM CategoryProduct cp " +
                "JOIN Product p ON cp.ID_Product = p.ID_Product " +
                "JOIN Category c ON c.ID_Category = cp.ID_Category " +
                "WHERE cp.ID_Product = ?";

        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, product.getIdProduct());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString(1);
                }
            }
        }
        return null;
    }

    public int getQuantityProduct(int idProduct) throws SQLException {
        //Restituisce la gategoria di un prodotto
        String sql = "SELECT Quantity FROM Product WHERE ID_Product = ?";

        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idProduct);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return -1;
    }

}
