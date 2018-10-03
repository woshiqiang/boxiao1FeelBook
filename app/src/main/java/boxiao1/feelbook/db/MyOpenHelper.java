package boxiao1.feelbook.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import boxiao1.feelbook.constant.DBConstants;

/**
 * @Date 2018-10-03.
 */
public class MyOpenHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;

    private static final String SQL = "create table " + DBConstants.TABLE_NAME +
            "(_id integer primary key autoincrement , type varchar(20), addtime timestamp , content varchar(1000) )";

    public MyOpenHelper(Context context) {
        super(context, DBConstants.DB_NAME, null, DB_VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        this.onCreate(sqLiteDatabase);
    }
}
