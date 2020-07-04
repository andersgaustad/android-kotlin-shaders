package com.example.android.findme.ui


import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatImageView
import com.example.android.findme.R
import kotlin.math.floor
import kotlin.random.Random

class SpotLightImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    // Fields
    private var paint = Paint()
    private var shouldDrawSpotLight = false
    private var gameOver = false

    private lateinit var winnerRect: RectF
    private var androidBitmapX = 0f
    private var androidBitmapY = 0f

    private lateinit var shader : Shader

    private val shaderMatrix = Matrix()



    val androidBitmap = BitmapFactory.decodeResource(
        resources,
        R.drawable.android
    )

    val spotlight = BitmapFactory.decodeResource(
        resources,
        R.drawable.mask
    )

    init {
        val bitmap = Bitmap.createBitmap(spotlight.width, spotlight.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val shaderPaint = Paint(Paint.ANTI_ALIAS_FLAG)

        shaderPaint.color = Color.BLACK
        canvas.drawRect(
            0.0f,
            0.0f,
            spotlight.width.toFloat(),
            spotlight.height.toFloat(),
            shaderPaint
        )

        // Step 2
        shaderPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
        canvas.drawBitmap(spotlight, 0.0f, 0.0f, shaderPaint)


        // Step 3
        shader = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        paint.shader = shader

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.apply {
            drawColor(Color.WHITE)
            drawBitmap(
                androidBitmap,
                androidBitmapX,
                androidBitmapY,
                paint
            )

            /*
            drawColor(Color.YELLOW)

            shaderMatrix.setTranslate(
                500f,
                1000f
            )
            shader.setLocalMatrix(shaderMatrix)

            drawRect(
                0.0f,
                0.0f,
                width.toFloat(),
                height.toFloat(),
                paint
            )

             */

        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        setUpWinnerRect()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event != null) {
            val motionEventX = event.x
            val motionEventY = event.y

            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    shouldDrawSpotLight = true
                    if (gameOver) {
                        gameOver = false
                        setUpWinnerRect()
                    }
                }

                MotionEvent.ACTION_UP -> {
                    shouldDrawSpotLight = false
                    gameOver = winnerRect.contains(motionEventX, motionEventY)
                }
            }

            // Update shader matrix
            shaderMatrix.setTranslate(
                motionEventX - spotlight.width / 2.0f,
                motionEventY - spotlight.height / 2.0f
            )
        }
        shader.setLocalMatrix(shaderMatrix)

        // Need to redraw
        invalidate()
        return event != null
    }


    // Private

    private fun setUpWinnerRect() {
        androidBitmapX = floor(Random.nextFloat() * (width - androidBitmap.width))
        androidBitmapY = floor(Random.nextFloat() * (height - androidBitmap.height))

        winnerRect = RectF(
            androidBitmapX,
            androidBitmapY,
            androidBitmapX + androidBitmap.width,
            androidBitmapY + androidBitmap.height
        )
    }
}