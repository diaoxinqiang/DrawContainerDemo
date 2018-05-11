//
// Source code recreated from mViewA .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.github.dxq.drawcontainer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import java.util.Stack;

import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_MOVE;
import static android.view.MotionEvent.ACTION_POINTER_1_UP;
import static android.view.MotionEvent.ACTION_UP;

/**
 * 郑重声明：本源码均来自互联网，仅供个人欣赏、学习之用，
 * 版权归36氪产品发行公司所有，任何组织和个人不得公开传播或用于任何商业盈利用途，
 * 否则一切后果由该组织或个人承担。
 * 本人不承担任何法律及连带责任！请自觉于下载后24小时内删除
 *
 */
@SuppressLint({"ClickableViewAccessibility", "FloatMath"})
public class DrawContainer extends RelativeLayout {
    private viewA mViewA;
    private ViewB mViewB;
    private Matrix mMatrix = new Matrix();
    private Matrix d = new Matrix();
    private PointF e = new PointF();
    private PointF f = new PointF();
    private float g = 1.0F;
    private Bitmap mUnderlayerBitmap;
    private Bitmap i;
    private float j;
    private float k;
    private float l = 1.0F;
    private int m = 0;
    private Stack<PathHistory> mStack;
    private boolean o = true;
    private boolean editable = false;
    private b q;

    public DrawContainer(Context var1) {
        super(var1);
        this.k();
    }

    public DrawContainer(Context var1, AttributeSet var2) {
        super(var1, var2);
        this.k();
    }

    private float canDraw(MotionEvent var1) {
        float var2;
        if(var1.getPointerCount() == 1) {
            var2 = -1.0F;
        } else {
            float var3 = var1.getX(0) - var1.getX(1);
            var2 = var1.getY(0) - var1.getY(1);
            var2 = (float) Math.sqrt(var3 * var3 + var2 * var2);
        }

        return var2;
    }

    private void canDraw(PointF var1, MotionEvent var2) {
        float var3 = var2.getX(0);
        float var4 = var2.getX(1);
        float var6 = var2.getY(0);
        float var5 = var2.getY(1);
        var1.set((var3 + var4) / 2.0F, (var6 + var5) / 2.0F);
    }

    private PathHistory getLayerInfo() {
        if(this.mStack.isEmpty()) {
            this.mStack.push(new PathHistory(this.mMatrix, this.l));
        }

        return (PathHistory)this.mStack.peek();
    }

    @SuppressLint({"NewApi"})
    private void k() {
        if(VERSION.SDK_INT >= 11) {
            this.setLayerType(1, (Paint)null);
        }

        this.mViewA = new viewA(this.getContext());
        this.mViewB = new ViewB(this.getContext());
        LayoutParams var1 = new LayoutParams(-1, -1);
        this.addView(this.mViewB, var1);
        this.addView(this.mViewA, var1);
        this.mStack = new Stack();
        this.mViewA.setLayerInfos(DrawContainer.this.mStack);
        this.mViewA.setEditable(this.editable);
    }

    private void l() {
        float var3 = 0.0F;
        if(this.mUnderlayerBitmap != null) {

            float var1;
            float var2;
            if (this.mMatrix != null) {
                float[] var4 = new float[9];
                this.mMatrix.getValues(var4);
                var2 = var4[0];
                var1 = var4[2];
                var3 = var4[5];
            } else {
                var1 = 0.0F;
                var2 = 1.0F;
            }

            Log.v("test", "scale: " + var2 + ", tranx: " + var1 + ", trany: " + var3);
            if(var2 < 1.0F) {
                this.mMatrix.reset();
                this.d.reset();
            }

            this.m();
        }

    }

    private void m() {
        if(this.mUnderlayerBitmap != null) {
            float scale;
            float tranX;
            float transY;
            if (this.mMatrix != null) {
                float[] var6 = new float[9];
                this.mMatrix.getValues(var6);
                scale = var6[0];
                tranX = var6[2];
                transY = var6[5];
            } else {
                tranX = 0.0F;
                scale = 1.0F;
                transY = 0.0F;
            }

            Log.v("test", "scale: " + scale + ", tranx: " + tranX + ", trany: " + transY);
            int var4 = (int) ((float) this.mUnderlayerBitmap.getWidth() * this.l * scale);
            int var5 = (int) (scale * (float) this.mUnderlayerBitmap.getHeight() * this.l);
            LayoutParams var7 = (LayoutParams)this.mViewA.getLayoutParams();
            var7.leftMargin = (int) (tranX + this.j);
            var7.topMargin = (int) (transY + this.k);
            var7.width = var4;
            var7.height = var5;
            this.mViewB.a(var4, var5);
            this.mViewA.setLayoutParams(var7);
            this.mViewB.setLayoutParams(var7);
            PathHistory var8 = this.getLayerInfo();
            var8.leftMargin = var7.leftMargin;
            var8.topMargin = var7.topMargin;
            var8.setMetrix(this.mMatrix);
            this.mViewA.postInvalidate();
        }

    }

