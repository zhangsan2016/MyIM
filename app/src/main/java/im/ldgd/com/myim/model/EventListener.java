package im.ldgd.com.myim.model;

import android.content.Context;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import com.hyphenate.EMContactListener;

import im.ldgd.com.myim.utils.LogUtil;

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

        Toast.makeText(mContext, "xxxx", Toast.LENGTH_SHORT).show();
        // 注册一个联系人变化的监听
     //   EMClient.getInstance().contactManager().setContactListener(new MyContactListener());


        // 注册一个群信息变化的监听
        // EMClient.getInstance().groupManager().addGroupChangeListener(eMGroupChangeListener);

    }


    /**
     * 注册一个联系人变化的监听
     */
    private final EMContactListener emContactListener = new EMContactListener() {

        /**
         * 联系人增加后执行的方法
         * @param s
         */
        @Override
        public void onContactAdded(String s) {
            Toast.makeText(mContext, "xxxxxxxxxxxxxxxxxxxxxxxonContactAdded", Toast.LENGTH_SHORT).show();
            LogUtil.e("onContactAdded");

        }

        /**
         * 联系人删除后执行的方法
         * @param s
         */
        @Override
        public void onContactDeleted(String s) {
            Toast.makeText(mContext, "xxxxxxxxxxxxxxxxxxxxonContactDeleted", Toast.LENGTH_SHORT).show();
            LogUtil.e("onContactAdded");
        }

        /**
         * 接收到联系人的新邀请
         * @param s
         */
        @Override
        public void onContactInvited(String s, String s1) {
            Toast.makeText(mContext, "xxxxxxxxxxxxxxxxxxxxonContactInvited", Toast.LENGTH_SHORT).show();
            LogUtil.e("onContactAdded");
        }

        /**
         * 别人同意了你的好友邀请
         * @param s
         */
        @Override
        public void onFriendRequestAccepted(String s) {
            Toast.makeText(mContext, "xxxxxxxxxxxxxxxxxxxxonFriendRequestAccepted", Toast.LENGTH_SHORT).show();
            LogUtil.e("onContactAdded");
        }

        /**
         * 别人拒绝了你好友邀请
         * @param s
         */
        @Override
        public void onFriendRequestDeclined(String s) {
            Toast.makeText(mContext, "xxxxxxxxxxxxxxxxxxxxonFriendRequestDeclined", Toast.LENGTH_SHORT).show();
            LogUtil.e("onContactAdded");
        }
    };


    /***
     * 好友变化listener
     *
     */
    public class MyContactListener implements EMContactListener {

        @Override
        public void onContactAdded(String username) {
            Toast.makeText(mContext, "xxxxxxxxxxxxxxxxxxxxonFriendRequestDeclined", Toast.LENGTH_SHORT).show();
            LogUtil.e("onContactAdded");
        }

        @Override
        public void onContactDeleted(String username) {
            Toast.makeText(mContext, "xxxxxxxxxxxxxxxxxxxxonFriendRequestDeclined", Toast.LENGTH_SHORT).show();
            LogUtil.e("onContactAdded");
        }

        @Override
        public void onContactInvited(String username, String reason) {
            Toast.makeText(mContext, "xxxxxxxxxxxxxxxxxxxxonFriendRequestDeclined", Toast.LENGTH_SHORT).show();
            LogUtil.e("onContactAdded");
        }

        @Override
        public void onFriendRequestAccepted(String username) {
            Toast.makeText(mContext, "xxxxxxxxxxxxxxxxxxxxonFriendRequestDeclined", Toast.LENGTH_SHORT).show();
            LogUtil.e("onContactAdded");
        }

        @Override
        public void onFriendRequestDeclined(String username) {
            Toast.makeText(mContext, "xxxxxxxxxxxxxxxxxxxxonFriendRequestDeclined", Toast.LENGTH_SHORT).show();
            LogUtil.e("onContactAdded");
        }
    }


}
