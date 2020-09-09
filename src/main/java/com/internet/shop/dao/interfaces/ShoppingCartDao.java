package com.internet.shop.dao.interfaces;

import com.internet.shop.model.ShoppingCart;

public interface ShoppingCartDao extends GenericDao<ShoppingCart, Long> {
    ShoppingCart getByUserId(Long userId);
}
