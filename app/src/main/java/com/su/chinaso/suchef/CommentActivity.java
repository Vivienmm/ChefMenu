package com.su.chinaso.suchef;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;

import com.su.chinaso.suchef.dish.CommentEntity;
import com.su.chinaso.suchef.dish.CommentManageDao;
import com.su.chinaso.suchef.view.CommentAdapter;
import com.su.chinaso.suchef.view.DividerGridItemDecoration;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CommentActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.commentEtv)
    EditText commentEtv;
    @BindView(R.id.saveDishBtn)
    Button saveDishBtn;
    @BindView(R.id.commentRec)
    RecyclerView myRecycler;
    private CommentAdapter mAdapter;

    private int dishId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        initIntent();

        initView();
    }

    private void initIntent() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        dishId = bundle.getInt("dishId");
    }

    private void initView() {
        CommentManageDao manageDao = new CommentManageDao();
        List<CommentEntity> listComments = manageDao.getDishComment(dishId);
        LinearLayoutManager lLayoutManager = new LinearLayoutManager(CommentActivity.this);
        myRecycler.setLayoutManager(lLayoutManager);
        myRecycler.addItemDecoration(new DividerGridItemDecoration(this));


        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        myRecycler.setHasFixedSize(true);
        //创建并设置Adapter
        mAdapter = new CommentAdapter(listComments);
        myRecycler.setAdapter(mAdapter);
        //设置增加或删除条目的动画
        myRecycler.setItemAnimator(new DefaultItemAnimator());
    }

    @OnClick(R.id.saveDishBtn)
    public void saveComment() {

        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);

        String comment = commentEtv.getText().toString();
        CommentManageDao manageDao = new CommentManageDao();
        manageDao.insertComment(dishId, comment, dateString);
        CommentActivity.this.finish();
    }
}
