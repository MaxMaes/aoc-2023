package day07

import readInput

fun main() {
    val input = readInput("day_07")

    val cardValues = listOf('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2').reversed()

    data class Hand(val cards: String, val bid: Int): Comparable<Hand> {
        val handValue = calculateHandValue()

        fun calculateHandValue(): Int {
            // Count how many times each card appears in the hand
            val cardCounts = cards.groupingBy { it }.eachCount()

            return when {
                cardCounts.containsValue(5) -> 50
                cardCounts.containsValue(4) -> 40
                cardCounts.containsValue(3) && cardCounts.containsValue(2) -> 32
                cardCounts.containsValue(3) -> 30
                cardCounts.filter { it.value == 2 }.size == 2 -> 22
                cardCounts.containsValue(2) -> 20
                else -> 10
            }
        }

        override fun compareTo(other: Hand): Int {
            return if (this.handValue != other.handValue) {
                this.handValue - other.handValue
            } else {
                // Find the first non-equal card and compare it's index from the cardValues list
                val matchedCards = this.cards.toCharArray().zip(other.cards.toCharArray())
                for(pair in matchedCards) {
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