package elec.dev.notes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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

public class Activity_main extends AppCompatActivity {

    public final static String EDITOR_MODE = "elec.dev.notes.MODE";
    public final static String NOTE_ID = "elec.dev.notes.NOTE_ID";
    public final static String NOTE_TITLE = "elec.dev.notes.NOTE_TITLE";
    public final static String NOTE_CONTENT = "elec.dev.notes.NOTE_CONTENT";

    private NoteModel noteModel;
    private String mode;
    private String title;
    private String content;
    private RecyAdapter adapter;
    ArrayList<NoteObj> note_details;

    public Activity_main() {
        noteModel = new NoteModel(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

        Toolbar appbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(appbar);

        getSupportFragmentManager().findFragmentById(R.id.fragment_navdrawer);

        note_details = noteModel.getAllNotes();
        adapter = new RecyAdapter(Activity_main.this, note_details);
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
    public void onResume() {
        super.onResume();
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
