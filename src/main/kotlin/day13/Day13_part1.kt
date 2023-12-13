package day13

import readInput

fun main() {
    val input = readInput("day_13")

    var currentPatern = mutableListOf<String>()
    val patterns = mutableListOf<List<String>>()
    for (line in input) {
        if (line.isEmpty()) {
            patterns.add(currentPatern)
            currentPatern = mutableListOf()
            continue
        }
        currentPatern.add(line)
    }
    patterns.add(currentPatern)

    fun scanAlgorithm(pattern: List<String>): Int {
        return pattern.mapIndexed { index, line ->
            if (line == pattern.getOrNull(index + 1)) {
                var offset = 1
                var matches = true
                while (index + offset < pattern.size - 1 && index - offset >= 0 && matches) {
                    val lineA = pattern.getOrNull(index - offset)
                    val lineB = pattern.getOrNull(index + 1 + offset)
                    matches = lineA == lineB
                    offset++
                }
                if (matches) {
                    index + 1
                } else {
                    0
                }
            } else {
                0
            }
        }.sum()
    }

    fun scanPattern(pattern: List<String>): Pair<Int, Int> {
        println("----------------")
        println("Scanning pattern\n")
        pattern.forEach { println(it) }
        println("\n")
        println("Rows above result:")
        val rowsAbove = scanAlgorithm(pattern)
        var toLeft = 0
        if(rowsAbove == 0) {
            println("No mirror found, trying to rotate pattern")
            val rotatedPattern = mutableListOf<String>()
            for (i in pattern[0].indices) {
                val line = pattern.map { it[i] }.joinToString("")
                rotatedPattern.add(line)
            }
            println("Rows toLeft result:")
            toLeft = scanAlgorithm(rotatedPattern)
        }
        return Pair(rowsAbove, toLeft)
    }

    val (rowsAbove, rowsToleft) = patterns.map(::scanPattern).reduce { acc, pair ->
        Pair(acc.first + pair.first, acc.second + pair.second)
    }

    val answer = 100 * rowsAbove + rowsToleft
    println("Answer $answer")

    // 28651

}