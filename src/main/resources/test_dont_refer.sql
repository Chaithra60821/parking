CREATE TABLE VehicleType (
  VehicleTypeId INT PRIMARY KEY,
  VehicleTypeName VARCHAR(250) NOT NULL,
  CreatedAt datetime2 DEFAULT CURRENT_TIMESTAMP,
  UpdatedAt datetime2  NOT NULL
);

CREATE TABLE ParkingSpotType (
  ParkingSpotTypeId INT PRIMARY KEY,
  ParkingSpotTypeName VARCHAR(250) NOT NULL,
  CreatedAt datetime2 DEFAULT CURRENT_TIMESTAMP,
  UpdatedAt datetime2  NOT NULL
);

CREATE TABLE Strategy (
  StrategyId INT PRIMARY KEY,
  StrategyName VARCHAR(250) NOT NULL,
  CreatedAt datetime2 DEFAULT CURRENT_TIMESTAMP,
  UpdatedAt datetime2  NOT NULL
);

CREATE TABLE ParkingLot (
  ParkingLotId INT PRIMARY KEY,
  ParkingLotName VARCHAR(250) NOT NULL,
  NoOfFloors INT NOT NULL,
  StrategyId VARCHAR(250) NOT NULL,
  isActive NOT NULL,
  LockVersion TINYINT NOT NULL,
  CreatedAt datetime2 DEFAULT CURRENT_TIMESTAMP,
  UpdatedAt datetime2  NOT NULL

  CONSTRAINT UQ_ParkingLot UNIQUE(ParkingLotName),
  CONSTRAINT FK_ParkingLot FOREIGN KEY (StrategyId) REFERENCES Strategy(StrategyId)
);


CREATE TABLE ParkingFloor (
  ParkingFloorId INT NOT NULL,
  ParkingFloorNumber INT NOT NULL,
  ParkingLotId INT NOT NULL,
  ParkingSpotTypeId INT NOT NULL,
  NoParkingSpots INT NOT NULL,
  NoAvailableParkingSpots INT NOT NULL,
  bit isActive NOT NULL,
  LockVersion TINYINT NOT NULL,
  CreatedAt datetime2 DEFAULT CURRENT_TIMESTAMP,
  UpdatedAt datetime2  NOT NULL

  CONSTRAINT PK_ParkingFloor PRIMARY KEY (ParkingFloorId),
  CONSTRAINT UQ_ParkingFloor UNIQUE(ParkingFloorNumber, ParkingLotId),
  CONSTRAINT FK_ParkingFloor_1 FOREIGN KEY (ParkingLotId) REFERENCES ParkingLot(ParkingLotId),
  CONSTRAINT FK_ParkingFloor_2 FOREIGN KEY (ParkingSpotTypeId) REFERENCES ParkingSpotType(ParkingSpotTypeId)
);

CREATE TABLE ParkingSpot (
  ParkingSpotId INT NOT NULL,
  ParkingSpotNumber INT NOT NULL,
  ParkingSpotTypeId INT NOT NULL,
  LevelId TINYINT NOT NULL,
  isConverted bit NOT NULL,
  isFree bit NOT NULL,
  isActive bit NOT NULL,
  LockVersion TINYINT NOT NULL,
  CreatedAt datetime2 DEFAULT CURRENT_TIMESTAMP,
  UpdatedAt datetime2  NOT NULL
  CONSTRAINT PK_ParkingSpot PRIMARY KEY (ParkingSpotId),
  CONSTRAINT UQ_ParkingFloor UNIQUE(ParkingSpotNumber, ParkingFloorId),
  CONSTRAINT FK_ParkingSpot_2 FOREIGN KEY (ParkingSpotNumber) REFERENCES ParkingFloor(ParkingFloorId),
  CONSTRAINT FK_ParkingSpot_3 FOREIGN KEY (ParkingSpotTypeId) REFERENCES ParkingSpotType(ParkingSpotTypeId),
);

