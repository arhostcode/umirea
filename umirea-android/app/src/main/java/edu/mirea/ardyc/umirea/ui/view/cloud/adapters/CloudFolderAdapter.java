package edu.mirea.ardyc.umirea.ui.view.cloud.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.mirea.ardyc.umirea.R;
import edu.mirea.ardyc.umirea.data.model.cloud.CloudFolder;

public class CloudFolderAdapter extends RecyclerView.Adapter<CloudFolderAdapter.ViewHolder> {

    public CloudFolderAdapter(List<CloudFolder> folders) {
        this.folders = folders;
    }

    private List<CloudFolder> folders;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cloud_list_item, parent, false);

        return new CloudFolderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.folderName.setText(folders.get(position).getFolderName());
        CloudFileAdapter adapter = (CloudFileAdapter) holder.getFiles().getAdapter();
        if (adapter == null) {
            holder.getFiles().setLayoutManager(new LinearLayoutManager(holder.files.getContext()));
            holder.getFiles().setAdapter(new CloudFileAdapter(folders.get(position).getFiles()));
        } else {
            adapter.update(folders.get(position).getFiles());
        }
    }

    @Override
    public int getItemCount() {
        return folders.size();
    }

    public void update(List<CloudFolder> folders) {
        this.folders = folders;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private View parent;
        private TextView folderName;
        private RecyclerView files;
        private ImageView arrow;
        private View layout;
        private View folderLayout;

        public ViewHolder(View view) {
            super(view);
            parent = view;
            folderName = view.findViewById(R.id.folder_name);
            files = view.findViewById(R.id.files);
            arrow = view.findViewById(R.id.files_list_button);
            layout = view.findViewById(R.id.files_layout);
            folderLayout = view.findViewById(R.id.folder_layout);
            updateButtonAction();
            updateState();
        }

        public TextView getFolderName() {
            return folderName;
        }

        public RecyclerView getFiles() {
            return files;
        }

        private void updateButtonAction() {
            folderLayout.setOnClickListener(v -> {
                updateState();
            });
        }

        private void updateState() {
            if (layout.getVisibility() == View.GONE) {
                layout.setVisibility(View.VISIBLE);
                arrow.setImageDrawable(ResourcesCompat.getDrawable(arrow.getResources(), R.drawable.arrow_up, null));
            } else {
                layout.setVisibility(View.GONE);
                arrow.setImageDrawable(ResourcesCompat.getDrawable(arrow.getResources(), R.drawable.arrow_down, null));
            }
        }

    }

}
