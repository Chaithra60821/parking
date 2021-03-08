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

--index - parkingLotName
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

--index - ParkingLotId, ParkingSpotTypeId, NoAvailableParkingSpots, ParkingFloorNumber
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

--index - parkingFloorId, isFree, isActive, isConverted, ParkingSpotRowNumber, ParkingSpotColNumber, LevelId,
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

--index - VehicleRegNumber, VehicleTypeId
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

--index - vechileid status
CREATE TABLE T_Parking (
  ParkingTransId INT PRIMARY KEY,
  VehicleId INT NOT NULL,
  Status VARCHAR(250) NOT NULL,
  IsRoyal bit NOT NULL,
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