package com.norrielm.birthday;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

/**
 * Envelope for the birthday card. The envelope only opens when it is a birthday.
 * 
 * @author norrielm
 */
public class BirthdayEnvelope extends Activity {

	private static final int DAYS_ALLOWED_TO_VIEW = 5;
	private boolean CAN_OPEN_ENVELOPE = false;
	private Vibrator vb;
	private ImageView card;
	private int h;
	private int w;
	private EnvelopeMessage envelope;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.birthday_envelope);
		vb = (Vibrator) getSystemService(VIBRATOR_SERVICE);
		
		w = getWindowManager().getDefaultDisplay().getWidth();
		h = getWindowManager().getDefaultDisplay().getHeight();

		card = (ImageView) findViewById(R.id.cardView); 
		card.setOnTouchListener(new OnTouchListener() {
			
			public boolean onTouch(View v, MotionEvent event) {
				if (CAN_OPEN_ENVELOPE && 
						event.getAction() == MotionEvent.ACTION_DOWN) {
					//Launch card intent
					Intent intent = new Intent();
					intent.setClassName(getPackageName(), getPackageName() + ".BirthdayCardFront");
					startActivity(intent);
					vb.vibrate(22);
					return true;
				}
				return false;
			}
		});
	}

	@Override
	public void onResume() {
		super.onResume();
		Intent intent = new Intent();
		intent.setClassName(getPackageName(), getPackageName() + ".WidgetService");
		startService(intent);

		Birthday birthday = new Birthday();
		envelope = new EnvelopeMessage();

		try {
			CAN_OPEN_ENVELOPE = birthday.canOpenEnvelope(Calendar.getInstance(), DAYS_ALLOWED_TO_VIEW);
		} catch (InvalidAllowanceException e) {
			// Invalid number of days to view.
		}
		
		ImageView stamp = (ImageView) findViewById(R.id.stampView);
		stamp.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(final View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					vb.vibrate(20);
					AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
					builder.setMessage(CAN_OPEN_ENVELOPE ? 
								envelope.getStampMessageBirthday() : 
								envelope.getStampMessageNotBirthday())
							.setCancelable(true)
							.setPositiveButton(envelope.getStampDialogPositive(), new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int id) {
									dialog.dismiss();
								}
							})
							.setNegativeButton(envelope.getStampDialogNegative(), new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int id) {
									dialog.dismiss();
								}
							});
					AlertDialog alert = builder.create();
					alert.setTitle(envelope.getStampMessageTitle());
					alert.setIcon(R.drawable.stamp);
					alert.show();
					return true;
				}
				return false;
			}
		});

		card.setImageBitmap(birthday.textAsBitmap("", envelope.getEnvelopeName(), 
				(CAN_OPEN_ENVELOPE ? "(Open)"  : 
									 "(Open on " + birthday.getNextBirthDate(this) + ")"), 
				Color.BLACK, envelope.getEnvelopeTextSize(), envelope.getEnvelopeFont(), 
				w, h*2/3, getAssets()));
	}
}
