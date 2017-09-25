package im.ldgd.com.myim.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import im.ldgd.com.myim.R;
import im.ldgd.com.myim.model.Model;
import im.ldgd.com.myim.model.UserInfo;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etLoginName;
    private EditText etLoginPwd;
    private Button btLoginRegist;
    private Button btLoginLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        // 初始化控件
        initView();

/*        UserAccountDB userAccountDB = new UserAccountDB(this);
        userAccountDB.getWritableDatabase();*/


    }

    private void initView() {

        etLoginName = (EditText) findViewById(R.id.et_login_name);
        etLoginPwd = (EditText) findViewById(R.id.et_login_pwd);
        btLoginRegist = (Button) findViewById(R.id.bt_login_regist);
        btLoginLogin = (Button) findViewById(R.id.bt_login_login);

        btLoginRegist.setOnClickListener(this);
        btLoginLogin.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == btLoginRegist) {
            regist();
        } else if (v == btLoginLogin) {
            login();
        }
    }


    private void login() {

        // 判断帐号密码是否为空
        final String loginName = etLoginName.getText().toString();
        final String loginPwd = etLoginPwd.getText().toString();
        if (TextUtils.isEmpty(loginName) || TextUtils.isEmpty(loginPwd)) {
            showToast("用户名密码不能为空！");
            return;
        }

        // 登录逻辑处理
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {

                EMClient.getInstance().login(loginName, loginPwd, new EMCallBack() {

                    // 登录成功后的处理
                    @Override
                    public void onSuccess() {

                        //  对模型层数据的处理
                        Model.getInstance().loginSuccess(new UserInfo(loginName));

                        // 保存用户账号信息到本地
                        Model.getInstance().getUserAccountDao().addAccount(new UserInfo(loginName));

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                // 提示登录成功
                                Toast.makeText(LoginActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();

                                // 跳转到主页面
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();

                            }
                        });

                    }

                    // 登录失败后的处理
                    @Override
                    public void onError(int i, String s) {
                        // 登录失败
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this, "登录失败！", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    // 登录过程中的处理
                    @Override
                    public void onProgress(int i, String s) {

                    }
                });


            }
        });
        // 关闭页面

    }


    private void regist() {
        // 判断帐号密码是否为空
        final String name = etLoginName.getText().toString();
        final String pwd = etLoginPwd.getText().toString();
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(pwd)) {
            showToast("用户名密码不能为空！");
            return;
        }
        // 校验帐号密码
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    // 环信服务器注册帐号
                    EMClient.getInstance().createAccount(name, pwd);

                    // 在主线程中更新UI
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showToast("注册成功！");
                        }
                    });
                } catch (final HyphenateException e) {
                    e.printStackTrace();

                    // 在主线程中更新UI
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showToast("注册失败！" + e.toString());
                        }
                    });
                }
            }
        });
        // 关闭页面
    }


    private void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }


}
