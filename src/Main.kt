import org.w3c.dom.ranges.Range
import java.awt.*
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import kotlin.system.exitProcess

fun main() {
    val f = Frame("cube")
    val canvas = object : Canvas() {
        override fun paint(g: Graphics) {
            g.color = Color.GREEN
            repeat(40) {
                printVert(g, bounds, 0f, 0f, it)
                printVert(g, bounds, 1f, 0f, it)
                printVert(g, bounds, 0f, 1f, it)
                printVert(g, bounds, -1f, 0f, it)
                printVert(g, bounds, 0f, -1f, it)
                printVert(g, bounds, -1f, -1f, it)
                printVert(g, bounds, 1f, -1f, it)
                printVert(g, bounds, -1f, 1f, it)
                printVert(g, bounds, 1f, 1f, it)
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

fun printVert(g: Graphics, bounds: Rectangle, x: Float, y: Float, z: Int) {
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