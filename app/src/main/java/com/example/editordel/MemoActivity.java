package com.example.editordel;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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

        String title, content = "";


        try {
            title = getFileNameFromUri(getApplicationContext(), Uri.parse(uri));
            content = readFileFromUri(getApplicationContext(), Uri.parse(uri));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        tv_memo_title.setText(title);
        et_memo_content.setText(content);

    }

    public static String getFileNameFromUri(Context context, Uri uri) {
        String fileName = null;
        try {
            if (uri.getScheme().equals("content")) {
                ContentResolver contentResolver = context.getContentResolver();
                Cursor cursor = contentResolver.query(uri, null, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if (nameIndex != -1) {
                        fileName = cursor.getString(nameIndex);
                    }
                    cursor.close();
                }
            } else if (uri.getScheme().equals("file")) {
                fileName = uri.getLastPathSegment();
            }
        }
        catch (Exception err)
        {
            Log.e("ZTE", err + "");
        }
        return fileName;
    }

    public static String readFileFromUri(Context context, Uri uri) throws IOException {
        ContentResolver contentResolver = context.getContentResolver();
        StringBuilder stringBuilder = new StringBuilder();
        try (InputStream inputStream = contentResolver.openInputStream(uri);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append('\n');
            }
        }
        return stringBuilder.toString();
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
