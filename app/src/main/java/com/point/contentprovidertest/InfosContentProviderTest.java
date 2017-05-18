package com.point.contentprovidertest;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by Point on 2017/5/18.
 */

public class InfosContentProviderTest extends ContentProvider {
	UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);


	@Override
	public boolean onCreate() {
		matcher.addURI("com.point.contentprovidertest", "info", 1);
		return false;

	}

	@Nullable
	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		if (matcher.match(uri) == 1) {
			SQLiteDatabase db = new DBStructure(MyApplication.getContext()).getReadableDatabase();
			return db.query("infos", null, selection, selectionArgs, null, null, sortOrder);
		}
		return null;
	}

	@Nullable
	@Override
	public String getType(Uri uri) {
		if (matcher.match(uri) == 1) {
			return "vnd/android.cursor.item/com.point.contentprovidertest";
		}
		throw new IllegalArgumentException("wrong uri:" + uri);
	}

	@Nullable
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		return null;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		return 0;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		return 0;
	}
}
