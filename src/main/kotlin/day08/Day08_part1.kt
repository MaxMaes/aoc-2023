package day08

import readInput

fun main() {
    val input = readInput("day_08")

    val exitsRegex = Regex("[A-Z]{3}")
    val instructions = input[0]
    val map = input.subList(2, input.size).map { line ->
        val splitted = line.split(" = ")
        val exits = exitsRegex.findAll(splitted[1])
        splitted[0] to (exits.first().value to exits.last().value)
    }.toMap()

    var currentNode = "AAA"
    val destinationNode = "ZZZ"

    var steps = 0
    while (currentNode != destinationNode) {
        val exits = map[currentNode]!!
        val nextNode = when (val instruction = instructions[steps % instructions.length]) {
            'L' -> exits.first
            'R' -> exits.second
            else -> throw Exception("Unknown instruction: $instruction")
        }
        currentNode = nextNode
        steps += 1
    }

    println(steps)
}