package com.internet.shop.controllers;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.ShoppingCart;
import com.internet.shop.model.User;
import com.internet.shop.servise.interfaces.ShoppingCartService;
import com.internet.shop.servise.interfaces.UserService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InjectDataController extends HttpServlet {
    public static final Injector injector = Injector.getInstance("com.internet.shop");
    private UserService userService = (UserService) injector.getInstance(UserService.class);
    private final ShoppingCartService shoppingCartService =
            (ShoppingCartService) injector.getInstance(ShoppingCartService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user1 = userService.create(new User("User1", "log1", "111"));
        User user2 = userService.create(new User("User2", "log2", "222"));
        ShoppingCart shoppingCart1 = shoppingCartService.create(new ShoppingCart(user1.getId()));
        ShoppingCart shoppingCart2 = shoppingCartService.create(new ShoppingCart(user2.getId()));
        req.getRequestDispatcher("/WEB-INF/views/injectData.jsp").forward(req,resp);
    }
}