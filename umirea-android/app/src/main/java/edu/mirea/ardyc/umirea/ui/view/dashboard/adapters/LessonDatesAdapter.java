package edu.mirea.ardyc.umirea.ui.view.dashboard.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.mirea.ardyc.umirea.R;
import edu.mirea.ardyc.umirea.data.model.timetable.Lesson;

public class LessonDatesAdapter extends RecyclerView.Adapter<LessonDatesAdapter.ViewHolder> {

    private List<String> dates;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView date;
        private ImageView delete;

        public ViewHolder(View view) {
            super(view);
            date = view.findViewById(R.id.date_text);
            delete = view.findViewById(R.id.delete);
        }

        public TextView getDate() {
            return date;
        }

        public ImageView getDelete() {
            return delete;
        }
    }


    public LessonDatesAdapter(List<String> dates) {
        this.dates = dates;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.lesson_time_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.getDate().setText(dates.get(position));
        viewHolder.delete.setOnClickListener(v -> {
            dates.remove(position);
            notifyDataSetChanged();
        });
    }

    public void update(List<String> dates) {
        this.dates = dates;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return dates == null ? 0 : dates.size();
    }

    public List<String> getDates() {
        return dates;
    }
}
