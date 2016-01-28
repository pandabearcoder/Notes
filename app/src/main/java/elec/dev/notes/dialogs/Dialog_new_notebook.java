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
import elec.dev.notes.models.NoteModel;
import elec.dev.notes.models.NotebookModel;

public class Dialog_new_notebook extends DialogFragment implements View.OnClickListener {

    private TextView cancel, create;
    private EditText txt_nbName;
    NoteModel mNoteModel;
    NotebookModel mNBModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_new_notebook,null);

        mNBModel = new NotebookModel(getContext());
        mNoteModel = new NoteModel(getContext());

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
                    Activity_NavDrawer.notebook_details.clear();
                    Activity_NavDrawer.notebook_details.addAll(mNBModel.getAllNotebooks());
                    Activity_NavDrawer.adapter.notifyDataSetChanged();

                    Activity_main.appbar.setTitle(txt_nbName.getText().toString());
                    Activity_main.nb_id = mNBModel.getAvailableNotebook();
                    Activity_main.nb_name = txt_nbName.getText().toString();
                    Activity_main.note_details.clear();
                    Activity_main.note_details.addAll(mNoteModel.getAllNotes(Activity_main.nb_id));
                    Activity_main.adapter.notifyDataSetChanged();
                    Activity_main.empty_notebook = false;
                    Activity_main.fab.setVisibility(View.VISIBLE);
                    dismiss();
                    Activity_NavDrawer.closeDrawer();
                }
            }
        }
    }
}
