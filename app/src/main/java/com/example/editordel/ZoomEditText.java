package com.example.editordel;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ZoomEditText extends androidx.appcompat.widget.AppCompatEditText {

    private ScaleGestureDetector scaleGestureDetector;
    private float scaleFactor = 1.0f;

    float mRatio = 13.0f;

    public ZoomEditText(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        scaleGestureDetector = new ScaleGestureDetector(context, new ScaleListener());

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getPointerCount() == 2) {
            scaleGestureDetector.onTouchEvent(event); // 핀치 줌 이벤트 처리
            return true;
        } else {
            return super.onTouchEvent(event); // 텍스트 편집 이벤트 처리
        }
    }


    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scaleFactor *= detector.getScaleFactor();
            scaleFactor = Math.max(0.1f, Math.min(scaleFactor, 5.0f));
            setTextSize(mRatio * scaleFactor); // 텍스트 크기 조절
            return true;
        }
    }
}
