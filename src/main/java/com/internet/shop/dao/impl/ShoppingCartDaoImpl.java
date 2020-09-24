package com.internet.shop.dao.impl;

import com.internet.shop.dao.interfaces.ShoppingCartDao;
import com.internet.shop.db.Storage;
import com.internet.shop.model.ShoppingCart;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class ShoppingCartDaoImpl implements ShoppingCartDao {
    @Override
    public ShoppingCart create(ShoppingCart shoppingCart) {
        Storage.addShoppingCart(shoppingCart);
        return shoppingCart;
    }

    @Override
    public Optional<ShoppingCart> get(Long id) {
        return Storage.shoppingCarts.stream()
                .filter(sc -> sc.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<ShoppingCart> getAll() {
        return Storage.shoppingCarts;
    }

    @Override
    public Optional<ShoppingCart> getByUserId(Long userId) {
        return Storage.shoppingCarts.stream()
                .filter(sc -> sc.getUserId().equals(userId))
                .findFirst();
    }

    @Override
    public ShoppingCart update(ShoppingCart shoppingCart) {
        List<ShoppingCart> shoppingCarts = getAll();
        IntStream.range(0, shoppingCarts.size())
                .filter(i -> shoppingCarts.get(i).getId().equals(shoppingCart.getId()))
                .forEach(i -> shoppingCarts.set(i, shoppingCart));
        return shoppingCart;
    }

    @Override
    public boolean deleteById(Long id) {
        return Storage.shoppingCarts.removeIf(sc -> sc.getId().equals(id));
    }

    public boolean delete(ShoppingCart shoppingCart) {
        return Storage.shoppingCarts.remove(shoppingCart);
    }
}
