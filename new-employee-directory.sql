CREATE DATABASE  IF NOT EXISTS `new_employee_directory`;
USE `new_employee_directory`;

DROP TABLE IF EXISTS `employee`;
DROP TABLE IF EXISTS `department`;

CREATE TABLE `department` (
                              `id` int NOT NULL AUTO_INCREMENT,
                              `name` varchar(45) DEFAULT NULL,
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb3;

CREATE TABLE `employee` (
  `id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `department_id` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb3;

alter table employee
    add constraint employee_department_fk
        foreign key (department_id) references department (id);

INSERT INTO `department` VALUES
                             (1,'Flight Squadrons'),
                             (2,'Interrogation Group'),
                             (3,'Garrison'),
                             (4,'Command'),
                             (5,'Warehouse'),
                             (6,'IT'),
                             (7,'Hangar');

INSERT INTO `employee` VALUES 
	(1,'Darth','Vader','vader@galactic-empire.gov', 4),
	(2,'Moradmin','Bast','m.bast@galactic-empire.gov', 4),
	(3,'Iden','Versio','versio123@mail.com', 1),
	(4,'Wilhuff','Tarkin','supervisor@death-star.com', 4),
    (5,'Ansin','Thobel','trooper5@death-star.com', 3),
    (6,'IT-O',null,null,2),
	(7,'Kela','Neerik','hello-kitty01@mail.com', 1);

DROP TABLE IF EXISTS `authorities`;
DROP TABLE IF EXISTS `users`;

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
                         `username` varchar(50) NOT NULL,
                         `password` char(68) NOT NULL,
                         `enabled` tinyint NOT NULL,
                         PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Inserting data for table `users`
--
-- NOTE: The passwords are encrypted using BCrypt
--
-- A generation tool is avail at: https://www.bcryptcalculator.com/
--
-- Default passwords here are: fun123
--

INSERT INTO `users`
VALUES
    ('foo','{bcrypt}$2a$10$JHuRpQmlayNh9JwcFYxRmunsvNv8j.IiXzw49NN207cMrXM7TtOQC',1),
    ('homer','{bcrypt}$2a$10$7L88cqP/43VuI6lr6UCi2esQHijHPRtRV.4IUNmyl2WNW5odQ/zHa',1),
    ('admin','{bcrypt}$2a$10$DwmR7kTC3HPhei8Cns3UzepVRzmUpIQzsHRKpIHIGlcEMdVXFy2zm',1);


--
-- Table structure for table `authorities`
--

CREATE TABLE `authorities` (
                               `username` varchar(50) NOT NULL,
                               `authority` varchar(50) NOT NULL,
                               UNIQUE KEY `authorities4_idx_1` (`username`,`authority`),
                               CONSTRAINT `authorities4_ibfk_1` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Inserting data for table `authorities`
--

INSERT INTO `authorities`
VALUES
    ('foo','ROLE_USER'),
    ('homer','ROLE_USER'),
    ('homer','ROLE_MANAGER'),
    ('admin','ROLE_USER'),
    ('admin','ROLE_MANAGER'),
    ('admin','ROLE_ADMIN');
