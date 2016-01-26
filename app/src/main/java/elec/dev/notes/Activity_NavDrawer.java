package elec.dev.notes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

import elec.dev.notes.dialogs.Dialog_new_notebook;
import elec.dev.notes.models.NotebookModel;
import elec.dev.notes.obj.NotebookObj;
import elec.dev.notes.proj.data.Pref;

public class Activity_NavDrawer extends Fragment {

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private View containerView;
    private Boolean mUserLearnedDrawer;
    private Boolean mFromSavedInstanceState;
    private NotebookAdapter adapter;
    private NotebookModel notebookModel;
    private ArrayList<NotebookObj> notebook_details;

    public Activity_NavDrawer() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUserLearnedDrawer = Pref.readFromPreferences(getActivity(), Pref.KEY_LEARNED, false);
        mFromSavedInstanceState = savedInstanceState != null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);

        RecyclerView mRecycleView = (RecyclerView) view.findViewById(R.id.notebookView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecycleView.setLayoutManager(layoutManager);

        notebookModel = new NotebookModel(getActivity());
        notebook_details = notebookModel.getAllNotebooks();

        adapter = new NotebookAdapter(getActivity(), notebook_details);
        mRecycleView.setAdapter(adapter);

        LinearLayout box = (LinearLayout) view.findViewById(R.id.nav_action_new_cont);
        box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCreateNotebookDialog();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        notebook_details.clear();
        notebook_details.addAll(notebookModel.getAllNotebooks());
        adapter.notifyDataSetChanged();
    }

    public void setUp(int fragmentID, final DrawerLayout drawerLayout, Toolbar toolbar) {
        containerView = getActivity().findViewById(fragmentID);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                if(!mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;
                    Pref.saveToPreferences(getActivity(), Pref.KEY_LEARNED, true);
                }
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

                if(!mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;
                    Pref.saveToPreferences(getActivity(), Pref.KEY_LEARNED, true);
                }
                getActivity().invalidateOptionsMenu();
            }
        };

        if(!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(containerView);
        }

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }

    public void showCreateNotebookDialog() {
        FragmentManager manager = getFragmentManager();
        Dialog_new_notebook dialog = new Dialog_new_notebook();
        dialog.show(manager, "create");
    }
}
