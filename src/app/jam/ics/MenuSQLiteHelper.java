package app.jam.ics;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * 
 * @author Maryam
 *
 */
public class MenuSQLiteHelper extends SQLiteOpenHelper {
	
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_CAL = "calorie";
	public static final String COLUMN_PRICE = "price";
	public static final String COLUMN_PLACE = "place";
	
	public static final String TABLE_MEAL = "mealTable";
	public static final String DATABASE_NAME = "meal.db";
	private static final int DATABASE_VERSION = 1;

	// Database creation sql statement
	private static final String DATABASE_CREATE = "CREATE TABLE " + TABLE_MEAL + " (" 
			+ COLUMN_ID  	+ " INTEGER PRIMARY KEY AUTOINCREMENT, " 
			+ COLUMN_NAME 	+ " TEXT NOT NULL, " 
			+ COLUMN_CAL 	+ " TEXT NOT NULL, "
			+ COLUMN_PRICE 	+ " TEXT NOT NULL, " 
			+ COLUMN_PLACE 	+ " TEXT NOT NULL);";
	

	public MenuSQLiteHelper(Context context) {		
		super(context, DATABASE_NAME, null, DATABASE_VERSION);		
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		System.out.println("MealSQLHelper.onCreate()");
		database.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(MenuSQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS" + TABLE_MEAL);
		onCreate(db);		
	}
}
