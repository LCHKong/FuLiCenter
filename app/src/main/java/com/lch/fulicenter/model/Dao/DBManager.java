package com.lch.fulicenter.model.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lch.fulicenter.model.bean.User;
import com.lch.fulicenter.model.utils.L;
import com.lch.fulicenter.model.utils.SharePrefrenceUtils;

/**
 * Created by LCH on 2017/1/17.
 */

public class DBManager {
    private static final String TAG = DBManager.class.getSimpleName();
    private static DBOpenHelper dbHelper;
    static DBManager dbMgr = new DBManager();

    public DBManager() {

    }

    public static void onInit(Context context) {
        dbHelper = new DBOpenHelper(context);
    }

    public synchronized static DBManager getInstance() {
        if (dbHelper == null) {
            L.e(TAG, "没有调用onInit（）");
        }
        return dbMgr;
    }

    public boolean saveUser(User user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            ContentValues values = new ContentValues();
            values.put(UserDao.USER_COLUME_NAME, user.getMuserName());
            values.put(UserDao.USER_COLUME_NICK, user.getMuserNick());
            values.put(UserDao.USER_COLUME_AVATAR, user.getMavatarId());
            values.put(UserDao.USER_COLUME_AVATAR_PATH, user.getMavatarPath());
            values.put(UserDao.USER_COLUME_AVATAR_TYPE, user.getMavatarType());
            values.put(UserDao.USER_COLUME_AVATAR_SUFFIX, user.getMavatarSuffix());
            values.put(UserDao.USER_COLUME_AVATAR_UPDATE_TIME, user.getMavatarLastUpdateTime());
            return db.replace(UserDao.USER_TABLE_NAME, null, values) != -1;
        }
        return false;
    }

    public User getUser(String userName) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "SELECT * FROM " + UserDao.USER_TABLE_NAME
                + " WHERE " + UserDao.USER_COLUME_NAME + "=?";
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery(sql, new String[]{userName});
            if (cursor.moveToNext()) {
                User user = new User();
                user.setMuserName(userName);
                user.setMuserNick(cursor.getString(cursor.getColumnIndex(UserDao.USER_COLUME_NICK)));
                user.setMavatarId(cursor.getInt(cursor.getColumnIndex(UserDao.USER_COLUME_AVATAR)));
                user.setMavatarPath(cursor.getString(cursor.getColumnIndex(UserDao.USER_COLUME_AVATAR_PATH)));
                user.setMavatarType(cursor.getInt(cursor.getColumnIndex(UserDao.USER_COLUME_AVATAR_TYPE)));
                user.setMavatarSuffix(cursor.getString(cursor.getColumnIndex(UserDao.USER_COLUME_AVATAR_SUFFIX)));
                user.setMavatarLastUpdateTime(cursor.getString(cursor.getColumnIndex(UserDao.USER_COLUME_AVATAR_UPDATE_TIME)));
                return user;
            }
        }
        return null;
    }
}
