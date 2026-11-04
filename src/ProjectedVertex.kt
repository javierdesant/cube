import java.awt.Graphics
import java.awt.Rectangle

data class ProjectedVertex(
    private val bounds: Rectangle,
    private val originalX: Float,
    private val originalY: Float,
    private val depth: Float = 1f
) : Vertex(originalX, originalY, depth) {

    init {
        if (depth <= 0f) {
            throw IllegalArgumentException("Depth must be greater than 0!")
        }
    }

    private val size = 12
    private val wx = bounds.width
    private val wy = bounds.height
    private val minDim = wx.coerceAtMost(wy).toFloat()

    override val x: Float =
        (originalX / depth + 1) * minDim / 2 +
                (wx - minDim) / 2 - size / 2

    override val y: Float =
        (originalY / depth + 1) * minDim / 2 +
                (wy - minDim) / 2 - size / 2

    fun printVert(g: Graphics): ProjectedVertex {
        g.fillRect(x.toInt(), y.toInt(), size, size)
        return this
    }

    fun printConn(g: Graphics, vert: ProjectedVertex): ProjectedVertex {
        g.drawLine(x.toInt(), y.toInt(), vert.x.toInt(), vert.y.toInt())
        return this
    }
}


