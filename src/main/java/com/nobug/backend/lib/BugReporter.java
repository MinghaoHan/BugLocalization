package com.nobug.backend.lib;

public class BugReporter {
    private String path;

    public BugReporter(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
