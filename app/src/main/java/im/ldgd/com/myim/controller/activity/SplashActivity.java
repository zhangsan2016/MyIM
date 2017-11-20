package im.ldgd.com.myim.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.hyphenate.chat.EMClient;

import im.ldgd.com.myim.R;
import im.ldgd.com.myim.model.Model;
import im.ldgd.com.myim.model.bean.UserInfo;

public class SplashActivity extends AppCompatActivity {

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            // 如果当前Activit已经退出，不处理handler信息
            if (isFinishing()) {
                return;
            }

            // 判断进入主页面还是登录页面
            toMainOrLogin();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        // 发送2s种延迟消息
        handler.sendMessageDelayed(Message.obtain(), 2000);

    }

    // 判断进入主页面还是登录页面
    private void toMainOrLogin() {

        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {

                // 判断当前帐号是否已经登录过
                if (EMClient.getInstance().isLoggedInBefore()) {  // 登录过

                    // 获取当前登录用户信息
                    UserInfo account =  Model.getInstance().getUserAccountDao().getAccountByHxId(EMClient.getInstance().getCurrentUser());

                    if (account == null){
                        // 跳转到登录
                        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }else {
                        // 登录成功后的方法
                        Model.getInstance().loginSuccess(account);

                        // 跳转到主页面
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                    }



                } else {  // 没有登录过
                    // 跳转到登录
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                }

                // 关闭当前页面
                finish();

            }
        });

    }
}
