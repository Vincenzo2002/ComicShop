package model.dao;

import manager.ConPool;
import model.bean.Feedback;
import model.bean.Product;
import model.bean.User;
import model.interf.IModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FeedbackDAO implements IModel<Feedback> {
    @Override
    public void doSave(Feedback feedback) throws SQLException {
        //Salva un nuovo feedback
        String sql = "INSERT INTO Feedback (Title, Description, Score, ID_User, ID_Product) VALUES(?, ?, ?, ?, ?)";

        try(Connection con = ConPool.getConnection();
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

            ps.setString(1, feedback.getTitle());
            ps.setString(2, feedback.getDescription());
            ps.setInt(3, feedback.getScore());
            ps.setInt(4, feedback.getUser());
            ps.setInt(5, feedback.getProduct());

            ps.executeUpdate();

            try(ResultSet rs = ps.getGeneratedKeys()){
                if(rs.next()){
                    int id = rs.getInt(1);
                    feedback.setIdFeedback(id);
                }
            }
        }
    }

    @Override
    public boolean doDelete(int idFeedback) throws SQLException {
        //Elimina un feedback
        String sql = "DELETE FROM Feedback WHERE ID_Feedback = ?";

        try(Connection con = ConPool.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)){

            ps.setInt(1, idFeedback);

            int result = ps.executeUpdate();

            if(result == 1)
                return true;
            return false;
        }
    }

    @Override
    public Feedback doRetrieveByKey(int idFeedback) throws SQLException {
        //Restituisce un feedback in base all'id
        String sql = "SELECT * FROM Feedback WHERE ID_Feedback = ?";

        try(Connection con = ConPool.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)){

            ps.setInt(1, idFeedback);

            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    Feedback feedback = new Feedback();
                    feedback.setIdFeedback(rs.getInt("ID_Fedback"));
                    feedback.setTitle(rs.getString("Title"));
                    feedback.setDescription(rs.getString("Description"));
                    feedback.setScore(rs.getInt("Score"));
                    feedback.setUser(rs.getInt("ID_User"));
                    feedback.setProduct(rs.getInt("ID_Product"));
                    return feedback;
                }
            }
        }
        return null;
    }

    @Override
    public Collection<Feedback> doRetrieveAll(String order) throws SQLException {
        //Seleziona tutti i feedback
        String sql = "SELECT * FROM Feedback";
        List<Feedback> feedbacks = new ArrayList<>();

        if(order != null && !order.equals("")){
            sql +=" ORDER BY " + order;
        }

        try(Connection con = ConPool.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){

            while (rs.next()){
                Feedback feedback = new Feedback();
                feedback.setIdFeedback(rs.getInt("ID_Fedback"));
                feedback.setTitle(rs.getString("Title"));
                feedback.setDescription(rs.getString("Description"));
                feedback.setScore(rs.getInt("Score"));
                feedback.setUser(rs.getInt("ID_User"));
                feedback.setProduct(rs.getInt("ID_Product"));
                feedbacks.add(feedback);
            }
        }
        return feedbacks;
    }

    public Collection<Feedback> doRetrieveAll(String order, int skip, int limit) throws SQLException {
        //Seleziona tutti i feedback con skip e limit per la paginazione
        String sql = "SELECT * FROM Feedback";
        List<Feedback> feedbacks = new ArrayList<>();

        if(order != null && !order.equals("")){
            sql +=" ORDER BY " + order;
        }

        sql += "LIMIT ?, ?";

        try(Connection con = ConPool.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)){

            ps.setInt(1, skip);
            ps.setInt(2, limit);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Feedback feedback = new Feedback();
                    feedback.setIdFeedback(rs.getInt("ID_Fedback"));
                    feedback.setTitle(rs.getString("Title"));
                    feedback.setDescription(rs.getString("Description"));
                    feedback.setScore(rs.getInt("Score"));
                    feedback.setUser(rs.getInt("ID_User"));
                    feedback.setProduct(rs.getInt("ID_Product"));
                    feedbacks.add(feedback);
                }
            }
        }
        return feedbacks;
    }

    public Collection<Feedback> doRetrieveByProduct(int product) throws SQLException{
        //Selezione tutti i feedback di un prodotto
        String sql = "SELECT * FROM Feedback WHERE ID_Product = ?";
        List<Feedback> feedbacks = new ArrayList<>();

        try(Connection con = ConPool.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)){

            ps.setInt(1, product);

            try(ResultSet rs = ps.executeQuery()){

                while (rs.next()){
                    Feedback feedback = new Feedback();
                    feedback.setIdFeedback(rs.getInt("ID_Feedback"));
                    feedback.setTitle(rs.getString("Title"));
                    feedback.setDescription(rs.getString("Description"));
                    feedback.setScore(rs.getInt("Score"));
                    feedback.setUser(rs.getInt("ID_User"));
                    feedback.setProduct(rs.getInt("ID_Product"));
                    feedbacks.add(feedback);
                }
            }
        }
        return feedbacks;
    }

    public Collection<Feedback> doRetrieveByProduct(int product, int skip, int limit) throws SQLException{
        //Selezione tutti i feedback di un prodotto con skip e limit per la paginazione
        String sql = "SELECT * FROM Feedback WHERE ID_Product = ? LIMIT ?, ?";
        List<Feedback> feedbacks = new ArrayList<>();

        try(Connection con = ConPool.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)){

            ps.setInt(1, product);
            ps.setInt(2, skip);
            ps.setInt(3, limit);

            try(ResultSet rs = ps.executeQuery()){

                while (rs.next()){
                    Feedback feedback = new Feedback();
                    feedback.setIdFeedback(rs.getInt("ID_Feedback"));
                    feedback.setTitle(rs.getString("Title"));
                    feedback.setDescription(rs.getString("Description"));
                    feedback.setScore(rs.getInt("Score"));
                    feedback.setUser(rs.getInt("ID_User"));
                    feedback.setProduct(rs.getInt("ID_Product"));
                    feedbacks.add(feedback);
                }
            }
        }
        return feedbacks;
    }

    public void doUpdate(Feedback feedback) throws SQLException{
        //Aggiornamento feedback
        String sql = "UPDATE Feedback SET Title = ?, Description = ?, Score = ? WHERE ID_Feedback = ?";

        try(Connection con = ConPool.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)){

            ps.setString(1, feedback.getTitle());
            ps.setString(2, feedback.getDescription());
            ps.setInt(3, feedback.getScore());
            ps.setInt(4, feedback.getIdFeedback());

            ps.executeUpdate();
        }
    }

    public static Double doAverageScore(Product product) throws SQLException{

        String sql = "SELECT AVG(score) AS Media FROM Feedback WHERE ID_Product=?";
        double result = 0;

        try(Connection con = ConPool.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)){

            ps.setInt(1, product.getIdProduct());

            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    result = rs.getDouble("Media");
                }
            }
        }
        return result;
    }


}
