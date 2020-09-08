package com.internet.shop.servise.impl;

import com.internet.shop.dao.interfaces.OrderDao;
import com.internet.shop.dao.interfaces.ShoppingCartDao;
import com.internet.shop.db.Storage;
import com.internet.shop.lib.Inject;
import com.internet.shop.lib.Service;
import com.internet.shop.model.Order;
import com.internet.shop.model.Product;
import com.internet.shop.model.ShoppingCart;
import com.internet.shop.servise.interfaces.OrderService;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Inject
    private OrderDao orderDao;

    @Inject
    private ShoppingCartDao shoppingCartDao;

    @Override
    public Order completeOrder(ShoppingCart shoppingCart) {
        List<Product> products = List.copyOf(shoppingCart.getProducts());
        Long userId = shoppingCart.getUserId();
        Order order = new Order(userId);
        order.setProducts(products);
        orderDao.create(order);
        shoppingCart.getProducts().clear();
        return order;
    }

    @Override
    public List<Order> getUserOrders(Long userId) {
        return orderDao.getUserOrders(userId);
    }

    @Override
    public Order get(Long id) {
        return orderDao.get(id).get();
    }

    @Override
    public List<Order> getAll() {
        return orderDao.getAll();
    }

    @Override
    public boolean delete(Long id) {
        return orderDao.delete(id);
    }
}
