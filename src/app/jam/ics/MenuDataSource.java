package app.jam.ics;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.location.Location;
import android.view.Menu;

public class MenuDataSource {

	// Database fields
	private SQLiteDatabase database;
	
	
	public SQLiteDatabase getDatabase() {
		return database;
	}

	private MenuSQLiteHelper dbHelper;

	public MenuSQLiteHelper getDbHelper() {
		return dbHelper;
	}

	public MenuDataSource(Context context) {
		dbHelper = new MenuSQLiteHelper(context);
	}

	public void open() throws SQLException {

		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public void createMeal(long id, String name, String cal, String price, String place) {
		ContentValues cv = new ContentValues();
		cv.put(MenuSQLiteHelper.COLUMN_ID, id);
		cv.put(MenuSQLiteHelper.COLUMN_NAME, name);
		cv.put(MenuSQLiteHelper.COLUMN_CAL, cal);
		cv.put(MenuSQLiteHelper.COLUMN_PRICE, price);
		cv.put(MenuSQLiteHelper.COLUMN_PLACE, place);

		database.replace(MenuSQLiteHelper.TABLE_MEAL, null, cv);//TODO replace or create?
	}
	
	public String getName(long l) {
		// TODO Auto-generated method stub
		String[] columns = new String[] { MenuSQLiteHelper.COLUMN_ID,
				MenuSQLiteHelper.COLUMN_NAME, MenuSQLiteHelper.COLUMN_CAL, MenuSQLiteHelper.COLUMN_PRICE, MenuSQLiteHelper.COLUMN_PLACE};
		Cursor c = database.query(MenuSQLiteHelper.TABLE_MEAL, columns,
				MenuSQLiteHelper.COLUMN_ID + "=" + l, null, null, null, null);

		if (c != null & c.moveToFirst()) {
			c.moveToFirst();
			String name = c.getString(1);
			return name;
		}
		return null;
	}

	public String getCalorie(long l) {
		// TODO Auto-generated method stub

		String[] columns = new String[] { MenuSQLiteHelper.COLUMN_ID,
				MenuSQLiteHelper.COLUMN_NAME, MenuSQLiteHelper.COLUMN_CAL, MenuSQLiteHelper.COLUMN_PRICE, MenuSQLiteHelper.COLUMN_PLACE};

		Cursor c = database.query(MenuSQLiteHelper.TABLE_MEAL, columns,
				MenuSQLiteHelper.COLUMN_ID + "=" + l, null, null, null, null);

		if (c != null & c.moveToFirst()) {
			String cal = c.getString(2);
			return cal;
		}
		return null;
	}

	public List<Meal> getAllMeals(String place) {
		// TODO Auto-generated method stub
		
		List<Meal> mealList = new ArrayList<Meal>();
		String[] columns = new String[] { MenuSQLiteHelper.COLUMN_ID,
										  MenuSQLiteHelper.COLUMN_NAME, 
										  MenuSQLiteHelper.COLUMN_CAL, 
										  MenuSQLiteHelper.COLUMN_PRICE,
										  MenuSQLiteHelper.COLUMN_PLACE};
		
		Cursor c = database.query(MenuSQLiteHelper.TABLE_MEAL, columns, MenuSQLiteHelper.COLUMN_PLACE + " = ?", new String[]{place},
				null, null, null);
				
		int id = c.getColumnIndex(MenuSQLiteHelper.COLUMN_ID);// position 0
		int n = c.getColumnIndex(MenuSQLiteHelper.COLUMN_NAME);// position 1
		int cal = c.getColumnIndex(MenuSQLiteHelper.COLUMN_CAL);// position 2
		int pr = c.getColumnIndex(MenuSQLiteHelper.COLUMN_PRICE);// position 3
		int p = c.getColumnIndex(MenuSQLiteHelper.COLUMN_PLACE);// position 4
				
		if (c != null){
			for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {				
				mealList.add(new Meal(c.getLong(id), c.getString(n), c.getFloat(cal), c.getFloat(pr), c.getString(p)));
			}
		}
		return mealList;		
	}	
	
	public List<Meal> getAllMeals() {
		// TODO Auto-generated method stub
		
		List<Meal> mealList = new ArrayList<Meal>();
		String[] columns = new String[] { MenuSQLiteHelper.COLUMN_ID,
										  MenuSQLiteHelper.COLUMN_NAME, 
										  MenuSQLiteHelper.COLUMN_CAL, 
										  MenuSQLiteHelper.COLUMN_PRICE,
										  MenuSQLiteHelper.COLUMN_PLACE};
		
		Cursor c = database.query(MenuSQLiteHelper.TABLE_MEAL, columns, null, null,
				null, null, null);
				
		int id = c.getColumnIndex(MenuSQLiteHelper.COLUMN_ID);// position 0
		int n = c.getColumnIndex(MenuSQLiteHelper.COLUMN_NAME);// position 1
		int cal = c.getColumnIndex(MenuSQLiteHelper.COLUMN_CAL);// position 2
		int pr = c.getColumnIndex(MenuSQLiteHelper.COLUMN_PRICE);// position 3
		int p = c.getColumnIndex(MenuSQLiteHelper.COLUMN_PLACE);// position 4
				
		if (c != null){
			for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {				
				mealList.add(new Meal(c.getLong(id), c.getString(n), c.getFloat(cal), c.getFloat(pr), c.getString(p)));
			}
		}
		return mealList;		
	}	
	
	//to test
	public String getAllMealsAsString() {
		// TODO Auto-generated method stub	
		
		String[] columns = new String[] { MenuSQLiteHelper.COLUMN_ID,
										  MenuSQLiteHelper.COLUMN_NAME, 
										  MenuSQLiteHelper.COLUMN_CAL,
										  MenuSQLiteHelper.COLUMN_PRICE,
										  MenuSQLiteHelper.COLUMN_PLACE};
		Cursor c = database.query(MenuSQLiteHelper.TABLE_MEAL, columns, null,
				null, null, null, null);
		
		int id = c.getColumnIndex(MenuSQLiteHelper.COLUMN_ID);// position 0
		int n = c.getColumnIndex(MenuSQLiteHelper.COLUMN_NAME);// position 1
		int cal = c.getColumnIndex(MenuSQLiteHelper.COLUMN_CAL);// position 2
		int pr = c.getColumnIndex(MenuSQLiteHelper.COLUMN_PRICE);// position 3
		int p = c.getColumnIndex(MenuSQLiteHelper.COLUMN_PLACE);// position 4
		
		String result = "";
		
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			result = result + c.getString(id) + " " + c.getString(n) + " " +  c.getDouble(cal) + " " + c.getDouble(pr) + " " + c.getString(p) + "\n";
		}
		return result;		
	}	
	
