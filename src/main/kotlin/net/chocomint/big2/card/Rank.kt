package net.chocomint.big2.card

enum class Rank(val priority: Int, val symbol: String, val char: Char) {
    N3 (0,  "3",  '3'),
    N4 (1,  "4",  '4'),
    N5 (2,  "5",  '5'),
    N6 (3,  "6",  '6'),
    N7 (4,  "7",  '7'),
    N8 (5,  "8",  '8'),
    N9 (6,  "9",  '9'),
    N10(7,  "10", 'T'),
    J  (8,  "J",  'J'),
    Q  (9,  "Q",  'Q'),
    K  (10, "K",  'K'),
    A  (11, "A",  'A'),
    N2 (12, "2",  '2');

    override fun toString(): String {
        return symbol
    }

    companion object {
        fun of(priority: Int): Rank {
            return entries[priority]
        }

        fun by(symbol: String): Rank {
            return entries.first { it.symbol == symbol }
        }
    }
}