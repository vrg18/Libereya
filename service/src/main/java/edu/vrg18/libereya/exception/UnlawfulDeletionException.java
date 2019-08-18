package edu.vrg18.libereya.exception;

import lombok.Getter;

public class UnlawfulDeletionException extends RuntimeException {

    @Getter
    String message;

    public UnlawfulDeletionException(String message) {
        this.message = message;
    }
}
