package com.norrielm.birthday;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

/**
 * Displays text on the inside of the card. Image plays a sound onTouch.
 * 
 * @author norrielm
 */
public class BirthdayCardInside extends Activity {
	
	private Vibrator vb;
	private SoundPool soundPool;	 
	private int soundPoolId;
	private BirthdayMessage message;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.birthday_card);
		vb = (Vibrator) getSystemService(VIBRATOR_SERVICE);
		
		Birthday birthday = new Birthday();
		message = new BirthdayMessage();

		// The sound is played when the image inside the card is pressed.
		initSounds();
		ImageView sheep = (ImageView) findViewById(R.id.imageView);
		sheep.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				playSound(soundPoolId);
				vb.vibrate(new long[]{130,20,20}, -1);
				return true;
			}
		});

		int w = getWindowManager().getDefaultDisplay().getWidth();
		int h = getWindowManager().getDefaultDisplay().getHeight();
		ImageView card = (ImageView) findViewById(R.id.cardView);
		card.setImageBitmap(birthday.textAsBitmap(message.getToInside(), 
				message.getMessageInside(), message.getFromInside(), message.getColourInside(), 
				message.getFontSizeInside(), message.getFontInside(), w, h*2/3, getAssets()));
	}

	private void initSounds() {
		soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 100);
		soundPoolId = soundPool.load(this, message.getSoundInside(), 1);
	}

	private void playSound(int sound) {
		AudioManager mgr = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		int streamVolume = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);
		soundPool.play(sound, streamVolume, streamVolume, 1, 0, 1f);
	}
}
