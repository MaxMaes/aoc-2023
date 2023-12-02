package day02

import readInput

fun main() {
    val input = readInput("day_02")
    val maxValues = mapOf("red" to 12, "green" to 13, "blue" to 14)
    val remaining = input.filter { line ->
        val split = line.split(":")
        val gameRounds = split[1].split(";")
        gameRounds.all { round ->
            val roundCubes = round.split(", ")
            roundCubes.all { cubeCount ->
                val cubeCountSplit = cubeCount.trim().split(" ")
                val amount = cubeCountSplit[0].toInt()
                val color = cubeCountSplit[1]
                amount <= (maxValues[color]!!)
            }
        }
    }
    val answer = remaining.sumOf { line ->
        val split = line.split(":")
        split[0].split(" ")[1].toInt()
    }
    println(answer)
}