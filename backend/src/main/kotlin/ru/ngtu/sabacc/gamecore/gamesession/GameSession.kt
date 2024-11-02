package ru.ngtu.sabacc.gamecore.gamesession

import ru.ngtu.sabacc.game.GameStateDto
import ru.ngtu.sabacc.game.messaging.IGameMessageExchanger
import ru.ngtu.sabacc.game.messaging.IGameSession
import ru.ngtu.sabacc.gamecore.card.Card
import ru.ngtu.sabacc.gamecore.card.CardType
import ru.ngtu.sabacc.gamecore.gameboard.GameBoard
import ru.ngtu.sabacc.gamecore.player.Player
import ru.ngtu.sabacc.gamecore.token.Token
import ru.ngtu.sabacc.gamecore.turn.TurnDTO
import ru.ngtu.sabacc.gamecore.turn.TurnType
import kotlin.math.max


class GameSession(
    private val sessionId: Long,
    private val gameMessageExchanger: IGameMessageExchanger
) : IGameSession {

    private val players: MutableMap<Long, Player> = mutableMapOf()
    private lateinit var gameBoard: GameBoard
    private var currentPlayerId: Long = 0
    private var turn: Int = 1
    private var round: Int = 1
    private lateinit var waitingForMoveType: List<TurnType>
    private var playersIdIter = players.keys.iterator()
    private var passCount: Int = 0
    private var cardPrice: Int = 1

    init {
        startGame()
    }

    private fun startGame() {
        gameBoard = initGameBoard()
        waitingForMoveType = initWaitingForMoveType()

        for (player in players.values) {
            player.sandCards.add(
                gameBoard.sandDeck.removeLast()
            )
            player.bloodCards.add(
                gameBoard.bloodDeck.removeLast()
            )
        }
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

    private fun initWaitingForMoveType(): List<TurnType> {
        return listOf(
            TurnType.PASS,
            TurnType.GET_SAND,
            TurnType.GET_BLOOD,
            TurnType.GET_SAND_DISCARD,
            TurnType.GET_BLOOD_DISCARD,
            TurnType.PLAY_NO_TAX_TOKEN,
            TurnType.PLAY_TAKE_TWO_CHIPS_TOKEN,
            TurnType.PLAY_OTHER_PLAYERS_PAY_ONE_TOKEN
        )
    }

    override fun getCurrentState(): GameStateDto {
        return GameStateDto()
    }

    override fun tryMakeTurn(turnDTO: TurnDTO) {
        val playerId = turnDTO.playerId
        val turnType = turnDTO.turnType
        val details = turnDTO.details

        if (playerId != currentPlayerId) {
            TODO("Handle not the current player")
        }
        if (turnType !in waitingForMoveType) {
            TODO("Handle not the current move")
        }

        when(turnType) {
            TurnType.PASS -> pass()
            TurnType.GET_SAND -> getSand(playerId)
            TurnType.GET_BLOOD -> getBlood(playerId)
            TurnType.GET_SAND_DISCARD -> getSandDiscard(playerId)
            TurnType.GET_BLOOD_DISCARD -> getBloodDiscard(playerId)
            TurnType.DISCARD_FIRST_SAND -> discardSandCard(playerId, 0)
            TurnType.DISCARD_FIRST_BLOOD -> discardBloodCard(playerId, 0)
            TurnType.DISCARD_LAST_SAND -> discardSandCard(playerId, 1)
            TurnType.DISCARD_LAST_BLOOD -> discardBloodCard(playerId, 1)
            TurnType.PLAY_NO_TAX_TOKEN -> playToken(playerId, Token.NO_TAX)
            TurnType.PLAY_TAKE_TWO_CHIPS_TOKEN -> playToken(playerId, Token.TAKE_TWO_CHIPS)
            TurnType.PLAY_OTHER_PLAYERS_PAY_ONE_TOKEN -> playToken(playerId, Token.OTHER_PLAYERS_PAY_ONE)
            TurnType.SELECT_DICE -> roundResults(details as Int?)
        }
    }

    override fun getSessionId(): Long {
        return sessionId
    }

    override fun start() {
        TODO("Not yet implemented")
    }

    override fun pause() {
        TODO("Not yet implemented")
    }

    override fun unpause() {
        TODO("Not yet implemented")
    }

    private fun pass() {
        passCount++
        nextState()
    }

    private fun getSand(playerId: Long) {
        val player = players[playerId]!!

        if (!pay(player, cardPrice)) {
            TODO("Handle broke ass player")
        }

        player.sandCards.add(
            gameBoard.sandDeck.removeLast()
        )

        waitingForMoveType = listOf(
            TurnType.DISCARD_FIRST_SAND,
            TurnType.DISCARD_LAST_SAND
        )
    }

    private fun getBlood(playerId: Long) {
        val player = players[playerId]!!

        if (!pay(player, cardPrice)) {
            TODO("Handle broke ass player")
        }

        player.bloodCards.add(
            gameBoard.bloodDeck.removeLast()
        )

        waitingForMoveType = listOf(
            TurnType.DISCARD_FIRST_BLOOD,
            TurnType.DISCARD_LAST_BLOOD
        )
    }

    private fun getSandDiscard(playerId: Long) {
        val player = players[playerId]!!

        if (!pay(player, cardPrice)) {
            TODO("Handle broke ass player")
        }

        player.sandCards.add(
            gameBoard.sandDiscardDeck.removeLast()
        )

        waitingForMoveType = listOf(
            TurnType.DISCARD_FIRST_SAND,
            TurnType.DISCARD_LAST_SAND
        )
    }

    private fun getBloodDiscard(playerId: Long) {
        val player = players[playerId]!!

        if (!pay(player, cardPrice)) {
            TODO("Handle broke ass player")
        }

        player.bloodCards.add(
            gameBoard.bloodDiscardDeck.removeLast()
        )

        waitingForMoveType = listOf(
            TurnType.DISCARD_FIRST_BLOOD,
            TurnType.DISCARD_LAST_BLOOD
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

                for (opponent in players.values) {
                    if (opponent == player)
                        continue

                    pay(opponent, 1)
                }
            }
        }

        waitingForMoveType = listOf(
            TurnType.PASS,
            TurnType.GET_SAND,
            TurnType.GET_BLOOD,
            TurnType.GET_SAND_DISCARD,
            TurnType.GET_BLOOD_DISCARD
        )
    }

    private fun nextState() {
        waitingForMoveType = initWaitingForMoveType()
        cardPrice = 1

        if (playersIdIter.hasNext()) {
            currentPlayerId = playersIdIter.next()
        }
        else {
            nextTurn()
        }
    }

    private fun nextTurn() {
        if (turn < 3 && passCount != players.size) {
            playersIdIter = players.keys.iterator()
            currentPlayerId = playersIdIter.next()
            turn++
            passCount = 0
        }
        else {
            roundResults()
        }
    }

    private fun roundResults(value: Int? = null) {
        // Replace all Imposter cards with Value cards
        replaceImposterWithValue(value)

        // Rate players hand
        for (player in players.values) {
            val sandCard = player.sandCards.removeLast()
            val bloodCard = player.bloodCards.removeLast()

            player.handRating = rateHand(sandCard, bloodCard)
        }

        val playersSortedByRating = players.values.sortedWith(compareBy(
            { it.handRating!!.first },
            { it.handRating!!.second }
        ))

        // Collect taxes
        val winner = playersSortedByRating.first()
        winner.remainChips += winner.spentChips
        pay(winner, winner.handRating!!.first)

        val looser = playersSortedByRating.last()
        if (looser.handRating!!.first == 0) {
            pay(looser, 1)
        }
        else {
            pay(looser, looser.handRating!!.first)
        }
        
        TODO("Show results")

        winner.spentChips = 0
        looser.spentChips = 0

        // Show the winner
        if (looser.remainChips == 0) {
            TODO("Show the winner")
        }
        else {
            nextRound()
        }
    }

    private fun replaceImposterWithValue(value: Int?) {
        for (player in players.values) {
            if (!checkAndReplaceImposter(player.bloodCards, value) ||
                !checkAndReplaceImposter(player.sandCards, value))
                return
        }
    }

    private fun checkAndReplaceImposter(cards: MutableList<Card>, value: Int?): Boolean {
        val card = cards[0]

        if (card is Card.ImposterCard) {
            if (value == null) {
                waitingForMoveType = listOf(
                    TurnType.SELECT_DICE
                )
                TODO("Throw dice, ask player for number")
                return false
            }

            cards.removeLast()
            cards.add(
                Card.ValueCard(CardType.BLOOD, value)
            )
        }

        return true
    }

    // Difference and strength of hand
    private fun rateHand(sandCard: Card, bloodCard: Card): Pair<Int, Int> {
        if (sandCard is Card.SylopCard &&
            bloodCard is Card.SylopCard)
            return Pair(0, 0)

        if (sandCard is Card.SylopCard &&
            bloodCard is Card.ValueCard) {
            return Pair(0, bloodCard.value)
        }

        if (bloodCard is Card.SylopCard &&
            sandCard is Card.ValueCard) {
            return Pair(0, sandCard.value)
        }

        val bloodCardCast = bloodCard as Card.ValueCard
        val sandCardCast = sandCard as Card.ValueCard

        val difference = Math.abs(bloodCardCast.value - sandCardCast.value)
        return Pair(difference, Math.max(bloodCardCast.value, sandCardCast.value))
    }

    private fun nextRound() {
        round++
        turn = 1
        playersIdIter = players.keys.iterator()
        currentPlayerId = playersIdIter.next()
        passCount = 0

        startGame()
    }

    private fun pay(player: Player, price: Int): Boolean {
        if (player.remainChips < price)
            return false

        player.remainChips -= price
        player.spentChips += price
        return true
    }
}
