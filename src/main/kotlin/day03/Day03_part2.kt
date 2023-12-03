package day03

import readInput

data class Gear(val x: Int, val y: Int, val touchingDigits: MutableList<Int> = mutableListOf()) {
    val ratio: Int
        get() = touchingDigits.reduce { acc, i ->  i * acc }
}

fun main() {
    fun Int.length(): Int = this.toString().length

    fun Char.isGear(): Boolean = this == '*'

    val input = readInput("day_03")

    val gearCoordinates = input.flatMapIndexed { y: Int, s: String ->
        s.mapIndexedNotNull { x: Int, c: Char ->
            if (c.isGear()) {
                Gear(x, y)
            } else {
                null
            }
        }
    }.associateBy { gear -> (gear.x to gear.y) }

    input.forEachIndexed { numY, line ->
        var nextScanX = 0
        line.forEachIndexed { numX, character ->
            if (character.isDigit() && numX >= nextScanX) {
                val number = line.substring(numX).takeWhile { it.isDigit() }.toInt()
                val length = number.length()

                nextScanX = numX + length

                // Scan around coordinates of the number
                val minY = (numY - 1).coerceAtLeast(0)
                val maxY = (numY + 1).coerceAtMost(input.size - 1)
                val minX = (numX - 1).coerceAtLeast(0)
                val maxX = (numX + length).coerceAtMost(line.length - 1)

                for(y in minY..maxY) {
                    for(x in minX..maxX) {
                        if(input[y][x].isGear()) {
                            gearCoordinates[x to y]?.touchingDigits?.add(number)
                        }
                    }
                }
            }
        }
    }

    val validGears = gearCoordinates.filter { (_, gear) ->
        gear.touchingDigits.size > 1
    }

    println(validGears.values.sumOf { it.ratio })
}