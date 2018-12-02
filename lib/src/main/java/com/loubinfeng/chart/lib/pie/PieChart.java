package com.loubinfeng.chart.lib.pie;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PieChart extends View {

    private int[] colorArray = {0xffff4000, 0xffffff00, 0xff40ff00, 0xff00ffff, 0xff4000ff};
    private List<PieData> mData;
    private int mWidth;
    private int mHeight;
    private Paint mPaint;
    private RectF mRect;

    public PieChart(Context context) {
        super(context);
        initPaint();
    }

    public PieChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
    }

    /*
    控件确定宽高
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = h;
        mWidth = w;
        int r = Math.min(h, w) / 2;
        mRect = new RectF(-r, -r, r, r);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mData == null || mData.size() == 0)
            return;
        //1.将画布平移到中心
        canvas.translate(mWidth / 2, mHeight / 2);
        for (PieData data : mData) {
            mPaint.setColor(data.getColor());
            canvas.drawArc(mRect, 0, data.getAngle(), true, mPaint);
            canvas.rotate(data.getAngle());
        }
    }

    public void setData(List<PieData> data) {
        if (data == null || data.size() == 0)
            return;
        if (mData == null)
            mData = new ArrayList<>();
        mData.clear();
        mData.addAll(data);
        calculateData();
        postInvalidate();
    }

    /**
     * 计算百分比,处理绘制数据
     */
    private void calculateData() {
        float total = 0f;
        for (PieData data : mData) {
            total += data.getValue();
        }
        for (int i = 0; i < mData.size(); i++) {
            PieData pieData = mData.get(i);
            pieData.setPercent(pieData.getValue() / total);
            pieData.setColor(colorArray[i % colorArray.length]);
            pieData.setAngle(pieData.getPercent() * 360);
        }
    }
}

