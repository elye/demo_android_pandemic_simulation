package com.elyeproj.surfaceviewexplore

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Parcel
import android.os.Parcelable
import com.elyeproj.surfaceviewexplore.SimulationView.Companion.globalGlowRadius
import com.elyeproj.surfaceviewexplore.SimulationView.Companion.globalHeight
import com.elyeproj.surfaceviewexplore.SimulationView.Companion.globalItemRadius
import com.elyeproj.surfaceviewexplore.SimulationView.Companion.globalMobilityDistance
import com.elyeproj.surfaceviewexplore.SimulationView.Companion.globalWidth

data class Individual(var centerX: Float,
                      var centerY: Float,
                      var isInfected: Int) : Parcelable {

    private var currentRadius = 1f
    private var growState = IS_GROWING

    private val infectedRadiusPaint = Paint()
        .apply { color = Color.RED }
        .apply { style = Paint.Style.STROKE }
        .apply { isAntiAlias = true }
        .apply { strokeWidth = 2f }

    private val infectedPaint = Paint()
        .apply { color = Color.RED }
        .apply { style = Paint.Style.FILL_AND_STROKE }
        .apply { isAntiAlias = true }
        .apply { strokeWidth = 2f }

    private val uninfectedPaint = Paint()
        .apply { color = Color.BLACK }
        .apply { style = Paint.Style.FILL_AND_STROKE }
        .apply { isAntiAlias = true }
        .apply { strokeWidth = 2f }

    constructor(parcel: Parcel) : this(
            parcel.readFloat(),
            parcel.readFloat(),
            parcel.readInt()) {
        currentRadius = parcel.readFloat()
        growState = parcel.readInt()
    }

    fun draw(canvas: Canvas) {
        infectedRadiusPaint.apply { alpha = ((globalGlowRadius - currentRadius) / globalGlowRadius * MAX_ALPHA).toInt() }

        if (isInfected == IS_INFECTED) {
            if (currentRadius >= globalGlowRadius) {
                growState = IS_SHRINKING
            } else if (currentRadius <= 0) {
                growState = IS_GROWING
            }

            if (growState == IS_GROWING)
                currentRadius++
            else {
                currentRadius--
            }
            canvas.drawCircle(centerX, centerY, currentRadius, infectedRadiusPaint)
        }

        centerX = randomMove(centerX, globalWidth)
        centerY = randomMove(centerY, globalHeight)

        canvas.drawCircle(centerX, centerY, globalItemRadius,
            if (isInfected == IS_UNINFECTED) uninfectedPaint else infectedPaint)
    }

    private fun randomMove(current: Float, maxCurrent: Int): Float {
        var new: Float
        do {
            new = current + (-globalMobilityDistance..globalMobilityDistance).random()
        } while (new >= maxCurrent || new <= 0 )

        return new
    }

    fun isValid() = true

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeFloat(centerX)
        parcel.writeFloat(centerY)
        parcel.writeInt(isInfected)
        parcel.writeFloat(currentRadius)
        parcel.writeInt(growState)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Individual> {
        private const val MAX_ALPHA = 255
        private const val IS_GROWING = 1
        private const val IS_SHRINKING = 0
        const val IS_INFECTED = 1
        const val IS_UNINFECTED = 0

        override fun createFromParcel(parcel: Parcel): Individual {
            return Individual(parcel)
        }

        override fun newArray(size: Int): Array<Individual?> {
            return arrayOfNulls(size)
        }
    }
}
