fun main() {
    val grid = readInput("day_14").map { it.toMutableList() }
    var rockMovedInIteration = true
    while(rockMovedInIteration) {
        rockMovedInIteration = false
        for(y in grid.indices) {
            val line = grid[y]
            for(x in line.indices) {
                val char = line[x]
                if(char == '.') continue // Ground
                if(char == '#') continue // Static rock
                if(char == 'O') {
                    // See if the rock can move up
                    val lineAbove = grid.getOrNull(y-1)
                    if(lineAbove != null) {
                        val charAbove = lineAbove.getOrNull(x)
                        if(charAbove == '.') {
                            grid[y-1][x] = 'O'
                            grid[y][x] = '.'
                            rockMovedInIteration = true
                        }
                    }
                }
            }
        }
    }

    val weights = grid.mapIndexed { y, line ->
        line.mapIndexed { x, char ->
            if(char == 'O') {
                grid.size - y
            } else {
                0
            }
        }
    }.flatten().sum()

    println(grid.map { it.joinToString("") }.joinToString("\n"))
    println(weights)
}