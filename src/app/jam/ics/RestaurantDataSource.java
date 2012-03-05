package app.jam.ics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

/**
 * 
 * @author Maryam
 *
 */
public class RestaurantDataSource {

	// Database fields
	private SQLiteDatabase database;
	
	public SQLiteDatabase getDatabase() {
		return database;
	}

	private RestaurantSQLiteHelper dbHelper;
	

	private String[] allColumns = { RestaurantSQLiteHelper.COLUMN_ID,
									RestaurantSQLiteHelper.COLUMN_NAME,	
									RestaurantSQLiteHelper.COLUMN_LONG,
									RestaurantSQLiteHelper.COLUMN_LAT};

	public RestaurantDataSource(Context context) {
		dbHelper = new RestaurantSQLiteHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public void createRestaurant(long row, String name, String lo, String la) {
		ContentValues cv = new ContentValues();
		cv.put(RestaurantSQLiteHelper.COLUMN_ID, row);
		cv.put(RestaurantSQLiteHelper.COLUMN_NAME, name);
		cv.put(RestaurantSQLiteHelper.COLUMN_LONG, lo);
		cv.put(RestaurantSQLiteHelper.COLUMN_LAT, la);			
		
		database.replace(RestaurantSQLiteHelper.TABLE_RESTAURANT, null, cv);		
	}		

	//to test
	public String getAllRestaurantsAsString() {	
		
		Cursor c = database.query(RestaurantSQLiteHelper.TABLE_RESTAURANT, allColumns, null, null, null, null, null);
		String result = "";
		
		int id = c.getColumnIndex(RestaurantSQLiteHelper.COLUMN_ID);//position 0
		int n = c.getColumnIndex(RestaurantSQLiteHelper.COLUMN_NAME);//position 1		
		int lo = c.getColumnIndex(RestaurantSQLiteHelper.COLUMN_LONG);//position 2
		int la = c.getColumnIndex(RestaurantSQLiteHelper.COLUMN_LAT);//position 3		
		
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			result = result + c.getString(id) + " " + 
							  c.getString(n) + " " +							  
							  c.getString(lo) + " " +
							  c.getString(la) + "\n";
		}
		return result;
	}
	
	public List<Restaurant> getAllRestaurants() {
		// TODO Auto-generated method stub
		List<Restaurant> restList = new ArrayList<Restaurant>();		
		Cursor c = database.query(RestaurantSQLiteHelper.TABLE_RESTAURANT, allColumns, null, null, null, null, null);		
		
		int id = c.getColumnIndex(RestaurantSQLiteHelper.COLUMN_ID);//position 0
		int n = c.getColumnIndex(RestaurantSQLiteHelper.COLUMN_NAME);//position 1
		int lo = c.getColumnIndex(RestaurantSQLiteHelper.COLUMN_LONG);//position 2
		int la = c.getColumnIndex(RestaurantSQLiteHelper.COLUMN_LAT);//position 3		
		
		Restaurant rest;
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			rest = new Restaurant(c.getLong(id), c.getString(n), c.getDouble(lo), c.getDouble(la));		
			restList.add(rest);			
		}
		return restList;
	}	
	
	List<Restaurant> getRestaurantRecommendation(List<Meal> mealList){
		
		System.out.println("restDS.getRestRecom()");
		List<Restaurant> restList = new ArrayList<Restaurant>();
		
		Cursor rest_c;
		Restaurant rest;		
		
		for (int i=0; i<mealList.size(); i++){
			System.out.println("mealList.get(i).getPlace(): " + mealList.get(i).getPlace());
	
			rest_c = database.query(RestaurantSQLiteHelper.TABLE_RESTAURANT, allColumns, MenuSQLiteHelper.COLUMN_NAME + " = ?", new String[]{mealList.get(i).getPlace()}, null, null, null);
			System.out.println("rest_c: " + rest_c);
			
			int _id = rest_c.getColumnIndex(RestaurantSQLiteHelper.COLUMN_ID);//position 0
			int na = rest_c.getColumnIndex(RestaurantSQLiteHelper.COLUMN_NAME);//position 1		
			int lo = rest_c.getColumnIndex(RestaurantSQLiteHelper.COLUMN_LONG);//position 2
			int la = rest_c.getColumnIndex(RestaurantSQLiteHelper.COLUMN_LAT);//position 3		
			
			
			for (rest_c.moveToFirst(); !rest_c.isAfterLast(); rest_c.moveToNext()) {
				rest = new Restaurant(rest_c.getLong(_id), rest_c.getString(na), rest_c.getDouble(lo), rest_c.getDouble(la));				
				
				System.out.println("added rest: " + rest.getName());
				restList.add(rest);
			}
		}
		return restList;
	}

	public List<Restaurant> filterByDistance(String distance) {
		// TODO Auto-generated method stub
		List<Restaurant> restList = new ArrayList<Restaurant>();		
//		Cursor c = database.query(RestaurantSQLiteHelper.TABLE_RESTAURANT, allColumns, null, null, null, null, null);		
		String sql = "SELECT * FROM " + MenuSQLiteHelper.TABLE_MEAL +" WHERE long <= " + distance;		
		Cursor c = database.rawQuery(sql, null);
		
		int id = c.getColumnIndex(RestaurantSQLiteHelper.COLUMN_ID);//position 0
		int n = c.getColumnIndex(RestaurantSQLiteHelper.COLUMN_NAME);//position 1
		int lo = c.getColumnIndex(RestaurantSQLiteHelper.COLUMN_LONG);//position 2
		int la = c.getColumnIndex(RestaurantSQLiteHelper.COLUMN_LAT);//position 3		
		
		Restaurant rest;
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			rest = new Restaurant(c.getLong(id), c.getString(n), c.getDouble(lo), c.getDouble(la));		
			restList.add(rest);			
		}
		return restList;	
	}
}
