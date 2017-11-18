package com.demo.slidingtabdemo.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.demo.slidingtabdemo.R;
import com.demo.slidingtabdemo.adapter.ItemAdapter;
import com.demo.slidingtabdemo.base.BaseFragment;
import com.demo.slidingtabdemo.entity.DataSet;
import com.demo.slidingtabdemo.listener.OnEventListner;
import com.demo.slidingtabdemo.listener.OnLoadMoreListener;
import com.demo.slidingtabdemo.utils.GetJsonData;


public class ShopFragment extends BaseFragment implements OnEventListner {

    private RecyclerView mRecyclerView;
    private View view;

    public static ShopFragment newInstance(String title, int indicatorColor, int dividerColor) {
        ShopFragment f = new ShopFragment();
        f.setTitle(title);
        f.setIndicatorColor(indicatorColor);
        f.setDividerColor(dividerColor);
        return f;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.frg_common, container, false);
        }

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mRecyclerView.setLayoutManager(layoutManager);
        GetJsonData getJsonData = new GetJsonData(getContext(), this);
        getJsonData.getJsonData(GetJsonData.TYPE_SHOP);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void getResponse(final DataSet dataSet) {
        final ItemAdapter itemAdapter = new ItemAdapter(getContext(), mRecyclerView, dataSet.datas);
        itemAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (dataSet.datas.size() <= 100) {
                    dataSet.datas.add(null);
                    itemAdapter.notifyItemInserted(dataSet.datas.size() - 1);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dataSet.datas.remove(dataSet.datas.size() - 1);
                            itemAdapter.notifyItemRemoved(dataSet.datas.size());

                            //Generating more data
                            int index = dataSet.datas.size();
                            int end = index + 5;
                            int j = 0;
                            for (int i = index; i < end; i++) {
                                dataSet.datas.add(dataSet.datas.get(j++));
                            }
                            itemAdapter.notifyDataSetChanged();
                            itemAdapter.setLoaded();
                        }
                    }, 2000);
                } else {
                    Toast.makeText(getActivity(), "Loading data completed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mRecyclerView.setAdapter(itemAdapter);
    }

    @Override
    public void getErrorResponse(VolleyError error) {
    }
}
