package app.jam.ics;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class PersonDataSource {

	// Database fields
	private SQLiteDatabase database;
	private PersonSQLiteHelper dbHelper;

	public PersonSQLiteHelper getDbHelper() {
		return dbHelper;
	}

	public PersonDataSource(Context context) {
		dbHelper = new PersonSQLiteHelper(context);
	}

	public void open() throws SQLException {

		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public void createOrReplacePerson(long id, String gender, String age,
			String feet, String inches, String weight) {
		ContentValues cv = new ContentValues();
		cv.put(PersonSQLiteHelper.COLUMN_ID, id);
		cv.put(PersonSQLiteHelper.COLUMN_GENDER, gender);
		cv.put(PersonSQLiteHelper.COLUMN_AGE, age);
		cv.put(PersonSQLiteHelper.COLUMN_FEET, feet);
		cv.put(PersonSQLiteHelper.COLUMN_INCHES, inches);
		cv.put(PersonSQLiteHelper.COLUMN_WEIGHT, weight);

		database.replace(PersonSQLiteHelper.TABLE_USER, null, cv);
	}

	public String getData() {
		// TODO Auto-generated method stub		
		
		String[] allColumns = new String[]{ PersonSQLiteHelper.COLUMN_ID,
				PersonSQLiteHelper.COLUMN_GENDER, PersonSQLiteHelper.COLUMN_AGE,
				PersonSQLiteHelper.COLUMN_FEET, PersonSQLiteHelper.COLUMN_INCHES,
				PersonSQLiteHelper.COLUMN_WEIGHT};
		Cursor c = database.query(PersonSQLiteHelper.TABLE_USER, allColumns, null,
				null, null, null, null);
		String result = "";

		int id = c.getColumnIndex(PersonSQLiteHelper.COLUMN_ID);// position 0
		int g = c.getColumnIndex(PersonSQLiteHelper.COLUMN_GENDER);// position 1
		int a = c.getColumnIndex(PersonSQLiteHelper.COLUMN_AGE);// position 2
		int f = c.getColumnIndex(PersonSQLiteHelper.COLUMN_FEET);// position 3
		int i = c.getColumnIndex(PersonSQLiteHelper.COLUMN_INCHES);// position 4
		int w = c.getColumnIndex(PersonSQLiteHelper.COLUMN_WEIGHT);// position 5

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			result = result + c.getString(id) + " " + c.getString(g) + " "
					+ c.getString(a) + " " + c.getString(f) + " "
					+ c.getString(i) + " " + c.getString(w) + "\n";
		}
		return result;
	}

	public void deletePerson(long id) {
		database.delete(PersonSQLiteHelper.TABLE_USER,
				PersonSQLiteHelper.COLUMN_ID + " = " + id, null);
	}

	public void updatePerson(long row, String gender, String age, String feet,
			String inches, String weight) throws SQLException {
		// TODO Auto-generated method stub
		ContentValues cvUpdate = new ContentValues();
		cvUpdate.put(PersonSQLiteHelper.COLUMN_GENDER, gender);
		cvUpdate.put(PersonSQLiteHelper.COLUMN_AGE, age);
		cvUpdate.put(PersonSQLiteHelper.COLUMN_FEET, feet);
		cvUpdate.put(PersonSQLiteHelper.COLUMN_INCHES, inches);
		cvUpdate.put(PersonSQLiteHelper.COLUMN_WEIGHT, weight);
		database.update(PersonSQLiteHelper.TABLE_USER, cvUpdate,
				PersonSQLiteHelper.COLUMN_ID + "=" + row, null);
	}
	
	public String getGender() {
		// TODO Auto-generated method stub
		
		 String[] columns = new String[]{ PersonSQLiteHelper.COLUMN_ID,
				PersonSQLiteHelper.COLUMN_GENDER, PersonSQLiteHelper.COLUMN_AGE,
				PersonSQLiteHelper.COLUMN_FEET, PersonSQLiteHelper.COLUMN_INCHES,
				PersonSQLiteHelper.COLUMN_WEIGHT};
				
				Cursor c = database.query(PersonSQLiteHelper.TABLE_USER, columns, PersonSQLiteHelper.COLUMN_ID + "=" + 1,
						null, null, null, null);

				if (c != null & c.moveToFirst()) {
					c.moveToFirst();
					String gender = c.getString(1);
					return gender;
				}
				return null;
	}

	public CharSequence getAge() {
		// TODO Auto-generated method stub
		
		String[] columns = new String[]{ PersonSQLiteHelper.COLUMN_ID,
					PersonSQLiteHelper.COLUMN_GENDER, PersonSQLiteHelper.COLUMN_AGE,
					PersonSQLiteHelper.COLUMN_FEET, PersonSQLiteHelper.COLUMN_INCHES,
					PersonSQLiteHelper.COLUMN_WEIGHT};
		Cursor c = database.query(PersonSQLiteHelper.TABLE_USER, columns, PersonSQLiteHelper.COLUMN_ID + "=" + 1,
				null, null, null, null);		
		
		if (c != null & c.moveToFirst()) {			
			String age = c.getString(2);		
			return age;
		}		
		return null;
	}

	public String getFeet() {
		// TODO Auto-generated method stub
		 String[] columns = new String[]{ PersonSQLiteHelper.COLUMN_ID,
					PersonSQLiteHelper.COLUMN_GENDER, PersonSQLiteHelper.COLUMN_AGE,
					PersonSQLiteHelper.COLUMN_FEET, PersonSQLiteHelper.COLUMN_INCHES,
					PersonSQLiteHelper.COLUMN_WEIGHT};
		Cursor c = database.query(PersonSQLiteHelper.TABLE_USER, columns, PersonSQLiteHelper.COLUMN_ID + "=" + 1,
				null, null, null, null);

		if (c != null & c.moveToFirst()) {
			c.moveToFirst();
			String feet = c.getString(3);
			return feet;
		}
		return null;
	}

	public String getInches() {
		// TODO Auto-generated method stub
		 String[] columns = new String[]{ PersonSQLiteHelper.COLUMN_ID,
					PersonSQLiteHelper.COLUMN_GENDER, PersonSQLiteHelper.COLUMN_AGE,
					PersonSQLiteHelper.COLUMN_FEET, PersonSQLiteHelper.COLUMN_INCHES,
					PersonSQLiteHelper.COLUMN_WEIGHT};
		Cursor c = database.query(PersonSQLiteHelper.TABLE_USER, columns, PersonSQLiteHelper.COLUMN_ID + "=" + 1,
				null, null, null, null);

		if (c != null & c.moveToFirst()) {
			c.moveToFirst();
			String inches = c.getString(4);
			return inches;
		}
		return null;
	}

	public CharSequence getWeight() {
		// TODO Auto-generated method stub
		 String[] columns = new String[]{ PersonSQLiteHelper.COLUMN_ID,
					PersonSQLiteHelper.COLUMN_GENDER, PersonSQLiteHelper.COLUMN_AGE,
					PersonSQLiteHelper.COLUMN_FEET, PersonSQLiteHelper.COLUMN_INCHES,
					PersonSQLiteHelper.COLUMN_WEIGHT};
		Cursor c = database.query(PersonSQLiteHelper.TABLE_USER, columns, PersonSQLiteHelper.COLUMN_ID + "=" + 1,
				null, null, null, null);

		if (c != null & c.moveToFirst()) {
			c.moveToFirst();
			String weight = c.getString(5);
			return weight;
		}
		return null;
	}		
}
