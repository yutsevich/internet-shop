package com.internet.shop.servise.impl;

import com.internet.shop.dao.interfaces.ShoppingCartDao;
import com.internet.shop.db.Storage;
import com.internet.shop.lib.Inject;
import com.internet.shop.lib.Service;
import com.internet.shop.model.Product;
import com.internet.shop.model.ShoppingCart;
import com.internet.shop.servise.interfaces.ShoppingCartServise;
import java.util.ArrayList;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartServise {

    @Inject
    private ShoppingCartDao shoppingCartDao;

    @Override
    public ShoppingCart create(ShoppingCart shoppingCart) {
        return shoppingCartDao.create(shoppingCart);
    }

    @Override
    public ShoppingCart addProduct(ShoppingCart shoppingCart, Product product) {
        shoppingCartDao.get(shoppingCart.getId()).get()
                .getProducts()
                .add(product);
        return shoppingCartDao.get(shoppingCart.getId()).get();
    }

    @Override
    public boolean deleteProduct(ShoppingCart shoppingCart, Product product) {
        return shoppingCartDao.get(shoppingCart.getId()).get()
                .getProducts()
                .removeIf(p -> p.getId().equals(product.getId()));
    }

    @Override
    public void clear(ShoppingCart shoppingCart) {
        Storage.shoppingCarts.stream()
                .filter(sc -> sc.getId().equals(shoppingCart.getId()))
                .forEach(sc -> sc.setProducts(new ArrayList<Product>()));
    }

    @Override
    public ShoppingCart getByUserId(Long userId) {
        return Storage.shoppingCarts.stream()
                .filter(sc -> sc.getUserId().equals(userId))
                .findFirst().get();
    }

    @Override
    public boolean delete(ShoppingCart shoppingCart) {
        return shoppingCartDao.delete(shoppingCart.getId());
    }
}
