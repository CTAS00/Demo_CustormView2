package custormview.view_text;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by koudai_nick on 2018/4/24.
 */

public class TextViewGroup extends LinearLayout {
    public TextViewGroup(Context context) {
        this(context,null);
    }

    public TextViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs,0);



    }

    public TextViewGroup(final Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        button = new MyView(context);
//        addView(button);
//        LinearLayout.LayoutParams layoutParams = (LayoutParams) button.getLayoutParams();
//        layoutParams.weight = 300;
//        layoutParams.height = 100;
//        button.setLayoutParams(layoutParams);
//        button.setText("我是一个按钮");
//        button.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(context,"点击了",Toast.LENGTH_SHORT).show();
//            }
//
//        });

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    private int mLastX,mLastY;

    // 外部拦截法
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        int x = (int) ev.getX();
//        int y = (int) ev.getY();
//        boolean isIntercept = false;
//        switch (ev.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                Log.e("CT_TEXT","TextViewGroup onInterceptTouchEvent ACTION_DOWN");
//                isIntercept = false;
//                break;
//            case MotionEvent.ACTION_MOVE:
//                Log.e("CT_TEXT","x = " + x + ",y = " + y  + " mLastX = " + mLastX + "mLastY = " + mLastY);
//                if(x - mLastX >y - mLastY){
//                    isIntercept = true; // 父类拦截
//                }else{
//                    isIntercept = false;// 父类不拦截 就会传递给子控件
//                }
//                Log.e("CT_TEXT","TextViewGroup onInterceptTouchEvent ACTION_MOVE");
//                break;
//            case MotionEvent.ACTION_UP:
//                Log.e("CT_TEXT","TextViewGroup onInterceptTouchEvent ACTION_UP");
//                isIntercept = false;
//                break;
//        }
//        mLastX = x;
//        mLastY = y;
//        return isIntercept;
//    }

    // 内部拦截法
    // 所有的事件都先传递给子控件 需要拦截的话 就直接可以在子控件里调控父控件的方法来实现


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // 一步步来   第一: 一个down事件下来,进入到子控件中
        // 子控件返回true
        /**表示消耗当前事件，后续事件过来，move事件传递过来
         *
         */

        // 子控件返回false
        /**
         *
         */






        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.e("CT_TEXT","TextViewGroup onTouchEvent ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e("CT_TEXT","TextViewGroup onTouchEvent ACTION_MOVE");
                Log.e("CT_TEXT","x = " + x + ",y = " + y);
                break;
            case MotionEvent.ACTION_UP:
                Log.e("CT_TEXT","TextViewGroup onTouchEvent ACTION_UP");
                break;
        }
//        Log.e("CT_TEXT","TextViewGroup onTouchEvent return =" + super.onTouchEvent(event));
        return true;
    }



}
