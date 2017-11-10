package im.ldgd.com.myim.controller.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hyphenate.easeui.ui.EaseContactListFragment;

import im.ldgd.com.myim.R;
import im.ldgd.com.myim.controller.activity.AddContactActivity;
import im.ldgd.com.myim.controller.activity.InviteActivity;
import im.ldgd.com.myim.utils.Constant;
import im.ldgd.com.myim.utils.LogUtil;
import im.ldgd.com.myim.utils.SpUtils;

/**
 * Created by ldgd on 2017/9/27.
 */

public class ContactListFragment extends EaseContactListFragment {

    private ImageView iv_contact_red;
    private LinearLayout ll_contact_invite;
    private LocalBroadcastManager mLBM;
    private BroadcastReceiver ContactInviteChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            LogUtil.e("新红点显示");
            // 更新红点显示
            iv_contact_red.setVisibility(View.VISIBLE);
            SpUtils.getInstance().save(SpUtils.IS_NEW_INVITE, true);

        }
    };

    @Override
    protected void initView() {
        super.initView();

        // 标题栏显示加号
        titleBar.setRightImageResource(R.drawable.em_add);

        // 添加头布局
        View headerView = View.inflate(getActivity(), R.layout.header_fragment_contact, null);
        listView.addHeaderView(headerView);

        // 获取红点对象
        iv_contact_red = (ImageView) headerView.findViewById(R.id.iv_contact_red);
        // 获取邀请信息条目的对象
        ll_contact_invite = (LinearLayout) headerView.findViewById(R.id.ll_contact_invite);


    }

    @Override
    protected void setUpView() {
        super.setUpView();

        titleBar.setRightLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddContactActivity.class);
                startActivity(intent);
            }
        });


        // 初始化红点显示状态
        boolean isNewInvite = SpUtils.getInstance().getBoolean(SpUtils.IS_NEW_INVITE, false);
        iv_contact_red.setVisibility(isNewInvite ? View.VISIBLE : View.GONE);

        // 设置点击事件
        ll_contact_invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 红点处理
                iv_contact_red.setVisibility(View.GONE);
                SpUtils.getInstance().save(SpUtils.IS_NEW_INVITE, false);

                // 跳转到练习人信息界面
                Intent intent = new Intent(getActivity(), InviteActivity.class);
                startActivity(intent);

            }
        });

        LogUtil.e( "注册广播");

        // 注册广播
        mLBM = LocalBroadcastManager.getInstance(getActivity());
        mLBM.registerReceiver(ContactInviteChangeReceiver, new IntentFilter(Constant.CONTACT_INVITE_CHANGED));

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 注销广播
        mLBM.unregisterReceiver(ContactInviteChangeReceiver);
    }
}
