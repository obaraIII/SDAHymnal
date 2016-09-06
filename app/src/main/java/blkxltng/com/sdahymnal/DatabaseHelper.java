package blkxltng.com.sdahymnal;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by firej on 8/29/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    //The Android's default system path of your application database.
    public static String DB_PATH = "/data/data/blkxltng.com.sdahymnal/databases/";

    public static String DB_NAME = "hymns.db";

    public static final int DB_VERSION = 1;

    public static final String TB_SECTIONS = "Sections";
    public static final String TB_HYMNS = "Hymns";

    public static final String COL_ID = "_id";
    public static final String COL_TITLE = "Title";
    public static final String COL_NUMBER = "Number";
    public static final String COL_SECTION = "Section";
    public static final String COL_SUBSECTION = "SubSection";
    public static final String COL_FIRSTHYMN = "FirstHymn";
    public static final String COL_LASTHYMN = "LastHymn";
    public static final String COL_REFRAIN = "Refrain";
    public static final String COL_VERSE1 = "Verse 1";
    public static final String COL_VERSE2 = "Verse 2";
    public static final String COL_VERSE3 = "Verse 3";
    public static final String COL_VERSE4 = "Verse 4";
    public static final String COL_VERSE5 = "Verse 5";
    public static final String COL_VERSE6 = "Verse 6";
    public static final String COL_FAVORITED = "Favorited";

    private SQLiteDatabase mDatabase;
    private Context mContext;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public synchronized void close() {
        if(mDatabase != null) {
            mDatabase.close();
        }
        super.close();
    }

    /***
     * Check if the database is exist on device or not
     * @return
     */
    private boolean checkDatabase() {
        SQLiteDatabase tempDB = null;
        try {
            String mPath = DB_PATH + DB_NAME;
            tempDB = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.OPEN_READWRITE);
        } catch (SQLiteException e) {
            Log.e("blkxltng - check", e.getMessage());
        }
        if (tempDB != null)
            tempDB.close();
        return tempDB != null ? true : false;
    }

    /***
     * Copy database from source code assets to device
     * @throws IOException
     */
    public void copyDatabase() throws IOException {
        try {
            InputStream mInput = mContext.getAssets().open(DB_NAME);
            String outputFileName = DB_PATH + DB_NAME;
            OutputStream mOutput = new FileOutputStream(outputFileName);

            byte[] buffer = new byte[1024];
            int length;

            while((length = mInput.read(buffer))>0){
                mOutput.write(buffer, 0, length);
            }

            mOutput.flush();
            mOutput.close();
            mInput.close();
        } catch (Exception e) {
            Log.e("blkxltng - copyDatabase", e.getMessage());
        }

    }

    /***
     * Open database
     * @throws SQLException
     */
    public void openDatabase() throws SQLException {
        String mPath = DB_PATH + DB_NAME;
        mDatabase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    /***
     * Check if the database doesn't exist on device, create new one
     * @throws IOException
     */
    public void createDatabase() throws IOException {
        boolean dbExist = checkDatabase();

        if (dbExist) {

        } else {
            this.getReadableDatabase();
            try {
                copyDatabase();
            } catch (IOException e) {
                Log.e("blkxltng - create", e.getMessage());
            }
        }
    }

    public List<Hymns> getAllSections(){
        List<Hymns> listSections = new ArrayList<Hymns>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;

        Hymns hymns = null;

        try {
            cursor = db.rawQuery("SELECT * FROM " + TB_SECTIONS , null);
            if(cursor == null) return null;

            cursor.moveToFirst();
            do {
                hymns = new Hymns();
                hymns.setId(cursor.getInt(cursor.getColumnIndex(COL_ID)));
                hymns.setSection(cursor.getString(cursor.getColumnIndex(COL_TITLE)));
                hymns.setFirstHymn(cursor.getInt(cursor.getColumnIndex(COL_FIRSTHYMN)));
                hymns.setLastHymn(cursor.getInt(cursor.getColumnIndex(COL_LASTHYMN)));
                listSections.add(hymns);
            } while (cursor.moveToNext());
            cursor.close();
        } catch (Exception e) {
            Log.e("blkxltng", e.getMessage());
        }

        db.close();

        return listSections;
    }

    public List<Hymns> getListAllHymns(){
        List<Hymns> listHymns = new ArrayList<Hymns>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;

        Hymns hymns = null;

        try {
            cursor = db.rawQuery("SELECT * FROM " + TB_HYMNS , null);
            if(cursor == null) return null;

            cursor.moveToFirst();
            do {
                hymns = new Hymns();
                hymns.setId(cursor.getInt(cursor.getColumnIndex(COL_ID)));
                hymns.setTitle(cursor.getString(cursor.getColumnIndex(COL_TITLE)));
                hymns.setNumber(cursor.getInt(cursor.getColumnIndex(COL_NUMBER)));
                hymns.setSection(cursor.getString(cursor.getColumnIndex(COL_SECTION)));
                listHymns.add(hymns);
            } while (cursor.moveToNext());
            cursor.close();
        } catch (Exception e) {
            Log.e("blkxltng", e.getMessage());
        }

        db.close();

        return listHymns;
    }

    public List<Hymns> getListHymns(int first, int last){
        List<Hymns> listHymns = new ArrayList<Hymns>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;

        Hymns hymns = null;

        try {
            cursor = db.rawQuery("SELECT * FROM " + TB_HYMNS , null);
            if(cursor == null) return null;

            cursor.moveToPosition(first-1);
            do {
                hymns = new Hymns();
                hymns.setId(cursor.getInt(cursor.getColumnIndex(COL_ID)));
                hymns.setTitle(cursor.getString(cursor.getColumnIndex(COL_TITLE)));
                hymns.setNumber(cursor.getInt(cursor.getColumnIndex(COL_NUMBER)));
                hymns.setSection(cursor.getString(cursor.getColumnIndex(COL_SECTION)));
                listHymns.add(hymns);
                cursor.moveToNext();
            } while (cursor.getPosition() < last);
            cursor.close();
        } catch (Exception e) {
            Log.e("blkxltng", e.getMessage());
        }

        db.close();

        return listHymns;
    }

    public List<Hymns> getHymn(int position){
        List<Hymns> listHymns = new ArrayList<Hymns>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;

        Hymns hymns = null;

        try {
            cursor = db.rawQuery("SELECT * FROM " + TB_HYMNS , null);
            if(cursor == null) return null;

            cursor.moveToPosition(position);
            hymns = new Hymns();
            hymns.setId(cursor.getInt(cursor.getColumnIndex(COL_ID)));
            hymns.setTitle(cursor.getString(cursor.getColumnIndex(COL_TITLE)));
            hymns.setNumber(cursor.getInt(cursor.getColumnIndex(COL_NUMBER)));
//                hymns.setSection(cursor.getString(cursor.getColumnIndex(COL_SECTION)));
            if(cursor.getString(cursor.getColumnIndex(COL_VERSE1)) != null)
                hymns.setVerse1(cursor.getString(cursor.getColumnIndex(COL_VERSE1)));
            if(cursor.getString(cursor.getColumnIndex(COL_VERSE2)) != null)
                hymns.setVerse2(cursor.getString(cursor.getColumnIndex(COL_VERSE2)));
            if(cursor.getString(cursor.getColumnIndex(COL_VERSE3)) != null)
                hymns.setVerse3(cursor.getString(cursor.getColumnIndex(COL_VERSE3)));
            if(cursor.getString(cursor.getColumnIndex(COL_VERSE4)) != null)
                hymns.setVerse4(cursor.getString(cursor.getColumnIndex(COL_VERSE4)));
            if(cursor.getString(cursor.getColumnIndex(COL_VERSE5)) != null)
                hymns.setVerse5(cursor.getString(cursor.getColumnIndex(COL_VERSE5)));
            if(cursor.getString(cursor.getColumnIndex(COL_VERSE6)) != null)
                hymns.setVerse6(cursor.getString(cursor.getColumnIndex(COL_VERSE6)));
            if(cursor.getString(cursor.getColumnIndex(COL_REFRAIN)) != null)
                hymns.setRefrain(cursor.getString(cursor.getColumnIndex(COL_REFRAIN)));
            hymns.setFavorited(cursor.getInt(cursor.getColumnIndex(COL_FAVORITED)));
            listHymns.add(hymns);
            cursor.close();
        } catch (Exception e) {
            Log.e("blkxltng", e.getMessage());
        }

        db.close();

        return listHymns;
    }

    public List<Hymns> searchHymns(String searchQuery){
        List<Hymns> listHymns = new ArrayList<Hymns>();
        SQLiteDatabase db = this.getWritableDatabase();

        Log.d("SearchQuery", searchQuery);

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                COL_ID,
                COL_NUMBER,
                COL_TITLE
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = COL_TITLE + " = ?";
        String[] selectionArgs = { "Title" };

        // How you want the results sorted in the resulting Cursor
        String sortOrder = COL_NUMBER + " DESC";


        Cursor cursor;

        Hymns hymns = null;

        try {
            cursor = db.rawQuery("SELECT * FROM " + TB_HYMNS + " WHERE " + COL_TITLE + " LIKE '%" + searchQuery + "%'"
                    + " OR " + COL_NUMBER + " LIKE '%" + searchQuery + "%'", null);
            if(cursor == null) return null;

            cursor.moveToFirst();
            do {
                hymns = new Hymns();
                hymns.setId(cursor.getInt(cursor.getColumnIndex(COL_ID)));
                hymns.setTitle(cursor.getString(cursor.getColumnIndex(COL_TITLE)));
                hymns.setNumber(cursor.getInt(cursor.getColumnIndex(COL_NUMBER)));
                listHymns.add(hymns);
            } while (cursor.moveToNext());
            cursor.close();
        } catch (Exception e) {
            Log.e("blkxltng", e.getMessage());
        }

        db.close();

        return listHymns;
    }

    public void favoritedHymn(int hymnNumber, int favorited){

        SQLiteDatabase db = this.getWritableDatabase();

        Log.d("Favorited_Hymn", "Number: " + hymnNumber + " Favorited: " + favorited);

        String updateString = "UPDATE " + TB_HYMNS + " SET " + COL_FAVORITED + " = " + favorited +
                " WHERE " + COL_NUMBER + " = " + hymnNumber;

        Log.d("UpdateString", updateString);

        db.execSQL(updateString);

        db.close();
    }

    public List<Hymns> getFavoriteHymns(){
        List<Hymns> listHymns = new ArrayList<Hymns>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;

        Hymns hymns = null;

        try {
            cursor = db.rawQuery("SELECT * FROM " + TB_HYMNS + " WHERE Favorited = 1" , null);
            if(cursor == null) return null;

            cursor.moveToFirst();
            do {
                hymns = new Hymns();
                hymns.setId(cursor.getInt(cursor.getColumnIndex(COL_ID)));
                hymns.setTitle(cursor.getString(cursor.getColumnIndex(COL_TITLE)));
                hymns.setNumber(cursor.getInt(cursor.getColumnIndex(COL_NUMBER)));
                hymns.setSection(cursor.getString(cursor.getColumnIndex(COL_SECTION)));
                listHymns.add(hymns);
            } while (cursor.moveToNext());
            cursor.close();
        } catch (Exception e) {
            Log.e("blkxltng", e.getMessage());
        }

        db.close();

        return listHymns;
    }
}
