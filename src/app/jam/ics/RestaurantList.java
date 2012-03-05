package app.jam.ics;

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;


public class RestaurantList extends ListActivity {
	
	RestaurantDataSource restaurantDataSource;
	ListAdapter listAdapter;	
	
	public void onCreate(Bundle bundle)
	{
		super.onCreate(bundle);
		setContentView(R.layout.restaurantlist);
		
		Button filterButton = (Button)findViewById(R.id.filterInRestaurantsList);
		Button mapButton = (Button)findViewById(R.id.mapButton);
		
		OnClickListener l = new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				buttonOnClick(v);
			}
		}; 
		
		filterButton.setOnClickListener(l);
		mapButton.setOnClickListener(l);		
		
		restaurantDataSource = new RestaurantDataSource(this);		
		restaurantDataSource.open();
		List<Restaurant> values = restaurantDataSource.getAllRestaurants();
		
		/*if (getIntent().getSerializableExtra("app.jam.ics.restFilterList") != null) {//directed from filter page			
			values = getIntent().getSerializableExtra("app.jam.ics.restFilterList");
		} else {
			values = restaurantDataSource.getAllRestaurants();
		}	*/	
				
		restaurantDataSource.close();

		// Use the SimpleCursorAdapter to show the elements in a ListView		
		ArrayAdapter<Restaurant> adapter = new ArrayAdapter<Restaurant>(this,
//				android.R.id.text1, values);
				android.R.layout.simple_list_item_1, values);
		setListAdapter(adapter);
	}	
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Restaurant item = (Restaurant) getListAdapter().getItem(position);
		Intent intent = new Intent(this, MenuList.class);
		intent.putExtra("app.jam.ics.place", item.getName());		
		startActivity(intent);
	}
	
	public void buttonOnClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		case(R.id.filterInRestaurantsList):
			startActivity(new Intent(this, Filter.class));
			break;
		case(R.id.mapButton):
			startActivity(new Intent(this, CalorieTrackerActivity.class));
			break;
		}
	}
}
