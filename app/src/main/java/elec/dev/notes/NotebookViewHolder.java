package elec.dev.notes;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NotebookViewHolder extends RecyclerView.ViewHolder {

    protected LinearLayout notebookBoxView;
    protected TextView nb_titleView;

    public NotebookViewHolder(View view) {
        super(view);
        this.notebookBoxView = (LinearLayout) view.findViewById(R.id.nav_list_cont);
        this.nb_titleView = (TextView) view.findViewById(R.id.nav_list_text);
    }
}
