package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private final Util util = new Util(); // Сделал здесь переменную, не знаю правильно ли так делать?
    public UserDaoJDBCImpl() {}

    public void createUsersTable() {

        String sql = "CREATE TABLE user (" +              /*"CREATE TABLE IF NOT EXISTS user*/
                "user_id INT PRIMARY KEY AUTO_INCREMENT," +
                "name VARCHAR(255)," +
                "last_name VARCHAR(255)," +
                "age INT" +
                ")";

        try (Connection connection = util.getConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate(sql);
            System.out.println("table is create");
        } catch (SQLException e) {
            if (e.getSQLState().equals("42S01")) {
                System.out.println("table already exists.");
            } else {
                e.printStackTrace();
            }
        }
    }

    public void dropUsersTable() {

        String sql = "DROP TABLE user";                       /*DROP TABLE IF EXISTS user */

        try (Connection connection = util.getConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate(sql);
            System.out.println("table is delete");
        } catch (SQLException e) {
            if (e.getSQLState().equals("42S02")) {
                System.out.println("table doesn't exist.");
            } else {
                e.printStackTrace();
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        String sql = "INSERT INTO user (name, last_name, age) VALUES(?, ?, ?)";

        try (Connection connection = util.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setInt(3, age);
            statement.executeUpdate();
            System.out.println("User с именем – " + name + " добавлен в базу данных");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {

        String sql = "DELETE FROM user WHERE user_id = ?";

        try (Connection connection = util.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            statement.executeUpdate();
            System.out.println("User with id: " + id + " is remove");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();

        String sql = "SELECT user_id, name, last_name, age FROM user";

        try (Connection connection = util.getConnection();
             Statement statement = connection.createStatement()) {

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
        return userList;
    }

    public void cleanUsersTable() {

        String sql = "DELETE FROM user";

        try (Connection connection = util.getConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate(sql);
            System.out.println("Table is cleared");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
