package com.tw.relife;

import com.tw.relife.annotation.RelifeMvcHandlerBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MultipleActionsTest {
    @Test
    void should_match_right_action() {
        RelifeAppHandler handler = new RelifeMvcHandlerBuilder()
                .addAction(
                        "/path",
                        RelifeMethod.GET,
                        request -> new RelifeResponse(200, "get action", "text/plain"))
                .addAction(
                        "/path",
                        RelifeMethod.POST,
                        request -> new RelifeResponse(403, "post action", "text/plain"))
                .build();
        RelifeApp app = new RelifeApp(handler);
        RelifeResponse getResponse = app.process(new RelifeRequest("/path", RelifeMethod.GET));
        RelifeResponse postResponse = app.process(new RelifeRequest("/path", RelifeMethod.POST));

        assertEquals(200, getResponse.getStatus());
        assertEquals("get action", getResponse.getContent());

        assertEquals(403, postResponse.getStatus());
        assertEquals("post action", postResponse.getContent());
    }

    @Test
    void should_match_first_added_action_when_duplicated_matching_condition() {
        RelifeAppHandler handler = new RelifeMvcHandlerBuilder()
                .addAction(
                        "/path",
                        RelifeMethod.GET,
                        request -> new RelifeResponse(200, "first action", "text/plain"))
                .addAction(
                        "/path",
                        RelifeMethod.GET,
                        request -> new RelifeResponse(403, "second action", "text/plain"))
                .build();
        RelifeApp app = new RelifeApp(handler);
        RelifeResponse getResponse = app.process(new RelifeRequest("/path", RelifeMethod.GET));

        assertEquals(200, getResponse.getStatus());
        assertEquals("first action", getResponse.getContent());
    }
}
