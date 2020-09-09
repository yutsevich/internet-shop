package com.internet.shop;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.Product;
import com.internet.shop.model.ShoppingCart;
import com.internet.shop.model.User;
import com.internet.shop.servise.interfaces.OrderService;
import com.internet.shop.servise.interfaces.ProductService;
import com.internet.shop.servise.interfaces.ShoppingCartService;
import com.internet.shop.servise.interfaces.UserService;

public class Application {
    private static Injector injector = Injector.getInstance("com.internet.shop");

    public static void main(String[] args) {
        ProductService productService = (ProductService) injector.getInstance(ProductService.class);
        productService.create(new Product("PS3", 650));
        productService.create(new Product("XBox", 900));
        productService.create(new Product("PS4", 1000));

        System.out.println("Just added products");
        System.out.println(productService.getAll());

        productService.deleteById(1L);
        Product ps4 = new Product(productService.get(3L).getName(),
                productService.get(3L).getPrice());
        ps4.setId(3L);
        ps4.setPrice(1100);
        productService.update(ps4);

        System.out.println("After service products");
        System.out.println(productService.getAll());

        //
        UserService userService = (UserService) injector.getInstance(UserService.class);
        User user1 = userService
                .create(new User("user1", "login1", "1"));
        User user2 = userService
                .create(new User("user2", "login2", "2"));
        System.out.println("Just added users");
        System.out.println(userService.getAll());

        //
        ShoppingCartService shoppingCartService =
                (ShoppingCartService) injector.getInstance(ShoppingCartService.class);
        ShoppingCart cart1 = shoppingCartService.create(
                new ShoppingCart(user1.getId()));
        ShoppingCart cart2 = shoppingCartService.create(
                new ShoppingCart(user2.getId()));
        shoppingCartService.addProduct(cart1, productService.get(3L));
        shoppingCartService.addProduct(cart1, productService.get(2L));
        shoppingCartService.addProduct(cart2, productService.get(3L));
        shoppingCartService.addProduct(cart2, productService.get(2L));

        System.out.println("Just added carts");
        System.out.println("First cart: " + shoppingCartService.getByUserId(user1.getId()));
        System.out.println("Second cart: " + shoppingCartService.getByUserId(user2.getId()));
        System.out.println("After service carts");

        shoppingCartService.deleteProduct(cart1, productService.get(3L));
        System.out.println(shoppingCartService.getByUserId(cart1.getId()));

        //
        OrderService orderService = (OrderService) injector.getInstance(OrderService.class);
        orderService.completeOrder(cart1);
        orderService.completeOrder(cart2);

        System.out.println(orderService.getUserOrders(user1.getId()));
        System.out.println(orderService.getUserOrders(user2.getId()));
        System.out.println("After service orders");
        orderService.deleteById(user1.getId());
        System.out.println(orderService.getAll());
    }
}
