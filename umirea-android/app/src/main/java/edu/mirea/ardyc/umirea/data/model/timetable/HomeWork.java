package edu.mirea.ardyc.umirea.data.model.timetable;

public class HomeWork extends Task {
    public HomeWork(String description) {
        super(description);
    }

    public static HomeWork fromTask(Task task) {
        return new HomeWork(task.getDescription());
    }
}
