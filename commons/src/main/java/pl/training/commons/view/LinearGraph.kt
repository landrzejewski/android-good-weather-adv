package pl.training.commons.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

class LinearGraph(context: Context, attributeSet: AttributeSet): View(context, attributeSet) {

    private var points = emptyList<Point>()
    private val paint: Paint = run {
        val paint = Paint()
        paint.style = Paint.Style.STROKE
        paint.color = Color.LTGRAY
        paint.strokeWidth = 10.0F
        paint
    }
    private val padding = 50F
    private var maxX = 0F
    private var maxY = 0F

    fun draw(points: List<Point>) {
        maxX = max(points.map { it.x }.toList())
        maxY = max(points.map { it.y }.toList())
        this.points = points
        invalidate()
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        canvas.translate(0F, height.toFloat())
        canvas.scale(1F, -1F)

        points.zipWithNext().forEach { drawLine(canvas, it.first, it.second) }
    }

    private fun drawLine(canvas: Canvas, start: Point, end: Point) {
        val path = Path()
        path.moveTo(scaleX(start.x), scaleY(start.y))
        path.lineTo(scaleX(end.x), scaleY(end.y))
        canvas.drawPath(path, paint)
    }

    private fun max(values: List<Float>) = values.maxOrNull() ?: 0f

    private fun scaleX(x: Float) = (x * width) / maxX

    private fun scaleY(y: Float) = (y * height) / maxY

}

data class Point(val x: Float, val y: Float)