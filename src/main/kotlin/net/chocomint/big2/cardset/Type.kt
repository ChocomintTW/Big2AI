package net.chocomint.big2.cardset

enum class Type(val priority: Int) {
    ERROR         (-1),
    SINGLE        (0),
    PAIR          (1),
    STRAIGHT      (2),
    FULL_HOUSE    (3),
    FOUR_OF_A_KIND(4),
    STRAIGHT_FLUSH(5),
    ONE_DRAGON    (6);
}