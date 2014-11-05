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
		int testInt = 5;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT Location FROM Locations WHERE MACOne = " + Integer.toString(testInt), null);
		
		if(cursor != null)
		{
			cursor.moveToFirst();
		}
		
		Location location = new Location(cursor.getString(0));
		return location;
	}
}
