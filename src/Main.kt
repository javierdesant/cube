import java.awt.*
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import kotlin.system.exitProcess

private val cube: List<Vert> = (1..2).flatMap { z ->
    listOf(
        Vert(-0.5,  0.5, z),
        Vert( 0.5,  0.5, z),
        Vert(-0.5, -0.5, z),
        Vert( 0.5, -0.5, z)
    )
}

fun main() {
    val f = Frame("cube")
    val canvas = object : Canvas() {
        override fun paint(g: Graphics) {
            g.color = Color.GREEN
            cube.forEach {
                printVert(g, bounds, it)
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

private data class Vert(
    val x: Float,
    val y: Float,
    val z: Int
) {
    constructor(x: Number, y: Number, z: Number) : this(x.toFloat(), y.toFloat(), z.toInt())
}

private fun printVert(g: Graphics, bounds: Rectangle, vert: Vert) {
    printVert(g, bounds, vert.x, vert.y, vert.z)
}
private fun printVert(g: Graphics, bounds: Rectangle, x: Float, y: Float, z: Int) {
    if (z <= 0) return

    val size = 12
    val wx = bounds.width
    val wy = bounds.height
    val maxDim = wx.coerceAtLeast(wy).toFloat()

    var x = x / z
    var y = y / z
    x = (x + 1) * maxDim / 2 + (wx - maxDim) / 2 - size / 2
    y = (y + 1) * maxDim / 2 + (wy - maxDim) / 2 - size / 2

    g.fillRect(x.toInt(), y.toInt(), size, size)
}