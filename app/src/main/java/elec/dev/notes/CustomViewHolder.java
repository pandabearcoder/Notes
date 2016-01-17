package elec.dev.notes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CustomViewHolder extends RecyclerView.ViewHolder {
    protected LinearLayout noteBoxView;
    protected TextView titleView;
    protected TextView contentView;
    protected TextView dateView;
    private final Context context;

    public CustomViewHolder(View view) {
        super(view);
        this.noteBoxView = (LinearLayout) view.findViewById(R.id.notebox);
        this.titleView = (TextView) view.findViewById(R.id.txt_titleView);
        this.contentView = (TextView) view.findViewById(R.id.txt_contentView);
        this.dateView = (TextView) view.findViewById(R.id.txt_dateView);
        context = view.getContext();
    }
}
