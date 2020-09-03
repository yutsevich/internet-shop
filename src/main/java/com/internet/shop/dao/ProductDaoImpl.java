package com.internet.shop.dao;

import com.internet.shop.dao.interfaces.ProductDao;
import com.internet.shop.db.Storage;
import com.internet.shop.lib.Dao;
import com.internet.shop.model.Product;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Dao
public class ProductDaoImpl implements ProductDao {

    @Override
    public Product create(Product product) {
        Storage.addProduct(product);
        return product;
    }

    @Override
    public Optional<Product> get(Long id) {
        return Storage.products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Product> getAll() {
        return Storage.products;
    }

    @Override
    public Product update(Product product) {
        List<Product> products = getAll();
        IntStream.range(0, products.size())
                .filter(i -> products.get(i).getId().equals(product.getId()))
                .forEach(index -> products.set(index, product));
        return product;
    }

    @Override
    public boolean delete(Long id) {
        return Storage.products
                .removeIf(p -> p.getId().equals(id));
    }
}