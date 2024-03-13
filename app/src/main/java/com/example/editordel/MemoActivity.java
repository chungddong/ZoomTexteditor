package com.example.editordel;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MemoActivity extends AppCompatActivity implements View.OnTouchListener {

    EditText tv_content;

    final static float STEP = 200;
    float mRatio = 1.0f;
    int mBaseDist;
    float mBaseRatio;
    float fontsize = 13;


    TextView tv_memo_title;
    EditText et_memo_content;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);


        Intent secondIntent = getIntent();
        String uri = secondIntent.getStringExtra("uri");
        /*tv_content = (EditText) findViewById(R.id.et_memo_content);
        tv_content.setTextSize(mRatio + 13);*/

        tv_memo_title = findViewById(R.id.tv_memo_title);
        et_memo_content = findViewById(R.id.et_memo_content);

        Toast.makeText(this, "" + uri, Toast.LENGTH_SHORT).show();

        

    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event.getPointerCount() == 2) {
            int action = event.getAction();
            int pureaction = action & MotionEvent.ACTION_MASK;
            if (pureaction == MotionEvent.ACTION_POINTER_DOWN) {
                mBaseDist = getDistance(event);
                mBaseRatio = mRatio;
            } else {
                float delta = (getDistance(event) - mBaseDist) / STEP;
                float multi = (float) Math.pow(2, delta);
                mRatio = Math.min(1024.0f, Math.max(0.1f, mBaseRatio * multi));
                //tv_content.setTextSize(mRatio + 13);

                //tv_content.setMovementMethod(new ScrollingMovementMethod());
            }
        }
        return true;
    }

    int getDistance(MotionEvent event) {
        int dx = (int) (event.getX(0) - event.getX(1));
        int dy = (int) (event.getY(0) - event.getY(1));
        return (int) (Math.sqrt(dx * dx + dy * dy));
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}
