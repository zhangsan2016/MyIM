package im.ldgd.com.myim.controller.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import im.ldgd.com.myim.R;

/**
 * Created by ldgd on 2017/9/27.
 */

public class ContactListFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_contact_list,null);

        //    Toast.makeText(getActivity(),"ContactListFragment",Toast.LENGTH_SHORT).show();
        return view;
    }
}
