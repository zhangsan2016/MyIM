package im.ldgd.com.myim.model.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import im.ldgd.com.myim.model.bean.UserInfo;
import im.ldgd.com.myim.model.db.DBHelper;

/**
 * Created by ldgd on 2017/10/21.
 * 联系人表操作类
 */

public class ContactTableDao {

    private DBHelper mHelper;

    public ContactTableDao(DBHelper mHelper) {
        this.mHelper = mHelper;
    }

    /**
     * 获取所有联系人
     *
     * @return
     */
    public List<UserInfo> getContacts() {

        //  获取数据库连接
        SQLiteDatabase db = mHelper.getReadableDatabase();

        // 执行查询语句
        String sql = "select * from " + ContactTable.TAB_NAME + " where " + ContactTable.COL_IS_CONTACT + "=1";
        Cursor cursor = db.rawQuery(sql, null);

        List<UserInfo> users = new ArrayList<>();

        while (cursor.moveToNext()) {
            UserInfo userInfo = new UserInfo();

            userInfo.setHxId(cursor.getString(cursor.getColumnIndex(ContactTable.COL_HXID)));
            userInfo.setName(cursor.getString(cursor.getColumnIndex(ContactTable.COL_NAME)));
            userInfo.setNick(cursor.getString(cursor.getColumnIndex(ContactTable.COL_NICK)));
            userInfo.setPhoto(cursor.getString(cursor.getColumnIndex(ContactTable.COL_PHOTO)));

            users.add(userInfo);

        }

        // 关闭资源
        cursor.close();

        // 返回信息
        return users;
    }


    /**
     * 通过环信id获取联系人单个信息
     *
     * @param hxId
     * @return
     */
    public UserInfo getContactByhxid(String hxId) {

        if (hxId == null) {
            return null;
        }

        // 获取数据库链接
        SQLiteDatabase db = mHelper.getReadableDatabase();

        // 执行查询语句
        String sql = "select * from " + ContactTable.TAB_NAME + " where " + ContactTable.COL_HXID + "= ?";
        Cursor cursor = db.rawQuery(sql, new String[]{hxId});

        UserInfo userInfo = null;
        if (cursor.moveToNext()) {
            userInfo = new UserInfo();

            userInfo.setHxId(cursor.getString(cursor.getColumnIndex(ContactTable.COL_HXID)));
            userInfo.setHxId(cursor.getString(cursor.getColumnIndex(ContactTable.COL_NAME)));
            userInfo.setHxId(cursor.getString(cursor.getColumnIndex(ContactTable.COL_NICK)));
            userInfo.setHxId(cursor.getString(cursor.getColumnIndex(ContactTable.COL_PHOTO)));

        }

        // 关闭资源
        cursor.close();

        // 返回数据
        return userInfo;
    }

    /**
     * 通过环信id获取多个用户联系人信息
     *
     * @return
     */
    public List<UserInfo> getContactsByHxId(List<String> hxIds) {

        if (hxIds == null || hxIds.size() <= 0) {
            return null;
        }

        List<UserInfo> contacts = new ArrayList<>();

        for (int i = 0; i < hxIds.size() ; i++) {
           UserInfo contact = getContactByhxid(hxIds.get(i));
            contacts.add(contact);
        }

        return contacts;
    }


    /**
     *  保存单个联系人
     */
    public void saveContact(UserInfo user,boolean isMyContact){

        if(user == null){
            return;
        }

        // 获取数据库连接
        SQLiteDatabase db =  mHelper.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(ContactTable.COL_HXID,user.getHxId());
        values.put(ContactTable.COL_NAME,user.getName());
        values.put(ContactTable.COL_NICK,user.getNick());
        values.put(ContactTable.COL_PHOTO,user.getPhoto());
        values.put(ContactTable.COL_IS_CONTACT,isMyContact ? 1: 0);

        db.replace(ContactTable.TAB_NAME,null,values);

    }

    /**
     * 保存联系人信息
     * @param contacts
     * @param isMyContact 是否是我的联系人
     */
    public void saveContacts(List<UserInfo> contacts ,boolean isMyContact){

        if(contacts == null && contacts.size() <= 0){
            return;
        }

        for (int i = 0; i < contacts.size() ; i++) {
            UserInfo contact = contacts.get(i);
            saveContact(contact,isMyContact);
        }

    }

    public  void  deleteContactByHxId(String hxId){

        if(hxId == null){
            return;
        }

        SQLiteDatabase db = mHelper.getReadableDatabase();

        db.delete(ContactTable.TAB_NAME,ContactTable.COL_HXID + " = ?",new String[]{hxId});
    }



}


















