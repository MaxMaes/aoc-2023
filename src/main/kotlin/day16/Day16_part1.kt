package day16

import Direction
import Point
import Size
import readInput

fun main() {
    val maze = readInput("day_16").map(String::toCharArray)

    fun Direction.passThrough(mirror: Char): List<Direction> {
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

    val beams = mutableListOf(Direction.EAST to Point(-1, 0))
    val mazeSize = Size(maze.first().size, maze.size)

    // For every coordinate, we need to keep a list of directions that a beam passes through it
    val tileBeams = maze.map { line ->
        List(line.size) { emptyList<Direction>().toMutableList() }
    }

    val tiles = maze.map { line ->
        MutableList(line.size) { '.' }
    }

    while (beams.isNotEmpty()) {
        val newBeamDirections = mutableListOf<Pair<Direction, Point>>()
        for (beam in beams) {
            // Let every beam take 1 step
            val (direction, coords) = beam
            // Move the beam if possible
            if (coords.moveWithinGrid(direction, mazeSize)) {
                // Beam was moved to a new coordinate, process it's character
                val mazeChar = maze[coords.y][coords.x]
                val newDirections = direction.passThrough(mazeChar)

                if(!tileBeams[coords.y][coords.x].containsAll(newDirections)) {
                    tileBeams[coords.y][coords.x].addAll(newDirections)
                    newBeamDirections.addAll(newDirections.map { it to coords.copy() })
                }
            }

            tiles[beam.second.y][beam.second.x] = '#'
        }
        beams.clear()
        beams.addAll(newBeamDirections)
    }

    val energisedTiles = tiles.sumOf { line -> line.count { it == '#' } }
//    tiles.map{it.joinToString("")}.forEach(::println)
    println("Energised tiles: $energisedTiles")
}