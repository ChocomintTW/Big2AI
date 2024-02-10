package net.chocomint.big2.card

import java.util.Objects

class Card(val rank: Rank, val suit: Suit) : Comparable<Card> {

    val priority: Int get() = rank.priority * 4 + suit.priority

    override fun toString(): String {
        return "$rank$suit"
    }

    override fun compareTo(other: Card): Int {
        return priority - other.priority
    }

    override fun hashCode(): Int {
        return Objects.hash(priority)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Card

        return compareTo(other) == 0
    }

    companion object {
        fun of(i: Int): Card {
            if (i >= 52) throw NoSuchCardError()
            return Card(
                Rank.of(i / 4),
                Suit.of(i % 4)
            )
        }

        fun by(str: String): Card {
            val len = str.length
            return Card(
                Rank.by(str.substring(0, len - 1)),
                Suit.byEn(str.substring(len - 1)[0])
            )
        }

        fun listAll(): List<Card> {
            return Array(52) { i -> of(i) }.toList()
        }

        class NoSuchCardError : Error("No Such Card")
    }
}