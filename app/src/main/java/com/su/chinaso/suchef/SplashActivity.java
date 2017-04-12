package com.su.chinaso.suchef;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.animation_view)
    LottieAnimationView animationView;

    private static final int MY_PERMISSIONS_REQUEST_STORAGE = 2;
    @BindView(R.id.takeOrderTv)
    TextView takeOrderTv;
    @BindView(R.id.newDishTv)
    TextView newDishTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        checkStoragePermision();


    }

    @OnClick({R.id.takeOrderTv, R.id.newDishTv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.takeOrderTv:
                Intent intent0 = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent0);
                break;
            case R.id.newDishTv:
                Intent intent1 = new Intent(SplashActivity.this, AddDishActivity.class);
                startActivity(intent1);
                break;
        }
    }

    private void checkStoragePermision() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // 判断是否需要解释获取权限原因
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {
                Toast.makeText(SplashActivity.this, "应用要求使用相机拍照", Toast.LENGTH_SHORT).show();
                // 需要向用户解释
                // 此处可以弹窗或用其他方式向用户解释需要该权限的原因
            } else {
                // 无需解释，直接请求权限
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_STORAGE);
                // MY_PERMISSIONS_REQUEST_READ_CONTACTS 是自定义的常量，在回调方法中可以获取到
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_STORAGE: {
                //如果请求被取消，那么 result 数组将为空
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 已经获取对应权限
                    // TODO 执行相应的操作联系人的方法
                } else {
                    // 未获取到授权，取消需要该权限的方法
                }
                return;
            }
            // 检查其他权限....
        }
    }


}
