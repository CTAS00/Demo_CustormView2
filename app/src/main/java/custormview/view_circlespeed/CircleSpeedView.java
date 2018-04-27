package custormview.view_circlespeed;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import phone.kdlc.com.demo_custormview.R;

/**
 * Created by koudai_nick on 2018/4/19.
 */

public class CircleSpeedView extends View {
    private int mFirstColor;
    private int mSecondColor;
    private int mCircleWidth;
    private int mSpeed;


    private int mProgress;



    private boolean isNext;


    private Paint mPaint;



    public CircleSpeedView(Context context) {
        this(context,null);
    }

    public CircleSpeedView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CircleSpeedView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.e("CTAS","CircleSpeedView");
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomProgressBar, defStyleAttr, 0);
        int n = a.getIndexCount();

        for (int i = 0; i < n; i++)
        {
            int attr = a.getIndex(i);
            switch (attr)
            {
                case R.styleable.CustomProgressBar_firstColor:
                    mFirstColor = a.getColor(attr, Color.GREEN);
                    break;
                case R.styleable.CustomProgressBar_secondColor:
                    mSecondColor = a.getColor(attr, Color.RED);
                    break;
                case R.styleable.CustomProgressBar_circleWidth:
                    mCircleWidth = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_PX, 20, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.CustomProgressBar_speed:
                    mSpeed = a.getInt(attr, 20);// 默认20
                    break;
            }
        }
        a.recycle();

        mPaint = new Paint();
    }

    public void startRotate(){
        // 开启一个不断旋转的
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    mProgress++;
                    if(mProgress==360){
                        mProgress = 0;
                        // 换样式
                        if(!isNext){
                            isNext = true;
                        }else{
                            isNext = false;
                        }
                    }
                    postInvalidate();
                    try {
                        Thread.sleep(mSpeed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }



            }
        }).start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int center = getWidth()/2; // 圆心对的x坐标的位置
        int radius = center - mCircleWidth/2;
        mPaint.setStyle(Paint.Style.STROKE); // 空心的描边
        mPaint.setStrokeWidth(mCircleWidth);
        mPaint.setAntiAlias(true);
        Log.e("CTAS","center = " + center + "|| radius =" + radius);
        RectF reactf=new RectF(center-radius, center-radius, center+radius, center+radius);//确定外切矩形范围
        if(!isNext){
            mPaint.setColor(mFirstColor);
            canvas.drawCircle(center,center,radius,mPaint);
            mPaint.setColor(mSecondColor);
            canvas.drawArc(reactf,-90,mProgress,false,mPaint);
        }else{
            mPaint.setColor(mSecondColor);
            canvas.drawCircle(center,center,radius,mPaint);
            mPaint.setColor(mFirstColor);
            canvas.drawArc(reactf,-90,mProgress,false,mPaint);
        }


    }
}
