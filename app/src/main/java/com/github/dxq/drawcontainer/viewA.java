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
    private float mMoveX;
    private float mMoveY;
    private android.graphics.Path mPath;
    private Paint mPaint;
    private Matrix mMatrix;
    private android.graphics.Path mPath1;
    private int g = 1;
    private boolean editable = false;
    private Stack<DrawContainer.PathHistory> layerInfoStack;
    private float x;
    private float y;
    private float scale = 1.0F;
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

    private void drawPath(Canvas var1, DrawContainer.PathHistory var2, DrawContainer.PathHistory var3, float var4) {
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

    /**
     * 移动时的处理
     * @param motionEvent
     */
    private void onMove(MotionEvent motionEvent) {
        DrawContainer.PathHistory var2 = this.getLayerInfo();
        this.mMoveX = motionEvent.getX() - (float)var2.leftMargin;
        this.mMoveY = motionEvent.getY() - (float)var2.topMargin;
        if(var2.mDrawPathStack == null) {
            var2.mDrawPathStack = new Stack();
        }

        this.mPath = new android.graphics.Path();
        var2.mDrawPathStack.add(new DrawContainer.DrawPath(this.mPath));
        this.mPath.moveTo(this.mMoveX, this.mMoveY);
    }

    /**
     * 线条移动绘制
     * @param motionEvent
     */
    private void moveLine(MotionEvent motionEvent) {
        DrawContainer.PathHistory pathHistory = this.getLayerInfo();
        float moveX = motionEvent.getX() - (float)pathHistory.leftMargin;
        float moveY = motionEvent.getY() - (float)pathHistory.topMargin;
        float distanceX = Math.abs(moveX - this.mMoveX);
        float distanceY = Math.abs(moveY - this.mMoveY);
        if(distanceX >= 3.0F || distanceY >= 3.0F) {
            this.mPath.lineTo(moveX, moveY);
            this.mMoveX = moveX;
            this.mMoveY = moveY;
        }

        this.invalidate();
    }

    private void onClick(MotionEvent var1) {
        if(this.g == 1 && this.mOnClickListener != null) {
            this.mOnClickListener.onClick(this);
        }

        this.g = 1;
    }

    private DrawContainer.PathHistory getLayerInfo() {
        if(this.layerInfoStack.isEmpty()) {
            this.layerInfoStack.push(new DrawContainer.PathHistory(this.scale));
        }

        return (DrawContainer.PathHistory)this.layerInfoStack.peek();
    }

    public void drawPaths(Canvas canvas, Stack<DrawContainer.PathHistory> pathHistories, float var3) {
        if(pathHistories != null) {
            for(int index = 0; index < pathHistories.size(); ++index) {
                this.drawPath(canvas, (DrawContainer.PathHistory)pathHistories.get(index), (DrawContainer.PathHistory)pathHistories.get(pathHistories.size() - 1), var3);
            }
        }

    }

    public void handleOnTouchEvent(MotionEvent motionEvent) {
        switch(motionEvent.getActionMasked()) {
        case MotionEvent.ACTION_DOWN:
            this.x = motionEvent.getX();
            this.y = motionEvent.getY();
            this.g = 1;
            break;
        case MotionEvent.ACTION_UP:
        case MotionEvent.ACTION_CANCEL:
            this.onClick(motionEvent);
            break;
        case MotionEvent.ACTION_MOVE:
            if(this.g == 1 && (motionEvent.getX() - this.x) * (motionEvent.getX() - this.x) + (motionEvent.getY() - this.y) * (motionEvent.getY() - this.y) > 25.0F) {
                if(this.editable) {
                    this.onMove(motionEvent);
                }

                this.g = 2;
            }

            if(this.g == 2 && this.editable) {
                this.moveLine(motionEvent);
            }
        }

    }

    public Paint getDrawPaint() {
        return this.mPaint;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.drawPaths(canvas, this.layerInfoStack, this.scale);
    }

    @Override
    @SuppressLint({"ClickableViewAccessibility"})
    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean defaultValue = true;
        boolean isConsume;
        switch(motionEvent.getAction()) {
        case MotionEvent.ACTION_DOWN:
            this.x = motionEvent.getX();
            this.y = motionEvent.getY();
            this.g = 1;
            isConsume = defaultValue;
            break;
        case MotionEvent.ACTION_UP:
            if(this.g == 1 && this.mOnClickListener != null) {
                this.mOnClickListener.onClick(this);
            }

            this.g = 1;
            isConsume = false;
            break;
        case MotionEvent.ACTION_MOVE:
            isConsume = defaultValue;
            if((motionEvent.getX() - this.x) * (motionEvent.getX() - this.x) + (motionEvent.getY() - this.y) * (motionEvent.getY() - this.y) > 25.0F) {
                this.g = 2;
                isConsume = defaultValue;
            }
            break;
        default:
            isConsume = super.onTouchEvent(motionEvent);
        }

        return isConsume;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public void setInitScale(float scale) {
        this.scale = scale;
    }

    public void setLayerInfos(Stack<DrawContainer.PathHistory> layerInfoStack) {
        this.layerInfoStack = layerInfoStack;
        this.postInvalidate();
    }

    @Override
    public void setOnClickListener(OnClickListener onClickListener) {
        this.setClickable(true);
        this.mOnClickListener = onClickListener;
    }
}
