package edu.mirea.ardyc.umirea.data.model.timetable;

import java.util.Objects;

public class Task {

    private String description;

    public Task(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(description, task.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description);
    }
}
