package im.ldgd.com.myim.controller.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import im.ldgd.com.myim.R;
import im.ldgd.com.myim.model.Model;
import im.ldgd.com.myim.model.bean.UserInfo;
import im.ldgd.com.myim.utils.LogUtil;

/**
 * Created by ldgd on 2017/9/29.
 */
public class AddContactActivity extends Activity implements View.OnClickListener {
    private EditText etAddName;
    private ImageView ivAddPhoto;
    private TextView tvAddName;
    private Button btAddAdd;
    private UserInfo userInfo;
    private TextView tvAddFind;
    private RelativeLayout rlAdd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_add_contact);

        findViews();

    }

    private void findViews() {
        tvAddFind = (TextView) this.findViewById(R.id.tv_add_find);
        etAddName = (EditText) findViewById(R.id.et_add_name);
        rlAdd = (RelativeLayout) findViewById(R.id.rl_add);
        ivAddPhoto = (ImageView) findViewById(R.id.iv_add_photo);
        tvAddName = (TextView) findViewById(R.id.tv_add_name);
        btAddAdd = (Button) findViewById(R.id.bt_add_add);

        btAddAdd.setOnClickListener(this);
        tvAddFind.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == btAddAdd) {

            // 添加好友
            addFriend();


        } else if (v == tvAddFind) {

            // 查找用户
            find();
        }
    }

    /**
     * 添加好友
     */
    private void addFriend() {
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {

                // 去环信服务器添加好友
                try {
             //       EMClient.getInstance().contactManager().addContact(userInfo.getName(), "添加好友");
                    //参数为要添加的好友的username和添加理由
                    EMClient.getInstance().contactManager().addContact(userInfo.getName(), "添加好友");
                    LogUtil.e("userInfo.getName() = " + userInfo.getName());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(AddContactActivity.this, "发送添加好友邀请成功", Toast.LENGTH_SHORT).show();
                        }
                    });

                } catch (final HyphenateException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(AddContactActivity.this, "发送添加好友邀请失败" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }


    /**
     * 查找按钮的处理
     */
    private void find() {
        // 获取name
        final String name = etAddName.getText().toString().trim();

        // 校验输入的名称
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(AddContactActivity.this, "输入的用户名称不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }

        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                // 去服务器判断当前用户是否存在
                userInfo = new UserInfo(name);

                // 更新UI
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        rlAdd.setVisibility(View.VISIBLE);
                        tvAddName.setText(userInfo.getName());
                    }
                });


            }
        });

    }

}
