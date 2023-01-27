package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private Connection con;

    public UserDaoJDBCImpl() {
        con = Util.getConnection();
    }

    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS user (id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(45), " +
                "lastName VARCHAR(45), age INT(3))";

        try (Statement statement = con.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS user";

        try (Statement statement = con.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO user(name, lastName, age) VALUES(?, ?, ?)";

        try(PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            con.setAutoCommit(false);

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();

            con.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println("User с именем - " + name + " добавлен в базу данных");
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM user WHERE ID = ?";

        try(PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT id, name, lastName, age FROM user";

        try(Statement statement = con.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                userList.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println(userList);
        return userList;
    }

    public void cleanUsersTable() {
        String sql = "TRUNCATE TABLE user";

        try (Statement statement = con.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
