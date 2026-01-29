package com.example.demo.exception;

public class SpotNotAvailableException extends RuntimeException {
    public SpotNotAvailableException(String message) {
        super(message);
    }

    public SpotNotAvailableException(Long spotId) {
        super(String.format("Parking spot with id %d is not available", spotId));
    }
}
