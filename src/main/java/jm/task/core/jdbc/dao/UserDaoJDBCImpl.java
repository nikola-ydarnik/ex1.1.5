package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {}

    public void createUsersTable() {
        Connection connection = Util.getConnection();

        String sql = "CREATE TABLE IF NOT EXISTS user (" +
                "user_id INT PRIMARY KEY AUTO_INCREMENT," +
                "name VARCHAR(255)," +
                "last_name VARCHAR(255)," +
                "age INT" +
                ")";

        try (Statement statement = connection.createStatement()) {

            statement.executeUpdate(sql);
            System.out.println("table is create");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Util.closeConnection(connection);
    }

    public void dropUsersTable() {
        Connection connection = Util.getConnection();

        String sql = "DROP TABLE IF EXISTS user";

        try (Statement statement = connection.createStatement()) {

            statement.executeUpdate(sql);
            System.out.println("table is delete");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Util.closeConnection(connection);
    }

    public void saveUser(String name, String lastName, byte age) {
        Connection connection = Util.getConnection();

        String sql = "INSERT INTO user (name, last_name, age) VALUES(?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setInt(3, age);
            statement.executeUpdate();
            System.out.println("User с именем – " + name + " добавлен в базу данных");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        Util.closeConnection(connection);
    }

    public void removeUserById(long id) {

        Connection connection = Util.getConnection();

        String sql = "DELETE FROM user WHERE user_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            statement.executeUpdate();
            System.out.println("User with id: " + id + " is remove");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        Util.closeConnection(connection);
    }

    public List<User> getAllUsers() {
        Connection connection = Util.getConnection();
        List<User> userList = new ArrayList<>();

        String sql = "SELECT * FROM user";

        try (Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("user_id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setAge(resultSet.getByte("age"));
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Util.closeConnection(connection);
        return userList;
    }

    public void cleanUsersTable() {
        Connection connection = Util.getConnection();
        String sql = "TRUNCATE TABLE user";

        try (Statement statement = connection.createStatement()) {

            statement.executeUpdate(sql);
            System.out.println("Table is cleared");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Util.closeConnection(connection);
    }
}
