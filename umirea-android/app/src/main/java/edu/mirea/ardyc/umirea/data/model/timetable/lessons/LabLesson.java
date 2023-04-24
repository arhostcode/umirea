package edu.mirea.ardyc.umirea.data.model.timetable.lessons;

import edu.mirea.ardyc.umirea.R;
import edu.mirea.ardyc.umirea.data.model.timetable.Lesson;

public class LabLesson extends Lesson {
    public LabLesson(){
        setLessonIconMini(R.drawable.note_icon);
        setLessonIcon(R.drawable.lab_icon);
    }

    @Override
    public int getType() {
        return 1;
    }
}
