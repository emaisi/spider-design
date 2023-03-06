SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS `sp_mongo`;
CREATE TABLE `sp_mongo` (
  `id` varchar(32) NOT NULL,
  `name` varchar(64) DEFAULT NULL,
  `alias` varchar(32) DEFAULT NULL,
  `host` varchar(64) DEFAULT NULL,
  `port` int(6) DEFAULT NULL,
  `database` varchar(64) DEFAULT NULL,
  `username` varchar(32) DEFAULT NULL,
  `password` varchar(32) DEFAULT NULL,
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
