package ca.com.androidcustomviews.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ca.com.androidcustomviews.R;

/**
 * Created by hp on 2018/7/10.
 */

public class ItemFragment extends Fragment {
    public static ItemFragment newInstance( String title){
        ItemFragment itemFragment = new ItemFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title",title);
        itemFragment.setArguments(bundle);
        return itemFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item,container,false);
        TextView tv_title = view.findViewById(R.id.tv_title);
        tv_title.setText(getArguments().getString("title"));
        return view;
    }
}
