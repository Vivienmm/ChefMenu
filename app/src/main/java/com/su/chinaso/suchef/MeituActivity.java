package com.su.chinaso.suchef;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MeituActivity extends AppCompatActivity {


    @BindView(R.id.meituImg)
    ImageView meituImg;
    @BindView(R.id.picDesTv)
    TextView picDesTv;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x122:
                    Glide.with(MeituActivity.this).load(picUrl).into(meituImg);
                    picDesTv.setText(picDes);
                    handler.sendEmptyMessageDelayed(0x123, 3000);
                    break;
                case 0x123:
                    Intent intent = new Intent(MeituActivity.this,
                            SplashActivity.class);
                    startActivity(intent);
                    finish();
                default:
                    break;
            }
        }
    };


    private String picUrl;
    private String picDes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meitu);
        ButterKnife.bind(this);

        Typeface typeFace = Typeface.createFromAsset(this.getAssets(), "fonts/FZSTK.TTF");
        picDesTv.setTypeface(typeFace);

        startLoadImg();
    }

    private void startLoadImg() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Document doc = Jsoup.connect("http://www.nationalgeographic.com.cn/photography/photo_of_the_day/").get();

                    Elements els = doc.select("div.showImg-list");
                    Elements pics = els.get(0).getElementsByTag("a");
                    Elements href = pics.get(0).getElementsByTag("img");
                    System.out.println("list--" + href.get(0).attr("src"));
                    picUrl = href.get(0).attr("src");
                    picDes = href.get(0).attr("alt");
                    Message msgMessage = new Message();
                    msgMessage.what = 0x122;
                    handler.sendMessage(msgMessage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
