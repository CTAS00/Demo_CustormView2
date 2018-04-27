package gestureview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import phone.kdlc.com.demo_custormview.R;

/**
 * Created by koudai_nick on 2018/4/27.
 * 依葫芦画瓢
 * https://blog.csdn.net/lmj623565791/article/details/36236113
 */

public class GestureLockViewGroup extends RelativeLayout {

    /**
     * GestureLockView无手指触摸的状态下内圆的颜色
     */
    private int mNoFingerInnerCircleColor = 0xFF939090;
    /**
     * GestureLockView无手指触摸的状态下外圆的颜色
     */
    private int mNoFingerOuterCircleColor = 0xFFE0DBDB;

    /**
     * GestureLockView手指触摸的状态下内圆和外圆的颜色
     */
    private int mFingerOnColor = 0xFF378FC9;
    /**
     * GestureLockView手指抬起的状态下内圆和外圆的颜色
     */
    private int mFingerUpColor = 0xFFFF0000;

    /**
     * 每个边上的GestureLockView的个数
     */
    private int mCount = 4;

    /**
     * 最大尝试次数
     */
    private int mTryTimes = 4;


    /**
     * 宽度
     */
    private int mWidth;
    /**
     * 高度
     */
    private int mHeight;


    // 给个容器存放子view
    private GestureLockView[] mGestureLockViews;

    /**
     * GestureLockView的边长 4 * mWidth / ( 5 * mCount + 1 )
     */
    private int mGestureLockViewWidth;

    /**
     * 每个GestureLockView中间的间距 设置为：mGestureLockViewWidth * 25%
     */
    private int mMarginBetweenLockView = 30;


    private Paint mPaint;

    /**
     * 保存用户选中的GestureLockView的id
     */
    private List<Integer> mChoose = new ArrayList<Integer>();

    // 滑动时候的指引线
    private int mLastPathX;
    private int mLastPathY;

    private Path mPath;


    // 最老的起点
    private int mOriginPathX;
    private int mOriginPathY;


    /**
     * 指引下的结束位置
     */
    private Point mTmpTarget = new Point();


