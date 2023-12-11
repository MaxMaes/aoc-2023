package day10

import readInput

fun main() {
    //| is a vertical pipe connecting north and south.
    //- is a horizontal pipe connecting east and west.
    //L is a 90-degree bend connecting north and east.
    //J is a 90-degree bend connecting north and west.
    //7 is a 90-degree bend connecting south and west.
    //F is a 90-degree bend connecting south and east.
    //. is ground; there is no pipe in this tile.
    //S is the starting position of the animal; there is a pipe on this tile, but your sketch doesn't show what shape the pipe has.

    val maze = readInput("day_10").map { it.toCharArray().toList() }
    // Maze is a 2D grid of pipe characters

    // Find the starting position
    val startingPosition = maze.mapIndexed { y, row ->
        row.mapIndexed { x, char ->
            if (char == 'S') {
                Pair(x, y)
            } else {
                null
            }
        }
    }.flatten().filterNotNull().first()


    val northPipe =
        Pair(startingPosition.first, startingPosition.second - 1).let { maze.getOrNull(it.second)?.getOrNull(it.first) }
    val eastPipe =
        Pair(startingPosition.first + 1, startingPosition.second).let { maze.getOrNull(it.second)?.getOrNull(it.first) }
    val southPipe =
        Pair(startingPosition.first, startingPosition.second + 1).let { maze.getOrNull(it.second)?.getOrNull(it.first) }
    val westPipe =
        Pair(startingPosition.first - 1, startingPosition.second).let { maze.getOrNull(it.second)?.getOrNull(it.first) }

    val startingPipeExitCharacters = mutableListOf<Char>()
    // Find out what pipe character should at the starting position S
    if (southPipe == '|' || southPipe == 'L' || southPipe == 'J') {
        println("Start connects to south")
        startingPipeExitCharacters.add('S')
    }
    if (northPipe == '|' || northPipe == '7' || northPipe == 'F') {
        println("Start connects to north")
        startingPipeExitCharacters.add('N')
    }
    if (eastPipe == '-' || eastPipe == 'J' || eastPipe == 'F') {
        println("Start connects to east")
        startingPipeExitCharacters.add('E')
    }
    if (westPipe == '-' || westPipe == 'L' || westPipe == '7') {
        println("Start connects to west")
        startingPipeExitCharacters.add('W')
    }

    val exitsMap = mutableMapOf(
        '|' to ('N' to 'S'),
        '-' to ('E' to 'W'),
        'L' to ('N' to 'E'),
        'J' to ('N' to 'W'),
        '7' to ('S' to 'W'),
        'F' to ('S' to 'E')
    )

    /**
     * Converts a destination direction to origin direction
     * Eg, when heading south you came from north
     */
    fun destinationToOrigin(currentDirection: Char): Char {
        return when (currentDirection) {
            'N' -> 'S'
            'S' -> 'N'
            'E' -> 'W'
            'W' -> 'E'
            else -> throw Exception("Invalid direction")
        }
    }

    fun directionForPath(pipe: Char, currentDirection: Char): Char {
        val (a, b) = exitsMap[pipe]!!
        val origin = destinationToOrigin(currentDirection)
        return when (origin) {
            a -> b
            b -> a
            else -> {
                throw Exception("Invalid direction")
            }
        }
    }

    fun offsetForDirection(direction: Char): Pair<Int, Int> {
        return when (direction) {
            'N' -> Pair(0, -1)
            'S' -> Pair(0, 1)
            'E' -> Pair(1, 0)
            'W' -> Pair(-1, 0)
            else -> throw Exception("Invalid direction")
        }
    }

    var direction = startingPipeExitCharacters.first()
    // Travel along the pipe for the first exit
    val firstExit = offsetForDirection(direction).let {
        Pair(
            startingPosition.first + it.first,
            startingPosition.second + it.second
        )
    }

    var nextCoord = firstExit
    val loop = mutableListOf(startingPosition)
    while (nextCoord != startingPosition) {
        loop.add(nextCoord)
        val nextPipe = maze.getOrNull(nextCoord.second)?.getOrNull(nextCoord.first)
        direction = directionForPath(nextPipe!!, direction)
        nextCoord = offsetForDirection(direction).let {
            Pair(
                nextCoord.first + it.first,
                nextCoord.second + it.second
            )
        }
    }

    println("Loop size: ${loop.size}")
    println("Answer: ${loop.size / 2}")
}