package im.ldgd.com.myim.controller.fragment;

import android.content.Intent;
import android.view.View;

import com.hyphenate.easeui.ui.EaseContactListFragment;

import im.ldgd.com.myim.R;
import im.ldgd.com.myim.controller.activity.AddContactActivity;

/**
 * Created by ldgd on 2017/9/27.
 */

public class ContactListFragment extends EaseContactListFragment {


    @Override
    protected void initView() {
        super.initView();

        // 标题栏显示加号
        titleBar.setRightImageResource(R.drawable.em_add);

        // 添加头布局
        View headerView = View.inflate(getActivity(), R.layout.header_fragment_contact, null);
        listView.addHeaderView(headerView);


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

    }







}
