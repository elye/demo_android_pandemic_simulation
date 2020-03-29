package com.elyeproj.surfaceviewexplore

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Parcel
import android.os.Parcelable
import com.elyeproj.surfaceviewexplore.SurfaceView.Companion.globalHeight
import com.elyeproj.surfaceviewexplore.SurfaceView.Companion.globalItemRadius
import com.elyeproj.surfaceviewexplore.SurfaceView.Companion.globalMaxMove
import com.elyeproj.surfaceviewexplore.SurfaceView.Companion.globalWidth

data class UnInfectedIndividual(var centerX: Float,
                                var centerY: Float) : Parcelable {

    private var currentRadius = 1f

    private val dotPaint = Paint()
        .apply { color = Color.BLACK }
        .apply { style = Paint.Style.FILL_AND_STROKE }
        .apply { isAntiAlias = true }
        .apply { strokeWidth = 2f }

    constructor(parcel: Parcel) : this(
            parcel.readFloat(),
            parcel.readFloat()) {
        currentRadius = parcel.readFloat()
    }

    fun draw(canvas: Canvas) {
        centerX = randomMove(centerX, globalWidth)
        centerY = randomMove(centerY, globalHeight)

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
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UnInfectedIndividual> {
        override fun createFromParcel(parcel: Parcel): UnInfectedIndividual {
            return UnInfectedIndividual(parcel)
        }

        override fun newArray(size: Int): Array<UnInfectedIndividual?> {
            return arrayOfNulls(size)
        }
    }
}
