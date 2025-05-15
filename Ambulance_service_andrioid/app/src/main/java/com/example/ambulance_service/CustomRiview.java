package com.example.ambulance_service;

import android.app.Activity;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomRiview extends ArrayAdapter<String>  {

	 private Activity context;       //for to get current activity context
	    SharedPreferences sh;
	private String[] fname;
	private String[] lname;
	private String[] places;
	private String[] driver;
	private String[] place;
	private String[] phone;





	 public CustomRiview(Activity context,String[] fname, String[] lname, String[] places, String[] dr, String[] pls, String[] ph) {
	        //constructor of this class to get the values from main_activity_class

	        super(context, R.layout.custom_review,dr);
	        this.context = context;



		 this.fname = fname;
		 this.lname = lname;
		 this.places = places;
		 this.phone = ph;
		 this.driver = dr;
		 this.place = pls;







	 }

	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	                 //override getView() method

	        LayoutInflater inflater = context.getLayoutInflater();
	        View listViewItem = inflater.inflate(R.layout.custom_review, null, true);
			//cust_list_view is xml file of layout created in step no.2

//	        ImageView im = (ImageView) listViewItem.findViewById(R.id.imageView1);
	        TextView t1=(TextView)listViewItem.findViewById(R.id.TextView1);

		TextView t2=(TextView)listViewItem.findViewById(R.id.TextView2);
			TextView t3=(TextView)listViewItem.findViewById(R.id.TextView3);
			TextView t4=(TextView)listViewItem.findViewById(R.id.TextView4);
			TextView t5=(TextView)listViewItem.findViewById(R.id.TextView5);
			TextView t6=(TextView)listViewItem.findViewById(R.id.TextView6);

			t1.setText(fname[position]);
			t2.setText(lname[position]);
			t3.setText(places[position]);
			t4.setText(driver[position]);
			t5.setText(place[position]);
			t6.setText(phone[position]);


//	       Toast.makeText(context, pth, Toast.LENGTH_LONG).show();

	        return  listViewItem;
	    }

		private TextView setText(String string) {
			// TODO Auto-generated method stub
			return null;
		}
}