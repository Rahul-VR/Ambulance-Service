/*
SQLyog Community v13.1.6 (64 bit)
MySQL - 8.0.31 : Database - ambulance_service
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`ambulance_service` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `ambulance_service`;

/*Table structure for table `ambulance` */

DROP TABLE IF EXISTS `ambulance`;

CREATE TABLE `ambulance` (
  `ambulance_id` int NOT NULL AUTO_INCREMENT,
  `login_id` int DEFAULT NULL,
  `driver` varchar(100) DEFAULT NULL,
  `place` varchar(100) DEFAULT NULL,
  `phone` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `latitude` varchar(100) DEFAULT NULL,
  `longitude` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`ambulance_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

/*Data for the table `ambulance` */

insert  into `ambulance`(`ambulance_id`,`login_id`,`driver`,`place`,`phone`,`email`,`latitude`,`longitude`) values 
(4,3,'arunraj','kml','123456789','abc@abc.com','9.9758457,76.2863011','9.9758457,76.2863011'),
(5,6,'aslam','kaloor','9867452310','muhamed@gmail.com','9.9763037,76.2862279','9.9763037,76.2862279'),
(6,9,'santhosh nayar','pattikade','9799999999','santhoshnayanparabil@gmail.com','9.981369062580182','76.29471201595398');

/*Table structure for table `complaint` */

DROP TABLE IF EXISTS `complaint`;

CREATE TABLE `complaint` (
  `complaint_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `complaint` varchar(100) DEFAULT NULL,
  `reply` varchar(100) DEFAULT NULL,
  `date` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`complaint_id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=latin1;

/*Data for the table `complaint` */

insert  into `complaint`(`complaint_id`,`user_id`,`complaint`,`reply`,`date`) values 
(1,1,'fbfdbgfdbg','tttttttttttttttttt','2022-12-13'),
(10,1,'jejej','cdcdscsd','2022-12-19'),
(11,2,'ieiidkekeiek','pending','2022-12-13'),
(12,2,'asdfghii','hai','2022-12-13'),
(13,2,'uuijhhh','pending','2022-12-13'),
(14,2,'ii','pending','2022-12-13'),
(15,2,'vg6','pending','2022-12-13'),
(16,2,'jejwjsjej','pending','2022-12-13'),
(17,2,'zzzezz','pending','2022-12-13'),
(18,1,'hehhee','pending','2022-12-19'),
(19,3,'rf','pending','2022-12-19');

/*Table structure for table `login` */

DROP TABLE IF EXISTS `login`;

CREATE TABLE `login` (
  `login_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `user_type` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`login_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

/*Data for the table `login` */

insert  into `login`(`login_id`,`username`,`password`,`user_type`) values 
(1,'admin','admin','admin'),
(2,'user','user','user'),
(3,'amb','Amb11','ambulance'),
(4,'arun','arun','user'),
(5,'asha','asha','user'),
(6,'ambb','Ambb1','ambulance'),
(7,'abhiraj ','1234','user'),
(8,'rahul','123','user'),
(9,'santhi','Dog123','ambulance');

/*Table structure for table `payment` */

DROP TABLE IF EXISTS `payment`;

CREATE TABLE `payment` (
  `payment_id` int NOT NULL AUTO_INCREMENT,
  `request_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  `amount` varchar(100) DEFAULT NULL,
  `date` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`payment_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

/*Data for the table `payment` */

insert  into `payment`(`payment_id`,`request_id`,`user_id`,`amount`,`date`) values 
(9,12,3,'3333','2022-12-19'),
(10,13,1,'1111','2022-12-19'),
(11,15,4,'100','2023-03-31'),
(12,16,4,'100','2023-03-31');

/*Table structure for table `request` */

DROP TABLE IF EXISTS `request`;

CREATE TABLE `request` (
  `request_id` int NOT NULL AUTO_INCREMENT,
  `ambulace_id` int DEFAULT NULL,
  `patient_id` int DEFAULT NULL,
  `image` varchar(100) DEFAULT NULL,
  `details` varchar(100) DEFAULT NULL,
  `amount` varchar(100) DEFAULT NULL,
  `latitude` varchar(100) DEFAULT NULL,
  `longitude` varchar(100) DEFAULT NULL,
  `date` varchar(100) DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`request_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;

/*Data for the table `request` */

insert  into `request`(`request_id`,`ambulace_id`,`patient_id`,`image`,`details`,`amount`,`latitude`,`longitude`,`date`,`status`) values 
(12,4,3,'static/uploads/1278af73-a1de-4752-ac2a-354092de1b47.jpg','cghhhjjj','3333','9.976045','76.28620500000001','2022-12-19','reject'),
(13,4,1,'static/uploads/e3c15fe5-ee9c-4736-97f9-2d6cc2d72693.jpg','aaaaaa','1111','9.9758457','76.2863011','2022-12-19','approve'),
(14,4,1,'static/uploads/67a034e9-4d82-436b-84fd-c56277e10b65.jpg','aaaaaa','1111','9.9758457','76.2863011','2022-12-19','pending'),
(15,5,4,'static/uploads/d2b6ca84-5971-4eed-b344-4c26116fbb7b.jpg','mental patient ','100','9.9763258','76.2862162','2023-03-31','approve'),
(16,6,4,'static/uploads/48934f33-d77e-46a3-8459-ed6763de16b5.jpg','dogshow','100','9.9763246','76.286215','2023-03-31','approve');

/*Table structure for table `review` */

DROP TABLE IF EXISTS `review`;

CREATE TABLE `review` (
  `review_id` int NOT NULL AUTO_INCREMENT,
  `patient_id` int DEFAULT NULL,
  `ambulance_id` int DEFAULT NULL,
  `rating` varchar(100) DEFAULT NULL,
  `review` varchar(100) DEFAULT NULL,
  `date` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`review_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

/*Data for the table `review` */

insert  into `review`(`review_id`,`patient_id`,`ambulance_id`,`rating`,`review`,`date`) values 
(1,1,2,'2.0','average','2022-12-05'),
(2,2,4,'3.0','good','2022-12-13'),
(3,2,5,'3.0','jjjk','2022-12-16');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `login_id` int DEFAULT NULL,
  `firstname` varchar(100) DEFAULT NULL,
  `lastname` varchar(100) DEFAULT NULL,
  `place` varchar(100) DEFAULT NULL,
  `phone` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `user` */

insert  into `user`(`user_id`,`login_id`,`firstname`,`lastname`,`place`,`phone`,`email`) values 
(2,4,'arun','ann','tvm','9876543210','abc@abc.con'),
(3,5,'asha','asha','kochi','1234567890','abc@abc.conm'),
(4,7,'Abhiraj','M R','CHERAI ','7736705624','abhirajmattuthara@gmail.com'),
(5,8,'Rahul','V R','vpz','8606744829','rahulrajeevan33@gmail.com');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
