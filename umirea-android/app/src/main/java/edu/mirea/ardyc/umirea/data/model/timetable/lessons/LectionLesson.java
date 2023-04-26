package edu.mirea.ardyc.umirea.data.model.timetable.lessons;

import edu.mirea.ardyc.umirea.R;
import edu.mirea.ardyc.umirea.data.model.timetable.Lesson;

public class LectionLesson extends Lesson {
    public LectionLesson() {
        setLessonIconMini(R.drawable.calendar_circle_lection);
        setLessonIcon(R.drawable.lec_icon);
    }

    @Override
    public int getType() {
        return 2;
    }

}
