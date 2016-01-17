package elec.dev.notes.proj.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "note.db";
    public static final String NOTEBOOK_TABLE = "notebooks";
    public static final String NOTE_TABLE = "notes";
    private static final int   DATABASE_VERSION = 2;
    private Context context;
    //FIELDS

    //notebook
    public static final String nb_ID = "nb_id";
    public static final String nb_Name = "nb_name";

    //note
    public static final String note_ID = "note_id";
    public static final String note_nbID = "note_nbid";
    public static final String note_Title = "note_title";
    public static final String note_Content = "note_content";
    public static final String note_Date = "note_date";

    public DatabaseHelper(Context context) {
        super (context,DATABASE_NAME,null,DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL("CREATE TABLE " + NOTEBOOK_TABLE + " ( " + nb_ID + " INTEGER PRIMARY KEY, " + nb_Name + " VARCHAR(50) NOT NULL );");
        }
        catch (Exception e) {
            Toast.makeText(context,"Failed to make nbTable",Toast.LENGTH_SHORT).show();
        }

        try {
            db.execSQL("CREATE TABLE "+NOTE_TABLE+"("+note_ID+" INTEGER PRIMARY KEY," +
                            " "+note_nbID+" INTEGER NOT NULL," +
                            " "+note_Title+" VARCHAR(25) NOT NULL," +
                            " "+note_Content+" VARCHAR(255) NOT NULL, " +
                            " "+note_Date+" VARCHAR(255)" +
                            ");"
            );
        }
        catch (Exception e) {
            Toast.makeText(context,"Failed to make noteTable",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXIST "+NOTEBOOK_TABLE);
        db.execSQL("DROP TABLE IF EXIST "+NOTE_TABLE);
        onCreate(db);
    }
}
