class Graph<T> {
    private val map = mutableMapOf<Vertex<T>, ArrayList<Edge<T>>>()

    fun createVertex(value: T): Vertex<T> {
        val vertex = Vertex(map.size, value)
        map[vertex] = arrayListOf()
        return vertex
    }

    fun vertexFor(value: T): Vertex<T>? {
        return map.keys.find { it.value == value }
    }

    fun connect(from: Vertex<T>, to: Vertex<T>, weight: Int = 0) {
        val edge = Edge(from, to, weight)
        map[from]?.add(edge)
        from.edges.add(edge)
    }

    inner class Edge<T>(val from: Vertex<T>, val to: Vertex<T>, val weight: Int = 0) {
    }

    inner class Vertex<T>(val index: Int, val value: T) {
        val edges = mutableListOf<Edge<T>>()
    }
}