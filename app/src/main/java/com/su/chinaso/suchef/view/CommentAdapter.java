package com.su.chinaso.suchef.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.su.chinaso.suchef.R;
import com.su.chinaso.suchef.dish.CommentEntity;

import java.util.List;

/**
 * Created by chinaso on 2017/4/12.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private List<CommentEntity> data;

    public CommentAdapter(List<CommentEntity> data) {
        this.data = data;
    }

    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_comment, parent, false);
        return new CommentAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CommentAdapter.ViewHolder holder, int position) {
        CommentEntity entity = data.get(position);
        holder.contentTv.setText(entity.getComment());
        holder.dateTv.setText(entity.getDate());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView contentTv;
        private TextView dateTv;

        public ViewHolder(View itemView) {
            super(itemView);
            contentTv = (TextView) itemView.findViewById(R.id.contentTv);
            dateTv = (TextView) itemView.findViewById(R.id.dateTv);
        }
    }
}
