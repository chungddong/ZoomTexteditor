package com.example.editordel;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView rv_main;
    MemoRecyclerAdapter rvAdapter;
    ArrayList<MemoListClass> items = new ArrayList<MemoListClass>();

    FloatingActionButton fab;
    private static final int REQ_CODE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        rv_main = findViewById(R.id.rv_main);
        fab = findViewById(R.id.fab);

        rvAdapter = new MemoRecyclerAdapter();

        rv_main.setAdapter(rvAdapter);
        rv_main.setLayoutManager(new LinearLayoutManager(this));

        items.add(new MemoListClass("메모 제목","안녕하세요 대충 제목을 이렇게 적으면 될것 같아요"));
        items.add(new MemoListClass("메모 제목","안녕하세요 대충 제목을 이렇게 적으면 될것 같아sdfsdfsdfsfsfsdf요"));


        rvAdapter.setList(items);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"fab 클릭", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent().setType("text/plain")
                        .setAction(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);

                startActivityForResult(Intent.createChooser(intent, "Select a file"), REQ_CODE);

                /*Intent intent = new Intent(getApplicationContext(), MemoActivity.class);
                startActivity(intent);*/
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_CODE && resultCode == RESULT_OK && data != null) {
            // 선택한 파일의 Uri를 가져옴
            if (data.getData() != null) {
                // 여기에서 선택한 파일을 처리하는 로직을 추가할 수 있음
                // 예를 들어, Uri를 사용하여 파일을 읽어오거나 처리할 수 있음
                String selectedFileUri;
                selectedFileUri = "content://com.android.providers.media.documents/document/document%3A1000009575";
                //selectedFileUri = String.valueOf(data.getData());
                Log.d("ZTE", "Selected file URI: " + getFileNameFromUri(getApplicationContext(), Uri.parse(selectedFileUri)));
                try {
                    Log.d("ZTE", "Selected file URI: " + readFileFromUri(getApplicationContext(), Uri.parse(selectedFileUri),30));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        }
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

    public static String readFileFromUri(Context context, Uri uri, int charCount) throws IOException {
        ContentResolver contentResolver = context.getContentResolver();
        StringBuilder stringBuilder = new StringBuilder();
        try (InputStream inputStream = contentResolver.openInputStream(uri);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            int count = 0;
            String line;
            while ((line = reader.readLine()) != null && count < charCount) {
                stringBuilder.append(line);
                int lineLength = line.length();
                count += lineLength;
                if (count >= charCount) {
                    stringBuilder.setLength(charCount);  // 글자 수가 넘어가면 잘라냄
                    break;
                } else {
                    stringBuilder.append('\n');  // 줄바꿈 추가
                    count++;  // 줄바꿈 문자 포함
                }
            }
        }
        return stringBuilder.toString();
    }

}