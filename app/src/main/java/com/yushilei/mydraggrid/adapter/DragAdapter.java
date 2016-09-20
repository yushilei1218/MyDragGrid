package com.yushilei.mydraggrid.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yushilei.mydraggrid.R;
import com.yushilei.mydraggrid.bean.Bean;
import com.yushilei.mydraggrid.interf.PreDragListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author by  yushilei.
 * @time 2016/9/20 -14:29.
 * @Desc
 */
public class DragAdapter extends RecyclerView.Adapter<DragAdapter.ViewH> implements View.OnLongClickListener, View.OnClickListener {
    Context mContext;
    public List<Bean> mBeans = new ArrayList<>();

    PreDragListener mListener;
    public Bean draggedBean;

    public void setListener(PreDragListener listener) {
        mListener = listener;
    }

    public DragAdapter(Context context) {
        mContext = context;
        int[] rids = new int[]{R.mipmap.a1, R.mipmap.a2, R.mipmap.a3, R.mipmap.a4, R.mipmap.a5, R.mipmap.a6
                , R.mipmap.a7, R.mipmap.a8, R.mipmap.a9, R.mipmap.a10, R.mipmap.a11, R.mipmap.a12};
        for (int i = 0; i < 12; i++) {
            mBeans.add(new Bean("item+" + i, rids[i % rids.length]));
        }
    }

    @Override
    public ViewH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item, parent, false);
        ViewH tag = new ViewH(view);
        view.setTag(tag);
        return tag;
    }

    @Override
    public void onBindViewHolder(ViewH holder, int position) {
        Bean bean = mBeans.get(position);

        if (bean.isLongClicked()) {
            holder.icon.setVisibility(View.VISIBLE);
        } else {
            holder.icon.setVisibility(View.GONE);
        }
        if (bean.isDragged()) {
            holder.itemView.setVisibility(View.INVISIBLE);
        } else {
            holder.itemView.setVisibility(View.VISIBLE);
        }
        holder.img.setImageResource(bean.getRid());
        holder.tv.setText(bean.getStr());

        //***需要每个显示的itemView 与其所显示的数据Bean进行绑定
        holder.itemView.setTag(R.id.id_bean_, mBeans.get(position));

        holder.itemView.setOnLongClickListener(this);//长按
        holder.itemView.setOnClickListener(this);//短按
    }


    @Override
    public int getItemCount() {
        return mBeans.size();
    }

    @Override
    public boolean onLongClick(View v) {
        //**长按时 获取该itemView 所绑定的Bean
        draggedBean = (Bean) v.getTag(R.id.id_bean_);
        draggedBean.setIsDragged(true);
        //**通知 该Bean所在mBeans位置发生变化
        notifyItemChanged(mBeans.indexOf(draggedBean));
        //**回调 DragParent 对DragGridView事件进行拦截
        if (mListener != null) {
            mListener.preDrag(v);
        }
        return true;
    }

    /**
     * 对外提供 拖拽结束时方法，对应呗拖拽数据重置拖拽状态 并刷新
     */
    public void dragEnd() {
        int index = mBeans.indexOf(draggedBean);
        draggedBean.setIsDragged(false);
        notifyItemChanged(index);
        draggedBean = null;
    }

    /**
     * 对外方法 获取当前拖拽Bean位于 mBeans的位置
     */
    public int dragBeanIndex() {
        if (draggedBean != null) {
            return mBeans.indexOf(draggedBean);
        }
        return -1;
    }

    @Override
    public void onClick(View v) {
        String str = ((Bean) v.getTag(R.id.id_bean_)).getStr();
        Toast.makeText(mContext, str, Toast.LENGTH_SHORT).show();
    }

    public static class ViewH extends RecyclerView.ViewHolder {
        private View icon;
        private ImageView img;
        private TextView tv;

        public ViewH(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.item_icon);
            img = ((ImageView) itemView.findViewById(R.id.item_img));
            tv = ((TextView) itemView.findViewById(R.id.item_tv));
        }
    }
}
