package im.ldgd.com.myim;

import android.app.Application;
import android.widget.Toast;

import com.hyphenate.EMContactListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;

import im.ldgd.com.myim.model.Model;
import im.ldgd.com.myim.utils.LogUtil;

/**
 * Created by ldgd on 2017/9/18.
 */

public class IMApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

       /* MultiDex.install(this);
        EMOptions options = new EMOptions();
        options.setAcceptInvitationAlways(false);
        options.setAutoAcceptGroupInvitation(false);*/

       // 初始化EaseUI
        EMOptions options = new EMOptions();
         // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        // 设置需要同意后才能接受群邀请
        options.setAutoAcceptGroupInvitation(false);
        //初始化
        EMClient.getInstance().init(this, options);
         //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
//        EMClient.getInstance().setDebugMode(true);
//        EaseUI.getInstance().init(this,options);


        // 初始化数据模型层类
        Model.getInstance().init(this);


        // 注册一个联系人变化的监听
        EMClient.getInstance().contactManager().setContactListener(new MyContactListener());


    }


    /***
     * 好友变化listener
     *
     */
    public class MyContactListener implements EMContactListener {

        @Override
        public void onContactAdded(String username) {
            Toast.makeText(IMApplication.this, "xxxxxxxxxxxxxxxxxxxxonFriendRequestDeclined", Toast.LENGTH_SHORT).show();
            LogUtil.e("onContactAdded");
        }

        @Override
        public void onContactDeleted(String username) {
            Toast.makeText(IMApplication.this, "xxxxxxxxxxxxxxxxxxxxonFriendRequestDeclined", Toast.LENGTH_SHORT).show();
            LogUtil.e("onContactAdded");
        }

        @Override
        public void onContactInvited(String username, String reason) {
            Toast.makeText(IMApplication.this, "xxxxxxxxxxxxxxxxxxxxonFriendRequestDeclined", Toast.LENGTH_SHORT).show();
            LogUtil.e("onContactAdded");
        }

        @Override
        public void onFriendRequestAccepted(String username) {
            Toast.makeText(IMApplication.this, "xxxxxxxxxxxxxxxxxxxxonFriendRequestDeclined", Toast.LENGTH_SHORT).show();
            LogUtil.e("onContactAdded");
        }

        @Override
        public void onFriendRequestDeclined(String username) {
            Toast.makeText(IMApplication.this, "xxxxxxxxxxxxxxxxxxxxonFriendRequestDeclined", Toast.LENGTH_SHORT).show();
            LogUtil.e("onContactAdded");
        }
    }
}
