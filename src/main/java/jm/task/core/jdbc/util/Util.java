package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String URL = "jdbc:mysql://localhost:3306/mydbtest";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "root";

    public Connection getConnection() throws SQLException {
//      Class.forName("com.mysql.cj.jdbc.Driver");
//      Прочитал что с JDBC 4 не надо  явно загружать драйвер, или всё таки надо по правилам?
        return DriverManager.getConnection(URL, USER_NAME, PASSWORD);
    }
}
