package edu.mirea.ardyc.umirea.ui.view.info.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.mirea.ardyc.umirea.R;
import edu.mirea.ardyc.umirea.data.model.group.Group;
import edu.mirea.ardyc.umirea.data.model.group.Member;
import edu.mirea.ardyc.umirea.data.model.info.InfoMessage;

public class InfoMessagesAdapter extends RecyclerView.Adapter<InfoMessagesAdapter.ViewHolder> {

    private List<InfoMessage> infoMessages;
    private Group group;

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
        InfoMessage infoMessage = infoMessages.get(getItemCount() - 1 - position);
        if (infoMessage == null)
            return;
        viewHolder.text.setText(infoMessage.getMessage());
        viewHolder.name.setText(infoMessage.getName());
        if (infoMessage.getOwner() != null) {
            Member member = group.getById(infoMessage.getOwner());
            if (member != null)
                viewHolder.senderName.setText(String.format(viewHolder.name.getResources().getString(R.string.user_full_name), member.getFirstName(), member.getLastName()));
        }
    }

    public void update(List<InfoMessage> messages) {
        this.infoMessages = messages;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return infoMessages == null ? 0 : infoMessages.size();
    }

    public void setGroup(Group group) {
        this.group = group;
    }

}
