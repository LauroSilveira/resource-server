package com.lauro.resource.server.exception;

public class TaskCreationException extends RuntimeException {
    public TaskCreationException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
