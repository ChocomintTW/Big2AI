package net.chocomint.big2

import net.chocomint.big2.annotations.ChatGPT
import net.chocomint.big2.card.Card
import net.chocomint.big2.cardset.CardSet
import net.chocomint.big2.cardset.Type
import java.util.*

object Big2 {
    fun shuffle4p(): List<Deck> {
        return Card.listAll().shuffled().chunked(13).map { Deck(it) }
    }

    fun shuffle4p(seed: Long): List<Deck> {
        return Card.listAll().shuffled(Random(seed)).chunked(13).map { Deck(it) }
    }

    fun shuffle3p(seed: Long): List<Deck> {
        val all = Card.listAll().shuffled(Random(seed)).chunked(17)
        val left = all[3][0]
        return all.subList(0, 3).map {
            if (it.contains(Card.of(0)))
                it + left
            else
                it
        }.map { Deck(it) }
    }

    class Deck(private val list: List<Card>) {

        fun asList(): List<Card> = list.sorted()

        fun extract(type: Type): List<CardSet>? {
            val map = list.groupingBy { it.rank }.eachCount()

            return when(type) {
                Type.ERROR -> null

                Type.SINGLE -> asList().map { CardSet(it) }

                Type.PAIR -> {
                    asList().combinations()
                        .filter { it.first.rank == it.second.rank }
                        .map { CardSet(it.first, it.second) }
                }

                Type.FULL_HOUSE -> {
                    map.filterValues { it == 3 }
                        .map { entry -> asList().filter { it.rank == entry.key } } // all 3-cards set
                        .flatMap { tuple3 ->
                            asList().combinations()
                                .filter { it.first.rank == it.second.rank } // all 2-cards set
                                .filter { it.first.rank != tuple3[0].rank } // check not the same with tuple3's rank
                                .map { tuple3 + it.first + it.second }
                                .map { CardSet(*it.toTypedArray()) }
                        }
                }

                Type.FOUR_OF_A_KIND -> {
                    map.filterValues { it == 4 }
                        .map { entry -> asList().filter { it.rank == entry.key } } // all 4-cards set
                        .flatMap { tuple4 ->
                            asList().filter { it.rank != tuple4[0].rank }
                                .map { tuple4 + it }
                                .map { CardSet(*it.toTypedArray()) }
                        }
                }

                Type.STRAIGHT, Type.STRAIGHT_FLUSH -> {
                    list.map { it.rank }
                        .toSet()
                        .sortedBy { (it.priority + 1) % 13 }
                        .consecutiveSubs(5)
                        .filter { (it.last().priority - it.first().priority + 13) % 13 == 4 }
                        .flatMap { rankList ->
                            // combine all possible rank for found straights
                            generateCombinations(rankList.map { rank -> list.filter { it.rank == rank } })
                        }
                        .filter { list -> type == Type.STRAIGHT || list.all { it.suit == list[0].suit } }
                        .map { CardSet(*it.toTypedArray()) }
                }

                Type.ONE_DRAGON -> null
            }
        }

        fun remove(card: Card): Deck {
            return Deck(list.filter { it != card })
        }

        fun remove(cards: List<Card>): Deck {
            return Deck(list.filter { !cards.contains(it) })
        }

        override fun toString(): String {
            return "[${asList().joinToString(", ")}]"
        }
    }

    @ChatGPT
    fun <T> List<T>.combinations(): List<Pair<T, T>> {
        return flatMapIndexed { i, outer ->
            subList(i + 1, size).map { inner -> outer to inner }
        }
    }

    @ChatGPT
    private fun <T> generateCombinations(arrays: List<List<T>>, currentCombination: List<T> = emptyList(), depth: Int = 0): List<List<T>> {
        if (depth == arrays.size) {
            return listOf(currentCombination)
        }

        val currentArray = arrays[depth]
        return currentArray.flatMap { element ->
            generateCombinations(arrays, currentCombination + element, depth + 1)
        }
    }

    private fun <T> List<T>.consecutiveSubs(n: Int): List<List<T>> {
        return (0..size - n).map { i -> subList(i, i + n) }
    }
}