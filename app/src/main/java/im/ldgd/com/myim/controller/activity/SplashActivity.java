package im.ldgd.com.myim.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.hyphenate.chat.EMClient;

import im.ldgd.com.myim.R;

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
        setContentView(R.layout.activity_splash);

        // 发送2s种延迟消息
        handler.sendMessageDelayed(Message.obtain(), 2000);

    }

    // 判断进入主页面还是登录页面
    private void toMainOrLogin() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                // 判断当前帐号是否已经登录过
                if(EMClient.getInstance().isLoggedInBefore()){  // 登录过

                    // 获取登录用户信息

                    // 跳转到主页面
                    Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                    startActivity(intent);

                }else{  // 没有登录过
                    // 跳转到登录
                    Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
                    startActivity(intent);
                }

                // 关闭当前页面
                finish();

            }
        }.start();


    }
}
