package im.ldgd.com.myim.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import im.ldgd.com.myim.model.dao.ContactTable;
import im.ldgd.com.myim.model.dao.InviteTable;

/**
 * Created by ldgd on 2017/9/29.
 */

public class DBHelper extends SQLiteOpenHelper {


    public DBHelper(Context context, String name) {
        super(context, name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // 创建联系人表
        db.execSQL(ContactTable.CREATE_TAB);

        // 创建邀请人信息表
        db.execSQL(InviteTable.CREATE_TAB);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
















