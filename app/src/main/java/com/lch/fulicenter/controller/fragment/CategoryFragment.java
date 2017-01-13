package com.lch.fulicenter.controller.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lch.fulicenter.R;
import com.lch.fulicenter.controller.adapter.CategoryAdapter;
import com.lch.fulicenter.model.bean.CategoryChildBean;
import com.lch.fulicenter.model.bean.CategoryGroupBean;
import com.lch.fulicenter.model.net.IModelNewCategory;
import com.lch.fulicenter.model.net.ModelCategory;
import com.lch.fulicenter.model.net.OnCompleteListener;
import com.lch.fulicenter.model.utils.ConvertUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment {
    IModelNewCategory model;
    CategoryAdapter mAdapter;
    ArrayList<CategoryGroupBean> mGroupBean = new ArrayList<>();
    ArrayList<ArrayList<CategoryChildBean>> mChildBean = new ArrayList<>();
    int groupCount;


    @BindView(R.id.elv_category)
    ExpandableListView elvCategory;
    @BindView(R.id.tv_nomore)
    TextView tvNomore;

    public CategoryFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_category, container, false);
        ButterKnife.bind(this, layout);
        mAdapter = new CategoryAdapter(getContext(), mChildBean, mGroupBean);
        elvCategory.setAdapter(mAdapter);
        initView(false);
        initData();
        return layout;
    }


    private void initData() {
        model = new ModelCategory();
        model.downData(getContext(), new OnCompleteListener<CategoryGroupBean[]>() {
            @Override
            public void onSuccess(CategoryGroupBean[] result) {
                if (result != null) {
                    initView(true);
                    ArrayList<CategoryGroupBean> list = ConvertUtils.array2List(result);
                    mGroupBean.addAll(list);
                    for (int i = 0; i < list.size(); i++) {
                        mChildBean.add(new ArrayList<CategoryChildBean>());
                        downloadChildData(list.get(i).getId(), i);
                    }
                } else {
                    initView(false);
                }
            }

            @Override
            public void onError(String error) {
                Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
            }
        });

    }

    // 下载小类
    private void downloadChildData(int id, final int index) {
        model.downData(getContext(), id, new OnCompleteListener<CategoryChildBean[]>() {
            @Override
            public void onSuccess(CategoryChildBean[] result) {
                groupCount++;
                if (result != null) {
                    ArrayList<CategoryChildBean> list = ConvertUtils.array2List(result);
                    mChildBean.set(index, list);
                }
                if (groupCount == mGroupBean.size()) {
                    mAdapter.initData(mGroupBean, mChildBean);
                }


            }

            @Override
            public void onError(String error) {
                Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView(boolean hasData) {
        tvNomore.setVisibility(hasData ? View.GONE : View.VISIBLE);
        elvCategory.setVisibility(hasData ? View.VISIBLE : View.GONE);
    }

}
