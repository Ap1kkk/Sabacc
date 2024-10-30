package ru.ngtu.sabacc.common;

/**
 * @author Egor Bokov
 */
public interface WebSocketApiEndpoint {
    String TOPIC = "/topic";
    String QUEUE = "/queue";
    String INPUT = "/input";

    String SESSION_ID = "{sessionId}";

    String ACCEPTED_TURNS_QUEUE = QUEUE + "/session/" + SESSION_ID + "/accepted-turns";
    String USER_SESSION_ERRORS_QUEUE = QUEUE + "/session/" + SESSION_ID + "/errors";
    String SESSION_CHAT_QUEUE = QUEUE + "/session/" + SESSION_ID + "/chat";

    String SESSION_CHAT_TOPIC = TOPIC + "/session/" + SESSION_ID + "/chat";
    String SESSION_CHAT_INPUT = INPUT + "/session/" + SESSION_ID + "/chat";
    String SESSION_TURN_INPUT = INPUT + "/session/" + SESSION_ID + "/turn";
}
