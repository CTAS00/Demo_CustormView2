package gestureview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import phone.kdlc.com.demo_custormview.R;

/**
 * Created by koudai_nick on 2018/4/26.
 */

public class MyTriangleView extends View {
    private Paint mPaint;
    private Path mPath;
    public MyTriangleView(Context context) {
        super(context);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPath = new Path();
    }

    public MyTriangleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTriangleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyTriangleView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        mPath.moveTo(10,10);
        mPath.lineTo(10,100);
        mPath.lineTo(500,100);
        mPath.close();
        canvas.drawPath(mPath,mPaint);

    }
}
