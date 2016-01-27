package elec.dev.notes.dialogs;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import elec.dev.notes.Activity_NavDrawer;
import elec.dev.notes.Activity_main;
import elec.dev.notes.R;
import elec.dev.notes.models.NotebookModel;

public class Dialog_edit_notebook extends DialogFragment implements View.OnClickListener {

    private TextView cancel, update;
    private EditText txt_nbName;
    NotebookModel mNBModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_edit_notebook,null);

        mNBModel = new NotebookModel(getContext());

        txt_nbName = (EditText) view.findViewById(R.id.txt_edit_notebook);

        cancel = (TextView) view.findViewById(R.id.lbl_action_cancel);
        update = (TextView) view.findViewById(R.id.lbl_action_update);
        cancel.setOnClickListener(this);
        update.setOnClickListener(this);

        setCancelable(false);
        return view;
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.lbl_action_cancel) {
            cancel.setBackgroundColor(0x1A000000);
            dismiss();
        }
        else if(view.getId()==R.id.lbl_action_update) {
            update.setBackgroundColor(0x1A000000);
            if(!txt_nbName.getText().toString().trim().equals("")) {
                if(mNBModel.updateNotebook(Activity_main.nb_id,txt_nbName.getText().toString())) {
                    Activity_NavDrawer.notebook_details.clear();
                    Activity_NavDrawer.notebook_details.addAll(mNBModel.getAllNotebooks());
                    Activity_NavDrawer.adapter.notifyDataSetChanged();
                    Activity_main.appbar.setTitle(txt_nbName.getText().toString());
                    dismiss();
                }
            }
        }
    }

}
