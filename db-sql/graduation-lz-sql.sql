/*
SQLyog 企业版 - MySQL GUI v8.14
MySQL - 5.6.35 : Database - graduation_lz
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`graduation_lz` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `graduation_lz`;

/*Table structure for table `lz_activity` */

DROP TABLE IF EXISTS `lz_activity`;

CREATE TABLE `lz_activity` (
  `activity_id` int(20) NOT NULL AUTO_INCREMENT COMMENT '活动主键ID',
  `activity_name` varchar(20) NOT NULL DEFAULT '' COMMENT '活动名',
  `activity_target` int(5) NOT NULL DEFAULT '1' COMMENT '活动对象（对应用户等级）',
  `activity_discount` double NOT NULL DEFAULT '1' COMMENT '活动折扣',
  `activity_state` int(5) NOT NULL DEFAULT '1' COMMENT '活动状态（1：有效  0：无效）',
  `activity_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '活动开始时间',
  PRIMARY KEY (`activity_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='活动信息表';

/*Table structure for table `lz_complain` */

DROP TABLE IF EXISTS `lz_complain`;

CREATE TABLE `lz_complain` (
  `complain_id` int(20) NOT NULL AUTO_INCREMENT COMMENT '投诉ID',
  `complain_user_id` bigint(11) NOT NULL DEFAULT '0' COMMENT '投诉人ID',
  `complain_goods_id` int(20) NOT NULL DEFAULT '0' COMMENT '投诉商品ID',
  `complain_describe` varchar(100) NOT NULL DEFAULT '' COMMENT '投诉信息',
  `complain_start_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '投诉时间',
  `complain_update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '操作时间',
  `complain_state` int(5) NOT NULL DEFAULT '0' COMMENT '留言状态(0;未读;1: 已读)',
  PRIMARY KEY (`complain_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='投诉信息表';

/*Table structure for table `lz_goods` */

DROP TABLE IF EXISTS `lz_goods`;

CREATE TABLE `lz_goods` (
  `goods_id` int(20) NOT NULL AUTO_INCREMENT COMMENT '商品ID',
  `goods_name` varchar(50) NOT NULL DEFAULT '' COMMENT '商品名称',
  `goods_picture` varchar(300) NOT NULL DEFAULT '' COMMENT '商品图片',
  `goods_price` double NOT NULL DEFAULT '0' COMMENT '商品价格',
  `goods_class` varchar(10) NOT NULL DEFAULT '' COMMENT '商品类别',
  `goods_sales` int(5) NOT NULL DEFAULT '0' COMMENT '商品是否促销（1：是  0：否）',
  `goods_gift` mediumtext COMMENT '赠品信息',
  `goods_sales_downtime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '促销截止时间',
  `goods_uptime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '商品添加时间',
  `goods_expire` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '商品过期时间',
  `goods_describe` varchar(100) NOT NULL DEFAULT '' COMMENT '商品描述',
  PRIMARY KEY (`goods_id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='商品信息表';

/*Table structure for table `lz_logger` */

DROP TABLE IF EXISTS `lz_logger`;

CREATE TABLE `lz_logger` (
  `logger_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(20) NOT NULL DEFAULT '0' COMMENT '用户Id',
  `logger_message` varchar(200) NOT NULL DEFAULT '' COMMENT '日志内容',
  `logger_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '日志时间',
  PRIMARY KEY (`logger_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

/*Table structure for table `lz_order` */

DROP TABLE IF EXISTS `lz_order`;

CREATE TABLE `lz_order` (
  `order_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '订单号',
  `user_id` varchar(50) NOT NULL DEFAULT '' COMMENT '用户id',
  `order_details` mediumtext NOT NULL COMMENT '订单详情   Json串',
  `order_totalprice` double NOT NULL DEFAULT '0' COMMENT '订单总价',
  `order_date_start` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '订单创建时间',
  `order_date_end` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '订单支付时间',
  `pay_status_id` tinyint(3) NOT NULL DEFAULT '0' COMMENT '订单状态（0：未支付    1：已支付    2.支付超时）',
  PRIMARY KEY (`order_id`),
  KEY `idx_order_id` (`order_id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='订单表';

/*Table structure for table `lz_shopcart` */

DROP TABLE IF EXISTS `lz_shopcart`;

CREATE TABLE `lz_shopcart` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户Id',
  `goods_id` int(20) NOT NULL DEFAULT '0' COMMENT '商品id',
  `goods_num` int(11) NOT NULL DEFAULT '0' COMMENT '商品数量',
  `start_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '操作时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='购物车表';

/*Table structure for table `lz_stock` */

DROP TABLE IF EXISTS `lz_stock`;

CREATE TABLE `lz_stock` (
  `stock_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `goods_id` int(20) NOT NULL DEFAULT '0' COMMENT '商品ID',
  `goods_ground` int(20) NOT NULL DEFAULT '0' COMMENT '商品上架数',
  `goods_stock_in` int(20) NOT NULL DEFAULT '0' COMMENT '商品入库（库存）数',
  `goods_stock_out` int(20) NOT NULL DEFAULT '0' COMMENT '商品出库数',
  `stock_in_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '入库时间',
  `stock_out_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '出库时间',
  PRIMARY KEY (`stock_id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='库存信息表';

/*Table structure for table `lz_user` */

DROP TABLE IF EXISTS `lz_user`;

CREATE TABLE `lz_user` (
  `user_id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `user_name` varchar(20) NOT NULL DEFAULT '' COMMENT '用户名称',
  `user_password` varchar(20) NOT NULL DEFAULT '' COMMENT '用户密码',
  `user_sex` varchar(5) NOT NULL DEFAULT '' COMMENT '用户性别',
  `user_power` int(3) NOT NULL DEFAULT '1' COMMENT '用户权限等级',
  `user_headurl` varchar(100) NOT NULL DEFAULT '' COMMENT '用户头像',
  `user_account` double NOT NULL DEFAULT '0' COMMENT '用户余额',
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '添加时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT