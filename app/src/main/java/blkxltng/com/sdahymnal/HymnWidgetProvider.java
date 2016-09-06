package blkxltng.com.sdahymnal;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * Created by firej on 9/5/2016.
 */
public class HymnWidgetProvider extends AppWidgetProvider {

    DatabaseHelper mDatabaseHelper;
    Context mContext;
    List<Hymns> hymn;
    Random mRandom = new Random();
    String hymnName = "";
    String verse1 = "";
    String verse2 = "";
    String verse3 = "";
    String verse4 = "";
    String verse5 = "";
    String verse6 = "";
    String refrain = "";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        mDatabaseHelper = new DatabaseHelper(context);
        try {
            mDatabaseHelper.createDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        final int count = appWidgetIds.length;

        for (int i = 0; i < count; i++) {

            int widgetId = appWidgetIds[i];

            int hymnId = mRandom.nextInt(41);
            hymn = mDatabaseHelper.getHymn(hymnId);

            String title = "Hymn of the Day: #" + hymn.get(0).getNumber();

            getVerses();

            String verses = arrangeHymn();

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_hymn);
            remoteViews.setTextViewText(R.id.textview_widgetTitle, title);
            remoteViews.setTextViewText(R.id.textview_widgetBody, verses);

            Intent intent = new Intent(context, HymnWidgetProvider.class);
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
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