package com.point.contentprovidertest;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Point on 2017/5/15.
 */

public class MainActivity extends Activity {

	Button btnSave, btnRead, btnModify, btnDelete, btnCpRead;
	ListView showView;
	ArrayAdapter<String> arrayAdapter;
	ArrayList<String> strings = new ArrayList<>();

	private static final String TAG = "point-cptest";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		bindView();
		addClickListeners();
	}

	private void bindView() {
		btnSave = (Button) findViewById(R.id.button3);
		btnRead = (Button) findViewById(R.id.button4);
		btnModify = (Button) findViewById(R.id.button5);
		btnDelete = (Button) findViewById(R.id.button6);
		btnCpRead = (Button) findViewById(R.id.button7);
		showView = (ListView) findViewById(R.id.listview);
		strings.add("1");
		arrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, strings);
		showView.setAdapter(arrayAdapter);

	}

	private void addClickListeners() {
		btnSave.setOnClickListener(new MyOnclickListener());
		btnRead.setOnClickListener(new MyOnclickListener());
		btnModify.setOnClickListener(new MyOnclickListener());
		btnDelete.setOnClickListener(new MyOnclickListener());
		btnCpRead.setOnClickListener(new MyOnclickListener());
	}

	private void notifyListView() {
		strings.clear();
		Cursor cursor = DBUtils.getInstance2().readInfos();
		Log.i(TAG, "cursor count:" + cursor.getCount());
		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			String string = "cursor key&value :" + cursor.getString(0) + " , " + cursor.getString(1);
			strings.add(string);
			Log.i(TAG, string);
		}
		cursor.close();
		arrayAdapter.notifyDataSetChanged();

	}

	class MyOnclickListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.button3:
					ArrayList<ContentValues> cvs = new ArrayList<>();
					ContentValues contentValues = new ContentValues();
					contentValues.put("key", "key1");
					contentValues.put("value", "value1");
					cvs.add(contentValues);
					contentValues = new ContentValues();
					contentValues.put("key", "key2");
					contentValues.put("value", "value2");
					cvs.add(contentValues);
					contentValues = new ContentValues();
					contentValues.put("key", "key3");
					contentValues.put("value", "value3");
					cvs.add(contentValues);
					DBUtils.getInstance2().insertInfo(cvs);
					notifyListView();
					break;
				case R.id.button4:
					notifyListView();
					break;
				case R.id.button5:
					DBUtils.getInstance2().updateInfo("key3", "value5");
					DBUtils.getInstance2().updateInfo("key2", "value6");
					notifyListView();
					break;
				case R.id.button6:
					DBUtils.getInstance2().deleteOneInfo("key2");
					notifyListView();
					break;
				case R.id.button7:
					ContentResolver contentResolver = getContentResolver();
					Cursor cursor = contentResolver.query(Uri.parse("content://com.point.contentprovidertest/info"), null, "key=?", new String[]{"key1"}, null);
					strings.clear();
					for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
						strings.add(cursor.getString(0) + " " + cursor.getString(1));
					}
					arrayAdapter.notifyDataSetChanged();
					if (cursor != null)
						cursor.close();
					break;
				default:
					break;
			}
		}
	}

}
