class Rect3D(startField: Point3D, endField: Point3D) {
    var start: Point3D = startField
        private set;
    var end: Point3D = endField
        private set;

    val bottom get() = minOf(start.z, end.z)
    val top get() = maxOf(start.z, end.z)
    val left get() = minOf(start.x, end.x)
    val right get() = maxOf(start.x, end.x)
    val front get() = minOf(start.y, end.y)
    val back get() = maxOf(start.y, end.y)

    override fun equals(other: Any?): Boolean {
        if (other !is Rect3D) {
            return false
        }
        return start == other.start && end == other.end
    }
    override fun toString(): String {
        return "Rect3D(start=$start, end=$end)"
    }

    fun move(direction: Direction3D) {
        val point = direction.toPoint3D()
        start += point
        end += point
    }

    fun intersects(other: Rect3D): Boolean {
        return !(other.left > this.right
                || other.right < this.left
                || other.front > this.back
                || other.back < this.front
                || other.bottom > this.top
                || other.top < this.bottom)
    }

    /**
     * Returns a list of all Rect3D shapes that are directly supported by shape
     */
    fun supportsDirectly(stack: List<Rect3D>): List<Rect3D> {
        val point = Direction3D.UP.toPoint3D()
        val movedRect = Rect3D(start + point, end + point)
        return stack.filter { this != it && it.intersects(movedRect) }
    }

    /**
     * Returns a list of all Rect3D shapes that are supported by this shape
     */
    fun supports(stack: List<Rect3D>): List<Rect3D> {
        val supported = mutableListOf<Rect3D>()
        var current = this
        while (true) {
            val supportedDirectly = current.supportsDirectly(stack)
            if (supportedDirectly.isEmpty()) {
                break
            }
            supported.addAll(supportedDirectly)
            current = supportedDirectly.first()
        }
        return supported
    }

    fun touchesInStack(direction: Direction3D, stack: List<Rect3D>): Boolean {
        val point = direction.toPoint3D()
        val movedRect = Rect3D(start + point, end + point)
        return stack.any { it.intersects(movedRect) }
    }
}