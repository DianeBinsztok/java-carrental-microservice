CREATE TABLE booking

(

    id    INT PRIMARY KEY NOT NULL AUTO_INCREMENT,

    startDate DATE NOT NULL,

    endDate DATE NOT NULL,

    VehicleId INT NOT NULL,

    userId INT NOT NULL,

);