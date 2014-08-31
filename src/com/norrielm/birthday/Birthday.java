package com.norrielm.birthday;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Typeface;
import android.text.format.DateFormat;

/**
 * Represents a birthday
 * 
 * @author norrielm
 */
public class Birthday {

	private final int day;
	private final int month;
	private final int year;

	public Birthday() {
		Calendar today = Calendar.getInstance();
		this.day = today.get(Calendar.DATE);
		this.month = today.get(Calendar.MONTH);
		this.year = 1990;
	}

	public Birthday(int day, int month, int year) {
		this.day = day;
		this.month = month; // Jan = 0
		this.year = year;
	}

	public int getDay() {
		return day;
	}

	public int getMonth() {
		return month;
	}

	public int getYear() {
		return year;
	}

	public boolean isBirthdayToday(Calendar today) {
		return today.get(Calendar.MONTH) == month  
				&& today.get(Calendar.DATE) == day;
	}

	/**
	 * Return the next birthday age.
	 */
	public int getAge(Calendar today) {
		if (today.get(Calendar.MONTH) == month  
				&& today.get(Calendar.DATE) < day) {
			return (today.get(Calendar.YEAR) - year - 1);
		} else {
			return today.get(Calendar.YEAR) - year;
		}
	}

	/**
	 * Return the next birthday date in millis.
	 */
	public long getNextBirthday(Calendar today) {
		// Clone birthday
		Calendar nextBirthday = Calendar.getInstance();
		nextBirthday.set(Calendar.DATE, day);
		nextBirthday.set(Calendar.MONTH, month);
		if (today.get(Calendar.MONTH) == nextBirthday.get(Calendar.MONTH)  
				&& today.get(Calendar.DATE) < nextBirthday.get(Calendar.DATE)) {
			nextBirthday.set(Calendar.YEAR, today.get(Calendar.YEAR));
			return nextBirthday.getTimeInMillis();
		} else {
			nextBirthday.set(Calendar.YEAR, today.get(Calendar.YEAR) + 1);
			return nextBirthday.getTimeInMillis();
		}
	}
	
	/**
	 * Return the most recent birthday this year.
	 */
	public Calendar getMostRecentBirthday(int thisYear) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DATE, day);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.YEAR, thisYear);
		return cal; 
	}

	public String getNextBirthDate(Context c) {
		 java.text.DateFormat df = DateFormat.getLongDateFormat(c);
		 return df.format(getNextBirthday(Calendar.getInstance()));
	}

	/**
	 * Allow a card to be read a certain number of days after a birthday.
	 * @throws Exception Invalid allowance. Allowance must be  0 <= n < 365 (or 366 on a leap year)
	 */
	public boolean canOpenEnvelope(Calendar today, int extraAllowance) throws InvalidAllowanceException {
		int thisYear = today.get(Calendar.YEAR);
		// Make sure that we have valid input.
		GregorianCalendar c = new GregorianCalendar();
		if (extraAllowance < 0) {
			throw new InvalidAllowanceException("Allowance must be at least 0");
		} else if ((extraAllowance == 365 && !c.isLeapYear(thisYear)) || extraAllowance > 365) {
			throw new InvalidAllowanceException("Allowance must be less than one year");
		}
		Calendar mostRecentBirthday = getMostRecentBirthday(thisYear);
		// Find the latest date that the card can be read this year.
		Calendar maxDate = Calendar.getInstance();
		maxDate.set(Calendar.DATE, day);
		maxDate.set(Calendar.MONTH, month);
		maxDate.set(Calendar.YEAR, thisYear);
		maxDate.add(Calendar.DATE, extraAllowance);
		// Determine whether we should open the card.
		boolean isBirthdayToday = day == today.get(Calendar.DATE) 
								&& month == today.get(Calendar.MONTH);
		boolean birthdayHasBeen = mostRecentBirthday.before(today);
		boolean isStillBirthdayPeriod = today.before(maxDate) || today.equals(maxDate);
		return isBirthdayToday || (birthdayHasBeen && isStillBirthdayPeriod);
	}

	/** 
	 * Shared by BirthdayEnvelope and BirthdayCardInside. 
	 */
	public Bitmap textAsBitmap(String toText, String message, String fromText, 
			int colour, int textSize, String fontface, int w, int h, AssetManager assets) {
		// Set up the canvas
		Typeface font = Typeface.createFromAsset(assets, fontface);
		Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_4444);
		Canvas canvas = new Canvas(bitmap);
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setSubpixelText(true);
		paint.setTypeface(font);
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(colour);
		paint.setTextSize(textSize);
		// Write the text
		paint.setTextAlign(Align.LEFT);
		canvas.drawText(toText, 50, h*0.1f, paint);
		paint.setTextAlign(Align.CENTER);
		canvas.drawText(message, w/2, h*0.45f, paint);
		paint.setTextAlign(Align.CENTER);
		canvas.drawText(fromText, w/2, h*0.8f, paint);
		return bitmap;
	}

	/** 
	 * Used by the BirthdayCake widget. 
	 */
	public Bitmap ageAsBitmap(int colour, int textSize, String fontface, AssetManager assets) {
		// Set up the canvas
		Typeface font = Typeface.createFromAsset(assets, fontface);
		Bitmap bitmap = Bitmap.createBitmap(54, 54, Bitmap.Config.ARGB_4444);
		Canvas ageCanvas = new Canvas(bitmap);
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setSubpixelText(true);
		paint.setTypeface(font);
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(Color.WHITE);
		paint.setTextSize(textSize);
		// Write the text
		paint.setTextAlign(Align.RIGHT);
		ageCanvas.drawText(String.valueOf(getAge(Calendar.getInstance())), 56, 54, paint);
		return bitmap;
	}
}
