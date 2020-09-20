package com.internet.shop.dao.impl;

import com.internet.shop.dao.interfaces.ProductDao;
import com.internet.shop.model.Product;
import com.internet.shop.util.ConnectionUtil;
import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class ProductDaoMySql implements ProductDao {
    Connection connection = ConnectionUtil.getConnection();

    @Override
    public Product create(Product item) {
        return null;
    }

    @Override
    public Optional<Product> get(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Product> getAll() {
        return null;
    }

    @Override
    public Product update(Product item) {
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        return false;
    }

    @Override
    public boolean delete(Product item) {
        return false;
    }
}
