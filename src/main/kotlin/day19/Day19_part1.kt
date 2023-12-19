package day19

import readFullInput

fun main() {
    val input = readFullInput("day_19").split("\n\n")


    data class Part(val x: Int, val m: Int, val a: Int, val s: Int) {
        fun sum() = x + m + a + s
    }

    data class Operation(val property: Char, val operator: Char, val value: Int, val trueOutcome: String) {
        /**
         * Tests the part against this operation.
         *
         * @return outcome if true, null if false
         */
        fun test(part: Part): String? {
            val valueToTest = when (property) {
                'x' -> part.x
                'm' -> part.m
                'a' -> part.a
                's' -> part.s
                else -> throw IllegalArgumentException("Invalid property: $property")
            }

            val result = when (operator) {
                '>' -> valueToTest > value
                '<' -> valueToTest < value
                else -> throw IllegalArgumentException("Invalid operator: $operator")
            }

            return if (result) {
                trueOutcome
            } else {
                null
            }
        }


    }

    data class Workflow(val name: String, val operations: List<Operation>, val elseAction: String) {
        fun process(part: Part): String {
            return operations.firstNotNullOfOrNull { it.test(part) }
                ?: elseAction
        }
    }

    val workflowLines = input.first()
    val partLines = input.last().lines()


    val partRegex = Regex("[xmas]=(\\d+)")
    val parts = partLines.map { line ->
        val match = partRegex.findAll(line).toList()
        val x = match[0].groupValues[1].toInt()
        val m = match[1].groupValues[1].toInt()
        val a = match[2].groupValues[1].toInt()
        val s = match[3].groupValues[1].toInt()

        Part(x, m, a, s)
    }

    val workflows = workflowLines.lines().map { line ->
        val name = line.takeWhile { it != '{' }
        val operations = line.dropWhile { it != '{' }.drop(1).dropLast(1)

        val splitOperation = operations.split(",")
        val elseAction = splitOperation.last()

        val ifOperations = splitOperation.dropLast(1).map { action ->
            val splitAction = action.split(":")
            val operation = splitAction.first()
            val testProperty = operation.first()
            val operator = operation.drop(1).first()
            val testValue = operation.drop(2).toInt()

            val outcomeIfTrue = splitAction.last()

            Operation(testProperty, operator, testValue, outcomeIfTrue)
        }

        Workflow(name, ifOperations, elseAction)
    }.associateBy { it.name }

    val startWorkflow = workflows.getValue("in")
    val acceptedParts = parts.filter { part ->
        var currentWorkflow = startWorkflow
        var outcome = currentWorkflow.process(part)
        while (outcome != "A" && outcome != "R") {
            currentWorkflow = workflows.getValue(outcome)
            outcome = currentWorkflow.process(part)
        }

        outcome == "A"
    }

    val answer = acceptedParts.sumOf { it.sum() }

    println(answer)
}