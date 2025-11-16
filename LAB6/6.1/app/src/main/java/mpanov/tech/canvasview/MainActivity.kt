package mpanov.tech.canvasview

import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Rect
import android.graphics.RectF
import android.os.Bundle
import android.view.SurfaceHolder
import android.view.SurfaceView
import kotlin.math.cos
import kotlin.math.sin

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(DrawView(this))
    }

    class DrawView : SurfaceView, SurfaceHolder.Callback {

        private lateinit var drawThread : DrawThread

        constructor(context: Context) : super(context){
            holder.addCallback(this)
        }

        override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}

        override fun surfaceCreated(holder: SurfaceHolder) {
            drawThread = DrawThread(holder)
            drawThread.setRunning(true)
            drawThread.start()
        }

        override fun surfaceDestroyed(holder: SurfaceHolder) {
            var retry : Boolean = true
            drawThread.setRunning(false)
            while (retry){
                try {
                    drawThread.join()
                    retry = false
                } catch (e: InterruptedException){ }
            }
        }

        class DrawThread : Thread {

            private var surfaceHolder : SurfaceHolder
            private var running : Boolean = false;

            constructor(surfaceHolder: SurfaceHolder) : super(){
                this.surfaceHolder = surfaceHolder;
            }

            fun setRunning(running: Boolean){
                this.running = running;
            }

            override fun run(){

                var canvas : Canvas?

                while (running) {
                    canvas = null

                    try {
                        canvas = surfaceHolder.lockCanvas(null)
                        if (canvas == null){
                            continue
                        }

                        val p: Paint = Paint()
                        val path1: Path = Path()
                        val path2: Path = Path()

                        canvas.drawColor(Color.BLACK)
                        p.setColor(Color.WHITE)

                        for (i in 0 until 5) {
                            val angle = Math.toRadians((i * 72 - 90).toDouble())
                            val x = (300F + 200f * cos(angle)).toFloat()
                            val y = (300F + 200f * sin(angle)).toFloat()
                            if (i == 0) path1.moveTo(x, y)
                            else path1.lineTo(x, y)
                        }

                        canvas.drawPath(path1, p)

                        for (i in 0 until 6) {
                            val angle = Math.toRadians((i * 60 - 90).toDouble())
                            val x = (300F + 200f * cos(angle)).toFloat()
                            val y = (800F + 200f * sin(angle)).toFloat()
                            if (i == 0) path2.moveTo(x, y)
                            else path2.lineTo(x, y)
                        }

                        canvas.drawPath(path2, p)

                    } finally {
                        if (canvas != null){
                            surfaceHolder.unlockCanvasAndPost(canvas)
                        }
                    }
                }
            }
        }

    }
}