package com.elyeproj.surfaceviewexplore

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.elyeproj.surfaceviewexplore.SlowSurfaceView.Companion.globalHeight
import com.elyeproj.surfaceviewexplore.SlowSurfaceView.Companion.globalWidth

class DrawAnimate(private val height: Int, private val width: Int) {

    private val backgroundPaint = Paint()
        .apply { color = Color.LTGRAY }

    var infectedIndividualList: List<InfectedIndividual> = listOf()

    init {
        infectedIndividualList = (0..100).map {
            InfectedIndividual(
                (0..globalWidth).random().toFloat(),
                (0..globalHeight).random().toFloat())
        }
    }

    fun draw(canvas: Canvas) {
        canvas.drawPaint(backgroundPaint)
        infectedIndividualList.forEach { it.draw(canvas) }
        infectedIndividualList = infectedIndividualList.filter { it.isValid() }
    }
}
