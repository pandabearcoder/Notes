package elec.dev.notes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import elec.dev.notes.obj.NotebookObj;

/**
 * Created by sly on 1/24/16.
 */
public class NotebookAdapter extends RecyclerView.Adapter<NotebookViewHolder> {

    private ArrayList<NotebookObj> notebookData;
    private Context mContext;

    int notebook_id;
    String notebook_name;

    public NotebookAdapter(Context context, ArrayList<NotebookObj> notebookDataList) {
        this.notebookData = notebookDataList;
        this.mContext = context;
    }

    @Override
    public NotebookViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.navdrawer_notebook_list, viewGroup, false);

        NotebookViewHolder viewHolder = new NotebookViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NotebookViewHolder customViewHolder, int i) {
        NotebookObj notebookList = notebookData.get(i);

        //Setting text view title
        customViewHolder.nb_titleView.setText(notebookList.nbName);

        customViewHolder.notebookBoxView.setOnClickListener(clickListener);
        customViewHolder.nb_titleView.setOnClickListener(clickListener);

        customViewHolder.notebookBoxView.setTag(customViewHolder);
        customViewHolder.nb_titleView.setTag(customViewHolder);
    }

    @Override
    public int getItemCount() {
        return (null != notebookData ? notebookData.size() : 0);
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            NoteViewHolder holder = (NoteViewHolder) view.getTag();
            int position = holder.getLayoutPosition();

            NotebookObj notebookList = notebookData.get(position);
            notebook_id = notebookList.nbID;
            notebook_name = notebookList.nbName;
            Activity_main.nb_id = notebook_id;
        }
    };
}