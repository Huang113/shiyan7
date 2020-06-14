package sy7;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class MyDatabase extends SQLiteOpenHelper {
    private Context mContext;

    public static final String CREATE_CONTACTS = "create table contacts ("
            + "id integer primary key autoincrement,"
            + "name text,"
            + "phone text,"
            + "sex text)";

    public MyDatabase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CONTACTS); 
        db.execSQL("insert into contacts (id,name,phone,sex) values(?,?,?,?)",
                new String[] { "1", "李富强 ", "13567652311", "男"});
        db.execSQL("insert into contacts (id,name,phone,sex) values(?,?,?,?)",
                new String[] { "2", "陈晓晨", "13563567310", "女"});
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists contacts");
        onCreate(db);
    }
}