CREATE TABLE Vehicle (
  VehicleId INT AUTO_INCREMENT PRIMARY KEY,
  VehicleRegNumber VARCHAR(250) NOT NULL,
  VehicleTypeId INT NOT NULL,
  ParkingSpotId INT NOT NULL,
  Count INT,
  LockVersion TINYINT NOT NULL,
  CreatedAt datetime2 DEFAULT CURRENT_TIMESTAMP,
  UpdatedAt datetime2  NOT NULL

  CONSTRAINT FK_Vehicle_1 FOREIGN KEY (VehicleTypeId) REFERENCES VehicleType(VehicleTypeId),
  CONSTRAINT FK_Vehicle_2 FOREIGN KEY (ParkingSpotId) REFERENCES ParkingSpot(ParkingSpotId),
);

CREATE TABLE T_Parking (
  ParkingTransId INT PRIMARY KEY,
  VehicleId INT NOT NULL,
  Status VARCHAR(250) NOT NULL,
  LockVersion TINYINT NOT NULL,
  CreatedAt datetime2 DEFAULT CURRENT_TIMESTAMP,
  UpdatedAt datetime2  NOT NULL

  CONSTRAINT FK_T_Parking_1 FOREIGN KEY (VehicleId) REFERENCES Vehicle(VehicleId)
);

CREATE TABLE T_Converted_Parking (
  ParkingSpotId INT NOT NULL,
  ConvertedParkingId INT NOT NULL,
  LockVersion TINYINT NOT NULL,
  CreatedAt datetime2 DEFAULT CURRENT_TIMESTAMP,
  UpdatedAt datetime2  NOT NULL

  CONSTRAINT FK_T_Parking_1 FOREIGN KEY (VehicleId) REFERENCES Vehicle(VehicleId)
);

============

CREATE TABLE ParkingLot (
  ParkingLotId VARCHAR(50) PRIMARY KEY,
  ParkingLotName VARCHAR(250) NOT NULL,
  NoOfFloors INT NOT NULL,
  StrategyId VARCHAR(250) NOT NULL,
  isActive bit NOT NULL,
  LockVersion TINYINT NOT NULL,
  CreatedAt datetime2 DEFAULT CURRENT_TIMESTAMP,
  UpdatedAt datetime2  NOT NULL,

  CONSTRAINT UQ_ParkingLot UNIQUE(ParkingLotName)
);

INSERT INTO ParkingLot (ParkingLotId, ParkingLotName,  NoOfFloors, isActive,StrategyId, LockVersion, UpdatedAt)
VALUES ('parkinglot1','lo11', 2, 1, 1, 1, GETDATE())

CREATE TABLE ParkingSpotType (
  ParkingSpotTypeId INT PRIMARY KEY,
  ParkingSpotTypeName VARCHAR(250) NOT NULL,
  CreatedAt datetime2 DEFAULT CURRENT_TIMESTAMP,
  UpdatedAt datetime2  NOT NULL
);

INSERT INTO ParkingSpotType (ParkingSpotTypeId, ParkingSpotTypeName, UpdatedAt)
VALUES(1, 'BIKE', GETDATE()),
(2, 'CAR', GETDATE());

CREATE TABLE ParkingFloor (
  ParkingFloorId varchar(50) PRIMARY KEY,
  ParkingFloorNumber INT NOT NULL,
  ParkingLotId varchar(50) NOT NULL,
  ParkingSpotTypeId INT NOT NULL,
  NoParkingSpots INT NOT NULL,
  NoAvailableParkingSpots INT NOT NULL,
  isActive bit NOT NULL,
  LockVersion TINYINT NOT NULL,
  CreatedAt datetime2 DEFAULT CURRENT_TIMESTAMP,
  UpdatedAt datetime2  NOT NULL,

  CONSTRAINT UQ_ParkingFloor UNIQUE(ParkingFloorNumber, ParkingLotId, ParkingSpotTypeId),
  CONSTRAINT FK_ParkingFloor_1 FOREIGN KEY (ParkingLotId) REFERENCES ParkingLot(ParkingLotId),
  CONSTRAINT FK_ParkingFloor_2 FOREIGN KEY (ParkingSpotTypeId) REFERENCES ParkingSpotType(ParkingSpotTypeId)
);

