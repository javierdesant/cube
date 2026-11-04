import java.awt.Rectangle
import kotlin.math.cos
import kotlin.math.sin

data class VirtualVertex(
    override val x: Float,
    override val y: Float,
    override val z: Float
) : Vertex(x, y, z) {
    constructor(x: Number, y: Number, z: Number) : this(x.toFloat(), y.toFloat(), z.toFloat())

    val offsetZ = 2.5f

    fun to2D(bounds: Rectangle): ProjectedVertex {
        // Offset z to prevent division by zero or negative depth during 3D rotation
        val perspectiveZ = z + offsetZ
        return ProjectedVertex(bounds, x, y, perspectiveZ)
    }

    fun rotateX(a: Double): VirtualVertex {
        val cosA = cos(a).toFloat()
        val sinA = sin(a).toFloat()
        return VirtualVertex(
            x = x,
            y = y * cosA - z * sinA,
            z = y * sinA + z * cosA
        )
    }

    fun rotateY(a: Double): VirtualVertex {
        val cosA = cos(a).toFloat()
        val sinA = sin(a).toFloat()
        return VirtualVertex(
            x = x * cosA + z * sinA,
            y = y,
            z = -x * sinA + z * cosA
        )
    }

    fun rotateZ(a: Double): VirtualVertex {
        val cosA = cos(a).toFloat()
        val sinA = sin(a).toFloat()
        return VirtualVertex(
            x = x * cosA - y * sinA,
            y = x * sinA + y * cosA,
            z = z
        )
    }
}

