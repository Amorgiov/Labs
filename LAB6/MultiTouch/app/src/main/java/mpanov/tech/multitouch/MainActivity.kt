package mpanov.tech.multitouch

import android.os.Bundle
import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class MainActivity : Activity(), View.OnTouchListener {

    private var scale = 1f
    private var oldDistance = 0F

    private lateinit var drawView: DrawView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        drawView = DrawView(this)
        drawView.setOnTouchListener(this)
        setContentView(drawView)
    }

    override fun onTouch(view: View?, event: MotionEvent): Boolean {

        val pointerCount = event.pointerCount

        when (event.actionMasked) {

            MotionEvent.ACTION_POINTER_DOWN -> {
                if (pointerCount == 2){
                    oldDistance = distance(event)
                }
            }

            MotionEvent.ACTION_UP -> {
                oldDistance = 0f
                scale = 1f

                drawView.scaleX = scale
                drawView.scaleY = scale
            }

            MotionEvent.ACTION_POINTER_UP -> {
                oldDistance = 0f
                scale = 1f
            }

            MotionEvent.ACTION_MOVE -> {
                if (pointerCount == 2){
                    val currentDistance = distance(event)

                    if (oldDistance != 0f) {
                        val scaleFactor = currentDistance / oldDistance

                        scale *= scaleFactor

                        drawView.scaleX = scale
                        drawView.scaleY = scale
                    }

                    oldDistance = currentDistance
                }
            }
        }

        return true
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

            override fun run() {

                var canvas: Canvas?

                while (running) {
                    canvas = null

                    try {
                        canvas = surfaceHolder.lockCanvas(null)
                        if (canvas == null) {
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
                        if (canvas != null) {
                            surfaceHolder.unlockCanvasAndPost(canvas)
                        }
                    }
                }
            }
        }
    }

    private fun distance(e: MotionEvent): Float {
        if (e.pointerCount < 2) return 0F
        if (e.pointerCount > 2) return 0F
        val dx = e.getX(0) - e.getX(1)
        val dy = e.getY(0) - e.getY(1)
        return sqrt(dx * dx + dy * dy)
    }
}
