package com.su.chinaso.suchef;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.su.chinaso.suchef.dish.DishEntity;
import com.su.chinaso.suchef.dish.Menu;
import com.su.chinaso.suchef.view.DiscreteScrollViewOptions;
import com.su.chinaso.suchef.view.ShopAdapter;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DiscreteScrollView.CurrentItemChangeListener,
        View.OnClickListener {
    private List<DishEntity> data;
    private Menu shop;

    private TextView currentItemName;
    private TextView currentItemPrice;
    private ImageView rateItemButton;
    private DiscreteScrollView itemPicker;

    private List<DishEntity> orderList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        currentItemName = (TextView) findViewById(R.id.item_name);
        currentItemPrice = (TextView) findViewById(R.id.item_price);
        rateItemButton = (ImageView) findViewById(R.id.item_btn_rate);


        initMenu();
        itemPicker = (DiscreteScrollView) findViewById(R.id.item_picker);
        itemPicker.setCurrentItemChangeListener(this);
        itemPicker.setAdapter(new ShopAdapter(data));
        itemPicker.setItemTransitionTimeMillis(DiscreteScrollViewOptions.getTransitionTime());
        itemPicker.setItemTransformer(new ScaleTransformer.Builder()
                .setMinScale(0.8f)
                .build());

        if (data.size() > 0) {
            onItemChanged(data.get(0));
        }


        findViewById(R.id.item_btn_rate).setOnClickListener(this);
        findViewById(R.id.item_btn_buy).setOnClickListener(this);
        findViewById(R.id.item_btn_comment).setOnClickListener(this);

        findViewById(R.id.home).setOnClickListener(this);
        findViewById(R.id.btn_smooth_scroll).setOnClickListener(this);
        findViewById(R.id.btn_transition_time).setOnClickListener(this);
    }

    private void initMenu() {
        shop = Menu.get();
        data = shop.getData();
        for (int i = 0; i < data.size(); i++) {
            shop.setRated(data.get(i).getId(), false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_btn_rate:
                DishEntity current = data.get(itemPicker.getCurrentItem());

                if (!shop.isRated(current.getId())) {
                    orderList.add(current);
                }

                shop.setRated(current.getId(), !shop.isRated(current.getId()));
                changeRateButtonState(current);
                break;
            case R.id.home:
                finish();
                break;
            case R.id.btn_transition_time:
                DiscreteScrollViewOptions.configureTransitionTime(itemPicker);
                break;
            case R.id.btn_smooth_scroll:
                DiscreteScrollViewOptions.smoothScrollToUserSelectedPosition(itemPicker, v);
                break;
            case R.id.item_btn_buy:
                showIngredientSnackBar();
                break;
            case R.id.item_btn_comment:
                DishEntity current1 = data.get(itemPicker.getCurrentItem());
                int dishId = current1.getId();
                Intent intent = new Intent();
                intent.putExtra("dishId", dishId);
                intent.setClass(this, CommentActivity.class);
                startActivity(intent);
                break;
            default:
                showUnsupportedSnackBar();
                break;
        }
    }

    private void showIngredientSnackBar() {
        DishEntity current = data.get(itemPicker.getCurrentItem());
        String ingredient = current.getMajorIngredient();
        Snackbar.make(itemPicker, ingredient, Snackbar.LENGTH_LONG).show();
    }

    private void onItemChanged(DishEntity item) {
        currentItemName.setText(item.getName());
        currentItemPrice.setText(item.getPrice()+" $RMB ");
        changeRateButtonState(item);
    }

    private void changeRateButtonState(DishEntity item) {


        if (shop.isRated(item.getId())) {
            rateItemButton.setImageResource(R.mipmap.ic_star_black_24dp);
            rateItemButton.setColorFilter(ContextCompat.getColor(this, R.color.shopRatedStar));
        } else {
            rateItemButton.setImageResource(R.mipmap.ic_star_border_black_24dp);
            rateItemButton.setColorFilter(ContextCompat.getColor(this, R.color.shopSecondary));
        }
    }

    @Override
    public void onCurrentItemChanged(RecyclerView.ViewHolder viewHolder, int position) {
        onItemChanged(data.get(position));
    }

    private void showUnsupportedSnackBar() {
        Snackbar.make(itemPicker, R.string.msg_unsupported_op, Snackbar.LENGTH_SHORT).show();
    }


    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            showMenuDialog(orderList);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showMenuDialog(List<DishEntity> orderList) {
        final MenuOrderDialog.Builder builder = new MenuOrderDialog.Builder(this);
        builder.setDatas(orderList);
        builder.setTitle("菜单生成");
        builder.setNegativeButton("",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.setPositiveButton("",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }
}
