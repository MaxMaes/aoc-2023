package day15

import readInput

fun main() {
    val input = readInput("day_15")
    val instructions = input.first().split(",")

    val boxes = hashMapOf<Long, MutableList<Pair<String, Int>>>()

    val focalLengthRegex = Regex("([a-z]+)=(\\d)")
    val removalInstructionRegex = Regex("([a-z]+)-")

    fun HASH(code: String): Long {
        return code.fold(0L) { acc, char ->
            val ascii = char.code
            val value = acc + ascii
            val multiplied = value * 17
            multiplied % 256
        }
    }

    for (instruction in instructions) {
        focalLengthRegex.matchEntire(instruction)?.let { match ->
            val label = match.groups[1]!!.value
            val boxNum = HASH(label)
            val focalLength = match.groups[2]!!.value.toInt()
            val box = boxes.getOrPut(boxNum) { mutableListOf() }

            val existingLabelIndex = box.indexOfLast { it.first == label }
            if (existingLabelIndex == -1) {
                box.add(label to focalLength)
            } else {
                box[existingLabelIndex] = label to focalLength
            }
        } ?: {
            removalInstructionRegex.matchEntire(instruction)?.let { match ->
                val label = match.groups[1]!!.value
                val boxNum = HASH(label)
                boxes[boxNum]?.removeIf { it.first == label }
            }
        }

    }

    // The focusing power of a single lens is the result of multiplying together:
    // One plus the box number of the lens in question.
    // The slot number of the lens within the box: 1 for the first lens, 2 for the second lens, and so on.
    // The focal length of the lens.
    val answer = boxes.map { (boxNum, lenses) ->
        lenses.mapIndexed { index, (label, focalLength) ->
            (1 + boxNum) * (index + 1) * focalLength
        }
    }.flatten().sum()

    println(answer)


}