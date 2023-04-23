package edu.mirea.ardyc.umirea.ui.view.dashboard.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.mirea.ardyc.umirea.R;
import edu.mirea.ardyc.umirea.data.model.timetable.Lesson;
import edu.mirea.ardyc.umirea.data.model.timetable.LessonTime;
import edu.mirea.ardyc.umirea.ui.view.dashboard.dialogs.TimetableDialog;

public class LessonItems extends RecyclerView.Adapter<LessonItems.ViewHolder> {

    private List<Lesson> lessons;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView lessonName;
        private ImageView lessonType;
        private TextView teacherName;
        private TextView startTime;
        private TextView finishTime;
        private ImageView hwIcon;
        private ImageView noteIcon;
        private LinearLayout lessonElement;

        public ViewHolder(View view) {
            super(view);
            lessonName = view.findViewById(R.id.lesson_name);
            lessonType = view.findViewById(R.id.lesson_type);
            teacherName = view.findViewById(R.id.teacher_name);
            startTime = view.findViewById(R.id.time_start);
            finishTime = view.findViewById(R.id.time_end);
            hwIcon = view.findViewById(R.id.hw_icon);
            noteIcon = view.findViewById(R.id.note_icon);
            lessonElement = view.findViewById(R.id.lesson_element);
        }

        public TextView getLessonName() {
            return lessonName;
        }

        public ImageView getLessonType() {
            return lessonType;
        }

        public TextView getTeacherName() {
            return teacherName;
        }

        public TextView getStartTime() {
            return startTime;
        }

        public TextView getFinishTime() {
            return finishTime;
        }

        public ImageView getHwIcon() {
            return hwIcon;
        }

        public LinearLayout getLessonElement() {
            return lessonElement;
        }

        public ImageView getNoteIcon() {
            return noteIcon;
        }
    }


    public LessonItems(List<Lesson> lessons) {
        this.lessons = new ArrayList<>(lessons);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.lessons_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Lesson lesson = lessons.get(position);
        viewHolder.getLessonName().setText(lesson.getName() + " " + lesson.getRoom());
        viewHolder.getStartTime().setText(LessonTime.getLessonStartTime(lesson.getLessonTime()));
        viewHolder.getFinishTime().setText(LessonTime.getLessonEndTime(lesson.getLessonTime()));
        viewHolder.getTeacherName().setText(lesson.getTeacherName());
        if (lesson.getHomeWork() == null)
            viewHolder.getHwIcon().setImageDrawable(null);
        else
            viewHolder.getHwIcon().setImageDrawable(ResourcesCompat.getDrawable(viewHolder.noteIcon.getResources(), R.drawable.hw_icon, null));

        if (lesson.getTask() == null)
            viewHolder.getNoteIcon().setImageDrawable(null);
        else
            viewHolder.getNoteIcon().setImageDrawable(ResourcesCompat.getDrawable(viewHolder.noteIcon.getResources(), R.drawable.note_icon, null));
        viewHolder.getLessonType().setImageResource(lesson.getLessonIcon());
        if (!lesson.getName().equals("Нет пары"))
            viewHolder.lessonElement.setOnClickListener(view -> new TimetableDialog(view.getContext(), lesson).show());
        else viewHolder.lessonElement.setOnClickListener(view -> {
        });

    }

    public void update(List<Lesson> lessons) {
        this.lessons = lessons;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return lessons == null ? 0 : lessons.size();
    }

    public List<Lesson> getLessons() {
        return lessons;
    }
}
