package com.norrielm.birthday;

/**
 * Custom message to be displayed in the stamp and on the envelope.
 * 
 * @author norrielm
 */
public class EnvelopeMessage {

	// The stamp images can be set in the layout file.
	private String stampMessageTitle = "A message from the Queen appears.";
	private String stampMessageNotBirthday = "'It's not your birthday yet', the Queen.";
	private String stampMessageBirthday = "'Happy birthday', the Queen.";
	private String stampDialogNegative = "Drop.";
	private String stampDialogPositive = "Pick it up.";
	// Envelope fields
	private String envelopeName = "The birthday person";
	private int envelopeTextSize = 100;
	private String envelopeFont = "handwritten2.ttf";

	public String getStampMessageTitle() {
		return stampMessageTitle;
	}

	public void setStampMessageTitle(String stampMessageTitle) {
		this.stampMessageTitle = stampMessageTitle;
	}

	public String getStampMessageNotBirthday() {
		return stampMessageNotBirthday;
	}

	public void setStampMessageNotBirthday(String stampMessageNotBirthday) {
		this.stampMessageNotBirthday = stampMessageNotBirthday;
	}

	public String getStampMessageBirthday() {
		return stampMessageBirthday;
	}

	public void setStampMessageBirthday(String stampMessageBirthday) {
		this.stampMessageBirthday = stampMessageBirthday;
	}

	public String getStampDialogNegative() {
		return stampDialogNegative;
	}

	public void setStampDialogNegative(String stampDialogNegative) {
		this.stampDialogNegative = stampDialogNegative;
	}

	public String getStampDialogPositive() {
		return stampDialogPositive;
	}

	public void setStampDialogPositive(String stampDialogPositive) {
		this.stampDialogPositive = stampDialogPositive;
	}

	public String getEnvelopeName() {
		return envelopeName;
	}

	public void setEnvelopeName(String envelopeName) {
		this.envelopeName = envelopeName;
	}

	public int getEnvelopeTextSize() {
		return envelopeTextSize;
	}

	public void setEnvelopeTextSize(int envelopeTextSize) {
		this.envelopeTextSize = envelopeTextSize;
	}

	public String getEnvelopeFont() {
		return envelopeFont;
	}

	public void setEnvelopeFont(String envelopeFont) {
		this.envelopeFont = envelopeFont;
	}
}
