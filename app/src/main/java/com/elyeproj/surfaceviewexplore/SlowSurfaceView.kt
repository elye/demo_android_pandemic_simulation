package com.elyeproj.surfaceviewexplore

import android.content.Context
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SlowSurfaceView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0)
    : SurfaceView(context, attrs, defStyleAttr), SurfaceHolder.Callback {

    companion object {
        var globalHeight = 0
        var globalWidth = 0
        var globalGlowRadius = 0f
        var globalItemRadius = 0f
        var globalMaxMove = 0
    }

    private var job: Job? = null
    private var doAnimate = true
    private val drawAnimate by lazy {
        globalHeight = height
        globalWidth = width
        globalGlowRadius = context.resources.getDimension(R.dimen.max_glowing_radius)
        globalItemRadius = context.resources.getDimension(R.dimen.max_item_radius)
        globalMaxMove = context.resources.getInteger(R.integer.max_random_move)
        DrawAnimate(height, width)
    }

    init {
        holder.addCallback(this)
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        // Do nothing for now
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        doAnimate = false
        job?.cancel()
        job = null
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        doAnimate = true
        job = GlobalScope.launch {
            while (doAnimate && isAttachedToWindow) {
                synchronized(holder) {
                    val canvas = holder.lockCanvas()
                    canvas?.let {
                        drawAnimate.draw(it)
                        holder.unlockCanvasAndPost(it)
                    }
                }
            }
        }
    }
}
