package alertme.flavortech.com.alertme.adapter;

/**
 * Created by etbdefi on 12/31/2015.
 */

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import alertme.flavortech.com.alertme.R;
import static alertme.flavortech.com.alertme.util.AlertMeConstant.*;

public class TripviewAdapter extends BaseAdapter {

    public ArrayList<HashMap> list;
    Activity activity;

    public TripviewAdapter(Activity activity, ArrayList<HashMap> list) {
        super();
        this.activity = activity;
        this.list = list;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    private class ViewHolder {
        TextView tvTripTitle;
        TextView tvTripPickup;
        TextView tvTripDrop;
        TextView tvTripDuration;
        TextView tvRemDuration;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        // TODO Auto-generated method stub
        ViewHolder holder;
        LayoutInflater inflater = activity.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listview_row_bill, null);
            holder = new ViewHolder();
            holder.tvTripTitle = (TextView) convertView
                    .findViewById(R.id.FirstText);
            holder.tvTripPickup = (TextView) convertView
                    .findViewById(R.id.SecondText);
            holder.tvTripDrop = (TextView) convertView
                    .findViewById(R.id.ThirdText);
            holder.tvTripDuration = (TextView) convertView
                    .findViewById(R.id.FourthText);
            holder.tvRemDuration = (TextView) convertView
                    .findViewById(R.id.FifthText);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        HashMap map = list.get(position);
        if (map != null && holder != null) {
            holder.tvTripTitle.setText((String) map.get(TRIP_TITLE));
            holder.tvTripPickup.setText((String) map.get(TRIP_PICKUP));
            holder.tvTripDrop.setText((String) map.get(TRIP_DROP));
            holder.tvTripDuration.setText((String) map.get(TRIP_DURATION));
            holder.tvRemDuration.setText((String) map.get(TRIP_STATUS));
        }
//        final String paymentDetails = (String) holder.tvTripDuration.getText();
//
//        if (paymentDetails.equalsIgnoreCase("Paid")) {
//            holder.tvTripDuration.setTextColor(Color.GREEN);
//        }
//
//        if (paymentDetails.equalsIgnoreCase("Unpaid")) {
//
//            holder.tvTripDuration.setTextColor(Color.RED);
//        }
//
//        if (paymentDetails.equalsIgnoreCase("In-Process")) {
//
//            holder.tvTripDuration.setTextColor(Color.YELLOW);
//        }

//        holder.tvTripDuration.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                Toast.makeText(activity.getApplicationContext(),"Show Bill payment details: "
//                        + paymentDetails,Toast.LENGTH_LONG).show();
//
//                Intent pPaymentDetailsActivity = new Intent(activity
//                        .getBaseContext(), PaymentDetailsActivity.class);
//
//                pPaymentDetailsActivity.putExtra("PAY_STATUS", paymentDetails);
//
//                activity.startActivity(pPaymentDetailsActivity);
//            }
//        });
//
//        String percentage = (String) holder.tvRemDuration.getText();
//
//        if (percentage.startsWith("+")) {
//            holder.tvRemDuration.setTextColor(Color.GREEN);
//        }
//        if (percentage.startsWith("-")) {
//            holder.tvRemDuration.setTextColor(Color.RED);
//        }
//
        return convertView;
    }

    // public Button GetTextView(int ResourceId) {
    // Button tv = null;
    //
    // LayoutInflater inflater = activity.getLayoutInflater();
    // View convertView = inflater.inflate(R.layout.listview_row, null);
    // if (convertView != null) {
    // tv = (Button) convertView.findViewById(ResourceId);
    // }
    // return tv;
    // }
}