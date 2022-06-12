CREATE DATABASE if not exists `shopping_mall`;

USE `shopping_mall`;

-- # 用户表:用户id、用户名、昵称、密码、手机号
CREATE TABLE IF NOT EXISTS `users` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(16) NOT NULL,
  `realname` varchar(32) NOT NULL,
  `password` varchar(32) NOT NULL,
  `phone_number` varchar(16) NOT NULL,
  `create_tm` datetime NOT NULL,
  `update_tm` datetime NOT NULL,	
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- # 账户表:用户id、余额、用户id、乐观锁版本号、创建时间、更新时间
CREATE TABLE IF NOT EXISTS `accounts` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(11) NOT NULL,
  `balance` decimal(16,2) NOT NULL,
  `lock_version` bigint(32) NOT NULL,
  `create_tm` datetime NOT NULL,
  `update_tm` datetime NOT NULL,	
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- # 店铺表：店铺id、名称、描述、创建时间、更新时间
CREATE TABLE IF NOT EXISTS `stores` (
    `id` bigint(11) NOT NULL AUTO_INCREMENT,
    `store_name` varchar(16) NOT NULL,
    `store_desc` varchar(256) NOT NULL,
    `create_tm` datetime NOT NULL,
    `update_tm` datetime NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- # 商品表：id、名称、描述、价格、重量、所属店铺id、所属店铺名称、商品状态、创建时间、更新时间
CREATE TABLE IF NOT EXISTS `goods` (
    `id` bigint(11) NOT NULL AUTO_INCREMENT,
    `good_name` varchar(16) NOT NULL,
    `good_desc` varchar(256) NOT NULL,
    `price` decimal(16, 2) NOT NULL,
    `weight` decimal(16, 2) NOT NULL,
    `store_id` bigint(11) NOT NULL,
    `store_name` varchar(16) NOT NULL,
    `status` tinyint(4) NOT NULL,
    `create_tm` datetime NOT NULL,
    `update_tm` datetime NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- # 订单表:id、用户id、商品列表、订单状态、物流状态、总价、创建时间、更新时间
CREATE TABLE IF NOT EXISTS `orders` (
    `id` bigint(11) NOT NULL AUTO_INCREMENT,
    `user_id` bigint(11) NOT NULL,
    `status` tinyint(1) NOT NULL,
    `total_price` decimal(16, 2) NOT NULL,
    `create_tm` datetime NOT NULL,
    `update_tm` datetime NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- # 订单商品中间表:id、订单id、商品id
CREATE TABLE IF NOT EXISTS `order_good` (
    `id` bigint(11) NOT NULL AUTO_INCREMENT,
    `order_id` bigint(11) NOT NULL,
    `good_id` bigint(11) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;