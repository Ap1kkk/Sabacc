package ru.ngtu.sabacc.gamecore.exception

class SessionNotFoundException(
    sessionId: Long
) : RuntimeException(
    "Session not found by id: $sessionId"
)
