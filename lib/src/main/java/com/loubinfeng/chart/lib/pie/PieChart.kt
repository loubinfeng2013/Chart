package com.loubinfeng.chart.lib.pie

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

class PieChart @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    private var mWidth: Float? = null
    private var mHeight: Float? = null
    private var mRectF: RectF? = null
    private val mColors: IntArray by lazy { intArrayOf(-0xc000, -0x100, -0xbf0100, -0xff0001, -0xbfff01) }
    private val mData: MutableList<PieData> by lazy { mutableListOf<PieData>() }
    private val mPaint: Paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).let {
            it.style = Paint.Style.FILL
            it
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w.toFloat()
        mHeight = h.toFloat()
        var r = Math.min(mWidth!!, mHeight!!) / 2
        mRectF = RectF(-r, -r, r, r)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (mData.isEmpty()) return
        canvas?.apply {
            translate(mWidth!! / 2, mHeight!! / 2)
            mData.forEach {
                mPaint.color = it.color
                drawArc(mRectF!!, 0f, it.angle, true, mPaint)
                rotate(it.angle)
            }
        }
    }

    public fun setData(data: List<PieData>) {
        if (data.isEmpty()) return
        mData.apply {
            clear()
            addAll(data)
            calculateData()
            postInvalidate()
        }
    }

    private fun calculateData() {
        val total = mData.asSequence().map { it.value }.reduce { acc, fl -> acc + fl }
        mData.forEachIndexed { index, pieData ->
            pieData.percent = pieData.value / total
            pieData.color = mColors[index % mColors.size]
            pieData.angle = pieData.percent * 360
        }
    }
}