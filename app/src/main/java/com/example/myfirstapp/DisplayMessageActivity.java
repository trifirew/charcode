package com.example.myfirstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class DisplayMessageActivity extends AppCompatActivity {

    private HistoryDataSource dataSource;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);
        setTitle(R.string.history);

        dataSource = new HistoryDataSource(this);
        dataSource.open();
        List<CharCode> values = dataSource.getAllHistory();

        mRecyclerView = (RecyclerView) findViewById(R.id.historyRecyclerView);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Specify an adapter
        mAdapter = new HistoryAdapter(values);
        mRecyclerView.setAdapter(mAdapter);


        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String myAscii = bundle.getString("ASCII");
        String myChar = bundle.getString("CHAR");
    }

    public void showDetail(View view) {
        Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(this, DisplayMessageActivity.class);
//        Bundle bundle = new Bundle();
//        String myChar = charEditText.getText().toString();
//        String myAscii = asciiEditText.getText().toString();
//        bundle.putString("CHAR", myChar);
//        bundle.putString("ASCII", myAscii);
//        intent.putExtras(bundle);
//        startActivity(intent);
    }

    @Override
    protected void onPause() {
        dataSource.close();
        super.onPause();
    }
}
