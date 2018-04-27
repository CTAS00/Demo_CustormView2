package custormview.view_text;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * Created by koudai_nick on 2018/4/24.
 */

public class HorizontalScrollViewEx extends LinearLayout {

    private int mLastX;
    private int mLastY;

    private int mLastXIntercept;
    private int mLastYIntercept;



    private Scroller mScroller;


    private int mChildWidth;// 子view的宽度
    private int mChildCount;//子view的个数
    public HorizontalScrollViewEx(Context context) {
        super(context);
        init();
    }

    public HorizontalScrollViewEx(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HorizontalScrollViewEx(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        if(mScroller ==null){
            mScroller = new Scroller(getContext());
        }
    }

   // 当时down 或者说 有子控件去消费的情况下就会这样处理
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean isInterrected = false;
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                isInterrected =false; // 默认不拦截
                // 仍然在滑动的情况下
                if(!mScroller.isFinished()){
                    mScroller.abortAnimation();
                    isInterrected = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                // 根据用户滑动的距离来判断是否拦截 每一次的move
                int deltaX = x - mLastXIntercept;
                int deltaY = y - mLastYIntercept;
                if(Math.abs(deltaX) > Math.abs(deltaY)){
                    isInterrected = true;
                }else{
                    isInterrected = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                isInterrected =false;
                break;
        }

        mLastX = (int) ev.getX();
        mLastY = (int) ev.getY();
        mLastXIntercept = (int) ev.getX();
        mLastYIntercept = (int) ev.getY();
        return isInterrected;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.e("CT_TEXT","HorizontalScrollViewEx onTouchEvent ACTION_DOWN");
                Log.e("CT_TEXT","x = " + x + ",y = " + y );
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e("CT_TEXT","HorizontalScrollViewEx onTouchEvent ACTION_MOVE");
                Log.e("CT_TEXT","x = " + x + ",y = " + y  + " mLastX = " + mLastX + "mLastY = " + mLastY);
                int deltax = x-mLastX;
                int deltay = y-mLastY;
                // 相对位置的滑动
//                scrollBy(-deltax,0);
                // 滑动到相对位置
                // 根据上一次的位置来滑动的
                Log.e("CT_TEXT","getscrollX = " + getScrollX() + ":deltax = " + deltax);
                scrollBy(-deltax,0);
                break;
            case MotionEvent.ACTION_UP:
                // 当用户抬起手指的时候
                int scrollX = getScrollX();// 内容边缘的偏移
                Log.e("CT_TEXT","HorizontalScrollViewEx onTouchEvent ACTION_UP" + "scrollX = " + scrollX);
                // 往右滑动
                if(scrollX<0){
                    smoothScrollBy(-scrollX,0);
                }
                if(scrollX>0&&scrollX>=mChildWidth){
                    Log.e("CT_TEXT","mChildWidth = " + mChildWidth);
                    smoothScrollBy(-mChildWidth,0);
                }
                break;
        }
        mLastX = x;
        mLastY = y;
        return true;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mChildWidth = getChildAt(0).getMeasuredWidth();
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        mChildCount = childCount;
        if(childCount == 0){
            // 要是没有子元素的话 就把自己的宽高设置为0
            setMeasuredDimension(0,0);
        }
    }

    private void smoothScrollBy(int dx,int dy){
        // 滑动的起点 和 滑动的距离
        mScroller.startScroll(getScrollX(),0,dx,0,500);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if(mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            postInvalidate();
        }
    }
}
