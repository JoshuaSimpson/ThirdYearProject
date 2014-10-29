package com.simpson.lost;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;


public class DataBase extends SQLiteAssetHelper{

	private static final String DATABASE_NAME = "LocationDatabase";
	private static final int DATABASE_VERSION = 1;
	private static final String[] COLUMNS = {"Location","MACOne"};
	
	public DataBase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		
		
    }
	
	public Location getLocation(String MAC)
	{
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query("Locations", // a. table
	            COLUMNS, // b. column names
	            "MACOne", // c. selections 
	            new String[] { String.valueOf(MAC) }, // d. selections args
	            null, // e. group by
	            null, // f. having
	            null, // g. order by
	            null);
		
		if(cursor != null)
		{
			cursor.moveToFirst();
		}
		
		Location location = new Location(cursor.getString(0));
		
		return location;
	}
}