    private void n() {
        PathHistory var1;
        if(this.mStack != null && !this.mStack.isEmpty()) {
            var1 = (PathHistory)this.mStack.peek();
            if(var1.mDrawPathStack == null || var1.mDrawPathStack.isEmpty()) {
                this.mStack.pop();
            }
        }

        var1 = new PathHistory(this.l);
        var1.setMetrix(this.mMatrix);
        this.mStack.push(var1);
    }

    private void o() {
        if(this.mUnderlayerBitmap != null) {
            int var3 = this.mUnderlayerBitmap.getWidth();
            int var6 = this.mUnderlayerBitmap.getHeight();
            int var4 = this.getMeasuredWidth();
            int var5 = this.getMeasuredHeight();
            float var2 = 1.0F;
            float var1 = var2;
            if(var4 > 0) {
                var1 = var2;
                if(var5 > 0) {
                    if(var3 * var5 < var4 * var6) {
                        var1 = (float)var5 / (float)var6;
                    } else {
                        var1 = (float)var4 / (float)var3;
                    }
                }
            }

            this.l = var1;
            this.j = ((float)this.getWidth() - (float)this.mUnderlayerBitmap.getWidth() * var1) / 2.0F;
            this.k = ((float)this.getHeight() - var1 * (float)this.mUnderlayerBitmap.getHeight()) / 2.0F;
            if(this.mViewA != null) {
                this.mViewA.setInitScale(this.l);
            }
        }

    }

    public boolean canDraw() {
        return this.editable;
    }

    public boolean b() {
        boolean var1;
        if(((LayoutParams)this.mViewA.getLayoutParams()).leftMargin >= 0) {
            var1 = true;
        } else {
            var1 = false;
        }

        return var1;
    }

    public boolean c() {
        LayoutParams var3 = (LayoutParams)this.mViewA.getLayoutParams();
        int var1 = var3.width;
        boolean var2;
        if(var3.leftMargin + var1 < this.getWidth()) {
            var2 = true;
        } else {
            var2 = false;
        }

        return var2;
    }

    public void d() {
        if(this.mStack != null && !this.mStack.isEmpty()) {
            label31:
            for(int var1 = this.mStack.size() - 1; var1 >= 0; --var1) {
                PathHistory var3 = (PathHistory)this.mStack.get(var1);

                for(int var2 = var3.mDrawPathStack.size() - 1; var2 >= 0; --var2) {
                    DrawPath var4 = (DrawPath)var3.mDrawPathStack.get(var2);
                    if(var4.a) {
                        var4.a = false;
                        break label31;
                    }
                }
            }

            this.m();
        }

        if(this.q != null) {
            this.q.a();
        }

    }

    public void e() {
        if(this.mStack != null && !this.mStack.isEmpty()) {
            label31:
            for(int var1 = 0; var1 < this.mStack.size(); ++var1) {
                PathHistory var3 = (PathHistory)this.mStack.get(var1);

                for(int var2 = 0; var2 < var3.mDrawPathStack.size(); ++var2) {
                    DrawPath var4 = (DrawPath)var3.mDrawPathStack.get(var2);
                    if(!var4.a) {
                        var4.a = true;
                        break label31;
                    }
                }
            }

            this.m();
        }

        if(this.q != null) {
            this.q.a();
        }

    }

    public void f() {
        if(this.mStack != null && !this.mStack.isEmpty()) {
            for(int var1 = this.mStack.size() - 1; var1 >= 0; --var1) {
                PathHistory var4 = (PathHistory)this.mStack.get(var1);

                for(int var2 = var4.mDrawPathStack.size() - 1; var2 >= 0; --var2) {
                    DrawPath var3 = (DrawPath)var4.mDrawPathStack.get(var2);
                    if(var3.a) {
                        var3.a = false;
                    }
                }
            }

            this.m();
        }

        this.setMarkBitmap((Bitmap)null);
    }

    public void g() {
        if(this.mUnderlayerBitmap != null && !this.mUnderlayerBitmap.isRecycled()) {
            this.mUnderlayerBitmap = null;
        }

        if(this.i != null && !this.i.isRecycled()) {
            this.i = null;
        }

        if(this.mViewA != null) {
            this.mViewA.setLayerInfos((Stack)null);
            this.mViewA = null;
        }

        if(this.mViewB != null) {
            this.mViewB.setUnderlayerBitmap((Bitmap)null);
            this.mViewB.setPostilBitmap((Bitmap)null);
            this.mViewB = null;
        }

    }

