package com.tw.relife.builder;

import com.sun.corba.se.impl.ior.OldJIDLObjectKeyTemplate;
import com.tw.relife.Action;
import com.tw.relife.RelifeAppHandler;
import com.tw.relife.RelifeMethod;
import com.tw.relife.repository.HandlerRepository;

import java.util.Arrays;
import java.util.Objects;

public class RelifeMvcHandlerBuilder {

    private HandlerRepository handlerRepository;

    public RelifeMvcHandlerBuilder() {
        this.handlerRepository = new HandlerRepository();
    }

    public HandlerRepository addAction(String path, RelifeMethod method, RelifeAppHandler handler) {
        checkIfNullParameter(path, method, handler);
        handlerRepository.addAction(new Action(path, method, handler));
        return handlerRepository;
    }

    public RelifeAppHandler build() throws NoSuchFieldException, IllegalAccessException {
        return new HandlerRepository().build();
    }

    private void checkIfNullParameter(Object... objects) {
        if (Arrays.stream(objects).anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException();
        }
    }
}
