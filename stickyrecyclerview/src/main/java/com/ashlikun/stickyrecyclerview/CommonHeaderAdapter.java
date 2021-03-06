package com.ashlikun.stickyrecyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author　　: 李坤
 * 创建时间: 2018/8/31 9:27
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：粘性头部的适配器
 * 1:默认判断是否是头部用数据的hashCode判断
 */

public abstract class CommonHeaderAdapter<T> implements StickyHeadersAdapter<CommonHeaderAdapter.StickyViewHolder> {
    protected Context mContext;
    protected int mLayoutId;
    protected List<T> mDatas;

    public CommonHeaderAdapter(Context context, int layoutId, List<T> datas) {
        mContext = context;
        mLayoutId = layoutId;
        mDatas = datas;
    }

    public void setDatas(List<T> mDatas) {
        this.mDatas = mDatas;
    }

    @Override
    public StickyViewHolder onCreateViewHolder(ViewGroup parent) {
        StickyViewHolder viewHolder = StickyViewHolder.get(mContext, parent, mLayoutId);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(StickyViewHolder headerViewHolder, int position) {
        headerViewHolder.updatePosition(position);
        convert(headerViewHolder, mDatas.size() > position ? mDatas.get(position) : null);
    }

    public abstract void convert(StickyViewHolder holder, T t);


    public static class StickyViewHolder extends RecyclerView.ViewHolder {
        private SparseArray<View> mViews;
        private int mPosition;
        private Context mContext;
        private int mLayoutId;

        public StickyViewHolder(Context context, View itemView, int position) {
            super(itemView);
            mContext = context;
            mPosition = position;
            mViews = new SparseArray();
            itemView.setTag(itemView.getId(), this);
        }


        public static StickyViewHolder get(Context context, ViewGroup parent, int layoutId) {
            View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
            StickyViewHolder holder = new StickyViewHolder(context, itemView, -1);
            holder.mLayoutId = layoutId;
            return holder;
        }

        /**
         * 通过viewId获取控件
         *
         * @param viewId
         * @return
         */
        public <T extends View> T getView(int viewId) {
            View view = mViews.get(viewId);
            if (view == null) {
                view = itemView.findViewById(viewId);
                mViews.put(viewId, view);
            }
            return (T) view;
        }


        public void updatePosition(int position) {
            mPosition = position;
        }

        public int getPositionInterior() {
            return mPosition;
        }

        public int getLayoutId() {
            return mLayoutId;
        }
    }

}
