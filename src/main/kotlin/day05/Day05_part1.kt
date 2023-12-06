package day05

import readInput
import java.math.BigInteger

fun main() {
    data class Rule(val destination: LongRange, val input: LongRange) {
        fun convert(toConvert: Long): Long {
            return if(toConvert in input) {
                val index = toConvert - input.first
                (destination.first + index)
            } else {
                toConvert
            }
        }
    }

    val input = readInput("day_05_example")

    val seeds = input[0].split("seeds: ")[1].split(" ").map { it.toLong() }

    val ruleSets = mutableListOf<List<Rule>>()
    var currentRuleList = mutableListOf<Rule>()
    input.subList(2, input.size).forEach { line ->
        if(line.contains("map")) {
            // Start a new map
            ruleSets.add(currentRuleList)
        } else if(line.isNotEmpty()) {
            // Conversion instructions
            val instructions = line.split(" ").map { it.toLong() }
            val destinationRange = instructions[0]..instructions[0]+instructions[2]
            val inputRange = instructions[1]..instructions[1]+instructions[2]

            currentRuleList.add(Rule(destinationRange, inputRange))
        } else if(line.isEmpty()) {
            // End of map
            currentRuleList = mutableListOf()
        }
    }
    val endLocations = seeds.map { seed ->
        var current = seed

        // Travel through all converters
        ruleSets.forEach { ruleSet ->
            // Try to find a rule in the current rule set
            val rule = ruleSet.find { it.input.contains(current) }
            current = rule?.convert(current) ?: current
        }

        current // Return the final result for the current seed
    }
    println(endLocations.min())
}