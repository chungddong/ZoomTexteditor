package com.example.editordel;

import android.content.Intent;
import android.os.Bundle;
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

                //Intent intent = new Intent().setType("*/*")
                //        .setAction(Intent.ACTION_OPEN_DOCUMENT);
                //intent.addCategory(Intent.CATEGORY_OPENABLE);

                //startActivityForResult(Intent.createChooser(intent, "Select a file"), REQ_CODE);

                Intent intent = new Intent(getApplicationContext(), MemoActivity.class);
                startActivity(intent);
            }
        });


    }
}