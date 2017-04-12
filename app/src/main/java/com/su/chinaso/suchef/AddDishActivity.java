package com.su.chinaso.suchef;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.su.chinaso.suchef.dish.DishManageDao;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddDishActivity extends AppCompatActivity {

    @BindView(R.id.picBtn)
    Button picBtn;
    @BindView(R.id.camBtn)
    Button camBtn;
    @BindView(R.id.dishImg)
    ImageView dishImg;

    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 1;
    @BindView(R.id.nameETv)
    EditText nameETv;
    @BindView(R.id.priceETv)
    EditText priceETv;
    @BindView(R.id.ingreETv)
    EditText ingreETv;
    @BindView(R.id.saveDishBtn)
    Button saveDishBtn;
    private String dateName;
    private String filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dish);
        ButterKnife.bind(this);
        checkCameraPermision();
    }

    private void checkCameraPermision() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // 判断是否需要解释获取权限原因
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {
                Toast.makeText(AddDishActivity.this, "应用要求使用相机拍照", Toast.LENGTH_SHORT).show();
                // 需要向用户解释
            } else {
                // 无需解释，直接请求权限
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_CAMERA);
            }
        }
    }

    @OnClick(R.id.camBtn)
    public void camePic() {
        SimpleDateFormat sTimeFormat = new SimpleDateFormat("yyyy-MM-dd  hh:mm:ss");
        dateName = sTimeFormat.format(new Date());
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(Environment.getExternalStorageDirectory() + "/suchef");
        if (!file.exists()) {
            file.mkdirs();
        }
        intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
        String filename = dateName + ".jpg";
        filePath = Environment.getExternalStorageDirectory() + "/suchef/" + filename;
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(file, filename)));
        startActivityForResult(intent, 0);
    }

    @OnClick(R.id.picBtn)
    public void selectPic() {
        Intent picture = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(picture, 1);
    }

    @OnClick(R.id.saveDishBtn)
    public void onClick() {
        String name = nameETv.getText().toString();
        String price = priceETv.getText().toString();
        String ingredient = ingreETv.getText().toString();
        DishManageDao manageDao = new DishManageDao();
        manageDao.insertDish(name, price, filePath, ingredient);


        AddDishActivity.this.finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 0:

                    Bitmap bitmap = CommonUtils.getBitmap(this, filePath);
                    dishImg.setImageBitmap(bitmap);
                    break;
                case 1:
                    Bitmap sbitmap = BitmapFactory.decodeFile(CommonUtils.getRealFilePath(this, data.getData()));
                    filePath = CommonUtils.getRealFilePath(this, data.getData());
                    dishImg.setImageBitmap(sbitmap);
                    break;

            }
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
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
