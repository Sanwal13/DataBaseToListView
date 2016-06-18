package woodenstreet.databasetolistview;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Sanwal Singh on 17/6/16.
 */
public class DataBaseHelper  {

    public static final String KEY_ROWID = "_id";
    public static final String KEY_COL1 = "col1";
    public static final String KEY_COL2 = "col2";

    private static final String DATABASE_NAME = "mydb";
    private static final String DATABASE_TABLE = "mytable";
    private static final int DATABASE_VERSION = 1;

    private final Context ourContext;
    private DbHelper dbh;
    private SQLiteDatabase odb;

    private static final String USER_MASTER_CREATE =
            "CREATE TABLE IF NOT EXISTS " + DATABASE_TABLE+ "("
                    + KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + KEY_COL1  + " VARCHAR(15) UNIQUE, " + KEY_COL2 + " VARCHAR(15) )";

    private static class DbHelper extends SQLiteOpenHelper {

        public DbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(USER_MASTER_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // if DATABASE VERSION changes
            // Drop old tables and call super.onCreate()
        }
    }

    public DataBaseHelper(Context c) {
        ourContext = c;
        dbh = new DbHelper(ourContext);
    }

    public DataBaseHelper open() throws SQLException {
        odb = dbh.getWritableDatabase();
        return this;
    }

    public void close() {
        dbh.close();
    }

    public long insertmaster(String col1, String col2) throws SQLException{
        Log.d("", col1);
        Log.d("", col2);

        ContentValues IV = new ContentValues();

        IV.put(KEY_COL1, col1);
        IV.put(KEY_COL2, col2);

        return odb.insert(DATABASE_TABLE, null, IV);
        // returns a number >0 if inserting data is successful
    }

    public void updateRow(long rowID, String col1, String col2) {
        ContentValues values = new ContentValues();
        values.put(KEY_COL1, col1);
        values.put(KEY_COL2, col2);

        try {
            odb.update(DATABASE_TABLE, values, KEY_ROWID + "=" + rowID, null);
        } catch (Exception e) {
        }
    }

    public boolean delete() {
        return odb.delete(DATABASE_TABLE, null, null) > 0;
    }

    public Cursor getAllTitles() {
        // using simple SQL query
        return odb.rawQuery("select * from " + DATABASE_TABLE, null);
    }

    public Cursor getallCols(String id) throws SQLException {
        Cursor mCursor = odb.query(DATABASE_TABLE, new String[] { KEY_COL1,
                KEY_COL2 }, null, null, null, null, null);
        Log.e("getallcols zmv", "opening successfull");
        return mCursor;
    }

    public Cursor getColsById(String id) throws SQLException {
        Cursor mCursor = odb.query(DATABASE_TABLE, new String[] { KEY_COL1,
                KEY_COL2 }, KEY_ROWID + " = " + id, null, null, null, null);
        Log.e("getallcols zmv", "opening successfull");
        return mCursor;
    }
}