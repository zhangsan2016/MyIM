package im.ldgd.com.myim.controller.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import im.ldgd.com.myim.R;
import im.ldgd.com.myim.controller.activity.LoginActivity;
import im.ldgd.com.myim.model.Model;

/**
 * Created by ldgd on 2017/9/27.
 */

public class SettingFragment extends Fragment {

    private Button bt_setting_out;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_setting, null);


        initData(view);

        return view;
    }

    private void initData(View view) {
        bt_setting_out = (Button) view.findViewById(R.id.bt_setting_out);
        bt_setting_out.setText("退出登录（" + EMClient.getInstance().getCurrentUser() + ")");
        bt_setting_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
                    @Override
                    public void run() {
                        EMClient.getInstance().logout(false, new EMCallBack() {

                            @Override
                            public void onSuccess() {

                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        // Toast提示退出
                                        Toast.makeText(getActivity(),"退出成功！",Toast.LENGTH_SHORT).show();

                                        // 回到登录页面
                                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                                        startActivity(intent);

                                        // 关闭当前界面
                                        getActivity().finish();

                                    }
                                });


                            }

                            @Override
                            public void onError(int i, String s) {
                                // Toast提示
                                Toast.makeText(getActivity(),"退出失败！",Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onProgress(int i, String s) {

                            }
                        });
                    }
                });

            }
        });

    }

}
