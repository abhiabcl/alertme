package alertme.flavortech.com.alertme.view;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import alertme.flavortech.com.alertme.R;
import alertme.flavortech.com.alertme.adapter.DashboardButton;


public class DashboardButtonView extends LinearLayout implements OnClickListener {
	private DashboardButton button;

	public DashboardButtonView(Context context) {
		super(context);
	}

	public DashboardButtonView(Context context, DashboardButton button) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.dashboard_button, this);
		initializeViews(button.getIconResourceId(), button.getTitleResourceId());
		this.button = button;
		setOnClickListener(this);
	}


	private void initializeViews(int iconResourceId, int titleResourceId) {
		ImageView iconView = (ImageView) findViewById(R.id.ButtonIcon);
		TextView titleView = (TextView) findViewById(R.id.ButtonText);

		if (iconView != null) {
			iconView.setImageResource(iconResourceId);
			iconView.setOnClickListener(this);
		}

		if (titleView != null) {
			titleView.setText(titleResourceId);
		}
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(getContext(), button.getActivityClass());
		getContext().startActivity(intent);
	}
}
