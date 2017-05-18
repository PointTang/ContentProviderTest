package com.point.contentresolvertest;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by Point on 2017/5/18.
 */

public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		TextView textView = (TextView) findViewById(R.id.textview);
		textView.setText(ReadOtherParams.getValueForKey1(this));


	}
}
