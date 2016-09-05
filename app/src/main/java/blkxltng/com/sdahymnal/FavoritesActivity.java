package blkxltng.com.sdahymnal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.IOException;
import java.util.List;

public class FavoritesActivity extends AppCompatActivity {

    List<Hymns> listFavorites;
    DatabaseHelper mDatabaseHelper;
    HymnListAdapter mHymnListAdapter;
    FavoritesActivity mFavoritesActivity;
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        getSupportActionBar().setTitle("Your Favorites");

        //use the query to search your data somehow
        mDatabaseHelper = new DatabaseHelper(getApplicationContext());
        try {
            mDatabaseHelper.createDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        listFavorites = mDatabaseHelper.getFavoriteHymns();

        mHymnListAdapter = new HymnListAdapter(getApplicationContext(), mFavoritesActivity, listFavorites);

        if(listFavorites != null) {
            mListView = (ListView) findViewById(R.id.listview_favorites);
            mListView.setAdapter(mHymnListAdapter);
        }

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("LOG_TAG", "Clicked");
                Intent intent = new Intent(getApplicationContext(), HymnActivity.class);
                int hymnNumber = listFavorites.get(i).getNumber();
                String hymnName = listFavorites.get(i).getTitle();
                intent.putExtra("HYMN_NUMBER", hymnNumber);
                intent.putExtra("HYMN_NAME", hymnName);
                intent.putExtra("HYMN_ID", i);
                startActivity(intent);
                finish();
            }
        });

    }
}
