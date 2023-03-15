package edu.mirea.ardyc.umirea.ui.view.dashboard;

import android.content.Context;
import android.graphics.Color;
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

public class DashboardCalendarDay extends LinearLayout {

    private TextView mainText;
    private Context context;
    private TimetableDay day;
    private LinearLayout lessons;

    public DashboardCalendarDay(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public DashboardCalendarDay(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public DashboardCalendarDay(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        removeAllViews();
        initLayout();
        addMainText(context);
        addLessonsView(context);
        if (day != null) {
            mainText.setText(String.valueOf(day.getDay()));
            for (Lesson l : day.getLessons()) {
                addLesson(l.getLessonIconMini());
            }
        }
    }

    public void setDay(TimetableDay day) {
        this.day = day;
        init();
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
        mainText.setTextColor(Color.WHITE);
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

    public TimetableDay getDay() {
        return day;
    }
}
