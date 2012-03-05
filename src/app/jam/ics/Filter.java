package app.jam.ics;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Filter extends Activity implements OnClickListener
{
	
	Location userLocation;
	
	EditText etPrice, etDistance;
	RestaurantDataSource restaurantDataSource;
	MenuDataSource menuDataSource;
	
	
	public void onCreate(Bundle bundle)
	{
		super.onCreate(bundle);
		setContentView(R.layout.filter);		
		
//		userLocation = (Location) getIntent().getSerializableExtra("app.jam.ics.position");
		
			
		etPrice = (EditText) findViewById(R.id.etDistance);
		etDistance = (EditText) findViewById(R.id.etPrice);		
		
		Button cancelButton = (Button)findViewById(R.id.cancelButton);
		cancelButton.setOnClickListener(this);
		
		Button searchButton1 = (Button)findViewById(R.id.searchButton1);
		searchButton1.setOnClickListener(this);		
	}

	public void onClick(View v) 
	{
		// TODO Auto-generated method stub

		switch(v.getId())
		{
		case(R.id.cancelButton):
			startActivity(new Intent(this, CalorieTrackerActivity.class));
			break;
		case(R.id.searchButton1):				
					
			/*Location restLocation;
			String distance = etDistance.getText().toString();
			String price = etPrice.getText().toString();
			
			Intent intent = new Intent(this, RestaurantList.class);
			ArrayList<Meal> filteredMealList = new ArrayList<Meal>();
			List<Restaurant> filteredRestList = new ArrayList<Restaurant>();
			
			if (!distance.equals("") && price.equals("")) {//filter by distance only
				System.out.println("1");
				restaurantDataSource = new RestaurantDataSource(this);
				restaurantDataSource.open();			
				filteredRestList = restaurantDataSource.filterByDistance(distance);
				restaurantDataSource.close();
				
				intent.putExtra("app.jam.ics.restFilteredList", filteredRestList);						
				startActivity(intent);
				
			} else if (distance.equals("") && !price.equals("")){//filter by price only
				System.out.println("2");
				
				menuDataSource = new MenuDataSource(this);
				menuDataSource.open();
				filteredMealList = menuDataSource.filterMealsByPrice(price);
				menuDataSource.close();
				
				intent = new Intent(this, MenuList.class);
				intent.putExtra("app.jam.ics.mealFilterList", filteredMealList);			
				startActivity(intent);
				
			} else if (!distance.equals("") && !price.equals("")){
				System.out.println("3");
				menuDataSource = new MenuDataSource(this);
				menuDataSource.open();
				filteredMealList = menuDataSource.filterMealsByPrice(restName, price);
				menuDataSource.close();
				
				intent. putExtra("app.jam.ics.restFilteredList", filteredRestByDistancePrice);					
				startActivity(intent);
				
			} else if (distance.equals("") && price.equals("")){
				Toast.makeText(
						getApplicationContext(),
						"Please enter price and distance information to narrow down the result!",
						Toast.LENGTH_LONG).show();
			}					
				
			break;*/
		}
	}
}