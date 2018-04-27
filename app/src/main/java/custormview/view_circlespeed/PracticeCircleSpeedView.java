package custormview.view_circlespeed;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import phone.kdlc.com.demo_custormview.R;

/**
 * Created by koudai_nick on 2018/4/19.
 * 圆环里面不断在变化
 * 7.2妙全都搞定
 */

public class PracticeCircleSpeedView extends View {
    private int mFirstColor ;// 一开始显示的颜色
    private int mSecondColor;// 第二圈显示的颜色
    private int mCircleWidth;//圆环的宽度
    private int mSpeed;// 运行的速度



    private int center;// 中心点的坐标 相对于这个容器来说
    private int radius;// 半径的长度



    private Paint mPaint;


    private int mProgress; // 扫过的区域

    private boolean isNext;




    public PracticeCircleSpeedView(Context context) {
        this(context,null);
    }

    public PracticeCircleSpeedView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PracticeCircleSpeedView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 获取自定义的属性
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
    // 开始运作
    public void startRotate(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    mProgress++;
                    if(mProgress==360){
                        mProgress=0;
                        if(!isNext){
                            isNext = true;
                        }else{
                            isNext = false;
                        }
                    }
                    postInvalidate();
                    try {
                        Thread.sleep(20);
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
        center = getWidth()/2;
        radius = getWidth()/2 - mCircleWidth/2;

        RectF rectF = new RectF(center-radius,center-radius,center+radius,center+radius);


        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mCircleWidth);

        if(!isNext){
            mPaint.setColor(mFirstColor);
            canvas.drawCircle(center,center,radius,mPaint);
            mPaint.setColor(mSecondColor);
            canvas.drawArc(rectF,-90,mProgress,false,mPaint);

        }else{
            mPaint.setColor(mSecondColor);
            canvas.drawCircle(center,center,radius,mPaint);
            mPaint.setColor(mFirstColor);
            canvas.drawArc(rectF,-90,mProgress,false,mPaint);
        }

    }
}
