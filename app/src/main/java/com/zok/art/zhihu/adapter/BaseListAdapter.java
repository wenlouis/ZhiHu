package com.zok.art.zhihu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.zok.art.zhihu.utils.CastUtil;

import java.util.List;

import butterknife.ButterKnife;

/**
 * @author 赵坤
 * @email artzok@163.com
 */
public abstract class BaseListAdapter<T> extends BaseAdapter {
    static final int ITEM_TYPE_NORMAL = 0;       // Plain item flag

    private Context mContext;
    private List<T> data;

    BaseListAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return data != null ? data.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return data != null ? data.get(position) : 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return ITEM_TYPE_NORMAL;
    }

    /**
     * Just use to inflate layout.
     *
     * @return Context of a activity
     */
    Context getContext() {
        return mContext;
    }

    /**
     * Set data set and refresh UI.
     *
     * @param data Data set.
     */
    public void setDataAndRefresh(List<T> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseViewHolder holder;
        if (convertView == null) {
            int itemViewType = getItemViewType(position);
            int itemLayoutId = getItemLayoutId(itemViewType);
            convertView = LayoutInflater.from(getContext()).inflate(itemLayoutId, parent, false);
            holder = createViewHolder(itemViewType, convertView);
            convertView.setTag(holder);
            // import to subclass cache
            convertView = holder.itemView;
        }
        holder = CastUtil.cast(convertView.getTag());
        holder.fillData(data.get(position), position);
        return convertView;

    }

    /**
     * Child class must implement the method and return layout resource id
     * of item view according to {@code itemViewType}.
     *
     * @param itemViewType Item type.
     * @return Resource id of specific item type.
     */
    protected abstract int getItemLayoutId(int itemViewType);

    /**
     * Child class must implement the method, then create a instance of
     * {@code BaseRecyclerViewHolder} subclass and return according to {@code itemViewType}
     *
     * @param itemViewType Item type.
     * @param itemView     Item view object.
     * @return
     */
    protected abstract BaseViewHolder createViewHolder(int itemViewType, View itemView);

    abstract class BaseViewHolder {
        View itemView;

        BaseViewHolder(View itemView) {
            this.itemView = itemView;
            ButterKnife.bind(this, itemView);
        }

        public abstract void fillData(T data, int position);
    }
}
