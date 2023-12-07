package day07

import readInput

fun main() {
    val input = readInput("day_07")

    val cardValues = listOf('A', 'K', 'Q', 'T', '9', '8', '7', '6', '5', '4', '3', '2', 'J').reversed()

    data class Hand(val cards: String, val bid: Int) : Comparable<Hand> {
        val handValue = calculateHandValue()

        fun calculateHandValue(): Int {
            // Count how many times each non-joker card appears in the hand
            val cardCounts = cards.filter { card ->
                card != 'J'
            }.groupingBy { it }.eachCount()
            // Count how many jokers we have
            val amountOfJokers = cards.count { it == 'J' }

            return when {
                cardCounts.containsValue(5) || amountOfJokers == 5 -> 50 // 5 of a kind
                cardCounts.containsValue(4) -> 10 * (amountOfJokers + 4) // 4 of a kind or 5 of a kind with a joker
                cardCounts.containsValue(3) && cardCounts.containsValue(2) -> 32 // Full house
                cardCounts.containsValue(3) -> 10 * (amountOfJokers + 3) // 3 of a kind or 4 of a kind with a joker or 5 of a kind with 2 jokers
                cardCounts.filter { it.value == 2 }.size == 2 -> 22 + 10 * amountOfJokers // 2 pairs or full house with a joker
                cardCounts.containsValue(2) -> 10 * (2 + amountOfJokers)// One pair or 3 of a kind+ with a joker
                else -> 10 * (1 + amountOfJokers) // High card or more with jokers
            }
        }

        override fun compareTo(other: Hand): Int {
            return if (this.handValue != other.handValue) {
                this.handValue - other.handValue
            } else {
                // Find the first non-equal card and compare it's index from the cardValues list
                val matchedCards = this.cards.toCharArray().zip(other.cards.toCharArray())
                for (pair in matchedCards) {
                    if (pair.first != pair.second) {
                        val firstIndex = cardValues.indexOf(pair.first)
                        val secondIndex = cardValues.indexOf(pair.second)
                        return firstIndex - secondIndex
                    }
                }
                0
            }
        }
    }

    val totalWinnings = input.map { line ->
        val splitted = line.split(" ")
        Hand(splitted[0], splitted[1].toInt())
    }.sorted().mapIndexed { index, hand ->
        hand.bid * (index + 1)
    }.sum()


    println(totalWinnings)

}