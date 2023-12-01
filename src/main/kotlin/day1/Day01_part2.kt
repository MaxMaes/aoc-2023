package day1

import readInput

fun main() {
    val input = readInput("day_01")

    val stringNumbers = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
    fun stringToInt(string: String): Int = when (string) {
        "one" -> 1
        "two" -> 2
        "three" -> 3
        "four" -> 4
        "five" -> 5
        "six" -> 6
        "seven" -> 7
        "eight" -> 8
        "nine" -> 9
        else -> throw IllegalArgumentException("Invalid string: $string")
    }

    val solution = input.sumOf { line ->
        val parsedLine = mutableListOf<Int>()
        line.forEachIndexed { index, char ->
            if(char.isDigit()) {
                parsedLine.add(char.toString().toInt())
            } else if(stringNumbers.any { it.startsWith(char.toString()) }) {
                val buffer = mutableListOf<String>()
                buffer.add(char.toString())
                for(i in index + 1..<line.length) {
                    val nextChar = line[i]
                    if(stringNumbers.any { it.startsWith(buffer.joinToString("") + nextChar) }) {
                        buffer.add(nextChar.toString())
                    } else {
                        break
                    }
                    if(stringNumbers.find { it == buffer.joinToString("") } != null) {
                        parsedLine.add(stringToInt(buffer.joinToString("")))
                        break
                    }
                }
            }
        }
            "${parsedLine.first()}${parsedLine.last()}".toInt()
    }

    println(solution)
}