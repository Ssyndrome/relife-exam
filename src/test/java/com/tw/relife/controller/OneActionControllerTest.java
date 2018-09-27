package com.tw.relife.controller;

import com.tw.relife.*;
import com.tw.relife.annotation.RelifeMvcHandlerBuilder;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class OneActionControllerTest {
    @Test
    void should_access_handler_with_controller_and_succeed_get_response() throws NoSuchMethodException, InstantiationException, IllegalAccessException, ClassNotFoundException, InvocationTargetException {
        RelifeAppHandler handler = new RelifeMvcHandlerBuilder()
                .addController(OneActionController.class)
                .build();
        RelifeApp app = new RelifeApp(handler);

        RelifeResponse response = app.process(new RelifeRequest("/path", RelifeMethod.GET));

        assertEquals(200, response.getStatus());
        assertEquals("Hello from /path", response.getContent());
    }

    @Test
    void should_throw_IllegalArgumentException_as_addConstructor_input_null() {
        assertThrows(IllegalArgumentException.class,
                () -> new RelifeMvcHandlerBuilder()
                        .addController(null)
                        .build());
    }
}