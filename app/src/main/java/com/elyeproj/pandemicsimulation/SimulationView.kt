package com.elyeproj.pandemicsimulation

import android.content.Context
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

class SimulationView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0)
    : SurfaceView(context, attrs, defStyleAttr), SurfaceHolder.Callback {

    companion object {
        var globalHeight = 0
        var globalWidth = 0
        var globalItemRadius = 0f
        var globalInfected = 0
        var globalUninfected = 0
        var globalMobilityDistance = 0
        var globalInfectiousDistance = 0
    }

    private var job: Job? = null
    private var doAnimate = true
    private val drawAnimate by lazy {
        globalHeight = height
        globalWidth = width
        globalItemRadius = context.resources.getDimension(R.dimen.max_item_radius)
        DrawAnimate(height, width)
    }

    private val timer = Timer()

    init {
        holder.addCallback(this)
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        // Do nothing for now
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        doAnimate = false
        timer.cancel()
        timer.purge()
        job?.cancel()
        job = null
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        doAnimate = true

        job = GlobalScope.launch {
            timer.scheduleAtFixedRate(object : TimerTask() {
                override fun run() {
                    if (doAnimate && isAttachedToWindow) {
                        synchronized(holder) {
                            val canvas = holder.lockCanvas()
                            canvas?.let {
                                doAnimate = drawAnimate.draw(it)
                                holder.unlockCanvasAndPost(it)
                            }
                        }
                        System.gc()
                    }
                }
            }, 0, 25)
        }
    }
}
