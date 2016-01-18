package elec.dev.notes;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import elec.dev.notes.obj.NoteObj;

public class RecyAdapter extends RecyclerView.Adapter<NoteViewHolder> {

    private ArrayList<NoteObj> noteData;
    private Context mContext;

    int note_id;
    String note_title;
    String note_content;
    String mode;

    public RecyAdapter(Context context, ArrayList<NoteObj> noteDataList) {
        this.noteData = noteDataList;
        this.mContext = context;
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_note_list, viewGroup, false);

        NoteViewHolder viewHolder = new NoteViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NoteViewHolder customViewHolder, int i) {
        NoteObj noteList = noteData.get(i);

        //Setting text view title
        customViewHolder.titleView.setText(noteList.noteTitle);
        customViewHolder.contentView.setText(noteList.noteContent);
        customViewHolder.dateView.setText(noteList.noteDate);

        customViewHolder.noteBoxView.setOnClickListener(clickListener);
        customViewHolder.titleView.setOnClickListener(clickListener);
        customViewHolder.contentView.setOnClickListener(clickListener);
        customViewHolder.dateView.setOnClickListener(clickListener);

        customViewHolder.noteBoxView.setTag(customViewHolder);
        customViewHolder.titleView.setTag(customViewHolder);
        customViewHolder.contentView.setTag(customViewHolder);
        customViewHolder.dateView.setTag(customViewHolder);
    }

    @Override
    public int getItemCount() {
        return (null != noteData ? noteData.size() : 0);
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            NoteViewHolder holder = (NoteViewHolder) view.getTag();
            int position = holder.getLayoutPosition();

            NoteObj noteList = noteData.get(position);
            note_id = noteList.noteID;
            note_title = noteList.noteTitle;
            note_content = noteList.noteContent;
            mode = "view";

            Intent intent = new Intent(mContext, Activity_NoteEditor.class);
            intent.putExtra(Activity_main.NOTE_ID,note_id);
            intent.putExtra(Activity_main.NOTE_TITLE, note_title);
            intent.putExtra(Activity_main.NOTE_CONTENT, note_content);
            intent.putExtra(Activity_main.EDITOR_MODE, mode);
            mContext.startActivity(intent);
        }
    };
}


