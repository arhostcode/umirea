package edu.mirea.ardyc.umirea.data.model.timetable;

import edu.mirea.ardyc.umirea.R;

public class SeminarLesson extends Lesson {
    public SeminarLesson() {
        setLessonIconMini(R.drawable.hw_icon);
        setLessonIcon(R.drawable.sem_icon);
    }

    @Override
    public int getType() {
        return 3;
    }
}
