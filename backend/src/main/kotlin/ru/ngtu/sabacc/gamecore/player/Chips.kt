package ru.ngtu.sabacc.gamecore.player

class Chips(
    private var remainChips: Int = 4
) {
    private var spentChips: Int = 0

    fun pay(cost: Int): Boolean {
        if (remainChips < cost)
            return false

        remainChips -= cost
        spentChips += cost

        return true
    }

    fun retrieveSpentChips(value: Int = spentChips) {
        if (value > spentChips) {
            remainChips += spentChips
            spentChips = 0
        }
        else {
            remainChips += value
            spentChips -= value
        }
    }

    fun discardSpentChips() {
        spentChips = 0
    }
}
