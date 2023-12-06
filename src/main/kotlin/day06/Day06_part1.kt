package day06

import readInput

fun main() {
    val input = readInput("day_06")

    val timeLine = input.first()
    val distanceLine = input.last()

    val timeValuesText = timeLine.split("Time:")[1]
    val distanceValuesText = distanceLine.split("Distance:")[1]

    val timeValues = timeValuesText.trim().split(" ").mapNotNull(String::toIntOrNull)
    val distanceValues = distanceValuesText.trim().split(" ").mapNotNull(String::toIntOrNull)

    val rounds = timeValues.zip(distanceValues)
    println(rounds)

    val chargeSpeed = 1

    fun distanceCalculator(holdTime: Int, roundDuration: Int): Int {
        val speed = chargeSpeed * holdTime
        val distance = speed * (roundDuration - holdTime)
        return distance
    }

    val waysToWin = rounds.map { (roundDuration, recordDistance) ->
        val waysToWinRound = (0..roundDuration).mapNotNull {holdTime ->
            val distance = distanceCalculator(holdTime, roundDuration)
            if(distance > recordDistance) {
                holdTime
            } else {
                null
            }
        }
        waysToWinRound.size
    }.reduce{acc, i -> acc * i}

    println(waysToWin)


}