package com.example.choiseungmin.test_expandable;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;

import java.util.HashMap;

public class card_demo extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView recyclerView2;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.LayoutManager layoutManager2;
    RecyclerView.Adapter adapter;
    RecyclerView.Adapter adapter2;

    LinearLayout first;
    LinearLayout second;

    GridLayout firstRecyclerView;
    GridLayout secondRecyclerView;

    HashMap<Integer,String[]> setdata = new HashMap();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_demo);
        // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);

        // 디비에서 정보 get
        String data[];
        for(int i = 0 ; i < 10; i++) {
            data = new String[3];
            data[0] = "koo"+i;
            data[1] = "010101010"+i;
            data[2] = "fuck"+i;
            setdata.put(i, data);
        }

        first = (LinearLayout) findViewById(R.id.first);
        second = (LinearLayout) findViewById(R.id.second);
        firstRecyclerView = (GridLayout) findViewById(R.id.firstRecyclerView);
        secondRecyclerView = (GridLayout) findViewById(R.id.secondRecyclerView);


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerAdapter("fuck",setdata);
        recyclerView.setAdapter(adapter);

        recyclerView2 = (RecyclerView) findViewById(R.id.recycler_view2);
        layoutManager2 = new LinearLayoutManager(this);
        recyclerView2.setLayoutManager(layoutManager2);
        adapter2 = new RecyclerAdapter("fuck1",setdata);
        recyclerView2.setAdapter(adapter2);


        Button buttonPop = (Button)findViewById(R.id.buttonPopup);


        first.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(firstRecyclerView.getVisibility() == View.VISIBLE)
                    firstRecyclerView.setVisibility(View.GONE);
                else
                    firstRecyclerView.setVisibility(View.VISIBLE);
            }
        });

        second.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(secondRecyclerView.getVisibility() == View.VISIBLE)
                    secondRecyclerView.setVisibility(View.GONE);
                else
                    secondRecyclerView.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_card_demo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
