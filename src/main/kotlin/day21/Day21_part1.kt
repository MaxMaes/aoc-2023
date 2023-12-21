package day21

import Point
import readInput

fun main() {
    val input = readInput("day_21").map(String::toCharArray)

    val startCoordinate = input.mapIndexedNotNull { y, chars ->
        val x = chars.indexOfFirst { it == 'S' }
        if (x != -1) Point(x, y) else null
    }.first()

    val stepsToTake = 64
    var stepsTaken = 0
    val currentPositions = mutableSetOf(startCoordinate)

    val stepOffsets = listOf(
        Point(0, -1),
        Point(1, 0),
        Point(0, 1),
        Point(-1, 0)
    )

    while(stepsTaken < stepsToTake) {
        val newPositions = mutableListOf<Point>()
        for (position in currentPositions) {
            for (offset in stepOffsets) {
                val newPosition = position + offset

                // Out of bounds check
                if (newPosition.x < 0 || newPosition.y < 0 || newPosition.y >= input.size || newPosition.x >= input[newPosition.y].size) {
                    continue
                }
                // Don't move into rocks
                if (input[newPosition.y][newPosition.x] == '#') {
                    continue
                }
                newPositions.add(newPosition)
            }
        }
        currentPositions.clear()
        currentPositions.addAll(newPositions)

        stepsTaken++
    }

    println(currentPositions.size)

    val visualised = input.mapIndexed { y, row ->
        row.mapIndexed { x, char ->
            if(Point(x, y) in currentPositions) 'O' else char
        }
    }

    visualised.forEach { println(it.joinToString("")) }
}