    public Bitmap getDrawingBitmap() {
        Bitmap underlayerBitmap = null;

        Bitmap bitmap;
        try {
            if(this.mUnderlayerBitmap == null) {
                return underlayerBitmap;
            }

            bitmap = Bitmap.createBitmap(this.mUnderlayerBitmap.getWidth(), this.mUnderlayerBitmap.getHeight(), Config.ARGB_8888);
            Canvas var4 = new Canvas(bitmap);
            Rect var3 = new Rect(0, 0, this.mUnderlayerBitmap.getWidth(), this.mUnderlayerBitmap.getHeight());
            if(this.i != null) {
                var4.drawBitmap(this.i, (Rect)null, var3, (Paint)null);
            }

            this.mViewA.drawPaths(var4, this.mStack, 1.0F);
        } catch (Throwable var5) {
            return underlayerBitmap;
        }

        underlayerBitmap = bitmap;
        return underlayerBitmap;
    }

    public Bitmap getMarkBitmap() {
        return this.i;
    }

    public boolean h() {
        boolean var3;
        if(this.mStack != null && !this.mStack.isEmpty()) {
            for(int var1 = this.mStack.size() - 1; var1 >= 0; --var1) {
                PathHistory var4 = (PathHistory)this.mStack.get(var1);

                for(int var2 = var4.mDrawPathStack.size() - 1; var2 >= 0; --var2) {
                    if(!((DrawPath)var4.mDrawPathStack.get(var2)).a) {
                        var3 = true;
                        return var3;
                    }
                }
            }
        }

        var3 = false;
        return var3;
    }

    public boolean i() {
        boolean var3;
        if(this.mStack != null && !this.mStack.isEmpty()) {
            for(int var1 = this.mStack.size() - 1; var1 >= 0; --var1) {
                PathHistory var4 = (PathHistory)this.mStack.get(var1);

                for(int var2 = var4.mDrawPathStack.size() - 1; var2 >= 0; --var2) {
                    if(((DrawPath)var4.mDrawPathStack.get(var2)).a) {
                        var3 = false;
                        return var3;
                    }
                }
            }
        }

        var3 = true;
        return var3;
    }

    public void j() {
        if(this.mStack != null && !this.mStack.isEmpty()) {
            PathHistory var1 = (PathHistory)this.mStack.peek();
            if(var1.mDrawPathStack != null && !var1.mDrawPathStack.isEmpty() && this.q != null && !this.mStack.isEmpty()) {
                this.q.a();
            }
        }

    }

