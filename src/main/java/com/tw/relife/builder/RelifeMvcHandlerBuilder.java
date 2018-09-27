package com.tw.relife.builder;

import com.tw.relife.Action;
import com.tw.relife.RelifeAppHandler;
import com.tw.relife.RelifeMethod;
import com.tw.relife.impl.RelifeAppHandlerImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class RelifeMvcHandlerBuilder {

    private List<Action> actions = new ArrayList<>();

    public RelifeMvcHandlerBuilder addAction(String path, RelifeMethod method, RelifeAppHandler handler) {
        checkIfNullParameter(path, method, handler);
        actions.add(new Action(path, method, handler));
        return this;
    }

    public RelifeAppHandler build() {
        return new RelifeAppHandlerImpl().setActions(this.actions);
    }

    private void checkIfNullParameter(Object... objects) {
        if (Arrays.stream(objects).anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException();
        }
    }
}
