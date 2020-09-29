package com.internet.shop.dao.mysqlimpl;

import com.internet.shop.dao.interfaces.UserDao;
import com.internet.shop.exceptions.DataProcessingException;
import com.internet.shop.lib.Dao;
import com.internet.shop.model.Role;
import com.internet.shop.model.User;
import com.internet.shop.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Dao
public class UserDaoJdbcImpl implements UserDao {

    @Override
    public Optional<User> findByLogin(String login) {
        String query = "SELECT * FROM users WHERE login=? AND deleted=FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getUserFromResultSet(resultSet, connection));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get user by login " + login, e);
        }
    }

    @Override
    public User create(User user) {
        String query = "INSERT INTO users (user_name, login, password) VALUES (?,?,?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getLogin());
            statement.setString(3, user.getPassword());
            statement.executeUpdate();
            addUsersRolesById(user, connection);
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not create user " + user, e);
        }
        return user;
    }

    @Override
    public Optional<User> get(Long id) {
        String query = "SELECT * FROM users WHERE user_id=? AND deleted=FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getUserFromResultSet(resultSet, connection));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get user by id " + id, e);
        }
    }

    @Override
    public List<User> getAll() {
        String query = "SELECT * FROM users WHERE deleted = FALSE";
        List<User> users = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                users.add(getUserFromResultSet(resultSet, connection));
            }
            return users;
        } catch (SQLException e) {
            throw new DataProcessingException("Can not get all users ", e);
        }
    }

    @Override
    public User update(User user) {
        String query = "UPDATE users SET user_name=?, login=?"
                + "WHERE password=? & deleted=FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getLogin());
            statement.setString(3, user.getPassword());
            deleteUsersRolesById(user.getId(), connection);
            addUsersRolesById(user, connection);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can not update user " + user, e);
        }
        return user;
    }

    @Override
    public boolean deleteById(Long id) {
        String query = "UPDATE users SET deleted=TRUE WHERE user_id=?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Can not delete user with id " + id, e);
        }
    }

    private void deleteUsersRolesById(Long userId, Connection connection) throws SQLException {
        String sql = "DELETE FROM users_roles WHERE user_id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, userId);
            statement.executeUpdate();
        }
    }

    private void addUsersRolesById(User user, Connection connection) throws SQLException {
        String sql = "INSERT INTO users_roles VALUES (?, ?);";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(2, user.getId());
            for (Role role : user.getRoles()) {
                statement.setLong(1, role.getRoleName().ordinal() + 1);
                statement.executeUpdate();
            }
        }
    }

    private User getUserFromResultSet(ResultSet resultSet, Connection connection)
            throws SQLException {
        Long id = resultSet.getLong("user_id");
        String name = resultSet.getString("user_name");
        String login = resultSet.getString("login");
        String password = resultSet.getString("password");
        Set<Role> roles = getUsersRoles(id, connection);
        return new User(id, name, login, password, roles);
    }

    private Set<Role> getUsersRoles(Long userId, Connection connection) {
        Set<Role> roles = new HashSet<>();
        String query = "SELECT * FROM users_roles JOIN roles "
                + "USING(role_id) WHERE deleted=FALSE AND user_id=?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                roles.add(Role.of(resultSet.getString("role_name")));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not get users roles " + userId, e);
        }
        return roles;
    }
}
