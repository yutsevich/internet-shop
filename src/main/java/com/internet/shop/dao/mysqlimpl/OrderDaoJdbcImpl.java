package com.internet.shop.dao.mysqlimpl;

import com.internet.shop.dao.interfaces.OrderDao;
import com.internet.shop.exceptions.DataProcessingException;
import com.internet.shop.lib.Dao;
import com.internet.shop.model.Order;
import com.internet.shop.model.Product;
import com.internet.shop.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class OrderDaoJdbcImpl implements OrderDao {

    @Override
    public List<Order> getUserOrders(Long userId) {
        List<Order> orders = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "SELECT * FROM orders WHERE user_id=?;";
            PreparedStatement statement = connection
                    .prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                orders.add(getOrderFromResultSet(resultSet, connection));
            }
            return orders;
        } catch (SQLException e) {
            throw new DataProcessingException("Can not get order by user id "
                    + userId, e);
        }
    }

    @Override
    public Order create(Order order) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "INSERT INTO orders (user_id) VALUES (?)";
            PreparedStatement statement = connection
                    .prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, order.getUserId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                order.setId(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not create order "
                    + order, e);
        }
        return order;
    }

    @Override
    public Optional<Order> get(Long id) {
        List<Order> orders = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "SELECT * FROM orders WHERE order_id=?;";
            PreparedStatement statement = connection
                    .prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getOrderFromResultSet(resultSet, connection));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Can not get order by id "
                    + id, e);
        }
    }

    @Override
    public List<Order> getAll() {
        List<Order> orders = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "SELECT * FROM orders;";
            PreparedStatement statement = connection
                    .prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                orders.add(getOrderFromResultSet(resultSet, connection));
            }
            return orders;
        } catch (SQLException e) {
            throw new DataProcessingException("Can not get all orders ", e);
        }
    }

    @Override
    public Order update(Order order) {
        deleteProductsFromOrder(order.getId());
        addProductsToOrder(order);
        return order;
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "UPDATE orders SET deleted = TRUE WHERE order_id=?;";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Can not delete order with id " + id, e);
        }
    }

    private void deleteProductsFromOrder(Long orderId) {
        String query = "DELETE FROM orders_products WHERE order_id=?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, orderId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete products from order", e);
        }
    }

    private void addProductsToOrder(Order order) {
        String query = "INSERT INTO orders_products(order_id, product_id) VALUES(?,?)";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            for (Product product : order.getProducts()) {
                statement.setLong(1, order.getId());
                statement.setLong(2, product.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add products to order", e);
        }
    }

    private List<Product> getProductsFromOrder(Long orderId, Connection connection)
            throws SQLException {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM orders_products JOIN products "
                + "USING(product_id) WHERE order_id=?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, orderId);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            products.add(new Product(resultSet.getLong("product_id"),
                    resultSet.getString("product_name"),
                    resultSet.getDouble("price")));
        }
        return products;
    }

    private Order getOrderFromResultSet(ResultSet resultSet, Connection connection)
            throws SQLException {
        Long id = resultSet.getLong("order_id");
        Long userId = resultSet.getLong("user_id");
        List<Product> products = getProductsFromOrder(id, connection);
        return new Order(id, products, userId);
    }
}
