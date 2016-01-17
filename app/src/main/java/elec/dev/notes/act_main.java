package elec.dev.notes;

import android.content.res.Configuration;
import android.support.annotation.UiThread;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;

import java.util.ArrayList;

import elec.dev.notes.models.NoteModel;

public class act_main extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    NavigationView navigation;

    public final static String EDITOR_MODE = "elec.dev.notes.MODE";
    public final static String NOTE_ID = "elec.dev.notes.NOTE_ID";
    public final static String NOTE_TITLE = "elec.dev.notes.NOTE_TITLE";
    public final static String NOTE_CONTENT = "elec.dev.notes.NOTE_CONTENT";

    private Toolbar appbar;
    private RecyclerView recyView;
    private NoteModel noteModel;
    private String mode;
    private String title;
    private String content;
    private RecyAdapter adapter;
    ArrayList note_details;

    public act_main() {
        noteModel = new NoteModel(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

        appbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(appbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.DrawerLayout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.app_name, R.string.app_name);
        drawerLayout.setDrawerListener(drawerToggle);

        navigation = (NavigationView) findViewById(R.id.navigation_view);
        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.navigation_item_settings:
                        //Do some thing here
                        // add navigation drawer item onclick method here
                        break;
                    case R.id.navigation_item_help:
                        //Do some thing here
                        // add navigation drawer item onclick method here
                        break;
                }
                return false;
            }
        });

        note_details = noteModel.getAllNotes();
        adapter = new RecyAdapter(act_main.this, note_details);
        recyView = (RecyclerView) findViewById(R.id.scrollableView);
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        return drawerToggle.onOptionsItemSelected(item);
    }

    //Custom Functions
    public void startEditor() {
        Intent intent = new Intent(this, act_note_editor.class);
        intent.putExtra(NOTE_TITLE, title);
        intent.putExtra(NOTE_CONTENT, content);
        intent.putExtra(EDITOR_MODE, mode);
        startActivity(intent);
    }
}
