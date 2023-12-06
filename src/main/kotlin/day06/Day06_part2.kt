package day06

import readInput

fun main() {
    val input = readInput("day_06")

    val timeLine = input.first()
    val distanceLine = input.last()

    val timeValuesText = timeLine.split("Time:")[1]
    val distanceValuesText = distanceLine.split("Distance:")[1]

    val timeValues = timeValuesText.replace(" ", "").toLong()
    val distanceValues = distanceValuesText.replace(" ", "").toLong()

    val rounds = timeValues to distanceValues

    val chargeSpeed = 1

    fun distanceCalculator(holdTime: Long, roundDuration: Long): Long {
        val speed = chargeSpeed * holdTime
        return speed * (roundDuration - holdTime)
    }

    val (roundDuration, recordDistance) = rounds
    val waysToWin = (0..roundDuration).mapNotNull { holdTime ->
        val distance = distanceCalculator(holdTime, roundDuration)
        if (distance > recordDistance) {
            holdTime
        } else {
            null
        }
    }

    println(waysToWin.size)

}