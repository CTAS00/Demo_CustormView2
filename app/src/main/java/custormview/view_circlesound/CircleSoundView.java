package custormview.view_circlesound;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import phone.kdlc.com.demo_custormview.R;

/**
 * Created by koudai_nick on 2018/4/19.
 */

public class CircleSoundView extends View {

    private int mFirstColor  = Color.GREEN;// 一开始显示的颜色
    private int mSecondColor = Color.RED; //后来的颜色
    private int mArcWidth = 40;



    private int dotSpace = 15; // 间隔
    private int dotCount = 12 ;// 想要12个
    private Paint mPaint;
    private int center;
    private int radius;


    private int mCurrentCount=12;// 当前的进度


    private Bitmap mBitmap;

    public CircleSoundView(Context context) {
        this(context,null);
    }

    public CircleSoundView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CircleSoundView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mBitmap=BitmapFactory.decodeStream(getClass().getResourceAsStream("/res/drawable/ic_launcher.png"));
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e("CTAS_onLayout","getWidth() = " + getWidth());
        Log.e("CTAS_onLayout","getMeasuredWidth() = " + getMeasuredWidth());
        center = getWidth()/2;
        radius = center - mArcWidth/2;
        doDot(canvas);

        Rect mRect = new Rect();
        /**
         * 计算内切正方形的位置
         */
        int relRadius = radius - mArcWidth / 2;// 获得内圆的半径
        /**
         * 内切正方形的距离顶部 = mCircleWidth + relRadius - √2 / 2
         */
        mRect.left = (int) (relRadius - Math.sqrt(2) * 1.0f / 2 * relRadius) + mArcWidth;
        /**
         * 内切正方形的距离左边 = mCircleWidth + relRadius - √2 / 2
         */
        mRect.top = (int) (relRadius - Math.sqrt(2) * 1.0f / 2 * relRadius) + mArcWidth;
        mRect.bottom = (int) (mRect.left + Math.sqrt(2) * relRadius);
        mRect.right = (int) (mRect.left + Math.sqrt(2) * relRadius);
        canvas.drawBitmap(mBitmap,null,mRect,mPaint);
    }


    private void down(){
        mCurrentCount--;
        postInvalidate();

    }
    private void up(){
        mCurrentCount++;
        postInvalidate();
    }

    private int xDown,xUp;

    // 这个是消费的方法
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 默认返回的是false 说明是不做消费的
//        Log.e("CTAS_onTouchEvent","onTouchEvent =" + super.onTouchEvent(event));
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.e("CTAS_onTouchEvent","CTAS_onTouchEvent = MotionEvent.ACTION_DOWN");
                xDown = (int) event.getY();
            break;
            case MotionEvent.ACTION_UP:
                Log.e("CTAS_onTouchEvent","CTAS_onTouchEvent = MotionEvent.ACTION_UP");
                xUp = (int) event.getY();
                if(xUp-xDown>0){
                    // 下滑 增加
                    down();
                }else{
                    up();
                }
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    // 因为没有消费 所以后续的事件并没有传递过来
    // 分发失败然后事件上传上去  若一直没人消费的话 就没有啦
    // 分发成功以后就不用调用父容器的消费事件了
    // 分发成功的话 就给自己的onTouchEvent去执行了
    // 分发失败的话 就将该事件不断上传上去，看看谁能实现 谁都不实现的话 就挥发掉了
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.e("CTAS_dispatchTouchEvent","MotionEvent.ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e("CTAS_dispatchTouchEvent","MotionEvent.ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.e("CTAS_dispatchTouchEvent","MotionEvent.ACTION_UP");
                break;
        }
        Log.e("CT_EVENT","分发是否成功 = " + super.dispatchTouchEvent(event));
        return super.dispatchTouchEvent(event);
    }

    // 做处理
    private void doDot(Canvas canvas) {
        RectF rectf = new RectF(center-radius,center-radius,center+radius,center+radius);
        mPaint.setColor(mFirstColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mArcWidth);
        mPaint.setStrokeCap(Paint.Cap.ROUND); // 设置线帽
        int itemSize=360/dotCount - dotSpace;
        // 想要有12个
        for(int i=0;i<dotCount;i++){
            // 画弧线
            canvas.drawArc(rectf,i*(itemSize+dotSpace),itemSize,false,mPaint);
        }
        for(int i=0;i<mCurrentCount;i++){
            mPaint.setColor(mSecondColor);
            canvas.drawArc(rectf,i*(itemSize+dotSpace),itemSize,false,mPaint);

        }
    }

    /**
     * 从这边就能看出来 measure这个方法只是给建议值，
     * 至于用不用的话 还是在layout方法中去实现的
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    public void layout(int l, int t, int r, int b) {
        Log.e("CTAS_onLayout","right - left = " +(r - l));
        Log.e("CTAS_onLayout","MeasuredWidth = " +getMeasuredWidth());
        super.layout(l, t, r-300, b);
    }
    // 测量自己的大小，为正式布局提供建议  注意  只是提供意见 至于用不用还是要看layout的
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
