package app.jam.ics;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * 
 * @author Maryam
 *
 */
public class PersonSQLiteHelper extends SQLiteOpenHelper {

	
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_GENDER = "gender";
	public static final String COLUMN_AGE = "age";
	public static final String COLUMN_FEET = "feet";
	public static final String COLUMN_INCHES = "inches";
	public static final String COLUMN_WEIGHT = "weight";

	public static final String TABLE_USER = "userTable";
	public static final String DATABASE_NAME = "user.db";
	private static final int DATABASE_VERSION = 1;

	// Database creation sql statement
	private static final String DATABASE_CREATE = "CREATE TABLE " + TABLE_USER + " (" 
			+ COLUMN_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
			+ COLUMN_GENDER + " TEXT NOT NULL, " 
			+ COLUMN_AGE 	+ " TEXT NOT NULL, "
			+ COLUMN_FEET + " TEXT NOT NULL, "
			+ COLUMN_INCHES + " TEXT NOT NULL, "
			+ COLUMN_WEIGHT + " TEXT NOT NULL);";
		
	public PersonSQLiteHelper(Context context) {		
		super(context, DATABASE_NAME, null, DATABASE_VERSION);		
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		System.out.println("PersonSQLHelper.onCreate()");
		database.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(PersonSQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS" + TABLE_USER);
		onCreate(db);		
	}
}
