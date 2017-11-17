package com.demo.slidingtabdemo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.demo.slidingtabdemo.R;
import com.demo.slidingtabdemo.base.BaseFragment;


public class EatFragment extends BaseFragment {

    private static final String DATA_NAME = "name";

    private String title = "";

    public static EatFragment newInstance(String title, int indicatorColor, int dividerColor) {

        EatFragment f = new EatFragment();
        f.setTitle(title);
        f.setIndicatorColor(indicatorColor);
        f.setDividerColor(dividerColor);

        //pass data
        Bundle args = new Bundle();
        args.putString(DATA_NAME, title);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //get data
        title = getArguments().getString(DATA_NAME);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //layout
        View view = inflater.inflate(R.layout.frg_common, container, false);

        //view
        TextView txtName = (TextView) view.findViewById(R.id.txtName);
        txtName.setText(title);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
