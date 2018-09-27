package com.tw.relife;

import com.tw.relife.annotation.RelifeMvcHandlerBuilder;
import com.tw.relife.annotation.SampleNotValidException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    void should_return_404_response_when_nothing_path_match_handler() throws NoSuchFieldException, IllegalAccessException {
        RelifeAppHandler handler = new RelifeMvcHandlerBuilder()
                .addAction(
                        "/path/apple",
                        RelifeMethod.GET,
                        request -> new RelifeResponse(200, "Hello", "text/plain")).build();
        RelifeApp app = new RelifeApp(handler);
        RelifeResponse response = app.process(new RelifeRequest("/path", RelifeMethod.GET));
        assertEquals(404, response.getStatus());
    }

    @Test
    void should_return_404_response_when_nothing_method_match_handler() throws NoSuchFieldException, IllegalAccessException {
        RelifeAppHandler handler = new RelifeMvcHandlerBuilder()
                .addAction(
                        "/path",
                        RelifeMethod.POST,
                        request -> new RelifeResponse(200, "Hello", "text/plain")).build();
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

    @Test
    void should_throw_IllegalArgumentException_having_null_path_parameter_when_addAction() {
        assertThrows(IllegalArgumentException.class, () -> new RelifeMvcHandlerBuilder().
                addAction(
                        null,
                        RelifeMethod.GET,
                        request -> { throw new SampleNotValidException(); }
                ).build());
    }

    @Test
    void should_throw_IllegalArgumentException_having_null_method_parameter_when_addAction() {
        assertThrows(IllegalArgumentException.class, () -> new RelifeMvcHandlerBuilder().
                addAction(
                        "/path",
                        null,
                        request -> { throw new SampleNotValidException(); }
                ).build());
    }

    @Test
    void should_throw_IllegalArgumentException_having_null_handler_parameter_when_addAction() {
        assertThrows(IllegalArgumentException.class, () -> new RelifeMvcHandlerBuilder().
                addAction(
                        "/path",
                        RelifeMethod.GET,
                        null
                ).build());
    }

    @Test
    void should_return_200_status_response_when_return_null_in_handler() throws NoSuchFieldException, IllegalAccessException {
        RelifeAppHandler handler = new RelifeMvcHandlerBuilder().
                addAction(
                        "/path",
                        RelifeMethod.GET,
                        request -> null
                ).build();
        RelifeApp app = new RelifeApp(handler);
        RelifeResponse response = app.process(new RelifeRequest("/path", RelifeMethod.GET));
        assertEquals(200, response.getStatus());
    }
}
