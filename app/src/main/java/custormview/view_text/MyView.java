package custormview.view_text;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by koudai_nick on 2018/4/24.
 */

public class MyView extends Button implements View.OnClickListener {
    private MyView mMyView;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
        }
    };

    private Scroller scroller ;
    private Context context;
    public MyView(Context context) {
        this(context,null);
    }
    public MyView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }
    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        scroller = new Scroller(context);
        setOnClickListener(this);
        mMyView = this;

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                MyView.this.scrollTo(-200,0);
//                MyView.this.scrollTo(10,0);

//                mMyView.scrollTo(400,0);
//                Log.e("CT_SCROLLER",mMyView.getScrollX() + "");

//                int delta = 200-mMyView.getScrollX();
//                scroller.startScroll(0,0,-200,0,500);
//                invalidate();

                scroller.startScroll(mMyView.getScrollX(),0,400,0,2000);
                invalidate();

            }
        },2000);
    }
    // 分发事件
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                // 请求父布局不要拦截事件   所以说down事件是会传递下来的
                getParent().requestDisallowInterceptTouchEvent(true);
                Log.e("CT_TEXT","MyView dispatchTouchEvent ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                // 父布局需要拦截事件的话
                getParent().requestDisallowInterceptTouchEvent(false);
                // 当下一次的move事件来传递的时候

                Log.e("CT_TEXT","MyView dispatchTouchEvent ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.e("CT_TEXT","MyView dispatchTouchEvent ACTION_UP");
                break;
        }
        return super.dispatchTouchEvent(event);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.e("CT_TEXT","MyView onTouchEvent ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e("CT_TEXT","MyView onTouchEvent ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.e("CT_TEXT","MyView onTouchEvent ACTION_UP");
                break;
        }
        Log.e("CT_TEXT","MyView onTouchEvent return =" + super.onTouchEvent(event));
        return super.onTouchEvent(event);
    }
    @Override
    public void onClick(View view) {
        Toast.makeText(context,"我被点击了",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void computeScroll() {
//        super.computeScroll();
        if(scroller.computeScrollOffset()){
            scrollTo(scroller.getCurrX(),scroller.getCurrY());
            postInvalidate();
        }

    }
}
