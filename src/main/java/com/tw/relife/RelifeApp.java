package com.tw.relife;

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
        RelifeResponse response = null;

        try {
            handler.process(request);
        } catch (Exception exception) {
            response = new RelifeResponse(500);
        }

        response = response == null ? handler.process(request) : response;
        return response;
    }
}
