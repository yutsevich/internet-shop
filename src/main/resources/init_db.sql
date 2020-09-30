CREATE SCHEMA `internet_shop` DEFAULT CHARACTER SET utf8;
CREATE TABLE `internet_shop`.`products`
(
    `product_id`   BIGINT(11)     NOT NULL AUTO_INCREMENT,
    `product_name` VARCHAR(225)   NOT NULL,
    `price`        DECIMAL(10, 2) NOT NULL,
    `deleted`      TINYINT        NOT NULL DEFAULT FALSE,
    PRIMARY KEY (`product_id`)
);
CREATE TABLE `internet_shop`.`users`
(
    `user_id`   BIGINT(11)     NOT NULL AUTO_INCREMENT,
    `user_name` VARCHAR(225)   NOT NULL,
    `login`     VARCHAR(225)   NOT NULL,
    `password`  VARCHAR(225)   NOT NULL,
    `salt`      VARBINARY(500) NOT NULL,
    `deleted`   TINYINT        NOT NULL DEFAULT FALSE,
    PRIMARY KEY (`user_id`)
);
CREATE TABLE `internet_shop`.`roles`
(
    `role_id`   BIGINT(11)   NOT NULL AUTO_INCREMENT,
    `role_name` VARCHAR(225) NOT NULL,
    `deleted`   TINYINT      NOT NULL DEFAULT FALSE,
    PRIMARY KEY (`role_id`)
);
CREATE TABLE `internet_shop`.`users_roles`
(
    `role_id` BIGINT(11) NOT NULL,
    `user_id` BIGINT(11) NOT NULL
);
CREATE TABLE `internet_shop`.`orders`
(
    `order_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
    `user_id`  BIGINT(11) NOT NULL,
    `deleted`  TINYINT    NOT NULL DEFAULT FALSE,
    PRIMARY KEY (`order_id`)
);
CREATE TABLE `internet_shop`.`orders_products`
(
    `order_id`   BIGINT(11) NOT NULL,
    `product_id` BIGINT(11) NOT NULL
);
CREATE TABLE `internet_shop`.`shopping_carts`
(
    `cart_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
    `user_id` BIGINT(11) NOT NULL,
    `deleted` TINYINT    NOT NULL DEFAULT FALSE,
    PRIMARY KEY (`cart_id`)
);
CREATE TABLE `internet_shop`.`shopping_carts_products`
(
    `cart_id`    BIGINT(11) NOT NULL,
    `product_id` BIGINT(11) NOT NULL
);
INSERT INTO `internet_shop`.`roles` (`role_name`)
VALUES ('ADMIN');
INSERT INTO `internet_shop`.`roles` (`role_name`)
VALUES ('USER');
