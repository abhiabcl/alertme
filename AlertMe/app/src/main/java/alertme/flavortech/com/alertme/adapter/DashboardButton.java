package alertme.flavortech.com.alertme.adapter;

import android.app.Activity;

public class DashboardButton {
	private int iconResourceId;
	private int titleResourceId;
	private int id;
	private Class<? extends Activity> activityClass;

	public DashboardButton(int id, int iconResourceId, int titleResourceId) {
		this.iconResourceId = iconResourceId;
		this.titleResourceId = titleResourceId;
		this.id = id;
	}

	public void setActivityClass(Class<? extends Activity> activityClass) {
		this.activityClass = activityClass;
	}

	public int getId() {
		return id;
	}

	public int getIconResourceId() {
		return iconResourceId;
	}

	public int getTitleResourceId() {
		return titleResourceId;
	}

	public Class<? extends Activity> getActivityClass() {
		return activityClass;
	}
}
