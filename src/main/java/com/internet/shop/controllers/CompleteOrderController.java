package com.internet.shop.controllers;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.Order;
import com.internet.shop.model.ShoppingCart;
import com.internet.shop.servise.interfaces.OrderService;
import com.internet.shop.servise.interfaces.ShoppingCartService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CompleteOrderController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("com.internet.shop");
    private final OrderService orderService =
            (OrderService) injector.getInstance(OrderService.class);
    private final ShoppingCartService shoppingCartService =
            (ShoppingCartService) injector.getInstance(ShoppingCartService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long shoppingCartUserID = Long.parseLong(req.getParameter("userId"));
        ShoppingCart shoppingCart = shoppingCartService.getByUserId(shoppingCartUserID);
        Order order = orderService.completeOrder(shoppingCart);
        req.setAttribute("order", order);
        req.getRequestDispatcher("/WEB-INF/views/order/order.jsp").forward(req, resp);
    }
}
