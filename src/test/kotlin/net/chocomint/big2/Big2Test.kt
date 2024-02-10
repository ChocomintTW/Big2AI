package net.chocomint.big2

import net.chocomint.big2.cardset.Type
import org.junit.jupiter.api.Test

class Big2Test {
    @Test
    fun extractTest() {
        val deck4 = Big2.shuffle4p(81913977002L)

        deck4.forEach {
            println(it)
            it.extract(Type.FULL_HOUSE)?.forEach(::println)
            println()
        }
    }

    @Test
    fun deleteTest() {
        val deck4 = Big2.shuffle4p(81913977002L)
        val deckAfter = deck4[1].remove(deck4[1].extract(Type.FULL_HOUSE)!![0].toList())
        println(deckAfter)
    }

    @Test
    fun shuffle3Test() {
        val deck3 = Big2.shuffle3p(12837918L)
        deck3.forEach(::println)
    }
}