package im.ldgd.com.myim.model.db;

import android.content.Context;

import im.ldgd.com.myim.model.dao.ContactTableDao;
import im.ldgd.com.myim.model.dao.InviteTableDao;

/**
 * Created by ldgd on 2017/11/8.
 * 联系人和邀请信息表的操作类的管理类
 */

public class DBManager {

    private final DBHelper dbHelper;
    private final ContactTableDao contactTableDao;
    private final InviteTableDao inviteTableDao;

    public DBManager(Context context, String name) {

        // 创建数据库
        dbHelper = new DBHelper(context, name);


        // 创建数据库两张表的操作类
        contactTableDao = new ContactTableDao(dbHelper);
        inviteTableDao = new InviteTableDao(dbHelper);

    }

    /**
     * 获取联系人操作类对象
     *
     * @return
     */
    public ContactTableDao getContactTableDao() {
        return contactTableDao;
    }

    /**
     * 获取邀请信息表的操作类对象
     *
     * @return
     */
    public InviteTableDao getInviteTableDao() {
        return inviteTableDao;
    }

    /**
     * 关闭数据库的方法
     */
    public void close() {
        dbHelper.close();
    }
}
