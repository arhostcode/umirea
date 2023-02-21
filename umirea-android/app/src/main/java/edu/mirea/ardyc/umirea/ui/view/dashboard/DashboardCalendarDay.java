package edu.mirea.ardyc.umirea.ui.view.dashboard;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import edu.mirea.ardyc.umirea.R;

public class DashboardCalendarDay extends LinearLayout {

    private TextView mainText;

    public DashboardCalendarDay(Context context) {
        super(context);
        init(context);
    }

    public DashboardCalendarDay(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DashboardCalendarDay(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        removeAllViews();
        mainText = new TextView(context);
        addView(mainText);
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
        setLayoutParams(new LinearLayout.LayoutParams(60, 80, 1));
        LinearLayout l = new LinearLayout(context);
        l.setOrientation(LinearLayout.HORIZONTAL);
        l.setGravity(Gravity.CENTER);
        l.setLayoutParams(new LinearLayout.LayoutParams(60, 10, 1));
        l.addView(createCircle());
        l.addView(createCircle());
        l.addView(createCircle());
        l.addView(createCircle());
        l.addView(createCircle());
        l.addView(createCircle());

        addView(l);
    }

    private LinearLayout createCircle() {
        LinearLayout circle = new LinearLayout(getContext());
        circle.setLayoutParams(new LinearLayout.LayoutParams(10, 10, 0));
        circle.setBackground(getContext().getDrawable(R.drawable.calendar_circle_lection));
        return circle;
    }

    public void setText(String text) {
        mainText.setText(text);
    }

    public void setTextColor(int color) {
        mainText.setTextColor(color);
    }
}
