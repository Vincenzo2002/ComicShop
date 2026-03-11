package model.dao;

import manager.ConPool;
import model.bean.Category;
import model.interf.IModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CategoryDAO implements IModel<Category> {

    @Override
    public void doSave(Category category) throws SQLException {
        //Inserimento di una categoria
        String sql = "INSERT INTO Category (Name) VALUES(?)";

        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, category.getName());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    category.setIdCategory(id);
                }
            }
        }
    }


    @Override
    public boolean doDelete(int idCategory) throws SQLException {
        //Elimina una categoria
        String sql = "DELETE FROM Category where ID_Category = ?";

        try(Connection con = ConPool.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)){

            ps.setInt(1, idCategory);
            int result = ps.executeUpdate();

            if(result == 1)
                return true;
            return false;
        }
    }

    @Override
    public Category doRetrieveByKey(int idCategory) throws SQLException {
        //Restituisce la categoria in base all'id
        String sql = "SELECT * FROM Category WHERE ID_Category = ?";

        try(Connection con = ConPool.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)){

            ps.setInt(1, idCategory);

            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    Category category = new Category();
                    category.setIdCategory(rs.getInt("ID_Category"));
                    category.setName(rs.getString("Name"));
                    return  category;
                }
            }
        }
        return null;
    }

    @Override
    public Collection<Category> doRetrieveAll(String order) throws SQLException {
        //Restituisce tutte le categorie
        String sql = "SELECT * FROM Category";
        List<Category> categories = new ArrayList<>();

        if(order != null && !order.equals("")){
            sql +=" ORDER BY " + order;
        }

        try(Connection con = ConPool.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){

            while(rs.next()){
                Category category = new Category();
                category.setIdCategory(rs.getInt("ID_Category"));
                category.setName(rs.getString("Name"));
                categories.add(category);
            }
        }
        return categories;
    }

    public Category doRetrieveByName(String nameCategory) throws SQLException{
        //Restituisce la categoria in base al nome
        String sql = "SELECT * FROM Category WHERE Name = ?";

        try(Connection con = ConPool.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)){

            ps.setString(1, nameCategory);

            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    Category category = new Category();
                    category.setIdCategory(rs.getInt("ID_Category"));
                    category.setName(rs.getString("Name"));
                    return  category;
                }
            }
        }
        return null;
    }


}
