package com.example.android.findme.ui


import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.example.android.findme.R

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

    }
}