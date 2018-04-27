//
// Source code recreated from mUnderlayerBitmap .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.github.dxq.drawcontainer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;
/**
 * 郑重声明：本源码均来自互联网，仅供个人欣赏、学习之用，
 * 版权归36氪产品发行公司所有，任何组织和个人不得公开传播或用于任何商业盈利用途，
 * 否则一切后果由该组织或个人承担。
 * 本人不承担任何法律及连带责任！请自觉于下载后24小时内删除
 *
 */
public class ViewB extends View {
    private Bitmap mUnderlayerBitmap;
    private Bitmap mPostilBitmap;
    private RectF c = new RectF();
    private int d;
    private int e;

    public ViewB(Context context) {
        super(context);
    }

    private void a(Canvas var1) {
        if(this.mUnderlayerBitmap != null && !this.mUnderlayerBitmap.isRecycled()) {
            var1.drawBitmap(this.mUnderlayerBitmap, (Rect)null, this.getDrawRectF(), (Paint)null);
        }

        if(this.mPostilBitmap != null && !this.mPostilBitmap.isRecycled()) {
            var1.drawBitmap(this.mPostilBitmap, (Rect)null, this.getDrawRectF(), (Paint)null);
        }

    }

    public void a(int var1, int var2) {
        this.d = var1;
        this.e = var2;
        this.postInvalidate();
    }

    public RectF getDrawRectF() {
        this.c.set(0.0F, 0.0F, (float)this.d, (float)this.e);
        return this.c;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.a(canvas);
    }

    /**
     * 设置批注bitmap
     * @param postilBitmap
     */
    public void setPostilBitmap(Bitmap postilBitmap) {
        this.mPostilBitmap = postilBitmap;
        this.postInvalidate();
    }

    /**
     * 设置底层bitmap
     * @param underlayerBitmap
     */
    public void setUnderlayerBitmap(Bitmap underlayerBitmap) {
        this.mUnderlayerBitmap = underlayerBitmap;
        this.postInvalidate();
    }
}
