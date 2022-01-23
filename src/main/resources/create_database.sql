CREATE DATABASE `family-budget` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_polish_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

CREATE TABLE `transaction` (
                               `id` int NOT NULL AUTO_INCREMENT,
                               `type` varchar(20) COLLATE utf8mb4_polish_ci NOT NULL,
                               `description` varchar(45) COLLATE utf8mb4_polish_ci NOT NULL,
                               `amount` decimal(10,2) NOT NULL,
                               `date` date NOT NULL,
                               PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_polish_ci;
