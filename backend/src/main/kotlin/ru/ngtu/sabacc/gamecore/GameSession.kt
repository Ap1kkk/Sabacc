package ru.ngtu.sabacc.gamecore

import ru.ngtu.sabacc.gamecore.card.Card
import ru.ngtu.sabacc.gamecore.card.CardType
import ru.ngtu.sabacc.gamecore.exception.GameSessionNotStartedException
import ru.ngtu.sabacc.gamecore.exception.GameSessionStartedException
import ru.ngtu.sabacc.gamecore.exception.PlayerBrokeException
import ru.ngtu.sabacc.gamecore.exception.WrongPlayerMoveException
import ru.ngtu.sabacc.gamecore.gameboard.GameBoard
import ru.ngtu.sabacc.gamecore.player.Chips
import ru.ngtu.sabacc.gamecore.player.Hand
import ru.ngtu.sabacc.gamecore.player.Player
import ru.ngtu.sabacc.gamecore.token.Token

class GameSession {
    private lateinit var gameBoard: GameBoard

    private val players: MutableList<Player> = mutableListOf()
    private lateinit var playersIter: MutableIterator<Player>
    private lateinit var currentPlayer: Player

    private var isStarted: Boolean = false
    private var turn: Int = 1
    private var round: Int = 1

    private var isMoved = false
    private var isNoTax = false
    private var cardTypeToDiscard: CardType? = null
    private var passCount = 0

    fun join(player: Player) {
        if (isStarted)
            throw GameSessionStartedException(this)

        players.add(player)

        if (players.size == 2) {
            startGame()
        }
    }

    fun leave(player: Player) {
        players.remove(player)
    }

    private fun startGame() {
        gameBoard = GameBoard()

        for (player in players) {
            player.hand = Hand(
                gameBoard.takeCard(CardType.BLOOD),
                gameBoard.takeCard(CardType.SAND)
            )

            player.chips = Chips()
        }

        gameBoard.discardCard(
            gameBoard.takeCard(CardType.SAND)
        )
        gameBoard.discardCard(
            gameBoard.takeCard(CardType.BLOOD)
        )

        isStarted = true
        playersIter = players.iterator()
        currentPlayer = playersIter.next()
    }

    fun takeCard(player: Player, cardType: CardType): Card {
        if (!isStarted)
            throw GameSessionNotStartedException(this)

        if (player != currentPlayer || isMoved)
            throw WrongPlayerMoveException(this, player)

        if (!isNoTax && !player.chips.pay(1))
            throw PlayerBrokeException(this, player)

        cardTypeToDiscard = cardType
        isMoved = true

        return gameBoard.takeCard(cardType)
    }

    fun takeDiscardCard(player: Player, cardType: CardType): Card {
        if (!isStarted)
            throw GameSessionNotStartedException(this)

        if (player != currentPlayer || isMoved)
            throw WrongPlayerMoveException(this, player)

        if (!isNoTax && !player.chips.pay(1))
            throw PlayerBrokeException(this, player)

        cardTypeToDiscard = cardType
        isMoved = true

        return gameBoard.takeDiscardCard(cardType)
    }

    fun discardCard(player: Player, card: Card) {
        if (!isStarted)
            throw GameSessionNotStartedException(this)

        if (player != currentPlayer || !isMoved || card.cardType != cardTypeToDiscard)
            throw WrongPlayerMoveException(this, player)

        gameBoard.discardCard(card)
        passCount = 0
        changePlayer()
    }

    fun playToken(player: Player, token: Token) {
        if (!isStarted)
            throw GameSessionNotStartedException(this)

        if (player != currentPlayer || isMoved)
            throw WrongPlayerMoveException(this, player)

        when(token) {
            Token.NO_TAX -> isNoTax = true
            Token.TAKE_TWO_CHIPS -> player.chips.retrieveSpentChips(2)
            Token.OTHER_PLAYERS_PAY_ONE -> {
                for (opponent in players) {
                    if (opponent == player)
                        continue

                    opponent.chips.pay(1)
                }
            }
        }
    }

    fun pass(player: Player) {
        if (!isStarted)
            throw GameSessionNotStartedException(this)

        if (player != currentPlayer || isMoved)
            throw WrongPlayerMoveException(this, player)

        passCount++
        changePlayer()
    }

    private fun changePlayer() {
        if (playersIter.hasNext()) {
            currentPlayer = playersIter.next()
        }
        else {
            nextTurn()
        }

        isMoved = false
        isNoTax = false
        cardTypeToDiscard = null
    }

    private fun nextTurn() {
        if (turn == 3 || passCount == players.size) {
            results()
            return
        }

        turn++
        playersIter = players.iterator()
        currentPlayer = playersIter.next()
        passCount = 0
    }

    private fun results() {
        for (player in players) {
            val hand = player.hand
            // TODO Вскрытие карт, подсчёт результатов
            // TODO Бросок кубиков у карты самозванца (они вроде бросаются при вскрытии карт
            // TODO Удаление игроков, у которых кончились фишки
        }

        // Продолжение игры, если остались игроки
        if (players.size > 1)
            nextRound()
    }

    private fun nextRound() {
        round++
        turn = 1
        playersIter = players.iterator()
        currentPlayer = playersIter.next()

        isMoved = false
        isNoTax = false
        cardTypeToDiscard = null
        passCount = 0
    }
}
