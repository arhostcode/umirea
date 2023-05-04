package edu.mirea.ardyc.umirea.ui.view.info.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import edu.mirea.ardyc.umirea.R;
import edu.mirea.ardyc.umirea.data.model.info.InfoMessage;
import edu.mirea.ardyc.umirea.data.model.timetable.Lesson;
import edu.mirea.ardyc.umirea.data.model.timetable.LessonTime;
import edu.mirea.ardyc.umirea.data.model.timetable.Task;
import edu.mirea.ardyc.umirea.data.model.timetable.TimetableDay;
import edu.mirea.ardyc.umirea.data.model.timetable.date.DateTask;
import edu.mirea.ardyc.umirea.ui.view.dashboard.adapters.LessonItems;
import edu.mirea.ardyc.umirea.ui.view.dashboard.dialogs.TimetableDialog;

public class InfoMessagesAdapter extends RecyclerView.Adapter<InfoMessagesAdapter.ViewHolder> {

    private List<InfoMessage> infoMessages;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView senderName;
        private final TextView text;

        public ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            senderName = view.findViewById(R.id.sender_name);
            text = view.findViewById(R.id.info_text);
        }

        public TextView getName() {
            return name;
        }

        public TextView getSenderName() {
            return senderName;
        }

        public TextView getText() {
            return text;
        }
    }


    public InfoMessagesAdapter(List<InfoMessage> infoMessages) {
        if (infoMessages == null)
            return;
        this.infoMessages = new ArrayList<>(infoMessages);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.info_element, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(InfoMessagesAdapter.ViewHolder viewHolder, final int position) {
        InfoMessage infoMessage = infoMessages.get(position);
        if (infoMessage == null)
            return;
        viewHolder.text.setText(infoMessage.getText());
        viewHolder.name.setText(infoMessage.getName());
        viewHolder.senderName.setText(infoMessage.getOwner());
    }

    public void update(List<InfoMessage> messages) {
        this.infoMessages = messages;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return infoMessages == null ? 0 : infoMessages.size();
    }

}
