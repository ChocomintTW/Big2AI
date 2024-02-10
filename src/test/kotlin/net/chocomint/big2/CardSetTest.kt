package net.chocomint.big2

import net.chocomint.big2.card.Card
import net.chocomint.big2.cardset.CardSet
import net.chocomint.big2.cardset.Type
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CardSetTest {
    @Test
    fun test5() {
        val set1 = CardSet(
            Card.by("5d"),
            Card.by("3c"),
            Card.by("4h"),
            Card.by("6c"),
            Card.by("7d")
        )
        val set2 = CardSet(
            Card.by("5d"),
            Card.by("5c"),
            Card.by("5h"),
            Card.by("5s"),
            Card.by("9d")
        )

        println(set1)
        println(set2)
    }

    @Test
    fun testPair() {
        val set = CardSet(Card.by("5c"), Card.by("5h"))
        assertEquals(set.type, Type.PAIR)
    }
}