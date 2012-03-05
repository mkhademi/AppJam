package app.jam.ics;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Maryam, fernando Starting point of app
 * 
 */
public class CaloriesActivity extends Activity implements OnClickListener,
		OnItemSelectedListener {
	EditText editAge, editWeight;
	Spinner feet, inches, gender;
	private PersonDataSource personDataSource;
	private RestaurantDataSource restaurantDataSource;
	private MenuDataSource menuDataSource;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		System.out.println("CaloriesActivity.onCreate()");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// get information of the user
		editAge = (EditText) findViewById(R.id.ageentrance);
		feet = (Spinner) findViewById(R.id.feetSpinner);
		inches = (Spinner) findViewById(R.id.inchSpinner);
		editWeight = (EditText) findViewById(R.id.editWeight);
		gender = (Spinner) findViewById(R.id.GenderSpinner);

		gender.setOnItemSelectedListener(this);
		Button register = (Button) findViewById(R.id.registerbutton);
		register.setOnClickListener(this);		
		
		
		initPersonDB();		
		initRestaurantDB();
	}

	private void initPersonDB() {
		personDataSource = new PersonDataSource(this);
		personDataSource.open();
		
		if (personDataSource.getGender() != null) {
			for (int i = 0; i < gender.getCount(); i++) {
				String item = (String) gender.getItemAtPosition(i);
				if (personDataSource.getGender().equals(item)) {
					gender.setSelection(i);
				}
			}
		}
		
		if (personDataSource.getAge() != null) {
			editAge.setText(personDataSource.getAge());			
		}

		if (personDataSource.getFeet() != null) {			
			for (int i = 0; i < feet.getCount(); i++) {
				String item = (String) feet.getItemAtPosition(i);
				if (personDataSource.getFeet().equals(item)) {
					feet.setSelection(i);
				}
			}
		}

		if (personDataSource.getInches() != null) {
			for (int i = 0; i < inches.getCount(); i++) {
				String item = (String) inches.getItemAtPosition(i);
				if (personDataSource.getInches().equals(item)) {
					inches.setSelection(i);
				}
			}
		}
		
		if (personDataSource.getWeight() != null) {
			editWeight.setText(personDataSource.getWeight());			
		}
		
		personDataSource.close();
	}

	private void initRestaurantDB() {
		// TODO Auto-generated method stub
		System.out.println("CaloriesActivity.initRest()");
		try {
			restaurantDataSource = new RestaurantDataSource(this);
			restaurantDataSource.open();			
			
			restaurantDataSource.createRestaurant(1, "Subway", "-117.844566", "33.645938");						
			restaurantDataSource.createRestaurant(2, "PandaExpress", "-117.842674", "33.649396");
			restaurantDataSource.createRestaurant(3, "JambaJuice", "-117.842207", "33.64897");
			restaurantDataSource.createRestaurant(4, "JavaCity", "-117.841209", "33.643423");
			restaurantDataSource.createRestaurant(5, "Starbucks", "-117.841939", "33.648403");
			restaurantDataSource.createRestaurant(6, "VeggieGrill", "-117.837775", "33.650382");
			restaurantDataSource.createRestaurant(7, "LeeSandwiches", "-117.839686", "33.659637");
			restaurantDataSource.createRestaurant(9, "In-n-out", "-117.840584", "33.650077");	
			
//			System.out.println("restaurants: "
//					+ restaurantDataSource.getAllRestaurantsAsString());// to test
			
			restaurantDataSource.close();			
			initMenuDB();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void initMenuDB() {
		// TODO Auto-generated method stub
		System.out.println("initMenuDB()");
		menuDataSource = new MenuDataSource(this);
		menuDataSource.open();	
		
		menuDataSource.createMeal(1, "6inch_Chipotle_Chicken_and_Chesse", "450", "5.20",  "Subway");
		menuDataSource.createMeal(2, "6inch_Meatball_Marinara", "480", "4.22",  "Subway");
		menuDataSource.createMeal(3, "6inch_Buffalo_Chiken", "420", "5.20",  "Subway");
		menuDataSource.createMeal(4, "Black_Forest_Ham_Salad", "110", "6.50",  "Subway");
		menuDataSource.createMeal(5, "Chocolate_Chip_Cookie_per_serving", "220", "0.33",  "Subway");
		
		menuDataSource.createMeal(6, "Chicken & Hummus", "270", "5.29",  "Starbucks");
		menuDataSource.createMeal(7, "Chicken Lettuce Wraps", "360", "6.95",  "Starbucks");
		menuDataSource.createMeal(8, "Butter Croissant", "310", "1.95",  "Starbucks");
		menuDataSource.createMeal(9, "Turkey & Swiss Sandwich", "390", "5.75",  "Starbucks");
		menuDataSource.createMeal(10, "Peach Raspberry Yogurt Parfait", "300", "3.45",  "Starbucks");
		
		menuDataSource.createMeal(11, "Southwestern Chicken Chorizo Wrap", "310", "3.10",  "Jamba Juice");
		menuDataSource.createMeal(12, "Awesome Apple Cinnamon Yogurt (small)", "210", "3.75",  "Jamba Juice");
		menuDataSource.createMeal(13, "Chedder Tomato Twist", "240", "1.95",  "Jamba Juice");
		menuDataSource.createMeal(14, "A Sixteen Berry Topper", "460", "3.75",  "Jamba Juice");
		menuDataSource.createMeal(15, "Fruit Blueberry & BlackBerry Oatmeal", "60", "3.18",  "Jamba Juice");
		
		menuDataSource.createMeal(16, "Jambon & Pork Roll", "410", "3.33",  "LeeSandwiches");
		menuDataSource.createMeal(17, "Cured Pork and Pate", "540", "2.95",  "LeeSandwiches");
		menuDataSource.createMeal(18, "Shrimp Rolls (Shrimp  & pork) per serving", "70", "2.99",  "LeeSandwiches");
		menuDataSource.createMeal(19, "Turkey Bacon Club", "680", "5.15",  "LeeSandwiches");
		menuDataSource.createMeal(20, "Ham, Egg & Chese Croissant", "370", "4.62",  "LeeSandwiches");
		
		menuDataSource.createMeal(21, "Café Latte (Vanilla)", "275", "2.69",  "JavaCity");
		menuDataSource.createMeal(22, "Caramel Macchiato", "315", "3.49",  "JavaCity");
		menuDataSource.createMeal(23, "Bewley's Iced Tea", "0.00", "2.09",  "JavaCity");
		menuDataSource.createMeal(24, "100% Fruit Smoothie--Mango", "312", "4.39",  "JavaCity");
		
		menuDataSource.createMeal(25, "Carne Asada", "140", "8.95",  "VeggieGrill");
		menuDataSource.createMeal(26, "Grillin' Chicken", "120", "8.95",  "VeggieGrill");
		menuDataSource.createMeal(27, "Baja Fiesta Salad", "585", "8.95",  "VeggieGrill");
		menuDataSource.createMeal(28, "Veggie Steak Burger", "140", "8.95",  "VeggieGrill");
		
		menuDataSource.createMeal(29, "Chow Mein (9.4oz)", "490", "2.00",  "PandaExpress");
		menuDataSource.createMeal(30, "Kung Pao Chicken (5.8oz)", "240", "3.00",  "PandaExpress");
		menuDataSource.createMeal(31, "Broccoli Beef (5.4oz)", "120", "3.00",  "PandaExpress");
		menuDataSource.createMeal(32, "Sweat & Sour Pork (6.2oz)", "390", "3.00",  "PandaExpress");
		menuDataSource.createMeal(33, "Veggie Spring Roll (3.4oz/2 rolls)", "160", "1.50",  "PandaExpress");
                
		menuDataSource.createMeal(34, "Blueberry Tart", "80", "0.97", "Yogurtland");
		menuDataSource.createMeal(35, "Chocolate Twilight", "80", "0.97", "Yogurtland");
		menuDataSource.createMeal(36, "French Vanilla", "80", "0.97", "Yogurtland");
		menuDataSource.createMeal(37, "Plain Tart", "110", "0.97", "Yogurtland");
		menuDataSource.createMeal(38, "Taro", "110", "0.97", "Yogurtland");		

		menuDataSource.createMeal(39, "Hamburger", "390", "1.90", "In-n-out");
		menuDataSource.createMeal(40, "Cheeseburger w/ Onion", "480", "2.20", "In-n-out");
		menuDataSource.createMeal(41, "Double-Double w/ Onion", "670", "3.20", "In-n-out");
		menuDataSource.createMeal(42, "French Fries", "395", "1.50", "In-n-out");
		menuDataSource.createMeal(43, "Strawberry Shake", "590", "1.99", "In-n-out");
		
		
//		System.out.println("meals: "
//				+ menuDataSource.getAllMealsAsString());// to test
		
		menuDataSource.close();
	}

	public void onClick(View view) {
		System.out.println("CaloriesActivity.onClick()");
		// TODO Auto-generated method stub

		switch (view.getId()) {
		case R.id.registerbutton:

			boolean didItWork = true;
			String g = (String) gender.getSelectedItem();
			String a = editAge.getText().toString();
			String f = (String) feet.getSelectedItem();
			String i = (String) inches.getSelectedItem();
			String w = editWeight.getText().toString();

			try {	
				personDataSource.open();
				personDataSource.createOrReplacePerson(1, g, a, f, i, w);				
//				System.out.println("person= " + personDataSource.getData());// to test
				personDataSource.close();
			} catch (Exception e) {
				didItWork = false;

				String error = e.toString();
				Toast.makeText(getApplicationContext(), "Error: " + error,
						Toast.LENGTH_LONG).show();
			} finally {
				if (didItWork) {

					/*Toast.makeText(
							getApplicationContext(),
							"User information has been successfully registered!",
							Toast.LENGTH_LONG).show();*/
				}
			}
			Intent intent = new Intent(this, CalorieTrackerActivity.class);
			startActivity(intent);
			break;
		}
	}

	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
	}

	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
	}

/*	@Override
	protected void onResume() {
		personDataSource.open();// TODO needed or not?
		restaurantDataSource.open();
		menuDataSource.open();
		super.onResume();
	}

	@Override
	protected void onPause() {
		personDataSource.close();// TODO correct or not?
		restaurantDataSource.close();
		menuDataSource.close();
		super.onPause();
	}*/
}