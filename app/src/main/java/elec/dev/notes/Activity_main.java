package elec.dev.notes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import elec.dev.notes.dialogs.Dialog_edit_notebook;
import elec.dev.notes.models.NoteModel;
import elec.dev.notes.models.NotebookModel;
import elec.dev.notes.obj.NoteObj;
import elec.dev.notes.proj.data.Pref;

public class Activity_main extends AppCompatActivity {

    public final static String EDITOR_MODE = "elec.dev.notes.MODE";
    public final static String NOTEBOOK_ID = "elec.dev.notes.NOTEBOOK_ID";
    public final static String NOTE_ID = "elec.dev.notes.NOTE_ID";
    public final static String NOTE_TITLE = "elec.dev.notes.NOTE_TITLE";
    public final static String NOTE_CONTENT = "elec.dev.notes.NOTE_CONTENT";

    public static int nb_id;
    public static boolean hasChanged;

    private NoteModel noteModel;
    private NotebookModel notebookModel;

    private String mode;
    private String title;
    private String content;
    public static String nb_name;
    public static NoteAdapter adapter;
    public static Toolbar appbar;
    public static ArrayList<NoteObj> note_details;

    public Activity_main() {
        noteModel = new NoteModel(this);
        notebookModel = new NotebookModel(this);
        hasChanged = false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

        nb_id = Pref.readFromPreferences(this, Pref.KEY_LAST_NOTEBOOK_ID, 1);

        String nbName = "";
        nbName = notebookModel.getNotebookName(nb_id);
        if(nbName.equals("")) {
            nb_name = "Notebook";
        }
        else {
            nb_name = nbName;
        }

        nb_name = notebookModel.getNotebookName(nb_id);

        //Initialize toolbar and navigation drawer
        appbar = (Toolbar) findViewById(R.id.main_toolbar);
        appbar.setTitle(nb_name);
        setSupportActionBar(appbar);

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.DrawerLayout);
        Activity_NavDrawer navDrawer = (Activity_NavDrawer) getSupportFragmentManager().findFragmentById(R.id.fragment_navdrawer);
        navDrawer.setUp(R.id.fragment_navdrawer,drawerLayout,appbar);

        //Initialize RecycleView and data
        note_details = noteModel.getAllNotes(nb_id);
        adapter = new NoteAdapter(Activity_main.this, note_details);
        RecyclerView recyView = (RecyclerView) findViewById(R.id.scrollableView);
        recyView.setLayoutManager(new LinearLayoutManager(this));
        recyView.setAdapter(adapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = "";
                content = "";
                mode = "new";
                startEditor();
            }
        });

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        if(hasChanged) {
            note_details.clear();
            note_details.addAll(noteModel.getAllNotes(nb_id));
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Pref.saveToPreferences(this, Pref.KEY_LAST_NOTEBOOK_ID, nb_id);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(hasChanged) {
            note_details.clear();
            note_details.addAll(noteModel.getAllNotes(nb_id));
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete_notebook:
                int result = notebookModel.deleteNotebook(nb_id);
                if(result>0) {
                    hasChanged = true;
                    Activity_NavDrawer.notebook_details.clear();
                    Activity_NavDrawer.notebook_details.addAll(notebookModel.getAllNotebooks());
                    Activity_NavDrawer.adapter.notifyDataSetChanged();
                    Activity_main.appbar.setTitle("Notebook");
                }
                return true;

            case R.id.action_update_notebook:
                showUpdateNotebookDialog();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    //Custom Functions
    public void startEditor() {
        Intent intent = new Intent(this, Activity_NoteEditor.class);
        intent.putExtra(NOTEBOOK_ID,nb_id);
        intent.putExtra(NOTE_TITLE, title);
        intent.putExtra(NOTE_CONTENT, content);
        intent.putExtra(EDITOR_MODE, mode);
        startActivity(intent);
    }

    public void showUpdateNotebookDialog() {
        FragmentManager manager = getSupportFragmentManager();
        Dialog_edit_notebook edit_dialog = new Dialog_edit_notebook();
        edit_dialog.show(manager, "edit");
    }
}
