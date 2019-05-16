package top.linxixiangxin.datastorage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
    private static final String TAG = "dialog";
    private static final String DATABASE_NAME = "MyDict.db";
    private static final int DATABASE_VERSION = 1;
    private final static String CREATE_TABLE_SQL = "CREATE TABLE dict(id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "Word TEXT NOT NULL,Describe TEXT NOT NULL)";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(TAG, "DBHelper: 数据库创建成功");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_SQL);
        Log.d(TAG, "onCreate: create table");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersioin, int newVersion) {

    }

    @Override
    public synchronized void close() {
        super.close();
    }
}
