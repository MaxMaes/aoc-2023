package day22

import Graph
import Point3D
import Rect3D
import readInput

fun main() {
    val bricks = readInput("day_22_example").map { line ->
        line.split("~")
            .map { rawCoords ->
                rawCoords.split(",")
                    .map { it.toInt() }
            }
            .map { (x, y, z) -> Point3D(x, y, z) }
    }.map { (start, end) -> Rect3D(start, end) }.sortedBy { brick -> brick.bottom }

    // First, we need to drop all the cubes down until they cannot
    val floorZ = 1

    bricks.forEach { brick ->
        val otherBricks = bricks.filter { it != brick }
        while (brick.bottom > floorZ && !brick.touchesInStack(Direction3D.DOWN, otherBricks)) {
            brick.move(Direction3D.DOWN)
        }
    }

    // Find all bricks that are on the floor (bottom of the stack)
    val floorBricks = bricks.filter { it.bottom == floorZ }
    val brickGraph = Graph<Rect3D>()

    val bricksToVertices = bricks.map { brick -> brick to brickGraph.createVertex(brick) }

    // For each bricks, find all the bricks it supports
    bricksToVertices.forEach { (brick, vertex) ->
        val otherBricks = bricks.filter { it != brick }
        val supportedBy = brick.supportsDirectly(otherBricks)
        supportedBy.forEach { supportedBrick ->
            val supportedVertex = bricksToVertices.find { it.first == supportedBrick }!!.second
            brickGraph.connect(vertex, supportedVertex)
        }
    }


    println("a")
    // Count the number of bricks that are supported by more than one brick

}