CREATE DATABASE  IF NOT EXISTS `new_employee_directory`;
USE `new_employee_directory`;

DROP TABLE IF EXISTS `employee`;
DROP TABLE IF EXISTS `department`;

CREATE TABLE `department` (
                              `id` int NOT NULL AUTO_INCREMENT,
                              `name` varchar(45) DEFAULT NULL,
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `employee` (
  `id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `department_id` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

alter table employee
    add constraint employee_department_fk
        foreign key (department_id) references department (id);

INSERT INTO `department` VALUES
                             (1,'Warehouse'),
                             (2,'IT');

INSERT INTO `employee` VALUES 
	(1,'Homer','Simpson','dad@springfield.com', 2),
	(2,'Trupti','Bose','bose@mail.com', 2),
	(3,'Foo','Baz','baz@mail.com', 1),
	(4,'Vasya','Pupkin','vasya@mail.ru', 2),
	(5,'Bart','Simpson','karamba@springfield.com', 1);

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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

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
