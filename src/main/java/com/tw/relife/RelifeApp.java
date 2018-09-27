package com.tw.relife;

import com.tw.relife.exception.RelifeStatusCode;

public class RelifeApp implements RelifeAppHandler {
    private final RelifeAppHandler handler;

    public RelifeApp(RelifeAppHandler handler) {
        if (handler == null) {
            throw new IllegalArgumentException();
        }
        this.handler = handler;
    }

    @Override
    public RelifeResponse process(RelifeRequest request) {
        RelifeResponse response;

        try {
            response = handler.process(request);
        } catch (Exception exception) {
            if (exception.getClass().isAnnotationPresent(RelifeStatusCode.class)) {
                int statusCode = exception.getClass().getAnnotation(RelifeStatusCode.class).value();
                response = new RelifeResponse(statusCode);
            } else {
                response = new RelifeResponse(500);
            }
        }

        return response;
    }
}
