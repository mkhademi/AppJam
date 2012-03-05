package app.jam.ics;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;

/**
 * 
 * @author Maryam
 * 
 */
public class MenuList extends ListActivity {
	MenuDataSource menuDataSource;
	ListAdapter listAdapter;
	TextView tv;

	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.restaurantmenu);

		Button filterButton = (Button) findViewById(R.id.filterButtonInMenus);
		Button mapButton = (Button) findViewById(R.id.mapButtonInMenus);

		OnClickListener l = new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				buttonOnClick(v);
			}
		};

		/*if (getIntent().getSerializableExtra("app.jam.ics.restFilterList") != null) {// directed from filter page
			values = getIntent().getSerializableExtra(
					"app.jam.ics.restFilterList");
		} else {
			values = restaurantDataSource.getAllRestaurants();
		}*/

		filterButton.setOnClickListener(l);
		mapButton.setOnClickListener(l);

		String place = getIntent().getStringExtra("app.jam.ics.place");
		tv = (TextView) findViewById(R.id.textView2);
		tv.setText(place);

		menuDataSource = new MenuDataSource(this);
		menuDataSource.open();
		List<Meal> values = menuDataSource.getAllMeals(place);
		menuDataSource.close();

		ArrayList<String> mealNames = new ArrayList<String>();
		for (int i = 0; i < values.size(); i++) {
			System.out.println("values: " + values.get(i));
			mealNames.add(values.get(i).getName());
		}

		// Use the SimpleCursorAdapter to show the elements in a ListView
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, mealNames);
		setListAdapter(adapter);
	}

	public void buttonOnClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case (R.id.filterButtonInMenus):
			startActivity(new Intent(this, Filter.class));
			break;
		case (R.id.mapButtonInMenus):
			startActivity(new Intent(this, CalorieTrackerActivity.class));
			break;
		}
	}
}
