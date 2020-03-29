package com.elyeproj.surfaceviewexplore

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Parcel
import android.os.Parcelable
import com.elyeproj.surfaceviewexplore.SlowSurfaceView.Companion.globalGlowRadius
import com.elyeproj.surfaceviewexplore.SlowSurfaceView.Companion.globalHeight
import com.elyeproj.surfaceviewexplore.SlowSurfaceView.Companion.globalItemRadius
import com.elyeproj.surfaceviewexplore.SlowSurfaceView.Companion.globalMaxMove
import com.elyeproj.surfaceviewexplore.SlowSurfaceView.Companion.globalWidth

data class InfectedIndividual(private var centerX: Float,
                              private var centerY: Float) : Parcelable {

    private var currentRadius = 1f
    private var growState = IS_GROWING

    private val paint = Paint()
        .apply { color = Color.RED }
        .apply { style = Paint.Style.STROKE }
        .apply { isAntiAlias = true }
        .apply { strokeWidth = 2f }

    private val dotPaint = Paint()
        .apply { color = Color.RED }
        .apply { style = Paint.Style.FILL_AND_STROKE }
        .apply { isAntiAlias = true }
        .apply { strokeWidth = 2f }

    constructor(parcel: Parcel) : this(
            parcel.readFloat(),
            parcel.readFloat()) {
        currentRadius = parcel.readFloat()
        growState = parcel.readInt()
    }

    fun draw(canvas: Canvas) {
        paint.apply { alpha = ((globalGlowRadius - currentRadius) / globalGlowRadius * MAX_ALPHA).toInt() }
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

        centerX = randomMove(centerX, globalWidth)
        centerY = randomMove(centerY, globalHeight)

        canvas.drawCircle(centerX, centerY, currentRadius, paint)
        canvas.drawCircle(centerX, centerY, globalItemRadius, dotPaint)
    }

    private fun randomMove(current: Float, maxCurrent: Int): Float {
        var new: Float
        do {
            new = current + (-globalMaxMove..globalMaxMove).random()
        } while (new >= maxCurrent || new <= 0 )

        return new
    }

    fun isValid() = true

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeFloat(centerX)
        parcel.writeFloat(centerY)
        parcel.writeFloat(currentRadius)
        parcel.writeInt(growState)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<InfectedIndividual> {
        private const val MAX_ALPHA = 255
        private const val IS_GROWING = 1
        private const val IS_SHRINKING = 0

        override fun createFromParcel(parcel: Parcel): InfectedIndividual {
            return InfectedIndividual(parcel)
        }

        override fun newArray(size: Int): Array<InfectedIndividual?> {
            return arrayOfNulls(size)
        }
    }
}
