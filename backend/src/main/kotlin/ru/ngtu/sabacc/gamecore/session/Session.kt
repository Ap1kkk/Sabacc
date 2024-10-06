package ru.ngtu.sabacc.gamecore.session

import ru.ngtu.sabacc.gamecore.card.Card
import ru.ngtu.sabacc.gamecore.card.CardType
import ru.ngtu.sabacc.gamecore.gameboard.GameBoard
import ru.ngtu.sabacc.gamecore.player.Player
import ru.ngtu.sabacc.gamecore.state.GameState
import ru.ngtu.sabacc.gamecore.token.Token
import ru.ngtu.sabacc.gamecore.turn.TurnDTO
import ru.ngtu.sabacc.gamecore.turn.MoveType
import kotlin.math.max

class Session(
    val sessionId: Long
) {

    private val players: MutableMap<Long, Player> = mutableMapOf()
    private var gameBoard: GameBoard
    private val gameState: GameState
    private var waitingForMoveType: List<MoveType>
    private var playersIdIter = players.keys.iterator()
    private var passCount: Int = 0
    private var cardPrice: Int = 1

    init {
        gameBoard = initGameBoard()
        gameState = initGameState()
        waitingForMoveType = initWaitingForMoveType()
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

    private fun initGameState(): GameState {
        return GameState(0, 1, 1)
    }

    private fun initWaitingForMoveType(): List<MoveType> {
        return listOf(
            MoveType.PASS,
            MoveType.GET_SAND,
            MoveType.GET_BLOOD,
            MoveType.GET_SAND_DISCARD,
            MoveType.GET_BLOOD_DISCARD,
            MoveType.PLAY_NO_TAX_TOKEN,
            MoveType.PLAY_TAKE_TWO_CHIPS_TOKEN,
            MoveType.PLAY_OTHER_PLAYERS_PAY_ONE_TOKEN
        )
    }

    fun handleMove(turnDTO: TurnDTO) {
        val playerId = turnDTO.playerId
        val moveType = turnDTO.moveType

        if (playerId != gameState.currentPlayerId) {
            TODO("Handle not the current player")
        }
        if (moveType !in waitingForMoveType) {
            TODO("Handle not the current move")
        }

        when(moveType) {
            MoveType.PASS -> pass()
            MoveType.GET_SAND -> getSand(playerId)
            MoveType.GET_BLOOD -> getBlood(playerId)
            MoveType.GET_SAND_DISCARD -> getSandDiscard(playerId)
            MoveType.GET_BLOOD_DISCARD -> getBloodDiscard(playerId)
            MoveType.DISCARD_FIRST_SAND -> discardSandCard(playerId, 0)
            MoveType.DISCARD_FIRST_BLOOD -> discardBloodCard(playerId, 0)
            MoveType.DISCARD_LAST_SAND -> discardSandCard(playerId, 1)
            MoveType.DISCARD_LAST_BLOOD -> discardBloodCard(playerId, 1)
            MoveType.PLAY_NO_TAX_TOKEN -> playToken(playerId, Token.NO_TAX)
            MoveType.PLAY_TAKE_TWO_CHIPS_TOKEN -> playToken(playerId, Token.TAKE_TWO_CHIPS)
            MoveType.PLAY_OTHER_PLAYERS_PAY_ONE_TOKEN -> playToken(playerId, Token.OTHER_PLAYERS_PAY_ONE)
        }
    }

    private fun pass() {
        passCount++
        nextState()
    }

    private fun getSand(playerId: Long) {
        if (!pay(playerId, cardPrice)) {
            TODO("Handle broke ass player")
        }

        players[playerId]!!.sandCards.add(
            gameBoard.sandDeck.removeLast()
        )

        waitingForMoveType = listOf(
            MoveType.DISCARD_FIRST_SAND,
            MoveType.DISCARD_LAST_SAND
        )
    }

    private fun getBlood(playerId: Long) {
        if (!pay(playerId, cardPrice)) {
            TODO("Handle broke ass player")
        }

        players[playerId]!!.bloodCards.add(
            gameBoard.bloodDeck.removeLast()
        )

        waitingForMoveType = listOf(
            MoveType.DISCARD_FIRST_BLOOD,
            MoveType.DISCARD_LAST_BLOOD
        )
    }

    private fun getSandDiscard(playerId: Long) {
        if (!pay(playerId, cardPrice)) {
            TODO("Handle broke ass player")
        }

        players[playerId]!!.sandCards.add(
            gameBoard.sandDiscardDeck.removeLast()
        )

        waitingForMoveType = listOf(
            MoveType.DISCARD_FIRST_SAND,
            MoveType.DISCARD_LAST_SAND
        )
    }

    private fun getBloodDiscard(playerId: Long) {
        if (!pay(playerId, cardPrice)) {
            TODO("Handle broke ass player")
        }

        players[playerId]!!.bloodCards.add(
            gameBoard.bloodDiscardDeck.removeLast()
        )

        waitingForMoveType = listOf(
            MoveType.DISCARD_FIRST_BLOOD,
            MoveType.DISCARD_LAST_BLOOD
        )
    }

    private fun discardSandCard(playerId: Long, index: Int) {
        val player = players[playerId]!!
        val card = player.sandCards.removeAt(index)
        gameBoard.sandDiscardDeck.add(card)

        nextState()
    }

    private fun discardBloodCard(playerId: Long, index: Int) {
        val player = players[playerId]!!
        val card = player.bloodCards.removeAt(index)
        gameBoard.bloodDiscardDeck.add(card)

        nextState()
    }

    private fun playToken(playerId: Long, token: Token) {
        val player = players[playerId]!!
        if (token !in player.tokens) {
            TODO("Handle not found token")
        }

        when(token) {
            Token.NO_TAX -> {
                player.tokens.remove(Token.NO_TAX)

                cardPrice = 0
            }
            Token.TAKE_TWO_CHIPS -> {
                player.tokens.remove(Token.TAKE_TWO_CHIPS)

                val maxChips = max(player.spentChips, 2)
                player.remainChips += maxChips
                player.spentChips -= maxChips
            }
            Token.OTHER_PLAYERS_PAY_ONE -> {
                player.tokens.remove(Token.OTHER_PLAYERS_PAY_ONE)

                for (opponentId in players.keys) {
                    if (opponentId == playerId)
                        continue

                    pay(opponentId, 1)
                    TODO("What happens if player can't pay?")
                }
            }
        }

        waitingForMoveType = listOf(
            MoveType.PASS,
            MoveType.GET_SAND,
            MoveType.GET_BLOOD,
            MoveType.GET_SAND_DISCARD,
            MoveType.GET_BLOOD_DISCARD
        )
    }

    private fun nextState() {
        waitingForMoveType = initWaitingForMoveType()
        cardPrice = 1

        if (playersIdIter.hasNext()) {
            gameState.currentPlayerId = playersIdIter.next()
        }
        else {
            nextTurn()
        }
    }

    private fun nextTurn() {
        if (gameState.turn < 3 && passCount != players.size) {
            playersIdIter = players.keys.iterator()
            gameState.currentPlayerId = playersIdIter.next()
            gameState.turn++
            passCount = 0
        }
        else {
            nextRound()
        }
    }

    private fun nextRound() {
        TODO("Show the results of the round")

        gameBoard = initGameBoard()
        gameState.round++
        playersIdIter = players.keys.iterator()
        gameState.currentPlayerId = playersIdIter.next()
        gameState.turn = 1
        passCount = 0
    }

    private fun pay(playerId: Long, price: Int): Boolean {
        val player = players[playerId]!!

        if (player.remainChips < price)
            return false

        player.remainChips -= price
        player.spentChips += price
        return true
    }
}
