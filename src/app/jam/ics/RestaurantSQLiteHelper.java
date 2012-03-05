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
public class RestaurantSQLiteHelper extends SQLiteOpenHelper {

	public static final String TABLE_RESTAURANT = "restaurantTable";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_LONG = "long";
	public static final String COLUMN_LAT = "lat";

	private static final String DATABASE_NAME = "rest.db";
	private static final int DATABASE_VERSION = 1;

	// Database creation sql statement
	private static final String DATABASE_CREATE = "CREATE TABLE " + TABLE_RESTAURANT + " (" 
			+ COLUMN_ID   + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
			+ COLUMN_NAME + " TEXT NOT NULL, "
			+ COLUMN_LONG + " TEXT NOT NULL, " 
			+ COLUMN_LAT  + " TEXT NOT NULL);";					

	public RestaurantSQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(RestaurantSQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS" + TABLE_RESTAURANT);
		onCreate(db);
	}
}
