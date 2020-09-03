package com.internet.shop;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.Product;
import com.internet.shop.servise.interfaces.ProductService;
import java.util.List;

public class Application {
    private static Injector injector = Injector.getInstance("com.internet.shop");

    public static void main(String[] args) {
        ProductService productService = (ProductService) injector.getInstance(ProductService.class);
        productService.create(new Product("PS3", 650));
        productService.create(new Product("XBox", 900));
        productService.create(new Product("PS4", 1000));

        List<Product> items = productService.getAll();

        System.out.println("Just added products");
        items.forEach(System.out::println);

        productService.delete(1L);
        Product ps4 = productService.get(3L);
        ps4.setPrice(1100);
        productService.update(ps4);

        System.out.println("After service products");
        items.forEach(System.out::println);
    }
}
