package edu.mirea.ardyc.umirea.ui.view.dashboard.dialogs;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import edu.mirea.ardyc.umirea.R;
import edu.mirea.ardyc.umirea.databinding.BottomSheetLayoutBinding;
import edu.mirea.ardyc.umirea.ui.view.dashboard.adapters.LessonDatesAdapter;

public class LessonAdderDialog extends BottomSheetDialog {

    private BottomSheetLayoutBinding binding;
    private List<String> dates = new ArrayList<>();

    private LessonDialogProcessor processor;

    public LessonAdderDialog(@NonNull Context context, LessonDialogProcessor processor) {
        super(context, R.style.BottomSheetDialog);
        this.processor = processor;
        binding = BottomSheetLayoutBinding.inflate(getLayoutInflater());

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        binding.lessonTimes.setAdapter(new LessonDatesAdapter(dates));
        binding.lessonTimes.setLayoutManager(new LinearLayoutManager(getContext()));

        initButtons();

        setContentView(binding.getRoot());
    }

    private void initButtons() {
        binding.addTime.setOnClickListener((view) -> {
            DatePickerDialog dialog = new DatePickerDialog(getContext());
            dialog.setOnDateSetListener((view1, year, month, dayOfMonth) -> {
                dates.add(dayOfMonth + "." + (month + 1) + "." + year);
                ((LessonDatesAdapter) Objects.requireNonNull(binding.lessonTimes.getAdapter())).update(dates);
                binding.lessonTimes.scrollToPosition(dates.size() - 1);
            });
            dialog.show();
        });

        binding.createLesson.setOnClickListener((view) -> {
            String name = binding.lessonName.getText().toString();
            String teacherName = binding.teacherName.getText().toString();
            String room = binding.roomNum.getText().toString();
            int lessonType = 0;
            if (binding.labButton.isChecked())
                lessonType = 1;
            if (binding.lectionButton.isChecked())
                lessonType = 2;
            if (binding.semButton.isChecked())
                lessonType = 3;

            List<String> dates = ((LessonDatesAdapter) binding.lessonTimes.getAdapter()).getDates();
            List<Integer> times = new ArrayList<>();
            for (int i = 0; i < binding.times1.getChildCount(); i++) {
                if (((CheckBox) binding.times1.getChildAt(i)).isChecked()) {
                    times.add(i);
                    System.out.println(i);
                }
            }
            for (int i = 0; i < binding.times2.getChildCount(); i++) {
                if (((CheckBox) binding.times2.getChildAt(i)).isChecked()) {
                    times.add(i + 3);
                    System.out.println(i + 3);
                }
            }
            if (times.isEmpty() || dates.isEmpty() || name.isEmpty())
                return;
            processor.process(name, teacherName, room, lessonType, times, dates);
            hide();
        });
    }

}

