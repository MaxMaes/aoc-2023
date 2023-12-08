package day08

import readInput

fun main() {
    val input = readInput("day_08")

    val exitsRegex = Regex("[A-Z|\\d]{3}")
    val instructions = input[0]
    val map = input.subList(2, input.size).map { line ->
        val splitted = line.split(" = ")
        val exits = exitsRegex.findAll(splitted[1])
        splitted[0] to (exits.first().value to exits.last().value)
    }.toMap()

    val startNodes = map.filter { it.key.endsWith("A") }.keys

    // First travel all start nodes until they reach a node ending in Z
    val stepsPerNode = startNodes.map { node ->
        var currentNode = node

        var steps = 0
        while (!currentNode.endsWith('Z')) {
            val exits = map[currentNode]!!
            val nextNode = when (val instruction = instructions[steps % instructions.length]) {
                'L' -> exits.first
                'R' -> exits.second
                else -> throw Exception("Unknown instruction: $instruction")
            }
            currentNode = nextNode
            steps += 1
        }
        steps
    }

    fun gcd(aIn: Long, bIn: Long): Long {
        var a = aIn
        var b = bIn
        while (b > 0) {
            val temp = b
            b = a % b
            a = temp
        }
        return a
    }

    fun lcm(a: Long, b: Long): Long {
        return a * (b / gcd(a, b))
    }

    fun lcm(input: List<Long>): Long {
        var result = input[0]
        for (i in 1..<input.size) result = lcm(result, input[i])
        return result
    }

    // Find the answer by finding the least common multiple of the steps it took to reach a Z node from each start node
    println(lcm(stepsPerNode.map { it.toLong() }))
}