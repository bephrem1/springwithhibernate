package com.teamtreehouse.giflib.web;

public class FlashMessge {

    private String message;
    private Status status;

    public FlashMessge(String message, Status status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public Status getStatus() {
        return status;
    }

    public static enum Status {
        SUCCESS, FAILURE
    }

}
