package blkxltng.com.sdahymnal;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.io.IOException;
import java.util.List;

public class SearchResultsActivity extends AppCompatActivity {

    DatabaseHelper mDatabaseHelper;
    List<Hymns> listResults;
    HymnListAdapter mHymnListAdapter;
    SearchResultsActivity mSearchResultsActivity;
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            //use the query to search your data somehow
            mDatabaseHelper = new DatabaseHelper(getApplicationContext());
            try {
                mDatabaseHelper.createDatabase();
            } catch (IOException e) {
                e.printStackTrace();
            }

            listResults = mDatabaseHelper.searchHymns(query);

            mHymnListAdapter = new HymnListAdapter(getApplicationContext(), mSearchResultsActivity, listResults);

            if(listResults != null) {
                mListView = (ListView) findViewById(R.id.listview_search);
                mListView.setAdapter(mHymnListAdapter);
            }

        }

    }

}
