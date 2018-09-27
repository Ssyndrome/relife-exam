package com.tw.relife.builder;

import com.tw.relife.Action;
import com.tw.relife.RelifeAppHandler;
import com.tw.relife.RelifeMethod;
import com.tw.relife.repository.HandlerRepository;

public class RelifeMvcHandlerBuilder {

    private HandlerRepository handlerRepository;

    public RelifeMvcHandlerBuilder() {
        this.handlerRepository = new HandlerRepository();
    }

    public HandlerRepository addAction(String path, RelifeMethod method, RelifeAppHandler handler) {
        handlerRepository.addAction(new Action(path, method, handler));
        return handlerRepository;
    }

    public RelifeAppHandler build() throws NoSuchFieldException, IllegalAccessException {
        return new HandlerRepository().build();
    }
}
