package model.dao;

import manager.ConPool;
import model.bean.Order;
import model.bean.User;
import model.interf.IModel;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class OrderDAO implements IModel<Order> {

    @Override
    public void doSave(Order order) throws SQLException {
        //Salva un nuovo ordine
        String sql = "INSERT INTO `Order` (ID_User, Date, State) VALUES(?, ?, ?)";

        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, order.getIdUser());
            ps.setDate(2, Date.valueOf(LocalDate.now()));
            ps.setString(3, order.getState());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    order.setIdUser(id);
                }
            }
        }
    }

    public int doSaveAndGetId(Order order) throws SQLException {
        //Salva un nuovo ordine e restituisce id (Creata perche mi serviva ID nella servlet checkout)
        String sql = "INSERT INTO `Order` (ID_User, Date, State) VALUES(?, ?, ?)";

        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, order.getIdUser());
            ps.setDate(2, Date.valueOf(LocalDate.now()));
            ps.setString(3, order.getState());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    order.setIdUser(id);
                    return id;
                }
            }
        }
        return -1;
    }

    @Override
    public boolean doDelete(int objectId) throws SQLException {
        return false;
    }

    @Override
    public Order doRetrieveByKey(int idOrder) throws SQLException {
        //Seleziona ordine in base all id
        String sql = "SELECT * FROM `Order` WHERE ID_Order = ?";

        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idOrder);

            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    Order order = new Order();
                    order.setIdOrder(rs.getInt("ID_Order"));
                    order.setIdUser(rs.getInt("ID_User"));
                    order.setDateOrder(new Date(rs.getTimestamp(3).getTime()));
                    order.setState(rs.getString("State"));
                    return order;
                }
            }
        }
        return null;
    }


    @Override
    public Collection<Order> doRetrieveAll(String order) throws SQLException {
        //Restituisce tutti gli ordini
        String sql = "SELECT * FROM `Order`";
        List<Order> orders = new ArrayList<>();

        if(order != null && !order.equals("")){
            sql +=" ORDER BY " + order;
        }

        try(Connection con = ConPool.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){

            while (rs.next()) {
                Order order1 = new Order();
                order1.setIdOrder(rs.getInt("ID_Order"));
                order1.setIdUser(rs.getInt("ID_User"));
                order1.setDateOrder(new Date(rs.getTimestamp(3).getTime()));
                order1.setState(rs.getString("State"));
                orders.add(order1);
            }
        }
        return orders;
    }

    public Collection<Order> doRetrieveAll(String order, int skip, int limit) throws SQLException {
        //Restituisce tutti gli ordini, utilizzando skip e limit per la paginazione
        String sql = "SELECT * FROM `Order`";
        List<Order> orders = new ArrayList<>();

        if(order != null && !order.equals("")){
            sql +=" ORDER BY " + order;
        }

        sql += " LIMIT ?, ?";

        try(Connection con = ConPool.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)){

            ps.setInt(1, skip);
            ps.setInt(2, limit);

            try(ResultSet rs = ps.executeQuery()){
                while (rs.next()) {
                    Order order1 = new Order();
                    order1.setIdOrder(rs.getInt("ID_Order"));
                    order1.setIdUser(rs.getInt("ID_User"));
                    order1.setDateOrder(new Date(rs.getTimestamp(3).getTime()));
                    order1.setState(rs.getString("State"));
                    orders.add(order1);
                }
            }
        }
        return orders;
    }

    public List<Order> doRetrieveByUser(User user, String order) throws SQLException {
        //Restituisce tutti gli ordini di un utente
        String sql = "SELECT * FROM `Order` WHERE ID_User = ?";
        List<Order> orders = new ArrayList<>();

        if(order != null && !order.equals("")){
            sql +=" ORDER BY " + order;
        }

        try(Connection con = ConPool.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)){

                ps.setInt(1, user.getIdUser());

                try(ResultSet rs = ps.executeQuery()){
                    while (rs.next()) {
                        Order order1 = new Order();
                        order1.setIdOrder(rs.getInt("ID_Order"));
                        order1.setIdUser(rs.getInt("ID_User"));
                        order1.setDateOrder(new Date(rs.getTimestamp(3).getTime()));
                        order1.setState(rs.getString("State"));
                        orders.add(order1);
                    }
                }
        }
        return orders;
    }

    public List<Order> doRetrieveByUser(User user, String order, int skip, int limit) throws SQLException {
        //Restituisce tutti gli ordini di un utente utilizzando skip e limit per la paginazione
        String sql = "SELECT * FROM `Order` WHERE ID_User = ?";
        List<Order> orders = new ArrayList<>();

        if(order != null && !order.equals("")){
            sql +=" ORDER BY " + order;
        }

        sql += " LIMIT ?, ?";

        try(Connection con = ConPool.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)){

            ps.setInt(1, user.getIdUser());
            ps.setInt(2, skip);
            ps.setInt(3, limit);

            try(ResultSet rs = ps.executeQuery()){
                while (rs.next()) {
                    Order order1 = new Order();
                    order1.setIdOrder(rs.getInt("ID_Order"));
                    order1.setIdUser(rs.getInt("ID_User"));
                    order1.setDateOrder(new Date(rs.getTimestamp(3).getTime()));
                    order1.setState(rs.getString("State"));
                    orders.add(order1);
                }
            }
        }
        return orders;
    }

    public List<Order> doRetrieveByDate(Date init, Date end, int skip, int limit) throws SQLException{
        //Restituisce gli ordini effettuati in un intervallo di tempo
        String sql = "SELECT * FROM `Order` WHERE Date >= ? AND date <= ? ORDER BY Date DESC LIMIT ?, ?";
        List<Order> orders = new ArrayList<>();

        try(Connection con = ConPool.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)){

            ps.setString(1, new SimpleDateFormat("yyyy-MM-dd").format(init));
            ps.setString(2, new SimpleDateFormat("yyyy-MM-dd").format(end) + " 23:59:59");
            ps.setInt(3, skip);
            ps.setInt(4, limit);

            try(ResultSet rs = ps.executeQuery()){
                while (rs.next()) {
                    Order order1 = new Order();
                    order1.setIdOrder(rs.getInt("ID_Order"));
                    order1.setIdUser(rs.getInt("ID_User"));
                    order1.setDateOrder(new Date(rs.getTimestamp(3).getTime()));
                    order1.setState(rs.getString("State"));
                    orders.add(order1);
                }
            }
        }
        return orders;
    }


}
