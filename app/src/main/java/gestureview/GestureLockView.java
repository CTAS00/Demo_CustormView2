package gestureview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by koudai_nick on 2018/4/26.
 */

public class GestureLockView extends View {
    private static final String TAG = "GestureLockView";

    public GestureLockView(Context context) {
        this(context,null,0,0,0,0);
    }
    public GestureLockView(Context context,AttributeSet set) {
        this(context,set,0,0,0,0);
    }



    /**
     * GestureLockView的三种状态
     */
    public enum Mode
    {
        STATUS_NO_FINGER, STATUS_FINGER_ON, STATUS_FINGER_UP;
    }

    /**
     * GestureLockView的当前状态
     */
    private Mode mCurrentStatus = Mode.STATUS_NO_FINGER;


    /**
     * 四个颜色，可由用户自定义，初始化时由GestureLockViewGroup传入
     */
    private int mColorNoFingerInner;
    private int mColorNoFingerOutter;
    private int mColorFingerOn;
    private int mColorFingerUp;


    private Paint mPaint; // 画笔


    // 圆心的坐标
    private int mCenterX;
    private int mCenterY;

    private int mWidth;
    private int mHeight;

    private int mRadius; // 外圆的半径
    private int mInnerRadius; // 内圆的半径
    private int mStrokeWidth = 2;// 画笔的宽度


    // 内圆的处理 比例 比的是跟外圆的比例
    private float mInnerCircleRadiusRate = 0.3F;

    // 绘制三角形
    private Path mArrowPath;
    private int mArrowLength; // 三角形的最长的一条边


    public GestureLockView(Context context , AttributeSet set ,int colorNoFingerInner , int colorNoFingerOutter , int colorFingerOn , int colorFingerUp )
    {
        super(context,set);
        this.mColorNoFingerInner = colorNoFingerInner;
        this.mColorNoFingerOutter = colorNoFingerOutter;
        this.mColorFingerOn = colorFingerOn;
        this.mColorFingerUp = colorFingerUp;
        // 抗锯齿的画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        mArrowPath = new Path();

    }
    public GestureLockView(Context context , int colorNoFingerInner , int colorNoFingerOutter , int colorFingerOn , int colorFingerUp )
    {
        super(context);
        this.mColorNoFingerInner = colorNoFingerInner;
        this.mColorNoFingerOutter = colorNoFingerOutter;
        this.mColorFingerOn = colorFingerOn;
        this.mColorFingerUp = colorFingerUp;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        mArrowPath = new Path();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);

        // 取出这两者中的最小值
        mWidth = mWidth<mHeight?mWidth:mHeight;

        mCenterX = mCenterY = mWidth/2;

        mRadius = mCenterX - mStrokeWidth/2;


        mInnerRadius = (int) (mRadius *mInnerCircleRadiusRate);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void initTriangle(){
        float mHalfBottomLine = mRadius*mInnerCircleRadiusRate/2;
        mArrowPath = new Path();
        mArrowPath.moveTo(mWidth/2,mStrokeWidth+2);
        mArrowPath.lineTo(mWidth/2 -mHalfBottomLine,mStrokeWidth+2+mHalfBottomLine);
        mArrowPath.lineTo(mWidth / 2 + mHalfBottomLine, mStrokeWidth + 2
                + mHalfBottomLine);
        mArrowPath.close();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        switch (mCurrentStatus){
            case STATUS_NO_FINGER:

                mPaint.setStyle(Paint.Style.FILL);
                mPaint.setColor(Color.GREEN);
                canvas.drawCircle(mCenterX,mCenterY,mRadius,mPaint);

                mPaint.setColor(Color.RED);
                canvas.drawCircle(mCenterX,mCenterY,mInnerRadius,mPaint);

                initTriangle();
                canvas.drawPath(mArrowPath,mPaint);
                break;
            case STATUS_FINGER_UP:
                mPaint.setStyle(Paint.Style.FILL);
                mPaint.setColor(Color.GREEN);
                canvas.drawCircle(mCenterX,mCenterY,mRadius,mPaint);

                mPaint.setColor(Color.RED);
                canvas.drawCircle(mCenterX,mCenterY,mInnerRadius,mPaint);

                drawArrow(canvas);
                break;
        }
    }
    private int mArrowDegree = -1;
    // 绘制箭头
    private void drawArrow(Canvas canvas){
        if(mArrowDegree!=-1){
            mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            // 把当前的画布
            canvas.save();
            canvas.rotate(mArrowDegree,mCenterX,mCenterY);
            canvas.drawPath(mArrowPath,mPaint);
            canvas.restore();
        }

    }

    public void startDrawArrow(int ArrowDegree,Mode currentState){
        mArrowDegree = ArrowDegree;
        mCurrentStatus = currentState;
        postInvalidate();
    }

    public void setMode(Mode mode){

    }



}
