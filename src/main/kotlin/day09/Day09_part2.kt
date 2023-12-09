package day09

import readInput

fun main() {
    val input = readInput("day_09")

    val zeroed = input.map { historyLineIn ->
        val historyLine = historyLineIn.split(" ").map(String::toLong).toMutableList()

        val lineTree = mutableListOf(historyLine)

        while (lineTree.last().all { it == 0L }.not()) {
            val lastEntry = lineTree.last()
            val diffedLine = lastEntry.dropLast(1).mapIndexed { index, entry ->
                (lastEntry[index + 1] - entry)
            }.toMutableList()
            lineTree.add(diffedLine)
        }
        lineTree
    }

    // Now, extrapolate each tree
    val extrapolated = zeroed.map { tree ->
        // Add a 0 to the end
        tree.last().add(0L)

        // Going backwards through the tree, calculate the next value.
        for (i in tree.lastIndex downTo 1) {
            val lastLine = tree[i]
            val nextLine = tree[i - 1]

            // Take the first value from the last line
            val lastValue = lastLine.first()
            // Take the first value from the next line
            val nextValue = nextLine.first()
            val answer = nextValue - lastValue
            nextLine.add(0, answer)
        }
        
        tree
    }
    println(extrapolated.map { it.first() }.sumOf { it.first() })
}