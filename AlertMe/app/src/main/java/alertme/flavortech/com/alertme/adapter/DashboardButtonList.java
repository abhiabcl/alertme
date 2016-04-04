package alertme.flavortech.com.alertme.adapter;

import android.app.Activity;

import java.util.ArrayList;

public class DashboardButtonList extends ArrayList<DashboardButton> {
	private static final long serialVersionUID = -5925549356637930752L;

	public DashboardButtonList add(int iconResourceId, int titleResourceId, Class<? extends Activity> activityClass) {
		DashboardButton button = new DashboardButton(size(), iconResourceId, titleResourceId);
		button.setActivityClass(activityClass);
		add(button);

		return this;
	}

	public Class<? extends Activity> getActivityClass(int pos) {
		return get(pos).getActivityClass();
	}
}