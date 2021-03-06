package com.blkxltng.sdahymnal;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import com.blkxltng.sdahymnal.R;

public class HymnActivity extends AppCompatActivity {

    List<Hymns> hymn;
    DatabaseHelper mDatabaseHelper;
    int hymnId;
    int hymnNumber;
    String hymnName = "";
    String verse1 = "";
    String verse2 = "";
    String verse3 = "";
    String verse4 = "";
    String verse5 = "";
    String verse6 = "";
    String verse7 = "";
    String refrain = "";
    String refrain2 = "";
    int subSection = 0;
    boolean favorited = false;
    private ShareActionProvider mShareActionProvider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hymn);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        hymnId = intent.getIntExtra("HYMN_ID", 0);
        hymnNumber = intent.getIntExtra("HYMN_NUMBER", 0);
        hymnName = intent.getStringExtra("HYMN_NAME");

        String hymnTitle = "Hymn #" + hymnNumber;

        getSupportActionBar().setTitle(hymnTitle);

        mDatabaseHelper = new DatabaseHelper(getApplicationContext());
        try {
            mDatabaseHelper.createDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        TextView textViewHymnTitle = (TextView) findViewById(R.id.textview_hymntitle);
        textViewHymnTitle.setText(hymnName);

        hymn = mDatabaseHelper.getHymn(hymnNumber-1);

        getVerses();

        String lyrics = arrangeHymn();

        TextView textView = (TextView) findViewById(R.id.textview_lyrics);
        textView.setText(lyrics);

        Log.d("Check if favorited", "" + hymn.get(0).getFavorited());

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if(hymn.get(0).getFavorited() == 1){
            fab.setImageResource(R.drawable.ic_favorite_white_24dp);
            favorited = true;
        } else {
            fab.setImageResource(R.drawable.ic_favorite_border_white_24dp);
            favorited = false;
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(favorited){
                    Log.d("Set unfave", "" + hymn.get(0).getFavorited());
                    mDatabaseHelper.favoritedHymn(hymnNumber, 0);
                    fab.setImageResource(R.drawable.ic_favorite_border_white_24dp);
                    favorited = !favorited;
                    Toast.makeText(getApplicationContext(), "Removed from favorites", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("Set fave", "" + hymn.get(0).getFavorited());
                    mDatabaseHelper.favoritedHymn(hymnNumber, 1);
                    fab.setImageResource(R.drawable.ic_favorite_white_24dp);
                    favorited = !favorited;
                    Toast.makeText(getApplicationContext(), "Added to favorites", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_hymn, menu);

//        // Locate MenuItem with ShareActionProvider
//        MenuItem item = menu.findItem(R.id.share);
//
//        // Fetch and store ShareActionProvider
//        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        if(item.getItemId() == R.id.share) {
            String subject ="Hymn #" + hymnNumber + " - " + hymnName;
            String lyrics = subject + "\n\n" + arrangeHymn();
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, lyrics);
            startActivity(Intent.createChooser(sharingIntent, "Share Using:"));
        }
        return false;
    }

    private void getVerses() {
        if(hymn.get(0).getVerse1() != null)
            verse1 = hymn.get(0).getVerse1();
        if(hymn.get(0).getVerse2() != null)
            verse2 = hymn.get(0).getVerse2();
        if(hymn.get(0).getVerse3() != null)
            verse3 = hymn.get(0).getVerse3();
        if(hymn.get(0).getVerse4() != null)
            verse4 = hymn.get(0).getVerse4();
        if(hymn.get(0).getVerse5() != null)
            verse5 = hymn.get(0).getVerse5();
        if(hymn.get(0).getVerse6() != null)
            verse6 = hymn.get(0).getVerse6();
        if(hymn.get(0).getVerse7() != null)
            verse7 = hymn.get(0).getVerse7();
        if(hymn.get(0).getRefrain() != null)
            refrain = hymn.get(0).getRefrain();
        if(hymn.get(0).getRefrain2() != null)
            refrain2 = hymn.get(0).getRefrain2();
        subSection = hymn.get(0).getSubSection();
    }

    private String arrangeHymn() {

        String lyrics = "";

        if(verse1 != "")
            lyrics = "Verse 1:\n" + verse1 + "\n\n";
        if(refrain != "")
            lyrics += "Refrain:\n" + refrain + "\n\n";
        if(verse2 != "")
            lyrics += "Verse 2:\n" + verse2 + "\n\n";
        if(verse3 != "")
            lyrics += "Verse 3:\n" + verse3 + "\n\n";
        if(verse4 != "")
            lyrics += "Verse 4:\n" + verse4 + "\n\n";
        if(verse5 != "")
            lyrics += "Verse 5:\n" + verse5 + "\n\n";
        if(verse6 != "")
            lyrics += "Verse 6:\n" + verse6 + "\n\n";
        if(verse7 != "")
            lyrics += "Verse 7:\n" + verse7 + "\n\n";
        if(refrain2 != "")
            lyrics += "Refrain 2:\n" + refrain2 + "\n\n";
        lyrics += "Sub-section: " + subSection + "\n\n";

        return lyrics;
    }

//    // Call to update the share intent
//    private void setShareIntent(Intent shareIntent) {
//        if (mShareActionProvider != null) {
//            mShareActionProvider.setShareIntent(shareIntent);
//        }
//    }

}
