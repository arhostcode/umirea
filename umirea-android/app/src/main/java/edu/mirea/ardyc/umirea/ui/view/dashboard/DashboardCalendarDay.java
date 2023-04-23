package edu.mirea.ardyc.umirea.ui.view.dashboard;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import edu.mirea.ardyc.umirea.data.model.timetable.Lesson;
import edu.mirea.ardyc.umirea.data.model.timetable.TimetableDay;

public class DashboardCalendarDay extends View {
    private Context context;
    private String text;
    private int date;
    private int lessonsCount;
    private int color;
    private TimetableDay day;

    public DashboardCalendarDay(Context context) {
        super(context);
        this.context = context;
        setLayoutParams(new LinearLayout.LayoutParams(60, 80, 1));
    }

    public DashboardCalendarDay(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public DashboardCalendarDay(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    public void setDay(TimetableDay day) {
        this.day = day;
        lessonsCount = day.getLessons().size();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setTextSize(30);
        int off = date / 10;
        if (off > 0)
            off = 1;
        canvas.drawText(text, getWidth() / 2f - 10 - 11 * off, 30, paint);
        for (int i = 0; i < lessonsCount; i++) {
            float circleSize = getWidth() / 15f;
            canvas.drawCircle((getWidth() / 2f - lessonsCount * circleSize) + (circleSize + i * circleSize * 2), getHeight() - 20, getWidth() / 15f, paint);
        }


        super.onDraw(canvas);
    }


    public void setText(String text) {
        this.text = text;
        date = Integer.parseInt(text);
    }

    public void setTextColor(int color) {
        this.color = color;
    }

    public TimetableDay getDay() {
        return day;
    }
}
