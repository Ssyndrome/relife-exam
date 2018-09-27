package com.tw.relife.annotation;

import com.tw.relife.*;
import com.tw.relife.impl.RelifeAppHandlerImpl;
import org.omg.CORBA.portable.ResponseHandler;

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

    public RelifeMvcHandlerBuilder addController(Class controllerClass) {
        checkIfNullParameter(controllerClass);
        Arrays.stream(controllerClass.getDeclaredMethods())
                .forEach(method -> {
                    RelifeRequestMapping annotation = method.getDeclaredAnnotation(RelifeRequestMapping.class);
                    String annotatedPath = annotation.value();
                    RelifeMethod annotatedMethod = annotation.method();
                    RelifeAppHandler annotatedHandler = request -> {
                        method.setAccessible(true);
                        return (RelifeResponse) method.invoke(controllerClass.newInstance(), request);
                    };
                    actions.add(
                            new Action(
                                    annotatedPath,
                                    annotatedMethod,
                                    annotatedHandler
                            )
                    );
                });
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
