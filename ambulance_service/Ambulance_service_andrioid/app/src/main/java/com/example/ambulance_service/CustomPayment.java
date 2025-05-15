package com.example.ambulance_service;

import android.app.Activity;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomPayment extends ArrayAdapter<String>  {

	 private Activity context;       //for to get current activity context
	    SharedPreferences sh;
	private String[] driver;
	private String[] place;
//	private String[] phone;
//	private String[] details;
//	private String[] amount;
//	private String[] date;
//	private String[] status;




	 public CustomPayment(Activity context, String[] dr, String[] pls) {
	        //constructor of this class to get the values from main_activity_class

	        super(context, R.layout.custom_payment,dr);
	        this.context = context;

		    this.driver = dr;
		 	this.place = pls;
//		 this.phone = ph;
//		 this.details = deta;
//		 this.amount = amt;
//		 this.date = dates;
//		 this.status = stat;





	    }

	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	                 //override getView() method

	        LayoutInflater inflater = context.getLayoutInflater();
	        View listViewItem = inflater.inflate(R.layout.custom_payment, null, true);
			//cust_list_view is xml file of layout created in step no.2

//	        ImageView im = (ImageView) listViewItem.findViewById(R.id.imageView1);
	        TextView t1=(TextView)listViewItem.findViewById(R.id.TextView1);

		TextView t2=(TextView)listViewItem.findViewById(R.id.TextView2);
//			TextView t3=(TextView)listViewItem.findViewById(R.id.TextView3);
//			TextView t4=(TextView)listViewItem.findViewById(R.id.TextView4);
//			TextView t5=(TextView)listViewItem.findViewById(R.id.TextView5);
//			TextView t6=(TextView)listViewItem.findViewById(R.id.TextView6);
//			TextView t7=(TextView)listViewItem.findViewById(R.id.TextView7);

			t1.setText(driver[position]);
			t2.setText(place[position]);
//			t3.setText(phone[position]);
//			t4.setText("Details : "+details[position]);
//			t5.setText("Amount : "+amount[position]);
//			t6.setText("Date : "+date[position]);
//			t7.setText("Status : "+status[position]);

//	       Toast.makeText(context, pth, Toast.LENGTH_LONG).show();

	        return  listViewItem;
	    }

		private TextView setText(String string) {
			// TODO Auto-generated method stub
			return null;
		}
}