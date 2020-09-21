CREATE SCHEMA `internet_shop` DEFAULT CHARACTER SET utf8 ;
CREATE TABLE `internet_store`.`products` (
   `product_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
   `product_name` VARCHAR(225) NOT NULL,
   `price` DECIMAL(10,2) NOT NULL,
   `deleted` TINYINT NOT NULL DEFAULT 0,
   PRIMARY KEY (`product_id`));
