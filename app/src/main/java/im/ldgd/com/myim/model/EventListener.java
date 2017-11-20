package im.ldgd.com.myim.model;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.hyphenate.EMContactListener;
import com.hyphenate.chat.EMClient;

import im.ldgd.com.myim.model.bean.InvationInfo;
import im.ldgd.com.myim.model.bean.UserInfo;
import im.ldgd.com.myim.model.db.DBManager;
import im.ldgd.com.myim.utils.Constant;
import im.ldgd.com.myim.utils.LogUtil;
import im.ldgd.com.myim.utils.SpUtils;

/**
 * Created by ldgd on 2017/10/31.
 * 全局监听类
 */

public class EventListener {


    private Context mContext;
    private final LocalBroadcastManager mLBM;

    public EventListener(Context context) {

        mContext = context;

        // 创建一个发送广播的管理者对象
        mLBM = LocalBroadcastManager.getInstance(mContext);

        // 注册一个联系人变化的监听
        EMClient.getInstance().contactManager().setContactListener(emContactListener);

        // 注册一个群信息变化的监听
        // EMClient.getInstance().groupManager().addGroupChangeListener(eMGroupChangeListener);

    }


    /**
     * 注册一个联系人变化的监听
     */
    private final EMContactListener emContactListener = new EMContactListener() {

        /**
         * 联系人增加后执行的方法
         */
        @Override
        public void onContactAdded(String hxid) {
            // 数据更新
            Model.getInstance().getDbManager().getContactTableDao().saveContact(new UserInfo(hxid), true);

            // 发送邀请信息变化的广播
            mLBM.sendBroadcast(new Intent(Constant.CONTACT_INVITE_CHANGED));
        }

        /**
         * 联系人删除后执行的方法
         */
        @Override
        public void onContactDeleted(String hxid) {

            // 数据更新
            Model.getInstance().getDbManager().getContactTableDao().deleteContactByHxId(hxid);
            Model.getInstance().getDbManager().getInviteTableDao().removeInvitation(hxid);

            // 发送联系人变化的广播
            mLBM.sendBroadcast(new Intent(Constant.CONTACT_CHANGED));
        }

        /**
         * 接收到联系人的新邀请
         */
        @Override
        public void onContactInvited(String hxid, String reason) {
            LogUtil.e("接收到联系人的新邀请");

            // 数据库更新
            InvationInfo invationInfo = new InvationInfo();
            invationInfo.setUser(new UserInfo(hxid));
            invationInfo.setReason(reason);
            invationInfo.setStatus(InvationInfo.InvitationStatus.NEW_INVITE); // 新邀请

            DBManager db =Model.getInstance().getDbManager();
            db.getInviteTableDao();
            Model.getInstance().getDbManager().getInviteTableDao().addInvitation(invationInfo);

            // 修改红点SharedPreferences提示状态
            SpUtils.getInstance().save(SpUtils.IS_NEW_INVITE, true);

            // 发送邀请信息变化的广播
            mLBM.sendBroadcast(new Intent(Constant.CONTACT_INVITE_CHANGED));

        }

        /**
         * 别人同意了你的好友邀请
         */
        @Override
        public void onFriendRequestAccepted(String hxid) {
            // 数据库更新
            InvationInfo invitationInfo = new InvationInfo();
            invitationInfo.setUser(new UserInfo(hxid));
            invitationInfo.setStatus(InvationInfo.InvitationStatus.INVITE_ACCEPT_BY_PEER);// 别人同意了你的邀请

            Model.getInstance().getDbManager().getInviteTableDao().addInvitation(invitationInfo);

            // 红点的处理
            SpUtils.getInstance().save(SpUtils.IS_NEW_INVITE, true);

            // 发送邀请信息变化的广播
            mLBM.sendBroadcast(new Intent(Constant.CONTACT_INVITE_CHANGED));
        }

        /**
         * 别人拒绝了你好友邀请
         */
        @Override
        public void onFriendRequestDeclined(String hxid) {
            // 红点的处理
            SpUtils.getInstance().save(SpUtils.IS_NEW_INVITE, true);

            // 发送邀请信息变化的广播
            mLBM.sendBroadcast(new Intent(Constant.CONTACT_INVITE_CHANGED));
        }
    };


}