    public GestureLockViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GestureLockViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        /**
         * 获得所有自定义的参数的值
         */
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.GestureLockViewGroup, defStyleAttr, 0);
        int n = a.getIndexCount();

        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.GestureLockViewGroup_color_no_finger_inner_circle:
                    mNoFingerInnerCircleColor = a.getColor(attr,
                            mNoFingerInnerCircleColor);
                    break;
                case R.styleable.GestureLockViewGroup_color_no_finger_outer_circle:
                    mNoFingerOuterCircleColor = a.getColor(attr,
                            mNoFingerOuterCircleColor);
                    break;
                case R.styleable.GestureLockViewGroup_color_finger_on:
                    mFingerOnColor = a.getColor(attr, mFingerOnColor);
                    break;
                case R.styleable.GestureLockViewGroup_color_finger_up:
                    mFingerUpColor = a.getColor(attr, mFingerUpColor);
                    break;
                case R.styleable.GestureLockViewGroup_count:
                    mCount = a.getInt(attr, 3);
                    break;
                case R.styleable.GestureLockViewGroup_tryTimes:
                    mTryTimes = a.getInt(attr, 5);
                default:
                    break;
            }
        }

        a.recycle();
        mPaint = new Paint();
        mPath = new Path();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);

        mHeight = mWidth = mWidth < mHeight ? mWidth : mHeight;

        Log.e("CT_GESTURE", "mWidth = " + mWidth);

        if (mGestureLockViews == null) {
            mGestureLockViews = new GestureLockView[mCount * mCount];
            mGestureLockViewWidth = (int) (4 * mWidth * 1.0f / (5 * mCount + 1));
            Log.e("CT_GESTURE", "mGestureLockViewWidth = " + mGestureLockViewWidth);
            mMarginBetweenLockView = (int) (mGestureLockViewWidth * 0.25);
            // 设置画笔的宽度为GestureLockView的内圆直径稍微小点（不喜欢的话，随便设）
            mPaint.setStrokeWidth(mGestureLockViewWidth * 0.29f);
            for (int i = 0; i < mGestureLockViews.length; i++) {
                //初始化每个GestureLockView
                mGestureLockViews[i] = new GestureLockView(getContext(),
                        mNoFingerInnerCircleColor, mNoFingerOuterCircleColor,
                        mFingerOnColor, mFingerUpColor);
                mGestureLockViews[i].setId(i + 1);
                //设置参数，主要是定位GestureLockView间的位置
                RelativeLayout.LayoutParams lockerParams = new RelativeLayout.LayoutParams(
                        mGestureLockViewWidth, mGestureLockViewWidth);

                // 不是每行的第一个，则设置位置为前一个的右边
                if (i % mCount != 0) {
                    lockerParams.addRule(RelativeLayout.RIGHT_OF,
                            mGestureLockViews[i - 1].getId());
                }
                // 从第二行开始，设置为上一行同一位置View的下面
                if (i > mCount - 1) {
                    lockerParams.addRule(RelativeLayout.BELOW,
                            mGestureLockViews[i - mCount].getId());
                }
                //设置右下左上的边距
                int rightMargin = mMarginBetweenLockView;
                int bottomMargin = mMarginBetweenLockView;
                int leftMagin = 0;
                int topMargin = 0;
                /**
                 * 每个View都有右外边距和底外边距 第一行的有上外边距 第一列的有左外边距
                 */
                if (i >= 0 && i < mCount)// 第一行
                {
                    topMargin = mMarginBetweenLockView;
                }
                if (i % mCount == 0)// 第一列
                {
                    leftMagin = mMarginBetweenLockView;
                }

                lockerParams.setMargins(leftMagin, topMargin, rightMargin,
                        bottomMargin);
                mGestureLockViews[i].setMode(GestureLockView.Mode.STATUS_NO_FINGER);
                addView(mGestureLockViews[i], lockerParams);
            }
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                // 重置
                reset();

//                mPaint.setColor(mFingerOnColor);
//                mPaint.setAlpha(50);
//
//                GestureLockView child2 =getChildIdByPos(x,y);
//                mLastPathX = child2.getLeft() / 2 + child2.getRight() / 2;
//                mLastPathY = child2.getTop() / 2 + child2.getBottom() / 2;
//                mPath.moveTo(mLastPathX, mLastPathY);
                break;

            case MotionEvent.ACTION_MOVE:
                mPaint.setColor(Color.RED);
                mPaint.setAlpha(50);
                GestureLockView child = getChildIdByPos(x, y);
                if (child != null) {
                    int cId = child.getId();
                    if (!mChoose.contains(cId)) {
                        mChoose.add(cId);
//                        child.setMode(GestureLockView.Mode.STATUS_FINGER_ON);
//                        if (mOnGestureLockViewListener != null)
//                            mOnGestureLockViewListener.onBlockSelected(cId);
                        // 设置指引线的起点
                        mLastPathX = child.getLeft() / 2 + child.getRight() / 2;
                        mLastPathY = child.getTop() / 2 + child.getBottom() / 2;

                        Log.e("CT_GESTURE", "mLastPathX = " + mLastPathX + "===" + "mLastPathY = " + mLastPathY);
                        if (mChoose.size() == 1)// 当前添加为第一个
                        {
                            // 一开始到的位置
                            Log.e("CT_GESTURE", "MotionEvent.ACTION_MOVE moveTo moveTo moveTo");
                            // 当前第一个的
                            mPath.moveTo(mLastPathX, mLastPathY);
                        } else
                        // 非第一个，将两者使用线连上
                        {
                            // 不是第一个的情况下 就是会连接
                            Log.e("CT_GESTURE", "MotionEvent.ACTION_MOVE lineTo lineTo lineTo");
                            mPath.lineTo(mLastPathX, mLastPathY);
                        }

                    }
                }
                // 指引线的终点
                mTmpTarget.x = x;
                mTmpTarget.y = y;

                break;

            case MotionEvent.ACTION_UP:
                break;

        }

        invalidate();
        return true;
    }
    /**
     *  Paint.Style.FILL    :填充内部
        Paint.Style.FILL_AND_STROKE  ：填充内部和描边
        Paint.Style.STROKE  ：仅描边
     */
    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
//        Log.e("CT_GESTURE","dispatchDraw 方法执行!");
        //绘制GestureLockView间的连线
        if (mPath != null) {
            mPaint.setColor(Color.BLACK);
            mPaint.setStrokeWidth(40);
            mPaint.setStyle(Paint.Style.STROKE); // 描边的样式
            canvas.drawPath(mPath, mPaint);
        }
        //绘制指引线
        if (mChoose.size() > 0) {
            if (mLastPathX != 0 && mLastPathY != 0)
                canvas.drawLine(mLastPathX, mLastPathY, mTmpTarget.x,
                        mTmpTarget.y, mPaint);
        }
    }

    /**
     * 通过x,y获得落入的GestureLockView
     *
     * @param x
     * @param y
     * @return
     */
    private GestureLockView getChildIdByPos(int x, int y) {
        for (GestureLockView gestureLockView : mGestureLockViews) {
            if (checkPositionInChild(gestureLockView, x, y)) {
                return gestureLockView;
            }
        }

        return null;

    }

    /**
     * 检查当前左边是否在child中
     *
     * @param child
     * @param x
     * @param y
     * @return
     */
    private boolean checkPositionInChild(View child, int x, int y) {

        //设置了内边距，即x,y必须落入下GestureLockView的内部中间的小区域中，可以通过调整padding使得x,y落入范围不变大，或者不设置padding
        int padding = (int) (mGestureLockViewWidth * 0.15);

        if (x >= child.getLeft() + padding && x <= child.getRight() - padding
                && y >= child.getTop() + padding
                && y <= child.getBottom() - padding) {
            return true;
        }
        return false;
    }

    /**
     * 做一些必要的重置
     */
    private void reset() {
        mChoose.clear();
        mPath.reset();
//        for (GestureLockView gestureLockView : mGestureLockViews)
//        {
//            gestureLockView.setMode(GestureLockView.Mode.STATUS_NO_FINGER);
//            gestureLockView.setArrowDegree(-1);
//        }
    }
}
