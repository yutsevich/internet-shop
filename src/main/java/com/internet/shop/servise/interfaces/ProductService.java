package com.internet.shop.servise.interfaces;

import com.internet.shop.model.Product;
import java.util.List;

public interface ProductService {
    Product create(Product product);

    Product get(Long id);

    List<Product> getAll();

    Product update(Product product);

    boolean delete(Long id);
}
