package im.ldgd.com.myim.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import im.ldgd.com.myim.model.dao.UserAccountTable;

/**
 * Created by ldgd on 2017/9/20.
 * 用户数据库
 */

public class UserAccountDB extends SQLiteOpenHelper {

    /**
     * 构造
     * @param context
     */
    public UserAccountDB(Context context) {

        super(context, "account.db", null, 1);

    }


    /**
     * 数据库创建的时候使用
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
       // 创建数据库表的语句
        db.execSQL(UserAccountTable.CREATE_TAB);

    }

    /**
     *  数据库更新的时候调用
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
