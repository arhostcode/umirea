package edu.mirea.ardyc.umirea.ui.view.dashboard.dialogs;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.res.ResourcesCompat;

import java.util.function.Consumer;

import edu.mirea.ardyc.umirea.R;
import edu.mirea.ardyc.umirea.data.model.timetable.HomeWork;
import edu.mirea.ardyc.umirea.data.model.timetable.Lesson;
import edu.mirea.ardyc.umirea.data.model.timetable.LessonTime;
import edu.mirea.ardyc.umirea.data.model.timetable.Task;
import edu.mirea.ardyc.umirea.databinding.TimetableDialogBinding;

public class TimetableDialog extends AlertDialog {

    private final Lesson lesson;
    private final Consumer<String> homeWorkConsumer;
    private final Consumer<String> taskConsumer;
    private TimetableDialogBinding binding;

    public TimetableDialog(@NonNull Context context, Lesson lesson, Consumer<String> homeWorkConsumer, Consumer<String> taskConsumer) {
        super(context);
        this.lesson = lesson;
        this.homeWorkConsumer = homeWorkConsumer;
        this.taskConsumer = taskConsumer;
        initialize();
    }

    private void initialize() {
        binding = TimetableDialogBinding.inflate(getLayoutInflater());
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setView(binding.getRoot());

        initTextFields();
        initButtons();
    }

    private void initButtons() {
        binding.changeHw.setOnClickListener((view) -> {
            String hw = "";
            if (lesson.getHomeWork() != null)
                hw = lesson.getHomeWork().getDescription();
            new TaskModifierDialog(getContext(), hw, (val) -> {
                binding.homeworkText.setText(val);
                homeWorkConsumer.accept(val);
            }).show();
        });

        binding.changeNote.setOnClickListener((view) -> {
            String note = "";
            if (lesson.getHomeWork() != null)
                note = lesson.getTask().getDescription();
            new TaskModifierDialog(getContext(), note, (val) -> {
                binding.noteText.setText(val);
                taskConsumer.accept(val);
            }).show();
        });
    }

    private void initTextFields() {

        String lessonName = String.format(getContext().getResources().getString(R.string.lesson_name), lesson.getName(), lesson.getRoom());
        binding.lessonName.setText(lessonName);

        binding.teacherName.setText(lesson.getTeacherName());
        binding.timeStart.setText(LessonTime.getLessonStartTime(lesson.getLessonTime()));
        binding.timeEnd.setText(LessonTime.getLessonEndTime(lesson.getLessonTime()));

        if (lesson.getHomeWork() == null) {
            binding.hwIcon.setImageDrawable(null);
            binding.homeworkText.setText("");
        } else {
            binding.homeworkText.setText(lesson.getHomeWork().getDescription());
        }

        if (lesson.getTask() == null) {
            binding.noteText.setText("");
            binding.noteIcon.setImageDrawable(null);
        } else {
            binding.noteText.setText(lesson.getTask().getDescription());
        }

        binding.lessonType.setImageResource(lesson.getLessonIcon());
    }
}
