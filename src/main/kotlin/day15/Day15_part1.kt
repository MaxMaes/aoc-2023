package day15

import readInput

fun main() {
    val input = readInput("day_15")
    val codes = input.first().split(",")

    fun HASH(code: String): Long {
        return code.fold(0L) { acc, char ->
            val ascii = char.code
            val value = acc + ascii
            val multiplied = value * 17
            multiplied % 256
        }
    }

    val answer = codes.sumOf(::HASH)

    println(answer)
}