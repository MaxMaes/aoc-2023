package day04

import readInput
import kotlin.math.pow

fun main() {
    val input = readInput("day_04")
    val cardAmounts = MutableList(input.size) { 1 }

    input.forEachIndexed { index, cardLine ->
        val cardNumber = index + 1
        val split = cardLine.split(":")[1].split("|").map { it.trim().replace("  ", " ").split(" ") }
        val intersection = split[0].intersect(split[1].toSet())
        val winCountForCard = intersection.size

        for(i in cardNumber..<cardNumber + winCountForCard) {
            cardAmounts[i] += 1 * cardAmounts[index]
        }
    }

    println(cardAmounts.sum())
}