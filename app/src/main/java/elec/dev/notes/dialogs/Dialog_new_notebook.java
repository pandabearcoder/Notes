package elec.dev.notes.dialogs;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import elec.dev.notes.R;
import elec.dev.notes.models.NotebookModel;

public class Dialog_new_notebook extends DialogFragment implements View.OnClickListener {

    private TextView cancel, create;
    private EditText txt_nbName;
    NotebookModel mNBModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_new_notebook,null);

        mNBModel = new NotebookModel(getContext());

        txt_nbName = (EditText) view.findViewById(R.id.txt_new_notebook);

        cancel = (TextView) view.findViewById(R.id.lbl_action_cancel);
        create = (TextView) view.findViewById(R.id.lbl_action_create);
        cancel.setOnClickListener(this);
        create.setOnClickListener(this);

        setCancelable(false);
        return view;
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.lbl_action_cancel) {
            cancel.setBackgroundColor(0x1A000000);
            dismiss();
        }
        else if(view.getId()==R.id.lbl_action_create) {
            create.setBackgroundColor(0x1A000000);
            if(!txt_nbName.getText().toString().trim().equals("")) {
                if(mNBModel.addNoteBook(txt_nbName.getText().toString())) {
                    dismiss();
                }
            }
        }
    }
}
