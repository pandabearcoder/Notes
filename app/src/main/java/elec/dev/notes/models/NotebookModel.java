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

    public int delete(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(NOTE_TABLE,note_nbID+"=?",new String[]{ Integer.toString(id) });
        return db.delete(NOTEBOOK_TABLE,nb_ID+"=?",new String[]{ Integer.toString(id) });
    }
}
