import java.awt.*
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import kotlin.system.exitProcess

private val cube: List<VVert> = (1..2).flatMap { z ->
    listOf(
        VVert( 0.5,  0.5, z),
        VVert(-0.5,  0.5, z),
        VVert( 0.5, -0.5, z),
        VVert(-0.5, -0.5, z),
    )
}

fun main() {
    val f = Frame("cube")
    val canvas = object : Canvas() {
        override fun paint(g: Graphics) {
            g.color = Color.GREEN
            cube.forEach { i ->
                cube.forEach {
                    it.to2D(bounds).printConn(g, i.to2D(bounds))
                }
            }
        }
    }
    canvas.background = Color.BLACK
    f.add(canvas)

    f.setSize(800, 600)
    f.setLocationRelativeTo(null)

    f.addWindowListener(object : WindowAdapter() {
        override fun windowClosing(e: WindowEvent) {
            f.dispose()
            exitProcess(0)
        }
    })

    f.isVisible = true
}

private abstract class Vert(
    open val x: Number,
    open val y: Number,
    open val z: Number
)

private data class PVert(
    private val bounds: Rectangle,
    private val originalX: Float,
    private val originalY: Float,
    private val depth: Int = 1
) : Vert(originalX, originalY, 1) {

    init {
        if (depth <= 0) {
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

    fun printVert(g: Graphics): PVert {
        g.fillRect(x.toInt(), y.toInt(), size, size)
        return this
    }

    fun printConn(g: Graphics, vert: PVert): PVert{
        g.drawLine(x.toInt(), y.toInt(), vert.x.toInt(), vert.y.toInt())
        return this
    }
}

private data class VVert(
    override val x: Float,
    override val y: Float,
    override val z: Int
) : Vert(x, y, z) {
    constructor(x: Number, y: Number, z: Int) : this(x.toFloat(), y.toFloat(), z)

    fun to2D(bounds: Rectangle): PVert {
        return PVert(bounds, x, y, z)
    }
}
