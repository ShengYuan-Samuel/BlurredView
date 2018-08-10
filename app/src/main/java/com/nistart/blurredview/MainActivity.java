package com.nistart.blurredview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    //原始的图片
    @BindView(R.id.normalImg)
    ImageView normalImg;
    //模糊的图片
    @BindView(R.id.blurryImg)
    ImageView blurryImg;
    //变化的eSeekBar
    @BindView(R.id.img_seekBar)
    SeekBar imgSeekBar;
    //要展示的文字
    @BindView(R.id.seekBarTv)
    TextView seekBarTv;
    //透明度
    private int mAlpha;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //正常加载的图片
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.aaa);
        //模糊的图片
        Bitmap blur = BlurBitmap.blur(this, bitmap);
        //设置正常的图片
        normalImg.setImageBitmap(blur);
        //设置模糊图片
        blurryImg.setImageBitmap(bitmap);
        initSeekBar();
    }
    /**
     * 处理seekbar滑动事件
     */
    private void initSeekBar(){
        imgSeekBar.setMax(100);
        imgSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                mAlpha = progress;
                blurryImg.setAlpha((int) (255 - mAlpha * 2.55));
                seekBarTv.setText(String.valueOf(mAlpha));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
