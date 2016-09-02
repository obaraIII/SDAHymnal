package blkxltng.com.sdahymnal;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;
import java.util.List;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper mDatabaseHelper;
    RecyclerView recyclerView;
    MyRecyclerViewAdapter mMyRecyclerViewAdapter;
    List<Hymns> listSections;
    MainActivity mMainActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mMainActivity = this;
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle("SDA Hymnal");
        }

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        mDatabaseHelper = new DatabaseHelper(getApplicationContext());
        try {
            mDatabaseHelper.createDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        listSections = mDatabaseHelper.getAllSections();

        if(listSections != null){
            mMyRecyclerViewAdapter = new MyRecyclerViewAdapter(getApplicationContext(), mMainActivity, listSections);
            recyclerView.setAdapter(mMyRecyclerViewAdapter);
        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

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

    public void sectionClicked(int position) {
        String section = listSections.get(position).getSection();
        int firstHymn = listSections.get(position).getFirstHymn();
        int lastHymn = listSections.get(position).getLastHymn();
        Intent intent = new Intent(this, HymnList.class);
        intent.putExtra("HYMN_SECTION", section);
        intent.putExtra("HYMN_SECTION_FIRST", firstHymn);
        intent.putExtra("HYMN_SECTION_LAST", lastHymn);
        startActivity(intent);
    }
}
