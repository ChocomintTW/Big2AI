package net.chocomint.big2.card

enum class Suit(val priority: Int, val symbol: Char, val enSymbol: Char) {
    CLUB(0, '♣', 'c'),
    DIAMOND(1, '♦', 'd'),
    HEART(2, '♥', 'h'),
    SPADE(3, '♠', 's');

    override fun toString(): String {
        return symbol.toString()
    }

    companion object {
        fun of(priority: Int): Suit {
            return entries[priority]
        }

        fun byEn(enSymbol: Char): Suit {
            return Suit.entries.first { it.enSymbol == enSymbol }
        }
    }
}