package com.su.chinaso.suchef.view;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.su.chinaso.suchef.R;
import com.su.chinaso.suchef.dish.DishEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chinaso on 2017/3/21.
 */


public class OrderMenuAdapter extends RecyclerView.Adapter<OrderMenuAdapter.MyViewHolder> {

    private List<DishEntity> mDatas;
    private Context mContext;
    private LayoutInflater inflater;
    private OnItemClickListener mOnItemClickListener;

    public OrderMenuAdapter(Context context, List<DishEntity> datas) {
        this.mContext = context;
        this.mDatas = datas;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getItemCount() {

        return mDatas.size();
    }

    //填充onCreateViewHolder方法返回的holder中的控件
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        Typeface typeFace = Typeface.createFromAsset(mContext.getAssets(),"fonts/FZSTK.TTF");
        holder.recyTv.setTypeface(typeFace);

        holder.recyTv.setText(mDatas.get(position).getName());
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onClick(position);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemClickListener.onLongClick(position);
                    return false;
                }
            });
        }
    }

    //重写onCreateViewHolder方法，返回一个自定义的ViewHolder
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.recycler_view, parent, false);
        MyViewHolder holder = new MyViewHolder(view);


        return holder;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.recyTv)
        TextView recyTv;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public void addData(int position,DishEntity entity) {
        mDatas.add(position,entity);
        notifyItemInserted(position);
        notifyItemRangeChanged(position, mDatas.size());
    }

    public void removeData(int position) {
        mDatas.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mDatas.size());
        // recyclerview.scrollToPosition(position);//recyclerview滚动到新加item处
    }

    public interface OnItemClickListener {
        void onClick(int position);

        void onLongClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }


}