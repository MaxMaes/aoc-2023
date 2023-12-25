package day25

import Graph
import readInput

fun main() {
    val graph = Graph<String>()

    val input = readInput("day_25_example").map { line ->
        val (part, connectionsLine) = line.split(":")
        val connections = connectionsLine.trim().split(" ")
        println(connections)

        if(graph.vertexFor(part) == null) {
            graph.createVertex(part)
        } else {
            graph.vertexFor(part)!!
        }

        connections.forEach { connection ->
            if(graph.vertexFor(connection) == null) {
                graph.createVertex(connection)
            } else {
                graph.vertexFor(connection)!!
            }
            graph.connect(graph.vertexFor(part)!!, graph.vertexFor(connection)!!)
        }
    }

    println(graph)
}