package com.mysiga.common.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


/**
 * view holder adapter{@link ViewHolder}
 *
 * @author wilson.wu
 */

public abstract class ViewHolderAdapter<VH extends ViewHolderAdapter.ViewHolder> extends BaseAdapter {
    public VH holder;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            int viewType = getItemViewType(position);
            holder = onCreateViewHolder(parent, viewType);
            if (holder == null) {
                throw new NullPointerException("holder may not be null");
            }
            convertView = holder.itemView;
            convertView.setTag(holder);
        } else {
            holder = (VH) convertView.getTag();
        }
        holder.bindView(position);
        return convertView;
    }

    /**
     * {@link ViewHolder}
     *
     * @param parent {@link ViewHolderAdapter#getView(int, View, ViewGroup)}
     * @return
     */
    public abstract VH onCreateViewHolder(ViewGroup parent, int viewType);

    /**
     * base ViewHolder
     */
    public static abstract class ViewHolder {

        public final View itemView;

        public ViewHolder(View itemView) {
            if (itemView == null) {
                throw new NullPointerException("itemView may not be null");
            }
            this.itemView = itemView;
        }

        /**
         * @param position {@link ViewHolderAdapter#getView(int, View, ViewGroup)}
         */
        public abstract void bindView(int position);
    }
}
