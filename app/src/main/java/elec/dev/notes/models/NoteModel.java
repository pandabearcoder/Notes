package elec.dev.notes.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;

import elec.dev.notes.obj.NoteObj;
import elec.dev.notes.proj.data.DatabaseHelper;

public class NoteModel extends DatabaseHelper {

    Context context;

    public NoteModel(Context context) {
        super(context);
        this.context = context;
    }

    public ArrayList<NoteObj> getAllNotes(int nbID) {
        ArrayList<NoteObj> list = new ArrayList<NoteObj>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+NOTE_TABLE+ " WHERE " +note_nbID+"=" +nbID, null);
        if(cursor.moveToFirst()){
            do{
                NoteObj o = new NoteObj();
                o.noteID = cursor.getInt(cursor.getColumnIndex(note_ID));
                o.noteTitle = cursor.getString(cursor.getColumnIndex(note_Title));
                o.noteContent = cursor.getString(cursor.getColumnIndex(note_Content));
                o.noteDate = cursor.getString(cursor.getColumnIndex(note_Date));
                list.add(o);
            }while(cursor.moveToNext());
        }
        return list;
    }

    public boolean addNote(ContentValues cv){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.insert(NOTE_TABLE, null, cv);
        if(result<0) {
            Toast.makeText(context, "Oops, Note not saved!", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            Toast.makeText(context,"Note Saved!",Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    public int deleteNote(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result =  db.delete(NOTE_TABLE, note_ID + "=?", new String[]{Integer.toString(id)});
        if(result>0) {
            Toast.makeText(context,"Note deleted!",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context,"Oops.. Note not deleted",Toast.LENGTH_SHORT).show();
        }
        return result;
    }

    public int updateNote(ContentValues cv, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.update(NOTE_TABLE, cv, note_ID + "=?", new String[]{ Integer.toString(id) });
        if(result>0) {
            Toast.makeText(context,"Note updated!",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context,"Oops.. Note not updated",Toast.LENGTH_SHORT).show();
        }
        return result;
    }
}
