package ru.ngtu.sabacc.gamecore.game.messaging

import ru.ngtu.sabacc.game.messaging.IGameMessageExchanger
import ru.ngtu.sabacc.game.messaging.IGameSession
import ru.ngtu.sabacc.gamecore.game.GameErrorType
import ru.ngtu.sabacc.gamecore.game.GameFinishDto
import ru.ngtu.sabacc.gamecore.game.GameRoundDto
import ru.ngtu.sabacc.gamecore.turn.TurnDto

class MockGameMessageExchanger : IGameMessageExchanger {

    override fun sendErrorMessage(errorType: GameErrorType?, sender: IGameSession?) {
        println("Mock game message exchanger caught error $errorType")
    }

    override fun sendAcceptedTurn(turnDto: TurnDto?, sender: IGameSession?) {
        println("Mock game message exchanger accepted turn $turnDto")
    }

    override fun sendRoundResults(roundDto: GameRoundDto?, sender: IGameSession?) {
        println("Mock game message exchanger caught round results $roundDto")
    }

    override fun sendGameFinished(finishDto: GameFinishDto?, sender: IGameSession?) {
        println("Mock game message exchanger caught game results $finishDto")
    }
}
