package com.yxz.rxjavamvp.adapter.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yxz.rxjavamvp.R;

/**
 * Created by yxz on 2017/10/25.
 *
 * getItemOffsets(),可以实现类似padding的效果

    onDraw(),可以实现类似绘制背景的效果，内容在上面

    onDrawOver()，可以绘制在内容的上面，覆盖内容
 */

public class MainItemDecoration extends RecyclerView.ItemDecoration {
    private int dividerHeight;
    private Paint dividerPaint;
    public MainItemDecoration(Context context){
        dividerPaint = new Paint();
//        dividerPaint.setColor(context.getResources().getColor(R.color.colorAccent));
        dividerPaint.setColor(ContextCompat.getColor(context, R.color.colorAccent));
        dividerHeight = context.getResources().getDimensionPixelSize(R.dimen.divider_height);

    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = dividerHeight;//类似加了一个bottom padding
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int childCount = parent.getChildCount();
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            float top = view.getBottom();
            float bottom = view.getBottom() + dividerHeight;
            c.drawRect(left, top, right, bottom, dividerPaint);
        }
    }
}
