package com.lch.fulicenter.controller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import com.lch.fulicenter.R;
import com.lch.fulicenter.model.bean.CategoryChildBean;
import com.lch.fulicenter.model.bean.CategoryGroupBean;

import java.util.ArrayList;

/**
 * Created by LCH on 2017/1/13.
 */

public class CategoryAdapter extends BaseExpandableListAdapter {
    Context mContext;
    ArrayList<CategoryGroupBean> mGroupBean;
    ArrayList<ArrayList<CategoryChildBean>> mChildBean;

    public CategoryAdapter(Context Context, ArrayList<ArrayList<CategoryChildBean>> ChildBean,
                           ArrayList<CategoryGroupBean> GroupBean) {
        this.mContext = Context;
        this.mGroupBean = new ArrayList<>();
        mGroupBean.addAll(GroupBean);
        this.mChildBean = new ArrayList<>();
        mChildBean.addAll(ChildBean);
    }

    @Override
    public int getGroupCount() {
        return mGroupBean != null ? mGroupBean.size() : 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mChildBean != null && mChildBean.get(groupPosition) != null
                ? mChildBean.get(groupPosition).size() : 0;
    }

    @Override
    public CategoryGroupBean getGroup(int groupPosition) {
        return mGroupBean.get(groupPosition);
    }

    @Override
    public CategoryChildBean getChild(int groupPosition, int childPosition) {
        if (mChildBean != null && mChildBean.get(groupPosition) != null) {
            return mChildBean.get(groupPosition).get(childPosition);
        }
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View layout = LayoutInflater.from(mContext).inflate(R.layout.item_category_group, null);

        return layout;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View layout = LayoutInflater.from(mContext).inflate(R.layout.item_category_child, null);

        return layout;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
