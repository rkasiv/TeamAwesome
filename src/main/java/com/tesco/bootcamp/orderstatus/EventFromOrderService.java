package com.tesco.bootcamp.orderstatus;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Bradley on 12/01/2017.
 */
public class EventFromOrderService {

    private String timestamp;
    private int status;
    private String error;
    private String exception;
    private String message;
    private String path;

    @JsonCreator
    public EventFromOrderService(
            @JsonProperty("timestamp") String timestamp,
            @JsonProperty("status") int status,
            @JsonProperty("error") String error,
            @JsonProperty("exception") String exception,
            @JsonProperty("message") String message,
            @JsonProperty("path") String path )
    {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.exception = exception;
        this.message = message;
        this.path = path;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getException() {
        return exception;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }
}
