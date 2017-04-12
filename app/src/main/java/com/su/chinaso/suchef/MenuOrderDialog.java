package com.su.chinaso.suchef;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.su.chinaso.suchef.dish.DishEntity;
import com.su.chinaso.suchef.view.DividerGridItemDecoration;
import com.su.chinaso.suchef.view.OrderMenuAdapter;

import java.util.List;

/**
 * Created by chinaso on 2017/3/21.
 */

public class MenuOrderDialog extends Dialog {
    public MenuOrderDialog(Context context) {
        super(context);
    }

    public MenuOrderDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private Context context;
        private String title;
        private String negativeButtonText;
        private String positiveButtonText;
        private List<DishEntity> datas;
        private OnClickListener negativeButtonClickListener;
        private OnClickListener positiveButtonClickListener;
        private OrderMenuAdapter orderMenuAdapter = null;
        private RecyclerView myRecycler;
        private View layout;
        private MenuOrderDialog dialog;

        public Builder(Context context) {
            this.context = context;
        }


        public Builder setDatas(List<DishEntity> datas) {
            this.datas = datas;
            return this;
        }


        /**
         * Set the Dialog title from resource
         *
         * @param title
         * @return
         */
        public Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }

        /**
         * Set the Dialog title from String
         *
         * @param title
         * @return
         */

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setNegativeButton(int negativeButtonText,
                                         OnClickListener listener) {
            this.negativeButtonText = (String) context
                    .getText(negativeButtonText);
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(String negativeButtonText,
                                         OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }


        public Builder setPositiveButton(int positiveButtonText,
                                         OnClickListener listener) {
            this.positiveButtonText = (String) context
                    .getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setPositiveButton(String positiveButtonText,
                                         OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }


        public MenuOrderDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            dialog = new MenuOrderDialog(context, R.style.dialog);
            layout = inflater.inflate(R.layout.menu_order_dialog, null);
            dialog.addContentView(layout, new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            ((TextView) layout.findViewById(R.id.title)).setText(title);
            myRecycler = (RecyclerView) layout.findViewById(R.id.dishList);


            initRecycler();
            initButton();

            dialog.setContentView(layout);
            dialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
            return dialog;
        }

        private void initButton() {

            // set the cancel button
            if (negativeButtonText != null) {
                if (negativeButtonClickListener != null) {
                    layout.findViewById(R.id.closeBtn)
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    negativeButtonClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_NEGATIVE);
                                }
                            });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.closeBtn).setVisibility(
                        View.GONE);
            }

            if (positiveButtonText != null) {
                if (positiveButtonClickListener != null) {
                    layout.findViewById(R.id.confirmBtn)
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    positiveButtonClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_NEGATIVE);
                                }
                            });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.confirmBtn).setVisibility(
                        View.GONE);
            }
        }

        private void initRecycler() {
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            myRecycler.setLayoutManager(layoutManager);
            myRecycler.addItemDecoration(new DividerGridItemDecoration(context));


            //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
            myRecycler.setHasFixedSize(true);
            //创建并设置Adapter
            orderMenuAdapter = new OrderMenuAdapter(context, datas);
            myRecycler.setAdapter(orderMenuAdapter);
            //设置增加或删除条目的动画
            myRecycler.setItemAnimator(new DefaultItemAnimator());

            orderMenuAdapter.setOnItemClickListener(new OrderMenuAdapter.OnItemClickListener() {
                @Override
                public void onClick(int position) {
                    Toast.makeText(context, "onClick事件       您点击了第：" + position + "个Item", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onLongClick(int position) {
                    Toast.makeText(context, "onLongClick事件       您点击了第：" + position + "个Item", Toast.LENGTH_SHORT).show();

                }
            });
        }

    }
}
