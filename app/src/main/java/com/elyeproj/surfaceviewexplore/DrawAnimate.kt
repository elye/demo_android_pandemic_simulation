package com.elyeproj.surfaceviewexplore

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.elyeproj.surfaceviewexplore.SurfaceView.Companion.globalGlowRadius
import com.elyeproj.surfaceviewexplore.SurfaceView.Companion.globalHeight
import com.elyeproj.surfaceviewexplore.SurfaceView.Companion.globalWidth

class DrawAnimate(private val height: Int, private val width: Int) {

    private val backgroundPaint = Paint()
        .apply { color = Color.LTGRAY }

    var individualList: List<Individual> = listOf()

    init {
        individualList = (0..0).map {
            Individual(
                (0..globalWidth).random().toFloat(),
                (0..globalHeight).random().toFloat(),
                Individual.IS_INFECTED)
        }
        individualList += (0..99).map {
            Individual(
                (0..globalWidth).random().toFloat(),
                (0..globalHeight).random().toFloat(),
                Individual.IS_UNINFECTED)
        }
    }

    fun draw(canvas: Canvas) {
        canvas.drawPaint(backgroundPaint)

        val infectedList = individualList.filter { it.isInfected == Individual.IS_INFECTED }

        individualList.forEach {
            individual ->
            if (individual.isInfected == Individual.IS_UNINFECTED) {
                infectedList.asSequence().firstOrNull { infected ->
                    kotlin.math.abs(infected.centerX - individual.centerX) < globalGlowRadius &&
                        kotlin.math.abs(infected.centerY - individual.centerY) < globalGlowRadius
                }?.let {
                    individual.isInfected = Individual.IS_INFECTED
                }
            }
        }

        individualList.forEach { it.draw(canvas) }
        individualList = individualList.filter { it.isValid() }
    }
}
