package sy7;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class MyContentProvider extends ContentProvider {
    // 0、1是自定义代码，用于清楚表达我们想要访问访问数据库的哪个表或者哪一行数据
    public static final int CONTACTS_DIR = 0;
    public static final int CONTACTS_ITEM = 1;
    // 和清单文件里provider的authority属性一致
    public static final String AUTHORITY = "com.example.databasetest.provider";
    public static UriMatcher uriMatcher;
    private MyDatabase myDatabase;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);// NO_MATCH就是-1
        uriMatcher.addURI(AUTHORITY, "contacts", CONTACTS_DIR);
        uriMatcher.addURI(AUTHORITY, "contacts/#", CONTACTS_ITEM);
    }

    public MyContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = myDatabase.getWritableDatabase();
        int deletedRows = 0;
        switch (uriMatcher.match(uri)) {
            case CONTACTS_DIR:
                deletedRows = db.delete("contacts", selection, selectionArgs);
                break;
            case CONTACTS_ITEM:
                String id = uri.getPathSegments().get(1);
                deletedRows = db.delete("contacts", "id = ?", new String[]{id});
                break;
            default:
                break;
        }
        return deletedRows;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)){
            case CONTACTS_DIR:
                return "vnd.android.cursor.dir/vnd.com.example.databasetest.provider.contacts";
            case CONTACTS_ITEM:
                return "vnd.android.cursor.item/vnd.com.example.databasetest.provider.contacts";
        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = myDatabase.getWritableDatabase();
        Uri uriReturn = null;
        switch (uriMatcher.match(uri)) {
            case CONTACTS_DIR:
            case CONTACTS_ITEM:
                long newId = db.insert("contacts", null, values);
                uriReturn = Uri.parse("content://" + AUTHORITY + "/contacts/" + newId);
                break;
            default:
                break;
        }
        return uriReturn;
    }

    @Override
    public boolean onCreate() {
        myDatabase = new MyDatabase(getContext(), "contacts.db", null,2);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = myDatabase.getReadableDatabase();
        Cursor cursor = null;
        switch (uriMatcher.match(uri)) {
            case CONTACTS_DIR:
                cursor = db.query("contacts", projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case CONTACTS_ITEM:
                String id = uri.getPathSegments().get(1);
                cursor = db.query("contacts", projection, "id = ?", new String[]{id}, null, null, sortOrder);
                break;
            default:
                break;
        }
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        SQLiteDatabase db = myDatabase.getWritableDatabase();
        int updateRows = 0;
        switch (uriMatcher.match(uri)) {
            case CONTACTS_DIR:
                updateRows = db.update("contacts", values, selection, selectionArgs);
                break;
            case CONTACTS_ITEM:
                String id = uri.getPathSegments().get(1);
                updateRows = db.update("contacts", values, "id = ?", new String[]{id});
                break;
            default:
                break;
        }
        return updateRows;
    }
}
