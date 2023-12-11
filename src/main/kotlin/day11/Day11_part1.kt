package day11

import readInput
import kotlin.math.abs

fun main() {
    val rows = readInput("day_11_example").map { it.toCharArray().toMutableList() }.toMutableList()
    val rowsWithoutGalaxy = mutableListOf<Int>()
    rows.forEachIndexed { index, row ->
        if ('#' !in row) {
            rowsWithoutGalaxy.add(index)
        }
    }
    // Create columns from rows
    val columns = rows[0].indices.map { index -> rows.map { row -> row[index] } }
    val columnsWithoutGalaxy = mutableListOf<Int>()
    columns.forEachIndexed { index, column ->
        if ('#' !in column) {
            columnsWithoutGalaxy.add(index)
        }
    }

    rowsWithoutGalaxy.forEachIndexed { index, rowIndex ->
        rows.add(rowIndex + index, MutableList(rows[0].size) { '.' })
    }
    rows.forEach { row ->
        columnsWithoutGalaxy.forEachIndexed { index, columnIndex ->
            row.add(columnIndex + index, '.')
        }
    }

    val galaxies = rows.mapIndexed { y, row ->
        row.mapIndexedNotNull { x, char ->
            if (char == '#') x to y
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
    }.flatten().sumOf { (_, distance) -> distance }

    println(distances)

}