//
// Source code recreated from canDraw .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.github.dxq.drawcontainer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.os.Build.VERSION;
import android.view.MotionEvent;
import android.view.View;


import java.util.Stack;
/**
 * 郑重声明：本源码均来自互联网，仅供个人欣赏、学习之用，
 * 版权归36氪产品发行公司所有，任何组织和个人不得公开传播或用于任何商业盈利用途，
 * 否则一切后果由该组织或个人承担。
 * 本人不承担任何法律及连带责任！请自觉于下载后24小时内删除
 *
 */
public class viewA extends View {
    private float a;
    private float b;
    private android.graphics.Path mPath;
    private Paint mPaint;
    private Matrix mMatrix;
    private android.graphics.Path mPath1;
    private int g = 1;
    private boolean editable = false;
    private Stack<DrawContainer.PathHistory> layerInfoStack;
    private float j;
    private float k;
    private float l = 1.0F;
    private OnClickListener mOnClickListener;

    public viewA(Context context) {
        super(context);
        this.init();
    }

    @SuppressLint({"NewApi"})
    private void init() {
        if(VERSION.SDK_INT >= 11) {
            this.setLayerType(1, (Paint)null);
        }

        this.mPaint = new Paint(1);
        this.mPaint.setColor(-65536);
        this.mPaint.setStyle(Style.STROKE);
        this.mPaint.setStrokeJoin(Join.ROUND);
        this.mPaint.setStrokeCap(Cap.ROUND);
        this.mPaint.setAntiAlias(true);
        if(this.getContext().getResources().getDisplayMetrics().widthPixels >= 2048) {
            this.mPaint.setStrokeWidth(4.0F);
        } else {
            this.mPaint.setStrokeWidth(2.0F);
        }

        this.mPaint.setPathEffect(new CornerPathEffect(80.0F));
        this.mPath1 = new android.graphics.Path();
        this.mMatrix = new Matrix();
    }

    private void a(Canvas var1, DrawContainer.PathHistory var2, DrawContainer.PathHistory var3, float var4) {
        if(var2 != null) {
            float[] var7 = new float[9];
            var3.mMatrix.getValues(var7);
            float[] var6 = new float[9];
            var2.mMatrix.getValues(var6);
            Stack var8 = var2.mDrawPathStack;
            var1.save();
            var1.scale(var7[0] * var4 / var3.mDegree, var7[0] * var4 / var3.mDegree, 0.0F, 0.0F);
            if(var8 != null) {
                for(int var5 = 0; var5 < var8.size(); ++var5) {
                    DrawContainer.DrawPath var9 = (DrawContainer.DrawPath)var8.get(var5);
                    if(var9.a) {
                        this.mMatrix.reset();
                        this.mPath1.reset();
                        this.mMatrix.postScale(1.0F / var6[0], 1.0F / var6[0], 0.0F, 0.0F);
                        var9.mPath.transform(this.mMatrix, this.mPath1);
                        if(!this.mPath1.isEmpty()) {
                            var1.drawPath(this.mPath1, this.mPaint);
                        }
                    }
                }
            }

            var1.restore();
        }

    }

    private void b(MotionEvent var1) {
        DrawContainer.PathHistory var2 = this.getLayerInfo();
        this.a = var1.getX() - (float)var2.c;
        this.b = var1.getY() - (float)var2.d;
        if(var2.mDrawPathStack == null) {
            var2.mDrawPathStack = new Stack();
        }

        this.mPath = new android.graphics.Path();
        var2.mDrawPathStack.add(new DrawContainer.DrawPath(this.mPath));
        this.mPath.moveTo(this.a, this.b);
    }

    private void c(MotionEvent var1) {
        DrawContainer.PathHistory var6 = this.getLayerInfo();
        float var3 = var1.getX() - (float)var6.c;
        float var2 = var1.getY() - (float)var6.d;
        float var4 = Math.abs(var3 - this.a);
        float var5 = Math.abs(var2 - this.b);
        if(var4 >= 3.0F || var5 >= 3.0F) {
            this.mPath.lineTo(var3, var2);
            this.a = var3;
            this.b = var2;
        }

        this.invalidate();
    }

    private void d(MotionEvent var1) {
        if(this.g == 1 && this.mOnClickListener != null) {
            this.mOnClickListener.onClick(this);
        }

        this.g = 1;
    }

    private DrawContainer.PathHistory getLayerInfo() {
        if(this.layerInfoStack.isEmpty()) {
            this.layerInfoStack.push(new DrawContainer.PathHistory(this.l));
        }

        return (DrawContainer.PathHistory)this.layerInfoStack.peek();
    }

    public void draw(Canvas var1, Stack<DrawContainer.PathHistory> var2, float var3) {
        if(var2 != null) {
            for(int var4 = 0; var4 < var2.size(); ++var4) {
                this.a(var1, (DrawContainer.PathHistory)var2.get(var4), (DrawContainer.PathHistory)var2.get(var2.size() - 1), var3);
            }
        }

    }

    public void a(MotionEvent var1) {
        switch(var1.getActionMasked()) {
        case 0:
            this.j = var1.getX();
            this.k = var1.getY();
            this.g = 1;
            break;
        case 1:
        case 3:
            this.d(var1);
            break;
        case 2:
            if(this.g == 1 && (var1.getX() - this.j) * (var1.getX() - this.j) + (var1.getY() - this.k) * (var1.getY() - this.k) > 25.0F) {
                if(this.editable) {
                    this.b(var1);
                }

                this.g = 2;
            }

            if(this.g == 2 && this.editable) {
                this.c(var1);
            }
        }

    }

    public Paint getDrawPaint() {
        return this.mPaint;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.draw(canvas, this.layerInfoStack, this.l);
    }

    @Override
    @SuppressLint({"ClickableViewAccessibility"})
    public boolean onTouchEvent(MotionEvent var1) {
        boolean var3 = true;
        boolean var2;
        switch(var1.getAction()) {
        case 0:
            this.j = var1.getX();
            this.k = var1.getY();
            this.g = 1;
            var2 = var3;
            break;
        case 1:
            if(this.g == 1 && this.mOnClickListener != null) {
                this.mOnClickListener.onClick(this);
            }

            this.g = 1;
            var2 = false;
            break;
        case 2:
            var2 = var3;
            if((var1.getX() - this.j) * (var1.getX() - this.j) + (var1.getY() - this.k) * (var1.getY() - this.k) > 25.0F) {
                this.g = 2;
                var2 = var3;
            }
            break;
        default:
            var2 = super.onTouchEvent(var1);
        }

        return var2;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public void setInitScale(float var1) {
        this.l = var1;
    }

    public void setLayerInfos(Stack<DrawContainer.PathHistory> layerInfoStack) {
        this.layerInfoStack = layerInfoStack;
        this.postInvalidate();
    }

    @Override
    public void setOnClickListener(OnClickListener var1) {
        this.setClickable(true);
        this.mOnClickListener = var1;
    }
}
