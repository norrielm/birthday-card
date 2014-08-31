package com.norrielm.birthday;

import android.graphics.Color;

/**
 * Custom message to be displayed inside the card.
 * 
 * @author norrielm
 */
public class BirthdayMessage {

	// The image inside the card can be set in the layout file.
	private String insideToName = "To birthday person,";
	private String insideFromName = "From the birthday sheep";
	private String insideMessage = "Happy birthday!";
	private int insideSound = R.raw.sheep; 
	private String insideFont = "handwritten.ttf";
	private int insideFontSize = 80;
	private int insideFontColour = Color.BLACK;

	public String getToInside() {
		return insideToName;
	}

	public void setToInside(String toInside) {
		this.insideToName = toInside;
	}

	public String getFromInside() {
		return insideFromName;
	}

	public void setFromInside(String fromInside) {
		this.insideFromName = fromInside;
	}

	public String getMessageInside() {
		return insideMessage;
	}

	public void setMessageInside(String messageInside) {
		this.insideMessage = messageInside;
	}

	public int getSoundInside() {
		return insideSound;
	}

	public void setSoundInside(int soundInside) {
		this.insideSound = soundInside;
	}

	public String getFontInside() {
		return insideFont;
	}

	public void setFontInside(String fontInside) {
		this.insideFont = fontInside;
	}

	public int getFontSizeInside() {
		return insideFontSize;
	}

	public void setFontSizeInside(int fontSizeInside) {
		this.insideFontSize = fontSizeInside;
	}

	public int getColourInside() {
		return insideFontColour;
	}

	public void setColourInside(int colourInside) {
		this.insideFontColour = colourInside;
	}
}
