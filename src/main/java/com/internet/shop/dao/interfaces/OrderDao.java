package com.internet.shop.dao.interfaces;

import com.internet.shop.model.Order;
import java.util.List;

public interface OrderDao extends GenericDao<Order, Long> {
    List<Order> getUserOrders(Long userId);
}
