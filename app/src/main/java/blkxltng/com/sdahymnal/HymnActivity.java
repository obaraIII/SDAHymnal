package blkxltng.com.sdahymnal;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

public class HymnActivity extends AppCompatActivity {

    List<Hymns> hymn;
    DatabaseHelper mDatabaseHelper;
    String hymnName = "";
    String verse1 = "";
    String verse2 = "";
    String verse3 = "";
    String verse4 = "";
    String verse5 = "";
    String verse6 = "";
    String refrain = "";
    boolean favorited = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hymn);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(favorited == false){
                    fab.setImageResource(R.drawable.ic_favorite_border_white_24dp);
                    Toast.makeText(getApplicationContext(), "Removed from favorites", Toast.LENGTH_SHORT).show();
                } else {
                    fab.setImageResource(R.drawable.ic_favorite_white_24dp);
                    Toast.makeText(getApplicationContext(), "Added to favorites", Toast.LENGTH_SHORT).show();
                }

                favorited = !favorited;
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        int hymnId = intent.getIntExtra("HYMN_ID", 0);
        int hymnNumber = intent.getIntExtra("HYMN_NUMBER", 0);
        String hymnName = intent.getStringExtra("HYMN_NAME");

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

        hymn = mDatabaseHelper.getHymn(hymnId);

        getVerses();

        String lyrics = arrangeHymn();

        TextView textView = (TextView) findViewById(R.id.textview_lyrics);
        textView.setText(lyrics);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_hymn, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
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
        if(hymn.get(0).getRefrain() != null)
            refrain = hymn.get(0).getRefrain();
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

        return lyrics;
    }

}
