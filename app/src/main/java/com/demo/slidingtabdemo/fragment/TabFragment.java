package com.demo.slidingtabdemo.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.demo.slidingtabdemo.R;
import com.demo.slidingtabdemo.adapter.TabFragmentPagerAdapter;
import com.demo.slidingtabdemo.base.BaseFragment;
import com.demo.slidingtabdemo.view.SlidingTabLayout;

import java.util.LinkedList;

public class TabFragment extends Fragment {

    private SlidingTabLayout tabs;
    private ViewPager pager;
    private FragmentPagerAdapter adapter;

    public static Fragment newInstance() {
        TabFragment f = new TabFragment();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frg_tab, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //adapter
        final LinkedList<BaseFragment> fragments = getFragments();
        adapter = new TabFragmentPagerAdapter(getFragmentManager(), fragments);
        //pager
        pager = (ViewPager) view.findViewById(R.id.pager);
        pager.setAdapter(adapter);
        //tabs
        tabs = (SlidingTabLayout) view.findViewById(R.id.tabs);
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {

            @Override
            public int getIndicatorColor(int position) {
                return fragments.get(position).getIndicatorColor();
            }

            @Override
            public int getDividerColor(int position) {
                return fragments.get(position).getDividerColor();
            }
        });
        // Set background as requested
        tabs.setBackgroundResource(R.color.colorGrey);
        // Set customTabView as requested.
        tabs.setCustomTabView(R.layout.tab_title, R.id.txtTabTitle);
        tabs.setViewPager(pager);

    }

    private LinkedList<BaseFragment> getFragments() {
        // Set indicator color as requested.
        int indicatorColor = ContextCompat.getColor(getActivity(), R.color.colorDeepBlue);
        // Set divider color as transparent as requested.
        int dividerColor = Color.TRANSPARENT;

        LinkedList<BaseFragment> fragments = new LinkedList<BaseFragment>();
        // Add fragment page here.
        fragments.add(CityGuideFragment.newInstance(getString(R.string.tab_city_guide), indicatorColor, dividerColor));
        fragments.add(ShopFragment.newInstance(getString(R.string.tab_shop), indicatorColor, dividerColor));
        fragments.add(EatFragment.newInstance(getString(R.string.tab_eat), indicatorColor, dividerColor));
        return fragments;
    }

}
