package com.github.dxq.drawcontainer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * 郑重声明：本源码均来自互联网，仅供个人欣赏、学习之用，
 * 版权归36氪产品发行公司所有，任何组织和个人不得公开传播或用于任何商业盈利用途，
 * 否则一切后果由该组织或个人承担。
 * 本人不承担任何法律及连带责任！请自觉于下载后24小时内删除
 *
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final DrawContainer drawContaienr = (DrawContainer)findViewById(R.id.draw_container);
        Bitmap icon = BitmapFactory.decodeResource(getResources(),
                R.drawable.homework);
        drawContaienr.setUnderlayerBitmap(icon);
        drawContaienr.canDraw();
        drawContaienr.setEditable(true);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawContaienr.setEditable(!drawContaienr.canDraw());
            }
        });
    }
}
