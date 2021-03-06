package com.tw.relife.impl;

import com.tw.relife.*;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class RelifeAppHandlerImpl implements RelifeAppHandler {

    private List<Action> actions = new ArrayList<>();

    @Override
    public RelifeResponse process(RelifeRequest request) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Action notFoundAction = new Action("", null, someRequest -> new RelifeResponse(404));
        return actions.stream()
                .filter(action ->
                        action.getPath().equals(request.getPath()) && action.getMethod().equals(request.getMethod())
                )
                .findFirst()
                .orElse(notFoundAction).getHandler().process(request);
    }

    public RelifeAppHandler setActions(List<Action> addedActions) {
        actions.addAll(addedActions);
        return this;
    }

}
