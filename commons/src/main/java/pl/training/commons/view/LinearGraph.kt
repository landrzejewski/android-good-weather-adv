package pl.training.commons.view

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View

class LinearGraph(context: Context, attributeSet: AttributeSet): View(context, attributeSet) {

    private var points = emptyList<Point>()

    fun draw(points: List<Point>) {
        this.points = points
        invalidate()
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
    }

}

data class Point(val x: Float, val y: Float)