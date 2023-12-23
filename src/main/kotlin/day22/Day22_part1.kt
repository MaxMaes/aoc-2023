package day22

import Point3D
import Rect3D
import readInput

fun main() {
    val input = readInput("day_22").map { line ->
        line.split("~")
            .map { rawCoords ->
                rawCoords.split(",")
                    .map { it.toInt() }
            }
            .map { (x, y, z) -> Point3D(x, y, z) }
    }.map { (start, end) -> Rect3D(start, end) }.sortedBy { brick -> brick.bottom }

    // First, we need to drop all the cubes down until they cannot
    val floorZ = 1

    input.forEach { brick ->
        val otherBricks = input.filter { it != brick }
        while (brick.bottom > floorZ && !brick.touchesInStack(Direction3D.DOWN, otherBricks)) {
            brick.move(Direction3D.DOWN)
        }
    }

    // Calculate which bricks are supported by which other bricks
    val supports = input.map { brick ->
        val otherBricks = input.filter { it != brick }
        val supportedBy = brick.supports(otherBricks)
        supportedBy
    }

    // Count the number of bricks that are supported by more than one brick
    val ans = supports.filter { brick ->
        brick.all { supportedByThisBrick ->
            supports.count { supportedBy -> supportedBy.contains(supportedByThisBrick) } > 1
        }
    }.size

    println(ans)
}