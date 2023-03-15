package edu.mirea.ardyc.umirea.ui.view.dashboard;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.res.ResourcesCompat;

import edu.mirea.ardyc.umirea.R;
import edu.mirea.ardyc.umirea.data.model.timetable.Lesson;
import edu.mirea.ardyc.umirea.data.model.timetable.LessonTime;
import edu.mirea.ardyc.umirea.databinding.TimetableDialogBinding;

public class TimetableDialog extends AlertDialog {

    private Lesson lesson;
    private TimetableDialogBinding binding;

    public TimetableDialog(@NonNull Context context, Lesson lesson) {
        super(context);
        this.lesson = lesson;
        initialize();
    }

    private void initialize() {
        binding = TimetableDialogBinding.inflate(getLayoutInflater());
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setView(binding.getRoot());

        binding.lessonName.setText(lesson.getName());
        binding.teacherName.setText(lesson.getTeacherName());
        binding.timeStart.setText(LessonTime.getLessonStartTime(lesson.getLessonTime()));
        binding.timeEnd.setText(LessonTime.getLessonEndTime(lesson.getLessonTime()));
        if (lesson.getHomeWork() == null)
            binding.hwIcon.setImageDrawable(null);
        if (lesson.getTask() == null)
            binding.noteIcon.setImageDrawable(null);
        binding.lessonType.setImageResource(lesson.getLessonIcon());

    }
}
