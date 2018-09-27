package com.tw.relife;

import com.tw.relife.builder.RelifeMvcHandlerBuilder;
import com.tw.relife.exception.SampleNotValidException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RelifeHandlerBuilderTest {
    @Test
    void should_match_nothing_as_no_handler_in_handler_builder() throws NoSuchFieldException, IllegalAccessException {
        RelifeAppHandler handler = new RelifeMvcHandlerBuilder().build();
        RelifeApp app = new RelifeApp(handler);
        app.process(new RelifeRequest("/path", RelifeMethod.GET));
    }

    @Test
    void should_match_pointed_handler_with_request_path_and_method() throws NoSuchFieldException, IllegalAccessException {
        RelifeAppHandler handler = new RelifeMvcHandlerBuilder()
                .addAction(
                        "/path",
                        RelifeMethod.GET,
                        request -> new RelifeResponse(200, "Hello", "text/plain")).build();
        RelifeApp app = new RelifeApp(handler);
        RelifeResponse response = app.process(new RelifeRequest("/path", RelifeMethod.GET));
        assertEquals(200, response.getStatus());
        assertEquals("Hello", response.getContent());
    }

    @Test
    void should_return_404_response_when_nothing_match_handler() throws NoSuchFieldException, IllegalAccessException {
        RelifeAppHandler handler = new RelifeMvcHandlerBuilder().build();
        RelifeApp app = new RelifeApp(handler);
        RelifeResponse response = app.process(new RelifeRequest("/path", RelifeMethod.GET));
        assertEquals(404, response.getStatus());

    }

    @Test
    void should_get_500_request_when_throw_exception_in_handler() throws NoSuchFieldException, IllegalAccessException {
        RelifeAppHandler handler = new RelifeMvcHandlerBuilder().
                addAction(
                        "/path",
                        RelifeMethod.GET,
                        request -> { throw new RuntimeException("error occurred"); }
                ).build();
        RelifeApp app = new RelifeApp(handler);
        RelifeResponse response = app.process(new RelifeRequest("/path", RelifeMethod.GET));
        assertEquals(500, response.getStatus());
    }

    @Test
    void should_get_related_request_when_throw_annotated_exception_in_handler() throws NoSuchFieldException, IllegalAccessException {
        RelifeAppHandler handler = new RelifeMvcHandlerBuilder().
                addAction(
                        "/path",
                        RelifeMethod.GET,
                        request -> { throw new SampleNotValidException(); }
                ).build();
        RelifeApp app = new RelifeApp(handler);
        RelifeResponse response = app.process(new RelifeRequest("/path", RelifeMethod.GET));
        assertEquals(666, response.getStatus());
    }
}
