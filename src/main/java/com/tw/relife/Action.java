package com.tw.relife;

public class Action {
    private final String path;
    private final RelifeMethod method;
    private final RelifeAppHandler handler;

    public Action(String path, RelifeMethod method, RelifeAppHandler handler) {
        this.path = path;
        this.method = method;
        this.handler = handler;
    }

    public String getPath() {
        return path;
    }

    public RelifeMethod getMethod() {
        return method;
    }

    public RelifeAppHandler getHandler() {
        return handler;
    }
}
