package day11

import readInput
import kotlin.math.abs


fun main() {
    val map = readInput("day_11").map { it.toCharArray().toMutableList() }.toMutableList()
    val rowsWithoutGalaxy = mutableListOf<Long>()
    map.forEachIndexed { index, row ->
        if ('#' !in row) {
            rowsWithoutGalaxy.add(index.toLong())
        }
    }
    // Create columns from rows
    val columns = map[0].indices.map { index -> map.map { row -> row[index] } }
    val columnsWithoutGalaxy = mutableListOf<Long>()
    columns.forEachIndexed { index, column ->
        if ('#' !in column) {
            columnsWithoutGalaxy.add(index.toLong())
        }
    }
    
    val incrementer = 999999L
    var dy = 0L
    val newMap = map.mapIndexed { y, row ->
        var dx = 0L
        if (y.toLong() in rowsWithoutGalaxy) {
            dy += incrementer
        }
        row.mapIndexed { x, char ->
            if (x.toLong() in columnsWithoutGalaxy) {
                dx += incrementer
            }
            (x + dx) to (y + dy) to char
        }
    }.toMutableList()

    val galaxies = newMap.map { row ->
        row.mapNotNull { (a, char) ->
            if (char == '#') a
            else null
        }
    }.flatten()

    // calculate the distance between each galaxy
    // Make sure we don't calculate the distance between a galaxy and itself and don't calculate A to B and B to A
    val distances = galaxies.mapIndexed { index, galaxy ->
        galaxies.subList(index + 1, galaxies.size).map { otherGalaxy ->
            val distance = abs(galaxy.first - otherGalaxy.first) + abs(galaxy.second - otherGalaxy.second)
            galaxy to otherGalaxy to distance
        }
    }.flatten().sumOf { (_, distance) ->
        distance
    }

    println(distances)

}