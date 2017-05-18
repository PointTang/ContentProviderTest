package com.point.contentprovidertest;

import android.app.Application;
import android.content.Context;

/**
 * Created by Point on 2017/5/18.
 */

public class MyApplication extends Application {

	private static Context context ;


	@Override
	public void onCreate() {
		super.onCreate();
		setContext(getApplicationContext());
	}

	public static Context getContext(){
		return context;
	}

	public static final void setContext(Context context){
		MyApplication.context = context;
	}
}
