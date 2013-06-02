delimiter $$

CREATE TABLE `TCategory` (
  `CategoryId` bigint(20) NOT NULL AUTO_INCREMENT,
  `UserId` int(20) NOT NULL,
  `Name` varchar(255) NOT NULL,
  `Type` varchar(20) NOT NULL,
  `IgnoreForSummary` bit(1) NOT NULL,
  PRIMARY KEY (`CategoryId`),
  UNIQUE KEY `unique_constraint` (`UserId`,`Name`,`Type`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8$$


delimiter $$

CREATE TABLE `TCategoryAssignment` (
  `CategoryAssignmentId` bigint(20) NOT NULL AUTO_INCREMENT,
  `UserId` int(20) NOT NULL,
  `CategoryId` int(20) NOT NULL,
  `Vendor` varchar(255) NOT NULL,
  `Description` varchar(255) NOT NULL,
  PRIMARY KEY (`CategoryAssignmentId`),
  KEY `TCategoryAssignment_Unq` (`UserId`,`Vendor`,`Description`)
) ENGINE=InnoDB AUTO_INCREMENT=1356 DEFAULT CHARSET=utf8$$

delimiter $$

CREATE TABLE `TTransaction` (
  `TransactionID` bigint(20) NOT NULL AUTO_INCREMENT,
  `UserId` int(20) NOT NULL,
  `TransactionYear` int(4) NOT NULL,
  `TransactionMonth` int(2) NOT NULL,
  `TransactionDay` int(2) NOT NULL,
  `Amount` decimal(10,2) NOT NULL,
  `Description` varchar(255) NOT NULL,
  `Vendor` varchar(255) NOT NULL,
  `ImportSource` varchar(255) NOT NULL,
  PRIMARY KEY (`TransactionID`),
  UNIQUE KEY `TTransaction_Idx` (`UserId`,`TransactionYear`,`TransactionMonth`,`TransactionDay`,`Amount`,`Description`,`Vendor`)
) ENGINE=InnoDB AUTO_INCREMENT=287 DEFAULT CHARSET=utf8$$


delimiter $$

CREATE TABLE `TUser` (
  `UserID` bigint(20) NOT NULL AUTO_INCREMENT,
  `Email` varchar(255) NOT NULL,
  `Password` varchar(255) NOT NULL,
  `Name` varchar(50) NOT NULL,
  PRIMARY KEY (`UserID`),
  UNIQUE KEY `UK_Email` (`Email`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8$$
