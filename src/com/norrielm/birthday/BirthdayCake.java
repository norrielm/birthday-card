package com.norrielm.birthday;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.IBinder;
import android.widget.RemoteViews;

/**
 * Sets up the birthday cake widget.
 * 
 * @author norrielm
 */
public class BirthdayCake extends Service {

	private static final int REQUEST_CODE = 42;

	private CakeMessage cake;

	@Override
	public void onStart(Intent i, int startId) {
		RemoteViews widgetView = new RemoteViews(getPackageName(), R.layout.birthday_widget);
		ComponentName widgetName = new ComponentName(this, BirthdayWidget.class);

		// Open the envelope on click.
		Intent intent = new Intent();
		intent.setClassName(getPackageName(), getPackageName() + ".BirthdayEnvelope");
		PendingIntent pendingIntent = PendingIntent.getActivity(this, REQUEST_CODE, intent, 
				PendingIntent.FLAG_UPDATE_CURRENT);
		widgetView.setOnClickPendingIntent(R.id.widgetView, pendingIntent);
		
		Birthday birthday = new Birthday();
		cake = new CakeMessage();

		// Update widget with current age
		widgetView.setImageViewBitmap(R.id.imageView, birthday.ageAsBitmap(Color.WHITE, 32, 
				cake.getWidgetFont(), getAssets()));
		AppWidgetManager appWidgetManager = 
			AppWidgetManager.getInstance(this.getApplicationContext());
		appWidgetManager.updateAppWidget(widgetName, widgetView);
		
		if (birthday.isBirthdayToday(Calendar.getInstance())) {
			// Notify if birthday is today
			notifyBirthday(cake.getNotificationTickerText(), cake.getNotificationTitle(),
					cake.getNotificationMessage(), 0);
		} else {
			// Set an alarm to schedule the notification
			AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
			Intent birthdayIntent = new Intent();
			birthdayIntent.setClassName(getPackageName(), getPackageName() + ".WidgetService");
			PendingIntent pendingBirthdayIntent = PendingIntent.getActivity(this, 0, birthdayIntent, 
					PendingIntent.FLAG_CANCEL_CURRENT);
			manager.set(AlarmManager.RTC, birthday.getNextBirthday(Calendar.getInstance()), 
					pendingBirthdayIntent);
		}
		
		super.onStart(intent, startId);
	}

	private void notifyBirthday(String tickerText, String title, String text, long when) {
		NotificationManager mNotificationManager = 
			(NotificationManager) getSystemService(NOTIFICATION_SERVICE);

		Notification notification = new Notification(cake.getWidgetIcon(), tickerText, when);
		Intent notificationIntent = new Intent(this, BirthdayEnvelope.class);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 
				PendingIntent.FLAG_UPDATE_CURRENT);

		notification.setLatestEventInfo(getApplicationContext(), title, text, contentIntent);
		mNotificationManager.notify(42, notification);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}
