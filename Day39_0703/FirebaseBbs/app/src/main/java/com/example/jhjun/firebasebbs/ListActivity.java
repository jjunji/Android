
package com.example.jhjun.firebasebbs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

public class ListActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);


        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        ListAdapter adapter = new ListAdapter();



        recyclerView.setAdapter(adapter);
    }
}
