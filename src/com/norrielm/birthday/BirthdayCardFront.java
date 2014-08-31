package com.norrielm.birthday;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.MotionEvent;

/**
 * Displays an image on the front of card and launches the inside on touch. 
 * 
 * @author norrielm
 */
public class BirthdayCardFront extends Activity {
	
	private Vibrator vb;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.birthday_card_front);
		vb = (Vibrator) getSystemService(VIBRATOR_SERVICE);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) { 
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			//Launch card intent
			Intent intent = new Intent();
			intent.setClassName(getPackageName(), getPackageName() + ".BirthdayCardInside");
			startActivity(intent);
			vb.vibrate(22);
			return true;
		}
		return false;
	}
}
