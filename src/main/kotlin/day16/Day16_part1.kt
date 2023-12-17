package day16

import Direction
import Point
import Size
import readInput

fun main() {
    val maze = readInput("day_16_example").map(String::toCharArray)

    fun Direction.passThroughLens(mirror: Char): List<Direction> {
        return when (mirror) {
            '/' -> listOf(
                when (this) {
                    Direction.NORTH -> Direction.EAST
                    Direction.EAST -> Direction.NORTH
                    Direction.SOUTH -> Direction.WEST
                    Direction.WEST -> Direction.SOUTH
                }
            )

            '\\' -> listOf(
                when (this) {
                    Direction.NORTH -> Direction.WEST
                    Direction.EAST -> Direction.SOUTH
                    Direction.SOUTH -> Direction.EAST
                    Direction.WEST -> Direction.NORTH
                }
            )

            '|' -> when (this) {
                Direction.NORTH -> listOf(Direction.NORTH)
                Direction.EAST, Direction.WEST -> listOf(Direction.NORTH, Direction.SOUTH)
                Direction.SOUTH -> listOf(Direction.SOUTH)
            }

            '-' -> when (this) {
                Direction.NORTH, Direction.SOUTH -> listOf(Direction.EAST, Direction.WEST)
                Direction.EAST -> listOf(Direction.EAST)
                Direction.WEST -> listOf(Direction.WEST)
            }

            '.' -> listOf(this)

            else -> throw IllegalArgumentException("Invalid mirror: $mirror")
        }
    }

    val beamPaths = maze.map { line ->
        MutableList(line.size) { '.' to emptyList<Direction>().toMutableList() }
    }

    val beams = mutableListOf(Direction.EAST to Point(0, 0))
    val mazeSize = Size(maze.first().size, maze.size)
    while (beams.isNotEmpty()) {
        val newBeamDirections = mutableListOf<Pair<Direction, Point>>()
        for (beam in beams) {
            val (direction, coords) = beam
            // Move the beam if possible
            if (coords.moveWithinGrid(direction, mazeSize)) {
                // Beam was moved to a new coordinate, process it's character
                val mazeChar = maze[coords.y][coords.x]
                val newDirections = direction.passThroughLens(mazeChar)
                newBeamDirections.addAll(newDirections.map { it to coords.copy() })
            }

            val currentTileValue =  tiles[beam.second.y][beam.second.x]
            tiles[beam.second.y][beam.second.x] = '#'
        }
        beams.clear()
        beams.addAll(newBeamDirections)
    }

    tiles.forEach(::println)
}