INSERT INTO ParkingFloor (ParkingFloorId, ParkingFloorNumber,ParkingLotId,ParkingSpotTypeId, NoParkingSpots, NoAvailableParkingSpots, isActive, LockVersion, UpdatedAt)
VALUES ('PF12', 1, 'parkinglot1', 2,50, 50, 1, 1, GETDATE());

CREATE TABLE ParkingSpot (
  ParkingSpotId varchar(50) PRIMARY KEY,
  ParkingSpotRowNumber INT NOT NULL,
  ParkingSpotColNumber INT NOT NULL,
  ParkingFloorId varchar(50) NULL,
  ParkingSpotTypeId INT NOT NULL,
  LevelId TINYINT NOT NULL,
  IsConverted bit NOT NULL,
  IsFree bit NOT NULL,
  IsActive bit NOT NULL,
  LockVersion TINYINT NOT NULL,
  CreatedAt datetime2 DEFAULT CURRENT_TIMESTAMP,
  UpdatedAt datetime2  NOT NULL,
  CONSTRAINT UQ_ParkingSpot UNIQUE(ParkingSpotRowNumber, ParkingSpotColNumber),
  CONSTRAINT FK_ParkingSpot_2 FOREIGN KEY (ParkingFloorId) REFERENCES ParkingFloor(ParkingFloorId),
  CONSTRAINT FK_ParkingSpot_3 FOREIGN KEY (ParkingSpotTypeId) REFERENCES ParkingSpotType(ParkingSpotTypeId),
);

CREATE TABLE Vehicle (
  VehicleId INT AUTO_INCREMENT PRIMARY KEY,
  VehicleRegNumber VARCHAR(250) NOT NULL,
  VehicleTypeId INT NOT NULL,
  ParkingSpotId varchar(50) NOT NULL,
  Count INT,
  LockVersion TINYINT NOT NULL,
  CreatedAt datetime2 DEFAULT CURRENT_TIMESTAMP,
  UpdatedAt datetime2  NOT NULL

  CONSTRAINT FK_Vehicle_1 FOREIGN KEY (VehicleTypeId) REFERENCES VehicleType(VehicleTypeId),
  CONSTRAINT FK_Vehicle_2 FOREIGN KEY (ParkingSpotId) REFERENCES ParkingSpot(ParkingSpotId),
);

IF NOT EXISTS (SELECT * FROM Vehicle WHERE VehicleRegNumber = ?)
    INSERT INTO Vehicle (VehicleRegNumber, VehicleTypeId, ParkingSpotId, Count)
    VALUES ('reg', 1, 1, 1)
ELSE
    UPDATE Vehicle
    SET Count = Count + 1
    WHERE VehicleRegNumber = ?


INSERT INTO Vehicle (VehicleRegNumber, VehicleTypeId, ParkingSpotId, Count)
VALUES ('reg', 1, 1, @Count)

CREATE TABLE T_Parking (
  ParkingTransId INT PRIMARY KEY,
  VehicleId INT NOT NULL,
  Status VARCHAR(250) NOT NULL,
  LockVersion TINYINT NOT NULL,
  CreatedAt datetime2 DEFAULT CURRENT_TIMESTAMP,
  UpdatedAt datetime2  NOT NULL

  CONSTRAINT FK_T_Parking_1 FOREIGN KEY (VehicleId) REFERENCES Vehicle(VehicleId)
);

