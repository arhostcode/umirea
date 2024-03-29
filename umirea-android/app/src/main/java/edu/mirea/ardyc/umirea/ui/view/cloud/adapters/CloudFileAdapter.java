package edu.mirea.ardyc.umirea.ui.view.cloud.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.function.Consumer;

import edu.mirea.ardyc.umirea.R;
import edu.mirea.ardyc.umirea.data.model.cloud.CloudFile;

public class CloudFileAdapter extends RecyclerView.Adapter<CloudFileAdapter.ViewHolder> {

    private List<CloudFile> files;
    private Consumer<CloudFile> cloudFileConsumer;
    private Consumer<CloudFile> deleteFile;

    public CloudFileAdapter(List<CloudFile> files) {
        this.files = files;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cloud_file_element, parent, false);

        return new CloudFileAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getFileName().setText(files.get(position).getName());
        holder.getFileIcon().setImageDrawable(ResourcesCompat.getDrawable(holder.fileIcon.getResources(), R.drawable.hw_icon, null));
        holder.getItemView().setOnClickListener(v -> cloudFileConsumer.accept(files.get(position)));
        holder.deleteFile.setOnClickListener(v -> deleteFile.accept(files.get(position)));
    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView fileName;
        private final ImageView fileIcon;
        private final ImageView deleteFile;

        private final View itemView;

        public ViewHolder(View view) {
            super(view);
            fileName = view.findViewById(R.id.file_name);
            fileIcon = view.findViewById(R.id.file_icon);
            deleteFile = view.findViewById(R.id.delete_file);
            itemView = view;
        }

        public TextView getFileName() {
            return fileName;
        }

        public ImageView getFileIcon() {
            return fileIcon;
        }

        public View getItemView() {
            return itemView;
        }
    }

    public void update(List<CloudFile> files) {
        this.files = files;
        notifyDataSetChanged();
    }

    public void setCloudFileConsumer(Consumer<CloudFile> cloudFileConsumer) {
        this.cloudFileConsumer = cloudFileConsumer;
    }

    public void setOnDeleteFileListener(Consumer<CloudFile> cloudFileConsumer) {
        this.deleteFile = cloudFileConsumer;
    }
}
