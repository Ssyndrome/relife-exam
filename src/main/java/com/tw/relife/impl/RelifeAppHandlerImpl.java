package com.tw.relife.impl;

import com.tw.relife.Action;
import com.tw.relife.RelifeAppHandler;
import com.tw.relife.RelifeRequest;
import com.tw.relife.RelifeResponse;

import java.util.ArrayList;
import java.util.List;

public class RelifeAppHandlerImpl implements RelifeAppHandler {

    private List<Action> actions = new ArrayList<>();
    private RelifeResponse response = new RelifeResponse(404);

    @Override
    public RelifeResponse process(RelifeRequest request) {
        actions.forEach(action -> {
            if (action.getPath().equals(request.getPath()) && action.getMethod().equals(request.getMethod())) {
                response = action.getHandler().process(request);
            }
        });
        return response;
    }

    public RelifeAppHandler setActions(List<Action> addedActions) {
        actions.addAll(addedActions);
        return this;
    }

}
