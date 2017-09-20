package im.ldgd.com.myim.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ldgd on 2017/9/20.
 */

public class UserAccountDB extends SQLiteOpenHelper {


    public UserAccountDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {

        super(context, "account.db", null, 1);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
