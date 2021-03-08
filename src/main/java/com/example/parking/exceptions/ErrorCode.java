package com.example.parking.exceptions;

public enum ErrorCode {
    PARKING_ALREADY_EXIST("Sorry Parking Already Created, It CAN NOT be again recreated."),
    PARKING_CREATION("Exception while Creating the Parking lot"),
    PARKING_TRANSACTION("Exception while inseting a record to the parking tranaction table"),
    PARKING_NO_PARKING_SLOT("Exception while inseting a record to the parking tranaction table"),
    PARKING_PARK_EXCEPTION("Exception while parking"),
    PARKING_UNPARK_EXCEPTION("Exception while unparking the car"),
    PARKING_DB_GETPARKINGLOT("Error while getting the parking lot"),
    PARKING_DB_PUTPARKINGLOT("Error while inserting to the parking lot"),
    PARKING_LOT_IS_NOT_PRESENT("Error while getting the parking lot"),
    PARKING_LOT_NOT_CREATED("NOT able to create a parking LOT for the paking LOT {var}"),
    PARKING_SPOT_NOT_PRESENT("Parking spot is not present {var}"),
    PARKING_DB_CON_ERROR("Error while getting the connection")
    ;
    private String message = "";
    private ErrorCode(String s) {
        this.message = s;
    }

    public String getMessage() {
        return message;
    }
}
