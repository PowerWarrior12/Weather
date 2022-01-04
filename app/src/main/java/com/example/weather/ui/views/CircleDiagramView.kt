package com.example.weather.ui.views

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.weather.R
import kotlin.math.roundToInt
import android.graphics.RectF




private const val MAX_TEXT_SIZE = 100

class CircleDiagramView @JvmOverloads constructor(
    context : Context,
    attrs : AttributeSet? = null
) : View(context, attrs) {

    private val valuePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textRect = RectF()
    private var valueText = ""

    var value : Int = 0
        set(value){
            field = value
            valueText = field.toString()
            invalidate()
        }

    init{
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleDiagram)

        val diagramTextSize = typedArray.getDimensionPixelSize(
            R.styleable.CircleDiagram_textSize,
            (64f * resources.displayMetrics.scaledDensity).roundToInt()).toFloat()

        val textColor = typedArray.getColor(
            R.styleable.CircleDiagram_textColor,
            Color.BLACK
        )

        val valueColor = typedArray.getColor(
            R.styleable.CircleDiagram_valueColor,
            Color.BLUE
        )

        val value = typedArray.getInteger(
            R.styleable.CircleDiagram_value,
            0
        )

        textPaint.apply {
            color = textColor
            textSize = diagramTextSize
        }

        valuePaint.apply {
            color = valueColor
        }

        this.value = value
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val centreX = width * 0.5f
        val centreY = height * 0.5f

        val valueAngle = value / 100.0f * 360.0f

        textRect.left = centreX - width/2
        textRect.right = centreX + width/2
        textRect.bottom = centreY + height/2
        textRect.top = centreY - height/2
        canvas?.drawArc(textRect, 0F, valueAngle, true, valuePaint)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val maxTextWidth = textPaint.measureText(MAX_TEXT_SIZE.toString())

        val desiredWidth = (maxTextWidth + paddingLeft.toFloat() + paddingRight.toFloat()).roundToInt()
        val desiredHeight = (maxTextWidth + paddingTop.toFloat() + paddingBottom.toFloat()).roundToInt()

        val measuredWidth = resolveSize(desiredWidth, widthMeasureSpec)
        val measuredHeight = resolveSize(desiredHeight, heightMeasureSpec)

        setMeasuredDimension(measuredWidth, measuredHeight)

    }
}