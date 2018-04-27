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
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import java.util.Stack;
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
    private Matrix c = new Matrix();
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
    private Stack<a> n;
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

    private a getLayerInfo() {
        if(this.n.isEmpty()) {
            this.n.push(new a(this.c, this.l));
        }

        return (a)this.n.peek();
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
        this.n = new Stack();
        this.mViewA.setLayerInfos(DrawContainer.this.n);
        this.mViewA.setEditable(this.editable);
    }

    private void l() {
        float var3 = 0.0F;
        if(this.mUnderlayerBitmap != null) {

            float var1;
            float var2;
            if(this.c != null) {
                float[] var4 = new float[9];
                this.c.getValues(var4);
                var2 = var4[0];
                var1 = var4[2];
                var3 = var4[5];
            } else {
                var1 = 0.0F;
                var2 = 1.0F;
            }

            Log.v("test", "scale: " + var2 + ", tranx: " + var1 + ", trany: " + var3);
            if(var2 < 1.0F) {
                this.c.reset();
                this.d.reset();
            }

            this.m();
        }

    }

    private void m() {
        if(this.mUnderlayerBitmap != null) {
            float var1;
            float var2;
            float var3;
            if(this.c != null) {
                float[] var6 = new float[9];
                this.c.getValues(var6);
                var1 = var6[0];
                var2 = var6[2];
                var3 = var6[5];
            } else {
                var2 = 0.0F;
                var1 = 1.0F;
                var3 = 0.0F;
            }

            Log.v("test", "scale: " + var1 + ", tranx: " + var2 + ", trany: " + var3);
            int var4 = (int)((float)this.mUnderlayerBitmap.getWidth() * this.l * var1);
            int var5 = (int)(var1 * (float)this.mUnderlayerBitmap.getHeight() * this.l);
            LayoutParams var7 = (LayoutParams)this.mViewA.getLayoutParams();
            var7.leftMargin = (int)(var2 + this.j);
            var7.topMargin = (int)(var3 + this.k);
            var7.width = var4;
            var7.height = var5;
            this.mViewB.a(var4, var5);
            this.mViewA.setLayoutParams(var7);
            this.mViewB.setLayoutParams(var7);
            a var8 = this.getLayerInfo();
            var8.c = var7.leftMargin;
            var8.d = var7.topMargin;
            var8.a(this.c);
            this.mViewA.postInvalidate();
        }

    }

    private void n() {
        a var1;
        if(this.n != null && !this.n.isEmpty()) {
            var1 = (a)this.n.peek();
            if(var1.b == null || var1.b.isEmpty()) {
                this.n.pop();
            }
        }

        var1 = new a(this.l);
        var1.a(this.c);
        this.n.push(var1);
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
        if(this.n != null && !this.n.isEmpty()) {
            label31:
            for(int var1 = this.n.size() - 1; var1 >= 0; --var1) {
                a var3 = (a)this.n.get(var1);

                for(int var2 = var3.b.size() - 1; var2 >= 0; --var2) {
                    DrawContainer.c var4 = (DrawContainer.c)var3.b.get(var2);
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
        if(this.n != null && !this.n.isEmpty()) {
            label31:
            for(int var1 = 0; var1 < this.n.size(); ++var1) {
                a var3 = (a)this.n.get(var1);

                for(int var2 = 0; var2 < var3.b.size(); ++var2) {
                    DrawContainer.c var4 = (DrawContainer.c)var3.b.get(var2);
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
        if(this.n != null && !this.n.isEmpty()) {
            for(int var1 = this.n.size() - 1; var1 >= 0; --var1) {
                a var4 = (a)this.n.get(var1);

                for(int var2 = var4.b.size() - 1; var2 >= 0; --var2) {
                    DrawContainer.c var3 = (DrawContainer.c)var4.b.get(var2);
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
        Bitmap var1 = null;

        Bitmap var2;
        try {
            if(this.mUnderlayerBitmap == null) {
                return var1;
            }

            var2 = Bitmap.createBitmap(this.mUnderlayerBitmap.getWidth(), this.mUnderlayerBitmap.getHeight(), Config.ARGB_8888);
            Canvas var4 = new Canvas(var2);
            Rect var3 = new Rect(0, 0, this.mUnderlayerBitmap.getWidth(), this.mUnderlayerBitmap.getHeight());
            if(this.i != null) {
                var4.drawBitmap(this.i, (Rect)null, var3, (Paint)null);
            }

            this.mViewA.a(var4, this.n, 1.0F);
        } catch (Throwable var5) {
            return var1;
        }

        var1 = var2;
        return var1;
    }

    public Bitmap getMarkBitmap() {
        return this.i;
    }

    public boolean h() {
        boolean var3;
        if(this.n != null && !this.n.isEmpty()) {
            for(int var1 = this.n.size() - 1; var1 >= 0; --var1) {
                a var4 = (a)this.n.get(var1);

                for(int var2 = var4.b.size() - 1; var2 >= 0; --var2) {
                    if(!((DrawContainer.c)var4.b.get(var2)).a) {
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
        if(this.n != null && !this.n.isEmpty()) {
            for(int var1 = this.n.size() - 1; var1 >= 0; --var1) {
                a var4 = (a)this.n.get(var1);

                for(int var2 = var4.b.size() - 1; var2 >= 0; --var2) {
                    if(((DrawContainer.c)var4.b.get(var2)).a) {
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
        if(this.n != null && !this.n.isEmpty()) {
            a var1 = (a)this.n.peek();
            if(var1.b != null && !var1.b.isEmpty() && this.q != null && !this.n.isEmpty()) {
                this.q.a();
            }
        }

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent var1) {
        boolean var3 = true;
        int var2 = var1.getActionMasked();
        if(!this.o) {
            var3 = super.onInterceptTouchEvent(var1);
        } else {
            switch(var2) {
            case 0:
                this.m = 2;
                break;
            case 1:
                this.m = 0;
            case 2:
            case 3:
            case 4:
            default:
                break;
            case 5:
                this.g = this.canDraw(var1);
                if(this.g > 10.0F) {
                    this.d.set(this.c);
                    this.canDraw(this.f, var1);
                    this.m = 1;
                }
                break;
            case 6:
                this.m = 2;
            }

            if(this.m == 0) {
                var3 = false;
            }
        }

        return var3;
    }

    @Override
    protected void onSizeChanged(int var1, int var2, int var3, int var4) {
        super.onSizeChanged(var1, var2, var3, var4);
        if(var1 != var3 && var2 != var4) {
            this.setUnderlayerBitmap(this.mUnderlayerBitmap);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent var1) {
        boolean var7 = false;
        if(this.o && this.mUnderlayerBitmap != null) {
            switch(var1.getActionMasked()) {
            case 0:
                this.m = 2;
                this.d.set(this.c);
                this.e.set(var1.getX(), var1.getY());
                this.n();
                this.m();
                if(this.mViewA != null) {
                    this.mViewA.a(var1);
                }
                break;
            case 1:
                this.m = 0;
                this.l();
                this.mViewA.a(var1);
                this.j();
                this.n();
                break;
            case 2:
                float var2 = var1.getX() - this.e.x;
                float var3 = var1.getY();
                float var4 = this.e.y;
                if(this.m == 0) {
                    return var7;
                }

                if(this.m != 1 && this.editable) {
                    if(this.m == 2) {
                        this.mViewA.a(var1);
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

                    this.c.set(this.d);
                    this.c.postTranslate(var2, var3 - var4);
                    var2 = this.canDraw(var1);
                    if(var2 > 10.0F) {
                        var2 /= this.g;
                        this.c.postScale(var2, var2, this.f.x, this.f.y);
                    }

                    if(this.c != null) {
                        float[] var8 = new float[9];
                        this.c.getValues(var8);
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
                        this.c.postTranslate(-((float)var6 + var4 - (float)this.getWidth()), 0.0F);
                    }

                    if(var4 > 0.0F) {
                        this.c.postTranslate(-var4, 0.0F);
                    }

                    if(var2 < (float)(-(var5 - this.getHeight()))) {
                        this.c.postTranslate(0.0F, -((float)var5 + var2 - (float)this.getHeight()));
                    }

                    if(var2 > 0.0F) {
                        this.c.postTranslate(0.0F, -var2);
                    }

                    if(var6 <= this.getWidth() || var5 <= this.getHeight()) {
                        this.c.reset();
                        this.d.reset();
                    }

                    if(!this.editable) {
                        this.mViewA.a(var1);
                    }

                    this.m();
                }
            case 3:
            case 4:
            default:
                break;
            case 5:
                this.g = this.canDraw(var1);
                if(this.g > 10.0F) {
                    this.d.set(this.c);
                    this.canDraw(this.f, var1);
                    this.m = 1;
                }
                break;
            case 6:
                this.m = 0;
                this.d.set(this.c);
                this.m();
            }

            if(this.m != 0) {
                var7 = true;
            } else {
                var7 = false;
            }
        } else {
            var7 = super.onTouchEvent(var1);
        }

        return var7;
    }

    public void setEditable(boolean var1) {
        this.editable = var1;
        this.mViewA.setEditable(var1);
    }

    public void setLayerStack(Stack<a> var1) {
        this.n = var1;
        this.o();
        this.m();
        this.mViewA.setLayerInfos(this.n);
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
        this.c.reset();
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

    public static class a {
        public Matrix a = new Matrix();
        public Stack<DrawContainer.c> b = new Stack();
        public int c;
        public int d;
        public float e = 1.0F;

        public a(float var1) {
            this.e = var1;
        }

        public a(Matrix var1, float var2) {
            this.a(var1);
            this.e = var2;
        }

        public void a(Matrix var1) {
            this.a.set(var1);
        }
    }

    public interface b {
        void a();
    }

    public static class c {
        public boolean a = true;
        public Path b = new Path();

        public c() {
        }

        public c(Path var1) {
            this.b = var1;
        }
    }
}
