package edu.mirea.ardyc.umirea.ui.view.dashboard.dialogs;

import android.app.AlertDialog;
import android.content.Context;

import java.util.function.Consumer;

import edu.mirea.ardyc.umirea.R;
import edu.mirea.ardyc.umirea.databinding.TaskModifierDialogBinding;
import es.dmoral.toasty.Toasty;

public class TaskModifierDialog extends AlertDialog {

    private TaskModifierDialogBinding binding;

    protected TaskModifierDialog(Context context, String text, Consumer<String> stringConsumer) {
        super(context, R.style.BottomSheetDialog);
        binding = TaskModifierDialogBinding.inflate(getLayoutInflater());
        binding.task.setText(text);
        binding.apply.setOnClickListener((view) -> {
            if (binding.task.getText().toString().isEmpty())
                return;
            if (binding.task.getText().toString().split("\n").length > 10) {
                Toasty.info(context, "Слишком много строк").show();
                return;
            }
            stringConsumer.accept(binding.task.getText().toString());
            hide();
        });
        setView(binding.getRoot());
    }
}
