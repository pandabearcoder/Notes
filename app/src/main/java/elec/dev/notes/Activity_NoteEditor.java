package elec.dev.notes;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.Calendar;
import java.util.GregorianCalendar;

import elec.dev.notes.models.NoteModel;
import elec.dev.notes.proj.data.DatabaseHelper;

public class Activity_NoteEditor extends AppCompatActivity {

    private String mode;

    Toolbar appbar;

    EditText titleView;
    EditText contentView;

    NoteModel noteModel;

    int notebookID;
    int noteID;
    Calendar gc;

    public Activity_NoteEditor() {
        noteModel = new NoteModel(this);
        gc = new GregorianCalendar();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_note_editor);

        //Get EXTRA from Main Activity
        Intent intent = getIntent();
        mode = intent.getStringExtra(Activity_main.EDITOR_MODE);
        String title = intent.getStringExtra(Activity_main.NOTE_TITLE);
        String content = intent.getStringExtra(Activity_main.NOTE_CONTENT);
        noteID = intent.getIntExtra(Activity_main.NOTE_ID, 1);
        notebookID = intent.getIntExtra(Activity_main.NOTEBOOK_ID, 1);

        //Get View IDs
        titleView = (EditText) findViewById(R.id.txt_titleView_edit);
        contentView = (EditText) findViewById(R.id.txt_contentView_edit);
        appbar = (Toolbar) findViewById(R.id.edit_toolbar);

        //Set toolbar title to New Note if mode is new
        //then Set appbar as activity toolbar.
        if(mode.equals("new")) {
            appbar.setTitle("New Note");
        }
        setSupportActionBar(appbar);

        //Get toolbar to access it's functions
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        //Set Views' Content
        titleView.setText(title);
        contentView.setText(content);

        if(mode.equals("view")) {
            titleFocusListener();
            contentFocusListener();
        }
        else if(mode.equals("new")) {
            titleView.setFocusable(true);
            titleView.requestFocus();
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        MenuInflater inflater = getMenuInflater();
        if(mode.equals("edit") || mode.equals("new")) {
            inflater.inflate(R.menu.menu_note_edit, menu);
        }
        else if (mode.equals("view")) {
            inflater.inflate(R.menu.menu_note_view, menu);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                ContentValues cv = new ContentValues();
                String time = gc.getTime().toString();
                cv.put(DatabaseHelper.note_nbID, notebookID);
                cv.put(DatabaseHelper.note_Title, titleView.getText().toString());
                cv.put(DatabaseHelper.note_Content, contentView.getText().toString());
                cv.put(DatabaseHelper.note_Date, time);
                if(mode.equals("new")) {
                    noteModel.addNote(cv);
                }
                else if(mode.equals("edit")) {
                    noteModel.updateNote(cv, noteID);
                }
                finish();
                return true;

            case R.id.action_delete:
                int result = noteModel.deleteNote(noteID);
                if(result>0) {
                    finish();
                }
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public void titleFocusListener() {
        titleView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(hasWindowFocus()) {
                    if (mode.equals("view")) {
                        invalidateOptionsMenu();
                        mode = "edit";
                    }
                }
            }
        });
    }

    public void contentFocusListener() {
        contentView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(hasWindowFocus()) {
                    if (mode.equals("view")) {
                        invalidateOptionsMenu();
                        mode = "edit";
                    }
                }
            }
        });
    }
}
