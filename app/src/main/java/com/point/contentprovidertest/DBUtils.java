package com.point.contentprovidertest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.ListView;

import java.util.List;
import java.util.Map;

import static java.lang.Thread.yield;

/**
 * Created by Point on 2017/5/18.
 */

public class DBUtils {
	private static DBStructure dbHolder;
	private static SQLiteDatabase db = null;
	private static DBUtils dbUtils = null;
	private static Context context = MyApplication.getContext();

	public DBUtils() {
		dbHolder = new DBStructure(context);
		db = dbHolder.getWritableDatabase();
	}

	public static DBUtils getInstance2() {
		synchronized (DBUtils.class) {
			if (dbUtils == null) {
				dbUtils = new DBUtils();
			}
		}
		return dbUtils;
	}

	public static DBUtils getInstance() {
		return SingletonHolder.INSTANCE;
	}

	static class SingletonHolder {
		public static final DBUtils INSTANCE = new DBUtils();
	}

	public boolean isExistKey(String key){
		Cursor c = db.query("infos", null, "key=?", new String[]{key}, null, null, null);
		if (c.getCount() > 0){
			c.close();
			return true;
		}
		return false;
	}

	public Cursor readInfos() {
		Cursor c = db.query("infos", new String[]{"key", "value"}, null, null, null, null, null);
//		Cursor c = db.rawQuery("select * from infos", null);
		return c;
	}

	public Cursor readInfos(String[] keys) {
		Cursor c = db.query("infos", null, "key=?", keys, null, null, null);
		return c;
	}

	public String readOneInfo(String key) {
		String value = "";
		Cursor c = db.query("infos", new String[]{"value"}, "key=?", new String[]{key}, null, null, null);
		if (c.moveToFirst()) {
			value = c.getString(0);
		}
		c.close();
		return value;
	}

	public boolean insertInfo(String key, String value) {
		ContentValues contentValues = new ContentValues();
		contentValues.put("key", key);
		contentValues.put("value", value);
		long id = db.insert("infos", null, contentValues);
		return id == -1 ? false : true;
	}

	public boolean insertInfo(List<ContentValues> contentValues) {
		boolean result = false;
		for (ContentValues cv : contentValues) {
			Log.i("DBUtils", cv.toString());
			boolean temp = insertInfo(cv);
			result = result && temp;
		}
		return result;
	}

	public boolean insertInfo(ContentValues contentValues) {
		long id = db.insert("infos", null, contentValues);
		return id == -1 ? false : true;
	}

	public boolean updateOneInfo(String key, String value) {
		ContentValues contentValues = new ContentValues();
		contentValues.put("key", key);
		contentValues.put("value", value);
		int id = db.update("infos", contentValues, "key=?", new String[]{key});
		return id <= 0 ? false : true;
	}

	public boolean updateInfo(String key, String value){
		boolean result = updateOneInfo(key,value);
		if (!result){
			result = insertInfo(key,value);
		}
		return  result;
	}

	public void deleteOneInfo(String key) {
		db.delete("infos", "key=?", new String[]{key});
	}
}
