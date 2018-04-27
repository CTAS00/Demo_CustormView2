package custormview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;

/**
 * Created by koudai_nick on 2018/4/20.
 */

public class MyButton extends Button {
    public MyButton(Context context) {
        super(context);
    }

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.e("MyButtton","dispatchTouchEvent - ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e("MyButtton","dispatchTouchEvent - ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.e("MyButtton","dispatchTouchEvent - ACTION_UP");
                break;

        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.e("MyButtton","onTouchEvent - ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e("MyButtton","onTouchEvent - ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.e("MyButtton","onTouchEvent - ACTION_UP");
                break;

        }
        return super.onTouchEvent(event);
    }
}
