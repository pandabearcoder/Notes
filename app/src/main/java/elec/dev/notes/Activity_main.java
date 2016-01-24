package elec.dev.notes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import java.util.ArrayList;

import elec.dev.notes.models.NoteModel;
import elec.dev.notes.obj.NoteObj;
import elec.dev.notes.proj.data.Pref;

public class Activity_main extends AppCompatActivity {

    public final static String EDITOR_MODE = "elec.dev.notes.MODE";
    public final static String NOTEBOOK_ID = "elec.dev.notes.NOTEBOOK_ID";
    public final static String NOTE_ID = "elec.dev.notes.NOTE_ID";
    public final static String NOTE_TITLE = "elec.dev.notes.NOTE_TITLE";
    public final static String NOTE_CONTENT = "elec.dev.notes.NOTE_CONTENT";

    public static int nb_id;

    private NoteAdapter adapter;
    private NoteModel noteModel;
    private String mode;
    private String title;
    private String content;
    ArrayList<NoteObj> note_details;

    public Activity_main() {
        noteModel = new NoteModel(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

        nb_id = Pref.readFromPreferences(this, Pref.KEY_LAST_NOTEBOOK, 1);

        //Initialize toolbar and navigation drawer
        Toolbar appbar = (Toolbar) findViewById(R.id.main_toolbar);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        note_details.clear();
        note_details.addAll(noteModel.getAllNotes(nb_id));
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    //Custom Functions
    public void startEditor() {
        Intent intent = new Intent(this, Activity_NoteEditor.class);
        intent.putExtra(NOTE_TITLE, title);
        intent.putExtra(NOTE_CONTENT, content);
        intent.putExtra(EDITOR_MODE, mode);
        startActivity(intent);
    }
}
