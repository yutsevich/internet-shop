package com.internet.shop.controllers;

import com.internet.shop.lib.Injector;
import com.internet.shop.servise.interfaces.OrderService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetAllOrdersOfUserController extends HttpServlet {
    private static final Long USER_ID = 1L;
    private static final Injector injector = Injector.getInstance("com.internet.shop");
    private final OrderService orderService =
            (OrderService) injector.getInstance(OrderService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("orders", orderService.getUserOrders(USER_ID));
        req.getRequestDispatcher("/WEB-INF/views/order/usersOrders.jsp").forward(req, resp);
    }
}
