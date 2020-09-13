package com.internet.shop.controllers;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.Order;
import com.internet.shop.model.Product;
import com.internet.shop.servise.interfaces.OrderService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetOrderDetailsController extends HttpServlet {
    private static final Long ORDER_ID = 1L;
    private static final Injector injector = Injector.getInstance("com.internet.shop");
    private final OrderService orderService =
            (OrderService) injector.getInstance(OrderService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Order order = orderService.get(ORDER_ID);
        req.setAttribute("order", order);
        req.setAttribute("sum", order.getProducts().stream()
                .map(Product::getPrice)
                .reduce(Double::sum).get());
        req.getRequestDispatcher("/WEB-INF/views/order/details.jsp").forward(req, resp);
    }
}
