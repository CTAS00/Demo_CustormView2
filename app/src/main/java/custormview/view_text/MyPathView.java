package custormview.view_text;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by koudai_nick on 2018/4/27.
 */

public class MyPathView extends View {
    private Paint mPaint;
    private Path mPath;
    public MyPathView(Context context) {
        this(context,null);
    }

    public MyPathView(Context context, AttributeSet attrs) {
        this(context, attrs,0);

    }

    public MyPathView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(20);
        mPaint.setStyle(Paint.Style.STROKE);//填充样式改为描边
        mPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPath.moveTo(100,100);
        mPath.lineTo(400,100);
        canvas.drawPath(mPath,mPaint);

//        canvas.drawColor(Color.GREEN);
    }
}
