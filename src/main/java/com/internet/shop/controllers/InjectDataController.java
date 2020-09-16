package com.internet.shop.controllers;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.Product;
import com.internet.shop.model.Role;
import com.internet.shop.model.ShoppingCart;
import com.internet.shop.model.User;
import com.internet.shop.servise.interfaces.ProductService;
import com.internet.shop.servise.interfaces.ShoppingCartService;
import com.internet.shop.servise.interfaces.UserService;
import java.io.IOException;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InjectDataController extends HttpServlet {
    public static final Injector injector = Injector.getInstance("com.internet.shop");
    private final UserService userService = (UserService) injector.getInstance(UserService.class);
    private final ShoppingCartService shoppingCartService =
            (ShoppingCartService) injector.getInstance(ShoppingCartService.class);
    private final ProductService productService =
            (ProductService) injector.getInstance(ProductService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user1 = userService.create(new User("User1", "log1", "111"));
        user1.setRoles(Set.of(Role.of("USER")));
        User admin = userService.create(new User("admin", "admin", "222"));
        admin.setRoles(Set.of(Role.of("ADMIN")));
        shoppingCartService.create(new ShoppingCart(user1.getId()));
        shoppingCartService.create(new ShoppingCart(admin.getId()));
        productService.create(new Product("apple", 3));
        productService.create(new Product("peach", 4));
        productService.create(new Product("watermelon", 9));

        req.getRequestDispatcher("/WEB-INF/views/injectData.jsp").forward(req,resp);
    }
}
