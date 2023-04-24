package edu.mirea.ardyc.umirea.ui.view.cloud;

import android.app.AlertDialog;
import android.content.Context;

import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

import edu.mirea.ardyc.umirea.R;
import edu.mirea.ardyc.umirea.databinding.CreateFolderDialogBinding;

public class CreateFolderDialog extends AlertDialog {

    private CreateFolderDialogBinding binding;

    public CreateFolderDialog(Context context, Consumer<String> folderAction) {
        super(context, R.style.BottomSheetDialog);
        binding = CreateFolderDialogBinding.inflate(getLayoutInflater());
        setView(binding.getRoot());
        binding.create.setOnClickListener((view) -> {
            if (!binding.folderName.getText().toString().isEmpty()) {
                folderAction.accept(binding.folderName.getText().toString());
                hide();
            }
        });

    }

}
