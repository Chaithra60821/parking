package com.example.parking.exceptions;

public class ParkingException extends Exception{
    private ErrorCode errorCode;
    private Object[] errorParam;

    public ParkingException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public ParkingException(String message){
        super(message);
    }

    //TODo : fix this
    public ParkingException(ErrorCode errorCode, String message, Throwable throwable) {
        super(message, throwable);
        this.errorCode = errorCode;
    }

    public ParkingException(ErrorCode errorCode, String message, Object[] errorParam) {
        super(message);
        this.errorCode = errorCode;
        this.errorParam = errorParam;
    }

    public ParkingException(ErrorCode errorCode, String message, Object[] errorParam, Throwable throwable) {
        super(message, throwable);
        this.errorCode = errorCode;
        this.errorParam = errorParam;
    }

    public ParkingException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
