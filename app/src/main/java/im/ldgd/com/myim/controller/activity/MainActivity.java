package im.ldgd.com.myim.controller.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import im.ldgd.com.myim.R;
import im.ldgd.com.myim.controller.fragment.ChatFragment;
import im.ldgd.com.myim.controller.fragment.ContactListFragment;
import im.ldgd.com.myim.controller.fragment.SettingFragment;

public class MainActivity extends FragmentActivity {

    private FrameLayout flMain;
    private RadioGroup rgMain;
    private RadioButton rbMainChat;
    private RadioButton rbMainContact;
    private RadioButton rbMainSetting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();

        initListener();

    }

    private void initListener() {
        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                Fragment fragment = null;
                switch (checkedId) {
                    case R.id.rb_main_chat:
                        fragment = new ChatFragment();
                        break;
                    case R.id.rb_main_contact:
                        fragment = new ContactListFragment();
                        break;
                    case R.id.rb_main_setting:
                        fragment = new SettingFragment();
                        break;
                }

                // 实现fragment切换的方法
                switchFragment(fragment);
            }

        });
    }


    private void switchFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fl_main, fragment).commit();

    }


    private void findViews() {
        flMain = (FrameLayout) findViewById(R.id.fl_main);
        rgMain = (RadioGroup) findViewById(R.id.rg_main);
        rbMainChat = (RadioButton) findViewById(R.id.rb_main_chat);
        rbMainContact = (RadioButton) findViewById(R.id.rb_main_contact);
        rbMainSetting = (RadioButton) findViewById(R.id.rb_main_setting);

    }


}
