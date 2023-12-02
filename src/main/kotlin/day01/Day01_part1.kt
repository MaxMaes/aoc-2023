package day01

import readInput

fun main() {
    val input = readInput("day_01")

    val numbers = input.map {
        val first = it.find { it.isDigit() }
        val last = it.findLast { it.isDigit() }
        "$first$last".toInt()
    }.sum()

    println(numbers)
}