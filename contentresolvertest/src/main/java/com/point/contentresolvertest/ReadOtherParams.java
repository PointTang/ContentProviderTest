package com.point.contentresolvertest;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

/**
 * Created by Point on 2017/5/18.
 */

public class ReadOtherParams {

	public static String getValueForKey1(Context context){
		return getValue(context,"key1");
	}

	private static String getValue(Context context, String key){
		ContentResolver contentResolver = context.getContentResolver();
		Cursor cursor = contentResolver.query(Uri.parse("content://com.point.contentprovidertest/info"), null, "key=?", new String[]{key}, null);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			String value = cursor.getString(1);
			return value;
		}
		return null;
	}
}
