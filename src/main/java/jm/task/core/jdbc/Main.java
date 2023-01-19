package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Alexander", "Kovalev", (byte) 55);
        userService.saveUser("Vladimir", "Taraskin", (byte) 18);
        userService.saveUser("Igor", "Savelyev", (byte) 23);
        userService.saveUser("Olga", "Dubrova", (byte) 34);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
