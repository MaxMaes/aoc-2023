package day03

import readInput

fun main() {
    fun Int.length(): Int = this.toString().length

    fun Char.isValidSymbol(): Boolean {
        return !this.isLetterOrDigit() && this != '.'
    }

    val input = readInput("day_03")

    var total = 0
    input.forEachIndexed { numY, line ->
        var nextScanX = 0
        line.forEachIndexed { numX, character ->
            if(character.isDigit() && numX >= nextScanX) {
                val number = line.substring(numX).takeWhile { it.isDigit() }.toInt()
                val length = number.length()

                nextScanX = numX + length

                // Scan around coordinates of the number
                val minY = (numY - 1).coerceAtLeast(0)
                val maxY = (numY + 1).coerceAtMost(input.size - 1)
                val minX = (numX - 1).coerceAtLeast(0)
                val maxX = (numX + length).coerceAtMost(line.length - 1)

                if((minY..maxY).any { y ->
                    (minX..maxX).any { x ->
                       input[y][x].isValidSymbol()
                    }
                }) {
                    total += number
                }
            }
        }
    }

    println(total)
}