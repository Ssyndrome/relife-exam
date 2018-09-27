package com.tw.relife.repository;

import com.tw.relife.Action;
import com.tw.relife.RelifeAppHandler;
import com.tw.relife.RelifeMethod;
import com.tw.relife.impl.RelifeAppHandlerImpl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class HandlerRepository {

    private List<Action> actions = new ArrayList<>();

    public HandlerRepository addAction(Action action) {
        this.actions.add(action);
        return this;
    }

    public HandlerRepository addAction(String path, RelifeMethod method, RelifeAppHandler handler) {
        this.actions.add(new Action(path, method, handler));
        return this;
    }

    public RelifeAppHandler build() throws NoSuchFieldException, IllegalAccessException {
        RelifeAppHandler handler = new RelifeAppHandlerImpl();

        Field privateRepository = handler.getClass().getDeclaredField("actions");
        privateRepository.setAccessible(true);
        privateRepository.set(handler, this.actions);

        return handler;
    }
}
