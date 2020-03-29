package com.elyeproj.surfaceviewexplore

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.elyeproj.surfaceviewexplore.Individual.CREATOR.IS_INFECTED
import com.elyeproj.surfaceviewexplore.Individual.CREATOR.IS_UNINFECTED
import com.elyeproj.surfaceviewexplore.SimulationView.Companion.globalHeight
import com.elyeproj.surfaceviewexplore.SimulationView.Companion.globalInfected
import com.elyeproj.surfaceviewexplore.SimulationView.Companion.globalInfectiousDistance
import com.elyeproj.surfaceviewexplore.SimulationView.Companion.globalUninfected
import com.elyeproj.surfaceviewexplore.SimulationView.Companion.globalWidth

class DrawAnimate(private val height: Int, private val width: Int) {

    private val starttime = System.currentTimeMillis()
    private val backgroundPaint = Paint()
        .apply { color = Color.LTGRAY }

    private val textPaint = Paint()
        .apply { color = Color.BLUE }
        .apply { textSize = 96f }
        .apply { isAntiAlias = true }
        .apply { strokeWidth = 2f }
        .apply { textAlign = Paint.Align.CENTER }

    var individualList: List<Individual> = listOf()

    init {
        individualList = (0 until globalInfected).map {
            Individual(
                (0..globalWidth).random().toFloat(),
                (0..globalHeight).random().toFloat(),
                Individual.IS_INFECTED)
        }
        individualList += (0 until globalUninfected).map {
            Individual(
                (0..globalWidth).random().toFloat(),
                (0..globalHeight).random().toFloat(),
                Individual.IS_UNINFECTED)
        }
    }

    fun draw(canvas: Canvas): Boolean {
        canvas.drawPaint(backgroundPaint)
        
        val infectedList = individualList.filter { it.isInfected == Individual.IS_INFECTED }

        individualList.forEach {
            individual ->
            if (individual.isInfected == Individual.IS_UNINFECTED) {
                infectedList.asSequence().firstOrNull { infected ->
                    kotlin.math.abs(infected.centerX - individual.centerX) < globalInfectiousDistance &&
                        kotlin.math.abs(infected.centerY - individual.centerY) < globalInfectiousDistance
                }?.let {
                    individual.isInfected = Individual.IS_INFECTED
                }
            }
        }

        individualList.forEach { it.draw(canvas) }
        individualList = individualList.filter { it.isValid() }

        val timeDiff = System.currentTimeMillis() - starttime
        canvas.drawText("$timeDiff ms", width/2f, height/2f, textPaint)

        return !(individualList.none { it.isInfected == IS_INFECTED } ||
            individualList.none { it.isInfected == IS_UNINFECTED })
    }
}
