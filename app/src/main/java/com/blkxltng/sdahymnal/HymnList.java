package com.blkxltng.sdahymnal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.IOException;
import java.util.List;

import com.blkxltng.sdahymnal.R;

public class HymnList extends AppCompatActivity {

    ListView mListView;
    List<Hymns> mHymnsList;
    DatabaseHelper mDatabaseHelper;
    HymnListAdapter mHymnListAdapter;
    HymnList mHymnListActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hymn_list);

        Intent intent = getIntent();
        String hymnSection = intent.getStringExtra("HYMN_SECTION");
        int firstHymn = intent.getIntExtra("HYMN_SECTION_FIRST", 0);
        int lastHymn = intent.getIntExtra("HYMN_SECTION_LAST", 0);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(hymnSection);
        }

        mDatabaseHelper = new DatabaseHelper(getApplicationContext());
        try {
            mDatabaseHelper.createDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mHymnsList = mDatabaseHelper.getListHymns(firstHymn, lastHymn);
        mHymnListAdapter = new HymnListAdapter(getApplicationContext(), mHymnListActivity, mHymnsList);

        if(mHymnsList != null) {
            mListView = (ListView) findViewById(R.id.listview_hymns);
            mListView.setAdapter(mHymnListAdapter);
        }

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("LOG_TAG", "Clicked");
                Intent intent = new Intent(getApplicationContext(), HymnActivity.class);
                int hymnNumber = mHymnsList.get(i).getNumber();
                String hymnName = mHymnsList.get(i).getTitle();
                intent.putExtra("HYMN_NUMBER", hymnNumber);
                intent.putExtra("HYMN_NAME", hymnName);
                intent.putExtra("HYMN_ID", i);
                startActivity(intent);
            }
        });
    }
}
