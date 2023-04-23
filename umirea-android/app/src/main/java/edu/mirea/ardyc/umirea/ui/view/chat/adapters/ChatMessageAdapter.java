package edu.mirea.ardyc.umirea.ui.view.chat.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.mirea.ardyc.umirea.R;
import edu.mirea.ardyc.umirea.data.model.chat.Chat;
import edu.mirea.ardyc.umirea.data.model.cloud.CloudFile;
import edu.mirea.ardyc.umirea.data.model.group.Group;

public class ChatMessageAdapter extends RecyclerView.Adapter<ChatMessageAdapter.ViewHolder> {

    private Chat chat;
    private Group group;

    public ChatMessageAdapter(Chat chat, Group group) {
        this.chat = chat;
        this.group = group;
    }

    @NonNull
    @Override
    public ChatMessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_element, parent, false);

        return new ChatMessageAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatMessageAdapter.ViewHolder holder, int position) {
        if (chat != null) {
            if (group.getById(chat.getChatMessages().get(position).getSender()) != null) {
                holder.getName().setText(group.getById(chat.getChatMessages().get(position).getSender()).getFirstName() + " " + group.getById(chat.getChatMessages().get(position).getSender()).getLastName());
                holder.getMessage().setText(chat.getChatMessages().get(position).getMessage());
            }
        }
    }

    @Override
    public int getItemCount() {
        if (chat != null)
            return chat.getChatMessages().size();
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private View parent;
        private TextView name;
        private TextView message;


        public ViewHolder(View view) {
            super(view);
            parent = view;
            name = view.findViewById(R.id.sender_name);
            message = view.findViewById(R.id.chat_message);
        }

        public TextView getName() {
            return name;
        }

        public TextView getMessage() {
            return message;
        }

    }

    public void update(Chat chat) {
        this.chat = chat;
        notifyDataSetChanged();
    }

    public void update(Group group) {
        this.group = group;
        notifyDataSetChanged();
    }

}
