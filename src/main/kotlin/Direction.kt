enum class Direction(val angle: Int) {
    NORTH(0), EAST(90), SOUTH(180), WEST(270);
    operator fun plus(other: Direction) = Direction.of((angle + other.angle) % 360)
    operator fun minus(other: Direction) = Direction.of((angle - other.angle) % 360)

    fun toPoint() = when (this) {
        NORTH -> Point(0, -1)
        EAST -> Point(1, 0)
        SOUTH -> Point(0, 1)
        WEST -> Point(-1, 0)
    }

    companion object {
        fun of(c: Char): Direction {
            return when (c.uppercaseChar()) {
                'N', 'U' -> NORTH
                'E', 'R' -> EAST
                'S', 'D' -> SOUTH
                'W', 'L' -> WEST
                else -> throw IllegalArgumentException("Invalid rotation: $c")
            }
        }

        fun of(i: Int): Direction {
            return when ((i + 720) % 360) {
                0 -> NORTH
                90 -> EAST
                180 -> SOUTH
                270 -> WEST
                else -> throw IllegalArgumentException("Invalid rotation: $i")
            }
        }
    }
}