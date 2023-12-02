package day02

import readInput

fun main() {
    val input = readInput("day_02")
    val answer = input.sumOf { line ->
        val split = line.split(":")
        val gameRounds = split[1].split(";")

        val minRequiredCubes = mutableMapOf("red" to -1, "green" to -1, "blue" to -1)
        gameRounds.forEach { round ->
            val roundCubes = round.split(", ")
            roundCubes.forEach { cubeCount ->
                val cubeCountSplit = cubeCount.trim().split(" ")
                val amount = cubeCountSplit[0].toInt()
                val color = cubeCountSplit[1]

                if(amount > minRequiredCubes[color]!!) {
                    minRequiredCubes[color] = amount
                }
            }
        }
        minRequiredCubes.values.reduce{ acc, i -> acc * i }
    }
    println(answer)
}