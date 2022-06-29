package pl.training.commons.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class LinearGraph(context: Context, attributeSet: AttributeSet): View(context, attributeSet) {

    private var points = emptyList<Point>()
    private fun getPaint(): Paint {
        val paint = Paint()
        paint.style = Paint.Style.STROKE
        paint.color = Color.LTGRAY
        paint.strokeWidth = 10.0F
        return paint
    }

    fun draw(points: List<Point>) {
        this.points = points
        invalidate()
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
    }

}

data class Point(val x: Float, val y: Float)