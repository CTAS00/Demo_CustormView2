package phone.kdlc.com.demo_2custormview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import custormview.MyButton;
import custormview.view_circlesound.CircleSoundView;
import custormview.view_circlespeed.PracticeCircleSpeedView;
import gestureview.GestureLockView;
import gestureview.MyTriangleView;
import phone.kdlc.com.demo_custormview.R;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private FrameLayout container;


    private String[] data = {"Apple", "Orange", "Pear", "Cherry","Apple", "Orange", "Pear", "Cherry","Apple", "Orange", "Pear", "Cherry","Apple", "Orange", "Pear", "Cherry","Apple", "Orange", "Pear", "Cherry","Apple", "Orange", "Pear", "Cherry"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     setContentView(R.layout.activity_main);
    container = (FrameLayout) findViewById(R.id.container);

        View conflictview = LayoutInflater.from(MainActivity.this).inflate(R.layout.activity_view_px,container,false);
        container.addView(conflictview);
//        final GestureLockView gestureLockView = conflictview.findViewById(R.id.lockview);
//        Button btn = conflictview.findViewById(R.id.btn);
//        btn.setText("我是大按钮");
//        Observable.timer(3, TimeUnit.SECONDS)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<Long>() {
//                    @Override
//                    public void call(Long aLong) {
//                        if(gestureLockView!=null){
//                            gestureLockView.startDrawArrow(180, GestureLockView.Mode.STATUS_FINGER_UP);
//                        }
//
//                    }
//                });

//        container.addView(new MyTriangleView(this));

//        initCircleSound();

//    initTextDispatchTouchevent();
//        initConflict();

    }

    private void initConflict() {
        View conflictview = LayoutInflater.from(MainActivity.this).inflate(R.layout.activity_view_conflict,container,false);
        ListView list1 = conflictview.findViewById(R.id.list1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);
        list1.setAdapter(adapter);

        ListView list2 = conflictview.findViewById(R.id.list2);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);
        list2.setAdapter(adapter2);

        container.addView(conflictview);
    }

    private void initCircleSpeed(){
        View circleView = LayoutInflater.from(MainActivity.this).inflate(R.layout.activity_view_circlespeed,null);
        final PracticeCircleSpeedView circleSpeedView = circleView.findViewById(R.id.circleview);
        container.addView(circleView);

        Observable.timer(3, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        circleSpeedView.startRotate();
                    }
                });
    }
    private void initCircleSound(){

        View circleView = LayoutInflater.from(MainActivity.this).inflate(R.layout.activity_view_circlesound,null);

        MyButton text_btn = circleView.findViewById(R.id.text_btn);
//        text_btn.layout();
        text_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"哈哈",Toast.LENGTH_SHORT).show();
            }
        });
        final CircleSoundView circleSpeedView = circleView.findViewById(R.id.circleview);
        container.addView(circleView);

//        Observable.timer(3, TimeUnit.SECONDS)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<Long>() {
//                    @Override
//                    public void call(Long aLong) {
//                        circleSpeedView.startRotate();
//                    }
//                });


    }

    private void initTextDispatchTouchevent(){
        View circleView = LayoutInflater.from(MainActivity.this).inflate(R.layout.activity_view_text,container,false);
        container.addView(circleView);
    }

}
