enum class Direction3D() {
    /**
     * Indicates an increasing Z direction.
     */
    UP,
    /**
     * Indicates a decreasing Z direction.
     */
    DOWN,
    /**
     * Indicates a decreasing Y direction.
     */
    BACK,
    /**
     * Indicates an increasing Y direction.
     */
    FORWARD,
    /**
     * Indicates a decreasing X direction.
     */
    LEFT,
    /**
     * Indicates an increasing X direction.
     */
    RIGHT;

    fun toPoint3D() = when (this) {
        UP -> Point3D(0, 0, 1)
        DOWN -> Point3D(0, 0, -1)
        BACK -> Point3D(0, -1, 0)
        FORWARD -> Point3D(0, 1, 0)
        LEFT -> Point3D(-1, 0, 0)
        RIGHT -> Point3D(1, 0, 0)
    }
}