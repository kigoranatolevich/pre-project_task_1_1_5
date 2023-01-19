package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    private final static String URL =
            "jdbc:mysql://localhost:3306/pre_project_task_1_1_4?useUnicode=true" +
                    "&useSSL=true&useJDBCCompliantTimezoneShift=true" +
                    "&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private final static String USERNAME = "root";
    private final static String PASSWORD = "root";
    private static volatile Connection con;

    private Util() {

    }
    public static Connection getConnection() {
        Connection localCon = con;
        if (localCon == null) {
            synchronized (Util.class) {
                localCon = con;
                if (localCon == null) {
                    try {
                        localCon = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                        con = localCon;
                        System.out.println("Соединение создано");
                    } catch (SQLException e) {
                        System.out.println("Ошибка соединения");
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                }
            }
        }

        return localCon;
    }
}
