package net.chocomint.big2.cardset

import net.chocomint.big2.card.Card
import kotlin.math.max

class CardSet(vararg cards: Card) {
    private var cardSet = mutableListOf<Card>()

    init {
        cardSet.addAll(cards)
    }

    val type: Type get() = asTypeWithPriority().first

    val priority: Int get() = asTypeWithPriority().first.priority * 100 + asTypeWithPriority().second

    fun toList() = cardSet.toList()

    private fun asTypeWithPriority(): Pair<Type, Int> {
        when(cardSet.size) {
            1 -> return Type.SINGLE to cardSet[0].priority

            2 -> {
                val card1 = cardSet[0]
                val card2 = cardSet[1]
                return if (card1.rank == card2.rank)
                    Type.PAIR to max(card1.priority, card2.priority)
                else
                    error
            }

            5 -> {
                // map: 4/1 3/2
                val map = cardSet.groupingBy { it.rank }.eachCount()
                if (map.size == 2) {
                    return if (map.containsValue(3))
                        Type.FULL_HOUSE to map.filterValues { it == 3 }.keys.first().priority
                    else if (map.containsValue(4))
                        Type.FOUR_OF_A_KIND to map.filterValues { it == 4 }.keys.first().priority
                    else
                        error
                }

                if (map.size == 5) {
                    // check valid straight
                    val keys = map.keys.sortedBy { rank -> (rank.priority + 1) % 13 }
                    val priority = if ((keys.last().priority - keys.first().priority + 13) % 13 == 4) {
                        if (keys == listOf(0, 1, 2, 3, 12)) cardSet.max().suit.priority
                        else cardSet.max().priority
                    }
                    else return error

                    // check flush
                    return if (cardSet.all { it.suit == cardSet[0].suit })
                        Type.STRAIGHT_FLUSH to priority
                    else
                        Type.STRAIGHT to priority
                }

                return error
            }

            13 -> {
                val list = cardSet.sorted().map { it.rank.priority }
                for (i in 0..<(list.size - 1)) {
                    if (list[i] + 1 != list[i + 1]) {
                        return error
                    }
                }
                return Type.ONE_DRAGON to 0
            }

            else -> return error
        }
    }

    override fun toString(): String {
        val set = cardSet.sorted().joinToString(", ")
        return "[$set] $type ($priority)"
    }

    companion object {
        private val error = Type.ERROR to -1
    }
}