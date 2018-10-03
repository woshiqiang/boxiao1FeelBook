package boxiao1.feelbook.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import boxiao1.feelbook.bean.Mood;
import boxiao1.feelbook.constant.DBConstants;

/**
 * @Date 2018-10-03.
 */
public class DBManager {
    private MyOpenHelper helper;
    private SQLiteDatabase db;
    public static DBManager instance = null;

    private DBManager(Context context) {
        this.helper = new MyOpenHelper(context);
        this.db = helper.getWritableDatabase();
    }

    public static DBManager getInstance(Context context) {
        if (instance == null) {
            instance = new DBManager(context);
        }
        return instance;
    }

    private void close() {
        if (db.isOpen()) {
            db.close();
        }
        if (helper != null) {
            helper.close();
        }
        if (instance != null) {
            instance = null;
        }
    }

    /**
     * 查询添加的心情
     *
     * @return
     */
    public List<Mood> query() {
        Cursor cursor = db.query(DBConstants.TABLE_NAME, null, null, null, null, null, "_id desc");
        List<Mood> list = new ArrayList<>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            Mood mood = new Mood();
            mood.setId(cursor.getInt(cursor.getColumnIndex("_id")));
            mood.setAddtime(cursor.getLong(cursor.getColumnIndex("addtime")));
            mood.setContent(cursor.getString(cursor.getColumnIndex("content")));
            mood.setType(cursor.getString(cursor.getColumnIndex("type")));
            list.add(mood);
        }
        close();
        return list;
    }

    /**
     * 根据ID查询数据
     *
     * @param id
     * @return
     */
    public Mood queryById(String id) {
        Cursor cursor = db.query(DBConstants.TABLE_NAME, null, "_id = ?", new String[]{id}, null, null, "");
        Mood mood = new Mood();
        if (cursor != null) {
            cursor.moveToFirst();
            mood.setId(cursor.getInt(cursor.getColumnIndex("_id")));
            mood.setAddtime(cursor.getLong(cursor.getColumnIndex("addtime")));
            mood.setContent(cursor.getString(cursor.getColumnIndex("content")));
            mood.setType(cursor.getString(cursor.getColumnIndex("type")));
        }
        close();
        return mood;
    }

    /**
     * 新建或者更新数据	 * @param note
     */
    public void save(Mood mood) {
        if (mood.getId() != 0) {
            //更新
            ContentValues value = new ContentValues();
            value.put("addtime", mood.getAddtime());
            value.put("content", mood.getContent());
            value.put("type", mood.getType());
            db.update(DBConstants.TABLE_NAME, value, "_id=?", new String[]{mood.getId() + ""});
        } else {
            //插入数据
            ContentValues value = new ContentValues();
            value.put("addtime", mood.getAddtime());
            value.put("content", mood.getContent());
            value.put("type", mood.getType());
            db.insert(DBConstants.TABLE_NAME, "_id", value);
        }
        close();
    }


    /**
     * 删除
     *
     * @param mood
     */
    public void delete(Mood mood) {
        if (mood.getId() != 0) {
            db.delete(DBConstants.TABLE_NAME, "_id = ?", new String[]{mood.getId() + ""});
            close();
        } else {
            return;
        }
    }


}
