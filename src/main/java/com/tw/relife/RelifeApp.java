package com.tw.relife;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class RelifeApp implements RelifeAppHandler {
    private final RelifeAppHandler handler;

    public RelifeApp(RelifeAppHandler handler) {
        // TODO: You can start here
        if (handler == null) {
            throw new IllegalArgumentException();
        }
        this.handler = handler;
    }

    @Override
    public RelifeResponse process(RelifeRequest request) {
        // TODO: You can start here
        try {
            handler.process(request);
        } catch (Exception exception) {
            RelifeResponse response = new RelifeResponse(500);
            return response;
        }
        RelifeResponse response = new RelifeResponse(200);
        return response;
    }
}
