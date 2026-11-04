import java.awt.*
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.SwingUtilities
import javax.swing.Timer

private var shapeVertices = listOf(-1, 1).flatMap { z ->
    listOf(
        VirtualVertex(1, 1, z),
        VirtualVertex(-1, 1, z),
        VirtualVertex(1, -1, z),
        VirtualVertex(-1, -1, z),
    )
}

private fun mainAction(vert: VirtualVertex): VirtualVertex {
    return vert.rotateY(0.001)
        .rotateX(0.005)
}

private const val DELAY: Int = 1

fun main() {
    SwingUtilities.invokeLater {
        val f = JFrame("cube")
        val panel = object : JPanel() {
            override fun paintComponent(g: Graphics) {
                super.paintComponent(g)
                val g2d = g as Graphics2D
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
                g2d.color = Color.GREEN
                shapeVertices.forEach { i ->
                    shapeVertices.forEach { j ->
                        j.to2D(bounds).printConn(g2d, i.to2D(bounds))
                    }
                }
            }
        }
        panel.background = Color.BLACK
        f.contentPane.add(panel)

        f.setSize(800, 600)
        f.setLocationRelativeTo(null)
        f.defaultCloseOperation = JFrame.EXIT_ON_CLOSE

        val timer = Timer(DELAY) {
            shapeVertices = shapeVertices.map {
                mainAction(vert = it)
            }
            panel.repaint()
        }

        f.addWindowListener(object : WindowAdapter() {
            override fun windowClosing(e: WindowEvent) {
                timer.stop()
            }
        })

        f.isVisible = true
        timer.start()
    }
}
