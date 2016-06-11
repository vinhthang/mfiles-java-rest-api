package mfiles.service;

import mfiles.data.ObjectVersion;

public class ExportResult {
    private final String message;
    private final ObjectVersion result;

    public ExportResult(String message, ObjectVersion result) {
        this.message = message;
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public ObjectVersion getResult() {
        return result;
    }
}
