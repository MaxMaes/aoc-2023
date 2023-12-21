data class Point(var x: Int, var y: Int) {
    fun move(direction: Direction) {
        val offset = direction.toPoint()
        this.x += offset.x
        this.y += offset.y
    }

    fun moveWithinGrid(direction: Direction, gridSize: Size): Boolean {
        val offset = direction.toPoint()
        val newX = this.x + offset.x
        val newY = this.y + offset.y
        if (newX < 0 || newX >= gridSize.width || newY < 0 || newY >= gridSize.height) {
            return false
        }
        this.x = newX
        this.y = newY
        return true
    }

    operator fun plus(other: Point): Point = Point(this.x + other.x, this.y + other.y)
    operator fun times(other: Int): Point = Point(this.x * other, this.y * other)
    operator fun times(other: Point): Point = Point(this.x * other.x, this.y * other.y)

}