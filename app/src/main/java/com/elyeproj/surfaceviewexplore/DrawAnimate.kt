package com.elyeproj.surfaceviewexplore

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.elyeproj.surfaceviewexplore.SurfaceView.Companion.globalHeight
import com.elyeproj.surfaceviewexplore.SurfaceView.Companion.globalWidth

class DrawAnimate(private val height: Int, private val width: Int) {

    private val backgroundPaint = Paint()
        .apply { color = Color.LTGRAY }

    var infectedIndividualList: List<InfectedIndividual> = listOf()
    var unInfectedIndividualList: List<UnInfectedIndividual> = listOf()

    init {
        infectedIndividualList = (0..0).map {
            InfectedIndividual(
                (0..globalWidth).random().toFloat(),
                (0..globalHeight).random().toFloat())
        }
        unInfectedIndividualList = (0..99).map {
            UnInfectedIndividual(
                (0..globalWidth).random().toFloat(),
                (0..globalHeight).random().toFloat())
        }
    }

    fun draw(canvas: Canvas) {
        canvas.drawPaint(backgroundPaint)

        infectedIndividualList.forEach {

        }

        infectedIndividualList.forEach { it.draw(canvas) }
        infectedIndividualList = infectedIndividualList.filter { it.isValid() }
        unInfectedIndividualList.forEach { it.draw(canvas) }
        unInfectedIndividualList = unInfectedIndividualList.filter { it.isValid() }
    }
}
