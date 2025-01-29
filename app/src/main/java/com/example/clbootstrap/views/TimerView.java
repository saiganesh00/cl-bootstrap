package com.example.clbootstrap.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import androidx.core.content.ContextCompat;
import com.example.clbootstrap.R;

public class TimerView extends View {
    private Paint progressPaint;
    private Paint backgroundPaint;
    private Paint textPaint;
    private RectF circleBounds;
    private float progress = 0;
    private String timeText = "00:00";

    public TimerView(Context context) {
        super(context);
        init();
    }

    public TimerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        progressPaint = new Paint();
        progressPaint.setAntiAlias(true);
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeWidth(12f);
        progressPaint.setColor(ContextCompat.getColor(getContext(), R.color.purple_500));

        backgroundPaint = new Paint();
        backgroundPaint.setAntiAlias(true);
        backgroundPaint.setStyle(Paint.Style.STROKE);
        backgroundPaint.setStrokeWidth(12f);
        backgroundPaint.setColor(ContextCompat.getColor(getContext(), R.color.purple_100));

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setColor(ContextCompat.getColor(getContext(), R.color.purple_500));
        textPaint.setTextSize(40f);

        circleBounds = new RectF();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        float padding = 20f;
        circleBounds.set(padding, padding, w - padding, h - padding);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw background circle
        canvas.drawArc(circleBounds, 0, 360, false, backgroundPaint);

        // Draw progress arc
        float sweepAngle = 360 * progress;
        canvas.drawArc(circleBounds, -90, sweepAngle, false, progressPaint);

        // Draw time text
        float xPos = getWidth() / 2f;
        float yPos = (getHeight() / 2f) - ((textPaint.descent() + textPaint.ascent()) / 2f);
        canvas.drawText(timeText, xPos, yPos, textPaint);
    }

    public void setProgress(float progress) {
        this.progress = progress;
        invalidate();
    }

    public void setTimeText(String timeText) {
        this.timeText = timeText;
        invalidate();
    }
}
