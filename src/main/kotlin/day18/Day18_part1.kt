package day18

import Point
import readInput
import kotlin.math.sign

fun main() {
    val offsets = listOf(
        Point(0, -1),
        Point(-1, 0),
        Point(1, 0),
        Point(0, 1),
    )

    val input = readInput("day_18")
        .map { line -> line.split(" ") }
        .map { (instruction, amount, color) ->
            val moveInstruction = when (instruction) {
                "U" -> Point(0, -1)
                "L" -> Point(-1, 0)
                "R" -> Point(1, 0)
                "D" -> Point(0, 1)
                else -> throw IllegalArgumentException("Invalid instruction: $instruction")
            } * amount.toInt()

            moveInstruction to color
        }

    val grid = mutableListOf(mutableListOf('#'))
    var currentPoint = Point(0, 0)
    input.forEach { (moveInstruction, color) ->
        // If the grid is not big enough, add a new row/column to the grid
        // Make sure we have enough rows to reach the end point
        val targetPosition = currentPoint + moveInstruction

        while (grid.size <= targetPosition.y) {
            grid.add(MutableList(grid.maxOf { it.size }) { '.' })
        }
        if (targetPosition.y < 0) {
            // Add new rows to the top of the grid and transpose the current position
            while (targetPosition.y < 0) {
                grid.add(0, MutableList(grid.maxOf { it.size }) { '.' })
                targetPosition.y += 1
                currentPoint.y += 1
            }
        }

        // Move to the next point by adding the move instruction
        // Every point traveled over should add a '#' to the position in the grid
        while (currentPoint != targetPosition) {
            if (moveInstruction.y != 0) {
                // Vertical move by 1
                currentPoint.y += moveInstruction.y.sign
                // Make sure we have enough rows below the current row
                while (grid[currentPoint.y].size <= currentPoint.x) {
                    grid[currentPoint.y].add('.')
                }
                grid[currentPoint.y][currentPoint.x] = '#'
            } else if (moveInstruction.x != 0) {
                // Horizontal move by 1
                currentPoint.x += moveInstruction.x.sign
                if (currentPoint.x < 0) {
                    // Add a new column to the left of every row
                    grid.forEach { row -> row.add(0, '.') }
                    currentPoint.x = 0
                    targetPosition.x += 1
                }

                if (moveInstruction.x.sign == 1 && grid[currentPoint.y].size <= currentPoint.x) {
                    grid[currentPoint.y].add('#')
                } else {
                    grid[currentPoint.y][currentPoint.x] = '#'
                }
            } else {
                // not supported
            }
        }
    }

    // Print the outline
//    grid.forEach { row -> println(row.joinToString("")) }
//    println("\n\n")

    // Fill in the grid
    // Last character of the smallest row should be a '#' and character before that should be a '.' which is our starting point
    val smallestRow = grid.minBy { row -> row.size }
    val startingPoint = Point(smallestRow.size - 2, grid.indexOf(smallestRow))

    grid[startingPoint.y][startingPoint.x] = '#'
    val queue = mutableListOf(startingPoint)
    while(queue.isNotEmpty()) {
        val currentPoint = queue.removeFirst()
        offsets.forEach { offset ->
            val newPoint = currentPoint + offset
            if (grid[newPoint.y][newPoint.x] == '.') {
                grid[newPoint.y][newPoint.x] = '#'
                queue.add(newPoint)
            }
        }
    }

    grid.forEach { row -> println(row.joinToString("")) }

    grid.sumOf { row -> row.count { it == '#' } }.also { println(it) }
    println("\n\n")

}