	public List<Meal> getMealRecommendation(float calorie) {
		// TODO Auto-generated method stub
		System.out.println("menuds.getRestRecom()");		
		List<Meal> mealList = new ArrayList<Meal>();

		float from = calorie - 50;
		float to = calorie + 50;
		
		String menu_sql = "SELECT * FROM " + MenuSQLiteHelper.TABLE_MEAL +" WHERE calorie BETWEEN " + from +" AND " + to;		
		Cursor menu_c = database.rawQuery(menu_sql, null);		
				
		int id = menu_c.getColumnIndex(MenuSQLiteHelper.COLUMN_ID);// position 0
		int n = menu_c.getColumnIndex(MenuSQLiteHelper.COLUMN_NAME);// position 1
		int cal = menu_c.getColumnIndex(MenuSQLiteHelper.COLUMN_CAL);// position 2
		int pr = menu_c.getColumnIndex(MenuSQLiteHelper.COLUMN_PRICE);// position 3
		int p = menu_c.getColumnIndex(MenuSQLiteHelper.COLUMN_PLACE);// position 4				
		
		if (menu_c != null){
			for (menu_c.moveToFirst(); !menu_c.isAfterLast(); menu_c.moveToNext()) {
				System.out.println("meal recom: " + menu_c.getString(n));								
				mealList.add(new Meal(menu_c.getLong(id), menu_c.getString(n), menu_c.getFloat(cal), menu_c.getFloat(pr), menu_c.getString(p)));				
			}
		}		
		return mealList;
	}
	
	public List<Meal> filterMealsByPrice(String place, String price) {
		// TODO Auto-generated method stub
		
		List<Meal> mealList = new ArrayList<Meal>();
		
		String menu_sql = "SELECT * FROM " + MenuSQLiteHelper.TABLE_MEAL +" WHERE place = " + place + " AND price <= " + price;		
		Cursor c = database.rawQuery(menu_sql, null);
				
		int id = c.getColumnIndex(MenuSQLiteHelper.COLUMN_ID);// position 0
		int n = c.getColumnIndex(MenuSQLiteHelper.COLUMN_NAME);// position 1
		int cal = c.getColumnIndex(MenuSQLiteHelper.COLUMN_CAL);// position 2
		int pr = c.getColumnIndex(MenuSQLiteHelper.COLUMN_PRICE);// position 3
		int p = c.getColumnIndex(MenuSQLiteHelper.COLUMN_PLACE);// position 4
				
		if (c != null){
			for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {				
				mealList.add(new Meal(c.getLong(id), c.getString(n), c.getFloat(cal), c.getFloat(pr), c.getString(p)));
			}
		}
		return mealList;		
	}

	public ArrayList<Meal> filterMealsByPrice(String price) {
		// TODO Auto-generated method stub
		ArrayList<Meal> mealList = new ArrayList<Meal>();
	
		String menu_sql = "SELECT * FROM " + MenuSQLiteHelper.TABLE_MEAL +" WHERE price <= " + price;		
		Cursor c = database.rawQuery(menu_sql, null);
				
		int id = c.getColumnIndex(MenuSQLiteHelper.COLUMN_ID);// position 0
		int n = c.getColumnIndex(MenuSQLiteHelper.COLUMN_NAME);// position 1
		int cal = c.getColumnIndex(MenuSQLiteHelper.COLUMN_CAL);// position 2
		int pr = c.getColumnIndex(MenuSQLiteHelper.COLUMN_PRICE);// position 3
		int p = c.getColumnIndex(MenuSQLiteHelper.COLUMN_PLACE);// position 4
				
		if (c != null){
			for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {				
				mealList.add(new Meal(c.getLong(id), c.getString(n), c.getFloat(cal), c.getFloat(pr), c.getString(p)));
			}
		}
		return mealList;		
	}		
}
