package day04

import readInput
import kotlin.math.pow

fun main() {
    val input = readInput("day_04")

    val answer = input.sumOf { cardLine ->
        val split = cardLine.split(":")[1].split("|").map { it.trim().replace("  ", " ").split(" ") }
        val intersection = split[0].intersect(split[1].toSet())
         if(intersection.isNotEmpty()) {
             2.toDouble().pow(intersection.size - 1).toInt()
         } else {
             0
         }
    }

    println(answer)
}