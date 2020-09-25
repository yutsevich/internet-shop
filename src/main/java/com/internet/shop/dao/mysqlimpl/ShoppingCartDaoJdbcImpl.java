package com.internet.shop.dao.mysqlimpl;

import com.internet.shop.dao.interfaces.ShoppingCartDao;
import com.internet.shop.exceptions.DataProcessingException;
import com.internet.shop.lib.Dao;
import com.internet.shop.model.Product;
import com.internet.shop.model.ShoppingCart;
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
public class ShoppingCartDaoJdbcImpl implements ShoppingCartDao {

    @Override
    public Optional<ShoppingCart> getByUserId(Long userId) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "SELECT * FROM shopping_carts WHERE user_id=?;";
            PreparedStatement statement = connection
                    .prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getShoppingCartFromResultSet(resultSet, connection));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Can not get shopping cart by id "
                    + userId, e);
        }
    }

    @Override
    public ShoppingCart create(ShoppingCart shoppingCart) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "INSERT INTO shopping_carts (user_id) VALUES (?)";
            PreparedStatement statement = connection
                    .prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, shoppingCart.getUserId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                shoppingCart.setId(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not create shopping cart "
                    + shoppingCart, e);
        }
        return shoppingCart;
    }

    @Override
    public Optional<ShoppingCart> get(Long id) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "SELECT * FROM shopping_carts WHERE cart_id=?;";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getShoppingCartFromResultSet(resultSet, connection));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get shopping cart by id: " + id, e);
        }
    }

    @Override
    public List<ShoppingCart> getAll() {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "SELECT * FROM shopping_carts;";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            List<ShoppingCart> shoppingCarts = new ArrayList<>();
            while (resultSet.next()) {
                shoppingCarts.add(getShoppingCartFromResultSet(resultSet, connection));
            }
            return shoppingCarts;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get shopping carts ", e);
        }
    }

    @Override
    public ShoppingCart update(ShoppingCart shoppingCart) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            deleteShoppingCartProducts(shoppingCart.getId(), connection);
            insertProductsToShoppingCart(shoppingCart, connection);
        } catch (SQLException e) {
            throw new DataProcessingException("Can not delete products from shopping cart "
                    + shoppingCart, e);
        }
        return shoppingCart;
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "DELETE FROM shopping_carts WHERE cart_id=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.executeUpdate();
            deleteShoppingCartProducts(id, connection);
        } catch (SQLException e) {
            throw new DataProcessingException("Can not delete shopping cart by id " + id, e);
        }
        return false;
    }

    private boolean deleteShoppingCartProducts(Long cartId, Connection connection) {
        try {
            String query = "DELETE FROM shopping_carts_products WHERE cart_id=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can not delete products from shopping cart by id "
                    + cartId, e);
        }
        return true;
    }

    private List<Product> getProductsFromShoppingCart(Long cartId, Connection connection)
            throws SQLException {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM products JOIN shopping_carts_products "
                + "USING(product_id) WHERE cart_id=?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, cartId);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            products.add(new Product(resultSet.getLong("product_id"),
                    resultSet.getString("product_name"),
                    resultSet.getDouble("price")));
        }
        return products;
    }

    private boolean insertProductsToShoppingCart(ShoppingCart shoppingCart, Connection connection) {
        String query = "INSERT INTO shopping_carts_products (cart_id, product_id) VALUES (?,?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (Product product : shoppingCart.getProducts()) {
                statement.setLong(1, shoppingCart.getId());
                statement.setLong(2, product.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not add products to shopping cart "
                    + shoppingCart, e);
        }
        return true;
    }

    private ShoppingCart getShoppingCartFromResultSet(ResultSet resultSet, Connection connection)
            throws SQLException {
        Long id = resultSet.getLong("cart_id");
        Long userId = resultSet.getLong("user_id");
        ShoppingCart shoppingCart = new ShoppingCart(userId);
        shoppingCart.setProducts(getProductsFromShoppingCart(id, connection));
        return shoppingCart;
    }
}
