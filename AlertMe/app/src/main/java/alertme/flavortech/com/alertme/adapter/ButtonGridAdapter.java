package alertme.flavortech.com.alertme.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;

import alertme.flavortech.com.alertme.R;
import alertme.flavortech.com.alertme.activity.FeedbackActivity;
import alertme.flavortech.com.alertme.activity.ProfileActivity;
import alertme.flavortech.com.alertme.activity.SettingActivity;
import alertme.flavortech.com.alertme.activity.StartActivity;
import alertme.flavortech.com.alertme.activity.TripHistoryActivity;
import alertme.flavortech.com.alertme.view.DashboardButtonView;


public class ButtonGridAdapter extends BaseAdapter implements ListAdapter {
	private DashboardButtonList data;
	private Context context;

	public ButtonGridAdapter(Context context) {
		super();
		this.context = context;
		this.data = getDashboardButtons();
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return new DashboardButtonView(context, data.get(position));
	}

	public DashboardButtonList getDashboardButtons() {

		DashboardButtonList instance = new DashboardButtonList();

		instance.add(R.drawable.profile, R.string.profiletitle,
                ProfileActivity.class);
		instance.add(R.drawable.setting, R.string.settingtitle,
				SettingActivity.class);
		instance.add(R.drawable.start, R.string.starttitle,
                StartActivity.class);
        //If started disable it.
		instance.add(R.drawable.stop, R.string.triphistory,
                TripHistoryActivity.class);
		instance.add(R.drawable.feedback, R.string.feedbacktitle,
				FeedbackActivity.class);
		
		return instance;
	}
}
