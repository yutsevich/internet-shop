package com.internet.shop.controllers;

import com.internet.shop.lib.Injector;
import com.internet.shop.servise.interfaces.ShoppingCartService;
import com.internet.shop.servise.interfaces.UserService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteUserController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("com.internet.shop");
    private final UserService userService = (UserService) injector.getInstance(UserService.class);
    private final ShoppingCartService shoppingCartService =
            (ShoppingCartService) injector.getInstance(ShoppingCartService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String userId = req.getParameter("id");
        Long id = Long.parseLong(userId);
        shoppingCartService.delete(shoppingCartService.getByUserId(id));
        userService.deleteById(id);
        resp.sendRedirect(req.getContextPath() + "/user/all");
    }
}
