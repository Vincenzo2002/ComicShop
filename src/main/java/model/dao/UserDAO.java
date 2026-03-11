package model.dao;

import manager.ConPool;
import model.bean.User;
import model.interf.IModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDAO implements IModel<User> {

    @Override
    public void doSave(User user) throws SQLException {
        //Salvataggio nuovo utente nel database
        String sql = "INSERT INTO User (FirstName, LastName, Email, Password, Provincia, Via, Civico, CAP, Citta) VALUES (?, ?, ?, MD5(?), ?, ?, ?, ?, ?)";

        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getProvincia());
            ps.setString(6, user.getVia());
            ps.setString(7, user.getCivico());
            ps.setString(8, user.getCap());
            ps.setString(9, user.getCitta());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    user.setIdUser(id);
                }
            }
        }
    }

    @Override
    public boolean doDelete(int idUser) throws SQLException {
        //Cancellazione di un account
        String sql = "DELETE FROM User WHERE ID_User = ?";

        try(Connection con = ConPool.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)){

            ps.setInt(1, idUser);
            int result = ps.executeUpdate();

            if(result == 1)
                return true;
            return false;
        }
    }

    @Override
    public User doRetrieveByKey(int idUser) throws SQLException {
        //Restituisce un utente in base all id cercato
        String sql = "SELECT * FROM User WHERE ID_User = ?";

        try(Connection con = ConPool.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)){

            ps.setInt(1, idUser);
            try(ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setIdUser(rs.getInt("ID_User"));
                    user.setFirstName(rs.getString("FirstName"));
                    user.setLastName(rs.getString("LastName"));
                    user.setEmail(rs.getString("Email"));
                    user.setPassword(rs.getString("Password"));
                    user.setAdmin(rs.getBoolean("Admin"));
                    user.setProvincia(rs.getString("Provincia"));
                    user.setVia(rs.getString("Via"));
                    user.setCivico(rs.getString("Civico"));
                    user.setCap(rs.getString("CAP"));
                    user.setCitta(rs.getString("Citta"));
                    user.setAdmin(rs.getBoolean("Admin"));
                    return user;
                }
            }
        }
        return null;

    }

    @Override
    public Collection<User> doRetrieveAll(String order) throws SQLException {
        //Restituisce tutti gli utenti registrati
        String sql = "SELECT * FROM User";
        List<User> users = new ArrayList<>();

        if(order != null && !order.equals("")){
            sql +=" ORDER BY " + order;
        }

        try(Connection con = ConPool.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){

                while (rs.next()) {
                    User user = new User();
                    user.setIdUser(rs.getInt("ID_User"));
                    user.setFirstName(rs.getString("FirstName"));
                    user.setLastName(rs.getString("LastName"));
                    user.setEmail(rs.getString("Email"));
                    user.setPassword(rs.getString("Password"));
                    user.setAdmin(rs.getBoolean("Admin"));
                    user.setProvincia(rs.getString("Provincia"));
                    user.setVia(rs.getString("Via"));
                    user.setCivico(rs.getString("Civico"));
                    user.setCap(rs.getString("CAP"));
                    user.setCitta(rs.getString("Citta"));
                    user.setAdmin(rs.getBoolean("Admin"));
                    users.add(user);
                }
        }
        return users;
    }

    public User doRetrieveByEmailAndPassword(String email, String password) throws SQLException {
        //Restiusce utente con email e password
        String sql = "SELECT * FROM User WHERE Email = ? AND Password = MD5(?)";

        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setIdUser(rs.getInt("ID_User"));
                    user.setFirstName(rs.getString("FirstName"));
                    user.setLastName(rs.getString("LastName"));
                    user.setEmail(rs.getString("Email"));
                    user.setPassword(rs.getString("Password"));
                    user.setAdmin(rs.getBoolean("Admin"));
                    user.setProvincia(rs.getString("Provincia"));
                    user.setVia(rs.getString("Via"));
                    user.setCivico(rs.getString("Civico"));
                    user.setCap(rs.getString("CAP"));
                    user.setCitta(rs.getString("Citta"));
                    return user;
                }
            }
        }
        return null;
    }

    public User doRetrieveByEmail(String email) throws SQLException {
        //Restituisce utente sull'email
        String sql = "SELECT * FROM User WHERE Email = ?";

        try(Connection con = ConPool.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)){

            ps.setString(1, email);

            try(ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setIdUser(rs.getInt("ID_User"));
                    user.setFirstName(rs.getString("FirstName"));
                    user.setLastName(rs.getString("LastName"));
                    user.setEmail(rs.getString("Email"));
                    user.setPassword(rs.getString("Password"));
                    user.setAdmin(rs.getBoolean("Admin"));
                    user.setProvincia(rs.getString("Provincia"));
                    user.setVia(rs.getString("Via"));
                    user.setCivico(rs.getString("Civico"));
                    user.setCap(rs.getString("CAP"));
                    user.setCitta(rs.getString("Citta"));
                    user.setAdmin(rs.getBoolean("Admin"));
                    return user;
                }
            }
        }
        return null;
    }

    public void doUpdate(User user) throws SQLException {
        //Aggiornamento dati utente
        String sql = "UPDATE User SET FirstName = ?, LastName = ?, Email = ?, Password = ?, Provincia = ?, CAP = ?, Via = ?, Civico = ?, Citta = ? WHERE idUser = ?";

        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getProvincia());
            ps.setString(6, user.getCap());
            ps.setString(7, user.getVia());
            ps.setString(8, user.getCivico());
            ps.setString(9, user.getCitta());
            ps.setInt(10, user.getIdUser());

            ps.executeUpdate();

        }
    }

}
