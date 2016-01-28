package elec.dev.notes.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;

import elec.dev.notes.obj.NotebookObj;
import elec.dev.notes.proj.data.DatabaseHelper;

public class NotebookModel extends DatabaseHelper {

    Context context;

    public NotebookModel(Context context) {
        super(context);
        this.context = context;
    }

    public ArrayList<NotebookObj> getAllNotebooks() {
        ArrayList<NotebookObj> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+NOTEBOOK_TABLE, null);
        if(cursor.moveToFirst()){
            do{
                NotebookObj o = new NotebookObj();
                o.nbID = cursor.getInt(cursor.getColumnIndex(nb_ID));
                o.nbName = cursor.getString(cursor.getColumnIndex(nb_Name));
                list.add(o);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public String getNotebookName(int id) {
        String notebook_name = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT "+nb_Name+" FROM "+NOTEBOOK_TABLE+ " WHERE "+nb_ID+"="+id, null);
        if(cursor.moveToFirst()){
            do {
                notebook_name = cursor.getString(cursor.getColumnIndex(nb_Name));
            }
            while(cursor.moveToNext());
        }
        else {
            notebook_name = "Notebook";
        }
        cursor.close();
        return  notebook_name;
    }

    public int getAvailableNotebook() {
        int notebook_id = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT "+nb_ID+" FROM "+NOTEBOOK_TABLE, null);
        if(cursor.moveToFirst()){
            do {
                notebook_id = cursor.getInt(cursor.getColumnIndex(nb_ID));
            }
            while(cursor.moveToNext());
        }
        cursor.close();

        return notebook_id;
    }

    public boolean addNoteBook(String notebookName){
        ContentValues cv = new ContentValues();
        cv.put(nb_Name,notebookName);
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.insert(NOTEBOOK_TABLE, null, cv);
        if(result<0) {
            return false;
        }
        else {
            Toast.makeText(context, "Notebook Added", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    public boolean updateNotebook(int id, String notebook_name) {
        ContentValues cv = new ContentValues();
        cv.put(nb_Name,notebook_name);
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.update(NOTEBOOK_TABLE, cv, nb_ID + "=?", new String[]{Integer.toString(id)});
        if(result>0) {
            Toast.makeText(context,"Notebook updated!",Toast.LENGTH_SHORT).show();
            return true;
        }
        else {
            Toast.makeText(context,"Oops.. Note not updated",Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public int deleteNotebook(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(NOTE_TABLE,note_nbID+"=?",new String[]{ Integer.toString(id) });
        int result = db.delete(NOTEBOOK_TABLE,nb_ID+"=?",new String[]{ Integer.toString(id) });
        if(result>0) {
            Toast.makeText(context,"Notebook deleted!",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context,"Oops.. Notebook not deleted",Toast.LENGTH_SHORT).show();
        }
        return result;
    }
}
