package com.norrielm.birthday;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

/**
 * A birthday cake widget.
 * 
 * @author norrielm
 */
public class BirthdayWidget extends AppWidgetProvider {

	@Override 
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
        // Create an intent to update the service onClick.
		Intent intent = new Intent(context, BirthdayCake.class);
		PendingIntent pendingIntent = PendingIntent.getService(context.getApplicationContext(), 0, 
        		intent, PendingIntent.FLAG_UPDATE_CURRENT);
		RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                R.layout.birthday_widget);
        remoteViews.setOnClickPendingIntent(R.id.imageView, pendingIntent);
        // Update the view.
        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
        // Start the service to update the widget.
        context.startService(intent);
	}
}
