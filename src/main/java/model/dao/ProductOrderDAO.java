package model.dao;

import manager.ConPool;
import model.bean.Order;
import model.bean.Product;
import model.bean.ProductOrder;
import model.interf.IModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ProductOrderDAO implements IModel<ProductOrder> {

    @Override
    public void doSave(ProductOrder productOrder) throws SQLException {
        //Memorizza un nuovo prodotto di un ordine
        String sql = "INSERT INTO ProductOrder (Quantity, Price, ID_Order, ID_Product) VALUES(?, ?, ?, ?)";

        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, productOrder.getQuantity());
            ps.setDouble(2, productOrder.getPrice());
            ps.setInt(3, productOrder.getIdOrder());
            ps.setInt(4, productOrder.getProduct().getIdProduct());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    productOrder.setIdProductOrder(id);
                }
            }
        }

    }

    @Override
    public boolean doDelete(int objectId) throws SQLException {
        return false;
    }

    @Override
    public ProductOrder doRetrieveByKey(int idProductOrder) throws SQLException {
        //Restituisce un prodotto ordninato in base al suo id
        String sql = "SELECT * FROM ProductOrder WHERE ID_ProductOrder = ?";

        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)){

            ps.setInt(1, idProductOrder);

            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    Product product = new ProductDAO().doRetrieveByKey(rs.getInt("ID_Product"));
                    ProductOrder productOrder = new ProductOrder();
                    productOrder.setIdProductOrder(rs.getInt("ID_ProductOrder"));
                    productOrder.setQuantity(rs.getInt("Quantity"));
                    productOrder.setPrice(rs.getDouble("Price"));
                    productOrder.setIdOrder(rs.getInt("ID_Order"));
                    productOrder.setProduct(product);
                    return productOrder;
                }
            }
        }
        return null;
    }

    @Override
    public Collection<ProductOrder> doRetrieveAll(String order) throws SQLException {
        //Restituisce tutti i prodotti ordinati
        String sql = "SELECT * FROM ProductOrder ";
        List<ProductOrder> productOrders = new ArrayList<>();

        if(order != null && !order.equals("")){
            sql +=" ORDER BY " + order;
        }

        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()){

                while(rs.next()){
                    Product product = new ProductDAO().doRetrieveByKey(rs.getInt("ID_Product"));
                    ProductOrder productOrder = new ProductOrder();
                    productOrder.setIdProductOrder(rs.getInt("ID_ProductOrder"));
                    productOrder.setQuantity(rs.getInt("Quantity"));
                    productOrder.setPrice(rs.getDouble("Price"));
                    productOrder.setIdOrder(rs.getInt("ID_Order"));
                    productOrder.setProduct(product);
                    productOrders.add(productOrder);
            }
        }
        return productOrders;
    }

    public ArrayList<ProductOrder> doRetrieveByOrder(Order order) throws SQLException {
        //Restituisce tutti i prodotti di un ordine
        String sql = "SELECT * FROM ProductOrder WHERE ID_Order = ?";
        ArrayList<ProductOrder> productOrders = new ArrayList<>();

        try(Connection con = ConPool.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)){

            ps.setInt(1, order.getIdOrder());

            try(ResultSet rs = ps.executeQuery()){
                while (rs.next()){
                    Product product = new ProductDAO().doRetrieveByKey(rs.getInt("ID_Product"));
                    ProductOrder productOrder = new ProductOrder();
                    productOrder.setIdProductOrder(rs.getInt("ID_ProductOrder"));
                    productOrder.setQuantity(rs.getInt("Quantity"));
                    productOrder.setPrice(rs.getDouble("Price"));
                    productOrder.setIdOrder(rs.getInt("ID_Order"));
                    productOrder.setProduct(product);
                    productOrders.add(productOrder);
                }
            }
        }
        return productOrders;
    }


}
