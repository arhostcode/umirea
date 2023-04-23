package edu.mirea.ardyc.umirea.data.model.timetable;

import edu.mirea.ardyc.umirea.R;

public class LectionLesson extends Lesson {
    public LectionLesson() {
        setLessonIconMini(R.drawable.calendar_circle_lection);
        setLessonIcon(R.drawable.lec_icon);
    }

    @Override
    public int getType() {
        return 2;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
