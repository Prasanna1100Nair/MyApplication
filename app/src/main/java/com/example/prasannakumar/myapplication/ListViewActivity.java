package com.example.prasannakumar.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import Adapters.TrackAdapter;
import database.Note;

/**
 * Created by prasannakumar on 27/09/18.
 */

public class ListViewActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    TrackAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        setup();
    }

    private void setup() {
        recyclerView=findViewById(R.id.recycler_view);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();

        List<Note> thumbs=
                (List<Note>)bundle.getSerializable("List");

        mAdapter = new TrackAdapter(thumbs);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }
}