CREATE TABLE T_Converted_Parking (
  ParkingSpotId INT NOT NULL,
  ConvertedParkingId INT NOT NULL,
  LockVersion TINYINT NOT NULL,
  CreatedAt datetime2 DEFAULT CURRENT_TIMESTAMP,
  UpdatedAt datetime2  NOT NULL

  CONSTRAINT FK_T_Parking_1 FOREIGN KEY (VehicleId) REFERENCES Vehicle(VehicleId)
);

INSERT INTO ParkingSpot (ParkingSpotId, ParkingSpotRowNumber, ParkingSpotColNumber, ParkingFloorId, ParkingSpotTypeId,
LevelId, IsConverted, IsFree, IsActive, LockVersion, UpdatedAt)
VALUES('PS1',1,'PF11',1,0,0,1,1,1, GETDATE());

//normal parking
UPDATE ParkingSpot
SET IsFree = true
where ParkingSpotId =
(select top 1 ParkingSpotId from ParkingSpot
INNER JOIN
(select  top 1 ParkingFloorId from ParkingFloor where
ParkingLotId = 'parkinglot1' and IsActive = true and ParkingSpotTypeId = 1 and NoAvailableParkingSpots>0
order by ParkingFloorNumber ASC) pf on pf.ParkingFloorId = ParkingSpot.ParkingFloorId
where IsFree=true and IsActive=true and IsConverted =false
ORDER by ParkingSpotRowNumber ASC, ParkingSpotColNumber ASC, LevelId ASC)

//elderParking
UPDATE ParkingSpot
SET IsFree = true
where ParkingSpotId =
(select top 1 ParkingSpotId from ParkingSpot
INNER JOIN
(select  top 1 ParkingFloorId from ParkingFloor where
ParkingLotId = 'parkinglot1' and IsActive = true and ParkingSpotTypeId = 1 and NoAvailableParkingSpots>0
order by ParkingFloorNumber ASC) pf on pf.ParkingFloorId = ParkingSpot.ParkingFloorId
where IsFree=true and IsActive=true and IsConverted =false and LevelId = 0
ORDER by ParkingSpotRowNumber ASC, ParkingSpotColNumber ASC, LevelId ASC)

----
UPDATE ParkingSpot
SET IsFree = true
OUTPUT ParkingSpot.ParkingSpotId
where ParkingSpotId =
(select top 1 ParkingSpotId from ParkingSpot
INNER JOIN
(select  top 1 ParkingFloorId from ParkingFloor where
ParkingLotId = 'parkinglot1' and IsActive = true and ParkingSpotTypeId = 1 and NoAvailableParkingSpots>0
order by ParkingFloorNumber ASC) pf on pf.ParkingFloorId = ParkingSpot.ParkingFloorId
where IsFree=true and IsActive=true and IsConverted =false and LevelId = 0
ORDER by ParkingSpotRowNumber ASC, ParkingSpotColNumber ASC, LevelId ASC)

//car - bike
converting car - bike
-> if bikes are filled
-> getNearparkingSpotCar
-> insert emtabel -> parkingSpotid - 5 bike
-> isConverted = true



//VIP
select top 1 * from ParkingSpot
INNER JOIN
(select  top 1 ParkingFloorId from ParkingFloor where
ParkingLotId = 'parkinglot1' and IsActive = true and ParkingSpotTypeId = 1 and NoAvailableParkingSpots>0
order by ParkingFloorNumber ASC) pf on pf.ParkingFloorId = ParkingSpot.ParkingFloorId
where IsFree=true and IsActive=true and IsConverted =false and LevelId = 0
ORDER by ParkingSpotRowNumber ASC, ParkingSpotColNumber ASC, LevelId ASC


IF NOT EXISTS (SELECT * FROM Vehicle WHERE VehicleRegNumber = '1')
    INSERT INTO Vehicle (VehicleRegNumber, VehicleTypeId, ParkingSpotId, Count)
    VALUES ('reg', 1, 1, 1)
ELSE
    UPDATE Vehicle
    SET Count = Count + 1
    WHERE VehicleRegNumber = '1'