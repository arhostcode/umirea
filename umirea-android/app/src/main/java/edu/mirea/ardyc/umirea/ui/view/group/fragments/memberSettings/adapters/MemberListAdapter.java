package edu.mirea.ardyc.umirea.ui.view.group.fragments.memberSettings.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.function.Consumer;

import edu.mirea.ardyc.umirea.R;
import edu.mirea.ardyc.umirea.data.model.group.Group;
import edu.mirea.ardyc.umirea.data.model.group.Member;


public class MemberListAdapter extends RecyclerView.Adapter<MemberListAdapter.ViewHolder> {

    private Group group;

    private Consumer<Member> memberConsumer;

    public MemberListAdapter(Group group) {
        this.group = group;
    }

    public void setMemberConsumer(Consumer<Member> memberConsumer) {
        this.memberConsumer = memberConsumer;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.member_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (group != null) {
            String userName = String.format(holder.name.getResources().getString(R.string.user_full_name), group.getMembers().get(position).getFirstName(), group.getMembers().get(position).getLastName());
            holder.getName().setText(userName);
            holder.kick.setOnClickListener(v -> {
                memberConsumer.accept(group.getMembers().get(position));
            });
        }
    }

    @Override
    public int getItemCount() {
        if (group != null)
            return group.getMembers().size();
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private View parent;
        private TextView name;
        private ImageView kick;

        public ViewHolder(View view) {
            super(view);
            parent = view;
            name = view.findViewById(R.id.member_name);
            kick = view.findViewById(R.id.kick_member);
        }

        public TextView getName() {
            return name;
        }

    }

    public void update(Group group) {
        this.group = group;
        notifyDataSetChanged();
    }

}
