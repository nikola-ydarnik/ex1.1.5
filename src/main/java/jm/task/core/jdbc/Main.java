package jm.task.core.jdbc;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) {

        List<User> userList = new ArrayList<>(
                List.of(new User("Nick", "Tikhonov", (byte) 27),
                        new User("Mari", "Tikhonova", (byte) 26),
                        new User("Alex", "Tarhov", (byte) 70),
                        new User("Ivan", "Kyznecov", (byte) 70)));

        UserService userService = new UserServiceImpl();

        /*Создание таблицы User(ов)*/
        userService.createUsersTable();

        /*Добавление 4 User(ов) в таблицу с данными на свой выбор. После каждого добавления должен быть вывод в консоль ( User с именем – name добавлен в базу данных )*/
        for (User user: userList) {
            userService.saveUser(user.getName(), user.getLastName(), user.getAge());
        }

        /*Получение всех User из базы и вывод в консоль (должен быть переопределен toString в классе User)*/
        System.out.println(userService.getAllUsers());

        userService.removeUserById(3);
        System.out.println(userService.getAllUsers());

        /*Очистка таблицы User(ов)*/
        userService.cleanUsersTable();
        System.out.println(userService.getAllUsers());

        /*Удаление таблицы*/
        userService.dropUsersTable();

    }
}