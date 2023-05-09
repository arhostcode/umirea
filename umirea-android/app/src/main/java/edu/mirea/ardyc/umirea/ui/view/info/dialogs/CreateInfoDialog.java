package edu.mirea.ardyc.umirea.ui.view.info.dialogs;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.function.Consumer;

import edu.mirea.ardyc.umirea.R;
import edu.mirea.ardyc.umirea.data.model.info.InfoMessage;
import edu.mirea.ardyc.umirea.databinding.CreateInfoDialogBinding;
import es.dmoral.toasty.Toasty;

public class CreateInfoDialog extends BottomSheetDialog {

    private CreateInfoDialogBinding createInfoDialogBinding;

    private Consumer<InfoContent> infoMessageConsumer;

    public CreateInfoDialog(@NonNull Context context) {
        super(context, R.style.BottomSheetDialog);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        createInfoDialogBinding = CreateInfoDialogBinding.inflate(getLayoutInflater());

        createInfoDialogBinding.create.setOnClickListener(v -> {
            String title = createInfoDialogBinding.infoName.getText().toString();
            String text = createInfoDialogBinding.infoText.getText().toString();
            if (title.isEmpty() || text.isEmpty()) {
                return;
            }
            if (text.split("\n").length > 20) {
                Toasty.info(context, "Слишком много строк").show();
                return;
            }
            infoMessageConsumer.accept(new InfoContent(title, text));
            hide();
        });

        setContentView(createInfoDialogBinding.getRoot());
    }


    public void setInfoContentConsumer(Consumer<InfoContent> infoMessageConsumer) {
        this.infoMessageConsumer = infoMessageConsumer;
    }
}
