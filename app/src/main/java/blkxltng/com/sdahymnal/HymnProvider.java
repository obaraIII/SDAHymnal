package blkxltng.com.sdahymnal;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by firej on 9/6/2016.
 */
public class HymnProvider extends ContentProvider {

    private DatabaseHelper mDatabaseHelper;

    // used for the UriMacher
    private static final int HYMNS = 10;
    private static final int HYMN_ID = 20;

    private static final String AUTHORITY = "blkxltng.com.sdahymnal";

    private static final String BASE_PATH = "hymns";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
            + "/" + BASE_PATH);

    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
            + "/hymns";
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
            + "/hymn";

    private static final UriMatcher mURIMatcher = new UriMatcher(
            UriMatcher.NO_MATCH);
    static {
        mURIMatcher.addURI(AUTHORITY, BASE_PATH, HYMNS);
        mURIMatcher.addURI(AUTHORITY, BASE_PATH + "/#", HYMN_ID);
    }


    @Override
    public boolean onCreate() {
        mDatabaseHelper = new DatabaseHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {

        // Uisng SQLiteQueryBuilder instead of query() method
        SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();

        // check if the caller has requested a column which does not exists
        checkColumns(strings);

        // Set the table
        sqLiteQueryBuilder.setTables(DatabaseHelper.TB_HYMNS);

        int uriType = mURIMatcher.match(uri);
        switch (uriType) {
            case HYMNS:
                break;
            case HYMN_ID:
                // adding the ID to the original query
                sqLiteQueryBuilder.appendWhere(DatabaseHelper.COL_ID + "="
                        + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        Cursor cursor = sqLiteQueryBuilder.query(db, strings, s,
                strings1, null, null, s1);
        // make sure that potential listeners are getting notified
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {

        int uriType = mURIMatcher.match(uri);
        SQLiteDatabase sqlDB = mDatabaseHelper.getWritableDatabase();
        long id = 0;
        switch (uriType) {
            case HYMNS:
                id = sqlDB.insert(DatabaseHelper.TB_HYMNS, null, contentValues);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(BASE_PATH + "/" + id);
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        int uriType = mURIMatcher.match(uri);
        SQLiteDatabase sqlDB = mDatabaseHelper.getWritableDatabase();
        int rowsDeleted = 0;
        switch (uriType) {
            case HYMNS:
                rowsDeleted = sqlDB.delete(DatabaseHelper.TB_HYMNS, s,
                        strings);
                break;
            case HYMN_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(s)) {
                    rowsDeleted = sqlDB.delete(
                            DatabaseHelper.TB_HYMNS,
                            DatabaseHelper.COL_ID + "=" + id,
                            null);
                } else {
                    rowsDeleted = sqlDB.delete(
                            DatabaseHelper.TB_HYMNS,
                            DatabaseHelper.COL_ID + "=" + id
                                    + " and " + s,
                            strings);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {

        int uriType = mURIMatcher.match(uri);
        SQLiteDatabase sqlDB = mDatabaseHelper.getWritableDatabase();
        int rowsUpdated = 0;
        switch (uriType) {
            case HYMNS:
                rowsUpdated = sqlDB.update(DatabaseHelper.TB_HYMNS,
                        contentValues,
                        s,
                        strings);
                break;
            case HYMN_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(s)) {
                    rowsUpdated = sqlDB.update(DatabaseHelper.TB_HYMNS,
                            contentValues,
                            DatabaseHelper.COL_ID + "=" + id,
                            null);
                } else {
                    rowsUpdated = sqlDB.update(DatabaseHelper.TB_HYMNS,
                            contentValues,
                            DatabaseHelper.COL_ID + "=" + id
                                    + " and "
                                    + s,
                            strings);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }

    private void checkColumns(String[] projection) {
        String[] available = { DatabaseHelper.COL_NUMBER,
                DatabaseHelper.COL_TITLE, DatabaseHelper.COL_SECTION,
                DatabaseHelper.COL_ID };
        if (projection != null) {
            HashSet<String> requestedColumns = new HashSet<String>(
                    Arrays.asList(projection));
            HashSet<String> availableColumns = new HashSet<String>(
                    Arrays.asList(available));
            // check if all columns which are requested are available
            if (!availableColumns.containsAll(requestedColumns)) {
                throw new IllegalArgumentException(
                        "Unknown columns in projection");
            }
        }
    }
}
