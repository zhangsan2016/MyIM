package im.ldgd.com.myim.model.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import im.ldgd.com.myim.model.bean.GroupInfo;
import im.ldgd.com.myim.model.bean.InvationInfo;
import im.ldgd.com.myim.model.bean.UserInfo;
import im.ldgd.com.myim.model.db.DBHelper;

/**
 * Created by ldgd on 2017/10/23.
 * 邀请信息表的操作类
 */

public class InviteTableDao {

    private DBHelper mHelper;

    public InviteTableDao(DBHelper mHelper) {
        this.mHelper = mHelper;
    }

    /**
     * 添加邀请
     */
    public void addInvitation(InvationInfo invationInfo) {

        // 获取数据库链接
        SQLiteDatabase db = mHelper.getReadableDatabase();

        // 执行添加语句
        ContentValues values = new ContentValues();
        values.put(InviteTable.COL_REASON, invationInfo.getReason()); // 添加原因
        values.put(InviteTable.COL_STATUS, invationInfo.getStatus().ordinal()); // 状态(返回枚举类型的序数)

        UserInfo user = invationInfo.getUser();

        if (user != null) {  // 联系人
            values.put(InviteTable.COL_USRE_HXID, invationInfo.getUser().getHxId());
            values.put(InviteTable.COL_USER_NAME, invationInfo.getUser().getName());

        } else {  // 群组
            values.put(InviteTable.COL_GROUP_HXID, invationInfo.getGroup().getGroupId());
            values.put(InviteTable.COL_GROUP_NAME, invationInfo.getGroup().getGroupName());
            values.put(InviteTable.COL_USRE_HXID, invationInfo.getGroup().getInvatePerson()); // 邀请人
        }

        db.replace(InviteTable.TAB_NAME, null, values);

    }


    /**
     * 获取所有联系人
     *
     * @return
     */
    public List<InvationInfo> getInvitations() {

        // 获取数据库链接
        SQLiteDatabase db = mHelper.getReadableDatabase();

        // 执行查询语句
        String sql = "select * from " + InviteTable.TAB_NAME;
        Cursor cursor = db.rawQuery(sql, null);

        List<InvationInfo> invationInfos = new ArrayList<>();

        while (cursor.moveToNext()) {

            InvationInfo invationInfo = new InvationInfo();

            invationInfo.setReason(cursor.getString(cursor.getColumnIndex(InviteTable.COL_REASON)));
            invationInfo.setStatus(int2InviteStatus(cursor.getInt(cursor.getColumnIndex(InviteTable.COL_STATUS))));

            String groupId = cursor.getString(cursor.getColumnIndex(InviteTable.COL_GROUP_HXID));

            if (groupId == null) {  // 联系人邀请信息

                UserInfo userInfo = new UserInfo();

                userInfo.setHxId(cursor.getString(cursor.getColumnIndex(InviteTable.COL_USRE_HXID)));
                userInfo.setName(cursor.getString(cursor.getColumnIndex(InviteTable.COL_USER_NAME)));
                userInfo.setNick(cursor.getString(cursor.getColumnIndex(InviteTable.TAB_NAME)));

                invationInfo.setUser(userInfo);
            } else {  // 群组的邀请信息

                GroupInfo groupInfo = new GroupInfo();
                groupInfo.setGroupId(cursor.getString(cursor.getColumnIndex(InviteTable.COL_GROUP_HXID)));
                groupInfo.setGroupName(cursor.getString(cursor.getColumnIndex(InviteTable.COL_GROUP_NAME)));
                groupInfo.setInvatePerson(cursor.getString(cursor.getColumnIndex(InviteTable.COL_USRE_HXID)));

                invationInfo.setGroup(groupInfo);

            }

            invationInfos.add(invationInfo);

        }

        // 关闭资源
        cursor.close();

        // 返回数据
        return invationInfos;
    }


    /**
     * 删除邀请
     */
    public void removeInvitation(String hxId) {

        if (hxId == null) {
            return;
        }

        // 获取数据库链接
        SQLiteDatabase db = mHelper.getReadableDatabase();

        // 执行删除语句
        db.delete(InviteTable.TAB_NAME, InviteTable.COL_USRE_HXID + " = ?", new String[]{hxId});
    }


    /**
     * 更新邀请信息
     */
    public void updateInvitationStatus(InvationInfo.InvitationStatus invitationStatus, String hxId) {

        if (hxId == null) {
            return;
        }

        // 获取数据库链接
        SQLiteDatabase db = mHelper.getReadableDatabase();

        // 执行更新操作
        ContentValues values = new ContentValues();
        values.put(InviteTable.COL_STATUS, invitationStatus.ordinal());
        db.update(InviteTable.TAB_NAME, values, InviteTable.COL_USRE_HXID + " =?", new String[]{hxId});

    }

    private InvationInfo.InvitationStatus int2InviteStatus(int intStatus) {

        if (intStatus == InvationInfo.InvitationStatus.NEW_INVITE.ordinal()) {
            return InvationInfo.InvitationStatus.NEW_INVITE;
        }

        if (intStatus == InvationInfo.InvitationStatus.INVITE_ACCEPT.ordinal()) {
            return InvationInfo.InvitationStatus.INVITE_ACCEPT;
        }

        if (intStatus == InvationInfo.InvitationStatus.INVITE_ACCEPT_BY_PEER.ordinal()) {
            return InvationInfo.InvitationStatus.INVITE_ACCEPT_BY_PEER;
        }

        if (intStatus == InvationInfo.InvitationStatus.NEW_GROUP_INVITE.ordinal()) {
            return InvationInfo.InvitationStatus.NEW_GROUP_INVITE;
        }

        if (intStatus == InvationInfo.InvitationStatus.NEW_GROUP_APPLICATION.ordinal()) {
            return InvationInfo.InvitationStatus.NEW_GROUP_APPLICATION;
        }

        if (intStatus == InvationInfo.InvitationStatus.GROUP_INVITE_ACCEPTED.ordinal()) {
            return InvationInfo.InvitationStatus.GROUP_INVITE_ACCEPTED;
        }

        if (intStatus == InvationInfo.InvitationStatus.GROUP_APPLICATION_ACCEPTED.ordinal()) {
            return InvationInfo.InvitationStatus.GROUP_APPLICATION_ACCEPTED;
        }

        if (intStatus == InvationInfo.InvitationStatus.GROUP_INVITE_DECLINED.ordinal()) {
            return InvationInfo.InvitationStatus.GROUP_INVITE_DECLINED;
        }

        if (intStatus == InvationInfo.InvitationStatus.GROUP_APPLICATION_DECLINED.ordinal()) {
            return InvationInfo.InvitationStatus.GROUP_APPLICATION_DECLINED;
        }

        if (intStatus == InvationInfo.InvitationStatus.GROUP_ACCEPT_INVITE.ordinal()) {
            return InvationInfo.InvitationStatus.GROUP_ACCEPT_INVITE;
        }

        if (intStatus == InvationInfo.InvitationStatus.GROUP_ACCEPT_APPLICATION.ordinal()) {
            return InvationInfo.InvitationStatus.GROUP_ACCEPT_APPLICATION;
        }

        if (intStatus == InvationInfo.InvitationStatus.GROUP_REJECT_APPLICATION.ordinal()) {
            return InvationInfo.InvitationStatus.GROUP_REJECT_APPLICATION;
        }

        if (intStatus == InvationInfo.InvitationStatus.GROUP_REJECT_INVITE.ordinal()) {
            return InvationInfo.InvitationStatus.GROUP_REJECT_INVITE;
        }

        return null;
    }


}





























