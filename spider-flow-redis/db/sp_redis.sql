SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS `sp_redis`;
CREATE TABLE `sp_redis` (
  `id` varchar(32) NOT NULL,
  `host` varchar(64) DEFAULT NULL,
  `alias` varchar(64) DEFAULT NULL,
  `name` varchar(64) DEFAULT NULL,
  `port` int(6) DEFAULT NULL,
  `password` varchar(64) DEFAULT NULL,
  `db_index` int(6) DEFAULT NULL,
  `max_connections` int(6) DEFAULT NULL,
  `max_idle` int(6) DEFAULT NULL,
  `min_idle` int(6) DEFAULT NULL,
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
