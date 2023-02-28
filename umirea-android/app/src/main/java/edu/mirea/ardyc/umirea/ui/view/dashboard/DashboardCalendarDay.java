package edu.mirea.ardyc.umirea.ui.view.dashboard;

import android.content.Context;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

public class DashboardCalendarDay extends LinearLayout {

    private TextView mainText;
    private LinearLayout lessons;

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
        initLayout();
        addMainText(context);
        addLessonsView(context);
    }

    private void initLayout() {
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
        setLayoutParams(new LinearLayout.LayoutParams(60, 80, 1));
    }

    private void addLessonsView(Context context) {
        lessons = new LinearLayout(context);
        lessons.setOrientation(LinearLayout.HORIZONTAL);
        lessons.setGravity(Gravity.CENTER);
        lessons.setLayoutParams(new LinearLayout.LayoutParams(60, 10, 1));
        addView(lessons);
    }

    private void addMainText(Context context) {
        mainText = new TextView(context);
        mainText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        addView(mainText);
    }

    public void addLesson(int lessonResourceId) {
        addLessonToView(lessonResourceId);
    }

    private void addLessonToView(int lessonResourceId) {
        LinearLayout circle = new LinearLayout(getContext());
        circle.setLayoutParams(new LinearLayout.LayoutParams(10, 10, 0));
        circle.setBackground(ResourcesCompat.getDrawable(getResources(), lessonResourceId, null));
        lessons.addView(circle);
    }

    public void setText(String text) {
        mainText.setText(text);
    }

    public void setTextColor(int color) {
        mainText.setTextColor(color);
    }
}