    /**
     * 判断是否拦截触摸事件
     * @param motionEvent
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        boolean isConsume = true;
        int var2 = motionEvent.getActionMasked();
        if(!this.o) {
            isConsume = super.onInterceptTouchEvent(motionEvent);
        } else {
            switch(var2) {
                case ACTION_DOWN:
                this.m = 2;
                break;
                case MotionEvent.ACTION_UP:
                this.m = 0;
                case MotionEvent.ACTION_MOVE:
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_OUTSIDE:
            default:
                break;
                //单指下按
                case MotionEvent.ACTION_POINTER_1_DOWN:
                    this.g = this.canDraw(motionEvent);
                if(this.g > 10.0F) {
                    this.d.set(this.mMatrix);
                    this.canDraw(this.f, motionEvent);
                    this.m = 1;
                }
                break;
            case ACTION_POINTER_1_UP:
                this.m = 2;
            }

            if(this.m == 0) {
                isConsume = false;
            }
        }

        return isConsume;
    }

    @Override
    protected void onSizeChanged(int var1, int var2, int var3, int var4) {
        super.onSizeChanged(var1, var2, var3, var4);
        if(var1 != var3 && var2 != var4) {
            this.setUnderlayerBitmap(this.mUnderlayerBitmap);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean isConsume = false;
        if(this.o && this.mUnderlayerBitmap != null) {
            switch(motionEvent.getActionMasked()) {
            case ACTION_DOWN:
                this.m = 2;
                this.d.set(this.mMatrix);
                this.e.set(motionEvent.getX(), motionEvent.getY());
                this.n();
                this.m();
                if(this.mViewA != null) {
                    this.mViewA.handleOnTouchEvent(motionEvent);
                }
                break;
            case ACTION_UP:
                this.m = 0;
                this.l();
                this.mViewA.handleOnTouchEvent(motionEvent);
                this.j();
                this.n();
                break;
            case ACTION_MOVE:
                float var2 = motionEvent.getX() - this.e.x;
                float var3 = motionEvent.getY();
                float var4 = this.e.y;
                if(this.m == 0) {
                    return isConsume;
                }

                if(this.m != 1 && this.editable) {
                    if(this.m == 2) {
                        this.mViewA.handleOnTouchEvent(motionEvent);
                    }
                } else {
                    if(var2 > 0.0F) {
                        if(this.c()) {
                            this.getParent().requestDisallowInterceptTouchEvent(true);
                        } else {
                            this.getParent().requestDisallowInterceptTouchEvent(false);
                        }
                    } else if(this.b()) {
                        this.getParent().requestDisallowInterceptTouchEvent(true);
                    } else {
                        this.getParent().requestDisallowInterceptTouchEvent(false);
                    }

                    this.mMatrix.set(this.d);
                    this.mMatrix.postTranslate(var2, var3 - var4);
                    var2 = this.canDraw(motionEvent);
                    if(var2 > 10.0F) {
                        var2 /= this.g;
                        this.mMatrix.postScale(var2, var2, this.f.x, this.f.y);
                    }

                    if (this.mMatrix != null) {
                        float[] var8 = new float[9];
                        this.mMatrix.getValues(var8);
                        var3 = var8[0];
                        var4 = var8[2];
                        var2 = var8[5];
                    } else {
                        var4 = 0.0F;
                        var3 = 1.0F;
                        var2 = 0.0F;
                    }

                    int var6 = (int)((float)this.mUnderlayerBitmap.getWidth() * this.l * var3);
                    int var5 = (int)(var3 * (float)this.mUnderlayerBitmap.getHeight() * this.l);
                    if(var4 < (float)(-(var6 - this.getWidth()))) {
                        this.mMatrix.postTranslate(-((float) var6 + var4 - (float) this.getWidth()), 0.0F);
                    }

                    if(var4 > 0.0F) {
                        this.mMatrix.postTranslate(-var4, 0.0F);
                    }

                    if(var2 < (float)(-(var5 - this.getHeight()))) {
                        this.mMatrix.postTranslate(0.0F, -((float) var5 + var2 - (float) this.getHeight()));
                    }

                    if(var2 > 0.0F) {
                        this.mMatrix.postTranslate(0.0F, -var2);
                    }

                    if(var6 <= this.getWidth() || var5 <= this.getHeight()) {
                        this.mMatrix.reset();
                        this.d.reset();
                    }

                    if(!this.editable) {
                        this.mViewA.handleOnTouchEvent(motionEvent);
                    }

                    this.m();
                }
            case 3:
            case 4:
            default:
                break;
            case 5:
                this.g = this.canDraw(motionEvent);
                if(this.g > 10.0F) {
                    this.d.set(this.mMatrix);
                    this.canDraw(this.f, motionEvent);
                    this.m = 1;
                }
                break;
            case 6:
                this.m = 0;
                this.d.set(this.mMatrix);
                this.m();
            }

            if(this.m != 0) {
                isConsume = true;
            } else {
                isConsume = false;
            }
        } else {
            isConsume = super.onTouchEvent(motionEvent);
        }

        return isConsume;
    }

    public void setEditable(boolean var1) {
        this.editable = var1;
        this.mViewA.setEditable(var1);
    }

    public void setLayerStack(Stack<PathHistory> var1) {
        this.mStack = var1;
        this.o();
        this.m();
        this.mViewA.setLayerInfos(this.mStack);
        if(this.q != null) {
            this.q.a();
        }

    }

    public void setMarkBitmap(Bitmap var1) {
        this.i = var1;
        if(this.mViewB != null) {
            this.mViewB.setPostilBitmap(var1);
        }

        if(this.q != null) {
            this.q.a();
        }

    }

    @Override
    public void setOnClickListener(OnClickListener var1) {
        this.mViewA.setOnClickListener(var1);
    }

    public void setUnderlayerBitmap(Bitmap bitmap) {
        this.mUnderlayerBitmap = bitmap;
        this.mMatrix.reset();
        this.d.reset();
        this.o();
        if(this.mViewB != null) {
            this.mViewB.setUnderlayerBitmap(bitmap);
        }

        this.m();
    }

    public void setRevertListener(b var1) {
        this.q = var1;
    }

    public void setTouchable(boolean var1) {
        this.o = var1;
    }

    public static class PathHistory {
        public Matrix mMatrix = new Matrix();
        public Stack<DrawPath> mDrawPathStack = new Stack();
        public int leftMargin;
        public int topMargin;
        public float mDegree = 1.0F;

        public PathHistory(float var1) {
            this.mDegree = var1;
        }

        public PathHistory(Matrix matrix, float degree) {
            this.setMetrix(matrix);
            this.mDegree = degree;
        }

        public void setMetrix(Matrix matrix) {
            this.mMatrix.set(matrix);
        }
    }

    public interface b {
        void a();
    }

    public static class DrawPath {
        public boolean a = true;
        public android.graphics.Path mPath = new android.graphics.Path();

        public DrawPath() {
        }

        public DrawPath(android.graphics.Path path) {
            this.mPath = path;
        }
    }
}
