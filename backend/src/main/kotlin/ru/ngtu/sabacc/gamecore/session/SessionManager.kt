package ru.ngtu.sabacc.gamecore.session

import org.springframework.stereotype.Component
import ru.ngtu.sabacc.gamecore.exception.SessionNotFoundException
import ru.ngtu.sabacc.gamecore.turn.TurnDTO

@Component
class SessionManager {

    private val sessions: MutableList<Session> = mutableListOf()

    fun handleMove(turnDTO: TurnDTO) {
        val sessionId = turnDTO.sessionId
        val session = findSession(sessionId) ?: throw SessionNotFoundException(sessionId)
        session.handleMove(turnDTO)
    }

    private fun findSession(sessionId: Long) = sessions.find { it.sessionId == sessionId }
}
