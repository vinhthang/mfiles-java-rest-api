package mfiles.service;

public class MFilesException extends Error {
    private int statusCode;
    private String message;

    public MFilesException(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
