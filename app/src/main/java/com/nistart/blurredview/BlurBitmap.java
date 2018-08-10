package com.nistart.blurredview;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.RequiresApi;

/**
 * @author Samuel
 * @time 2018/8/10 14:52
 * @describe 转换模糊图片类
 */
public class BlurBitmap {

    /**
     * 图片缩放比例
     */
    private static final float BITMAP_SCALE = 0.4f;

    /**
     * 最大的模糊度在0.0到25.0之间
     */
    private static final  float BLUR_RADIUS = 25f;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static Bitmap blur(Context context, Bitmap bitmap){
        //计算出图片缩小后的宽度
        int width = Math.round(bitmap.getWidth() * BITMAP_SCALE);
        int height = Math.round(bitmap.getHeight() * BITMAP_SCALE);

        //将缩小的图片作为预渲染的图片
        Bitmap inputBitMap = Bitmap.createScaledBitmap(bitmap,width,height,false);
        //创建一张渲染后的输入图片
        Bitmap outputBitmap = Bitmap.createBitmap(inputBitMap);
        //创建RenderScript内核对象
        RenderScript rs = RenderScript.create(context);
        //创建一个迷糊效果的RenderScript的工具对象
        ScriptIntrinsicBlur blurScript  = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        //由于RenderScript并没有使用Vm来分配内存，所以需要使用Allocation类来创建和分配内存
        //创建Allocation对象的时候其实内存是空的，需要使用copyTo()将数据填充进来
        Allocation tmpIn = Allocation.createFromBitmap(rs, inputBitMap);
        Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);

        //设置渲染的模糊程度，25f是最大的模糊度
        blurScript.setRadius(BLUR_RADIUS);
        //设置blurScript对象的输入内存
        blurScript.setInput(tmpIn);
        //将输出数据保存到输出内存中
        blurScript.forEach(tmpOut);
        //将数据填充到Allcation中
        tmpOut.copyTo(outputBitmap);
        return outputBitmap;
    }
}
