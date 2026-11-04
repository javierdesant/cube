import java.awt.Rectangle
import kotlin.math.cos
import kotlin.math.sin

data class VVert(
    override val x: Float,
    override val y: Float,
    override val z: Float
) : Vert(x, y, z) {
    constructor(x: Number, y: Number, z: Number) : this(x.toFloat(), y.toFloat(), z.toFloat())

    fun to2D(bounds: Rectangle): PVert {
        // Offset z to prevent division by zero or negative depth during 3D rotation
        val perspectiveZ = z + 2.5f
        return PVert(bounds, x, y, perspectiveZ)
    }

    fun rotateX(a: Double): VVert {
        val cosA = cos(a).toFloat()
        val sinA = sin(a).toFloat()
        return VVert(
            x = x,
            y = y * cosA - z * sinA,
            z = y * sinA + z * cosA
        )
    }

    fun rotateY(a: Double): VVert {
        val cosA = cos(a).toFloat()
        val sinA = sin(a).toFloat()
        return VVert(
            x = x * cosA + z * sinA,
            y = y,
            z = -x * sinA + z * cosA
        )
    }

    fun rotateZ(a: Double): VVert {
        val cosA = cos(a).toFloat()
        val sinA = sin(a).toFloat()
        return VVert(
            x = x * cosA - y * sinA,
            y = x * sinA + y * cosA,
            z = z
        )
    }
}

