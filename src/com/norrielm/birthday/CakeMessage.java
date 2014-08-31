package com.norrielm.birthday;

/**
 * Message to be displayed in the widget and as a notification on the birthday.
 * 
 * @author norrielm
 */
public class CakeMessage {

	// The widget images can be set in the layout file.
	private String notificationTitle = "Birthday cake";
	private String notificationTickerText = "Happy birthday!";
	private String notificationMessage = "Happy birthday!";
	private int widgetIcon = R.drawable.cake;
	private String widgetFont = "minecraft.ttf";

	public String getNotificationTitle() {
		return notificationTitle;
	}

	public void setNotificationTitle(String notificationTitle) {
		this.notificationTitle = notificationTitle;
	}

	public String getNotificationMessage() {
		return notificationMessage;
	}

	public void setNotificationMessage(String notificationMessage) {
		this.notificationMessage = notificationMessage;
	}

	public String getNotificationTickerText() {
		return notificationTickerText;
	}

	public void setNotificationTickerText(String notificationTickerText) {
		this.notificationTickerText = notificationTickerText;
	}

	public int getWidgetIcon() {
		return widgetIcon;
	}

	public void setWidgetIcon(int widgetIcon) {
		this.widgetIcon = widgetIcon;
	}

	public String getWidgetFont() {
		return widgetFont;
	}

	public void setWidgetFont(String widgetFont) {
		this.widgetFont = widgetFont;
	}
	
}
