package im.ldgd.com.myim.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import im.ldgd.com.myim.model.bean.UserInfo;
import im.ldgd.com.myim.model.db.UserAccountDB;

/**
 * Created by ldgd on 2017/9/23.
 */

public class UserAccountDao {

    private final UserAccountDB mHelper;

    public UserAccountDao(Context context) {

        mHelper = new UserAccountDB(context);
    }

    // 添加数据到数据库
    public void addAccount(UserInfo user) {
        // 获取数据库对象
        SQLiteDatabase db = mHelper.getReadableDatabase();

        // 执行添加操作
        ContentValues values = new ContentValues();
        values.put(UserAccountTable.COL_HXID, user.getHxId());
        values.put(UserAccountTable.COL_NAME, user.getName());
        values.put(UserAccountTable.COL_NICK, user.getNick());
        values.put(UserAccountTable.COL_PHOTO, user.getPhoto());

        // replace 不想要重新添加，只是想将原有的数据进行更新
        db.replace(UserAccountTable.TAB_NAME, null, values);

    }


    /**
     * 更具环信id获取所有用户信息
     * @param hxId
     * @return
     */
    public UserInfo getAccountByHxId(String hxId) {
        // 获取数据库对象
        SQLiteDatabase db = mHelper.getReadableDatabase();

        // 执行查询语句
       // String sql = "select * from " + UserAccountTable.TAB_NAME + " where" + UserAccountTable.COL_HXID + "=?";
        String sql = "select * from " + UserAccountTable.TAB_NAME + " where " + UserAccountTable.COL_HXID + "=?";
        Cursor cursor = db.rawQuery(sql, new String[]{hxId});

        UserInfo userInfo = null;
        if (cursor.moveToNext()) {
            userInfo = new UserInfo();

            // 封装对象
            userInfo.setHxId(cursor.getString(cursor.getColumnIndex(UserAccountTable.COL_HXID)));
            userInfo.setName(cursor.getString(cursor.getColumnIndex(UserAccountTable.COL_NAME)));
            userInfo.setNick(cursor.getString(cursor.getColumnIndex(UserAccountTable.COL_NICK)));
            userInfo.setPhoto(cursor.getString(cursor.getColumnIndex(UserAccountTable.COL_PHOTO)));
        }

        // 关闭资源
        cursor.close();

        // 返回数据
        return userInfo;
    }

}





















