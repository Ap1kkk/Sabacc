package ru.ngtu.sabacc.gamecore.session

import ru.ngtu.sabacc.gamecore.card.Card
import ru.ngtu.sabacc.gamecore.card.CardType
import ru.ngtu.sabacc.gamecore.gameboard.GameBoard
import ru.ngtu.sabacc.gamecore.player.Player
import ru.ngtu.sabacc.gamecore.state.GameState
import ru.ngtu.sabacc.gamecore.turn.TurnDTO
import ru.ngtu.sabacc.gamecore.turn.MoveType

class Session(
    val sessionId: Long
) {

    private val players: MutableMap<Long, Player> = mutableMapOf()
    private val gameBoard: GameBoard
    private val gameState: GameState

    init {
        gameBoard = initGameBoard()
        gameState = GameState(0, 0, 0)
    }

    private fun initGameBoard(): GameBoard {
        val sandDeck: MutableList<Card> = mutableListOf()
        val bloodDeck: MutableList<Card> = mutableListOf()
        val sandDiscardDeck: MutableList<Card> = mutableListOf()
        val bloodDiscardDeck: MutableList<Card> = mutableListOf()

        for (value in 1..6) {
            sandDeck.add(
                Card.ValueCard(
                CardType.SAND, value
            ))
            bloodDeck.add(
                Card.ValueCard(
                CardType.BLOOD, value
            ))
        }

        for (i in 1..3) {
            sandDeck.add(
                Card.ImposterCard(
                CardType.SAND
            ))
            bloodDeck.add(
                Card.ImposterCard(
                CardType.BLOOD
            ))
        }

        sandDeck.add(
            Card.SylopCard(
            CardType.SAND
        ))
        bloodDeck.add(
            Card.SylopCard(
            CardType.BLOOD
        ))

        sandDeck.shuffle()
        bloodDeck.shuffle()

        return GameBoard(
            sandDeck,
            bloodDeck,
            sandDiscardDeck,
            bloodDiscardDeck
        )
    }

    fun handleMove(turnDTO: TurnDTO) {
        val playerId = turnDTO.playerId
        val moveType = turnDTO.moveType

        if (playerId != gameState.currentPlayerId) {
            TODO()
        }

        when(moveType) {
            MoveType.PASS -> pass(playerId)
            MoveType.GET_SAND -> getSand(playerId)
            MoveType.GET_BLOOD -> getBlood(playerId)
            MoveType.GET_SAND_DISCARD -> getSandDiscard(playerId)
            MoveType.GET_BLOOD_DISCARD -> getBloodDiscard(playerId)
            MoveType.DISCARD_FIRST_SAND -> discardSandCard(playerId, 0)
            MoveType.DISCARD_FIRST_BLOOD -> discardBloodCard(playerId, 0)
            MoveType.DISCARD_LAST_SAND -> discardSandCard(playerId, 1)
            MoveType.DISCARD_LAST_BLOOD -> discardBloodCard(playerId, 1)
            MoveType.PLAY_TOKEN -> TODO()
        }
    }

    private fun pass(playerId: Long) {
        TODO()
    }

    private fun getSand(playerId: Long) {
        pay(playerId, 1)

        players[playerId]!!.sandCards.add(
            gameBoard.sandDeck.removeLast()
        )
    }

    private fun getBlood(playerId: Long) {
        pay(playerId, 1)

        players[playerId]!!.bloodCards.add(
            gameBoard.bloodDeck.removeLast()
        )
    }

    private fun getSandDiscard(playerId: Long) {
        pay(playerId, 1)

        players[playerId]!!.sandCards.add(
            gameBoard.sandDiscardDeck.removeLast()
        )
    }

    private fun getBloodDiscard(playerId: Long) {
        pay(playerId, 1)

        players[playerId]!!.bloodCards.add(
            gameBoard.bloodDiscardDeck.removeLast()
        )
    }

    private fun discardSandCard(playerId: Long, index: Int) {
        val player = players[playerId]!!
        val card = player.sandCards.removeAt(index)
        gameBoard.sandDiscardDeck.add(card)
    }

    private fun discardBloodCard(playerId: Long, index: Int) {
        val player = players[playerId]!!
        val card = player.bloodCards.removeAt(index)
        gameBoard.bloodDiscardDeck.add(card)
    }

    private fun pay(playerId: Long, price: Int) {
        val player = players[playerId]!!

        player.spentChips += price
        player.remainChips -= price
    }
}
