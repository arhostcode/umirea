package edu.mirea.ardyc.umirea.ui.view.dashboard.dialogs;

import java.util.List;

public interface LessonDialogProcessor {
    public void process(String name, String teacherName, String room, int lessonType, List<Integer> times, List<String> dates);
}
