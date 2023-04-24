package edu.mirea.ardyc.umirea.data.model.timetable.lessons;

import edu.mirea.ardyc.umirea.R;
import edu.mirea.ardyc.umirea.data.model.timetable.Lesson;

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
