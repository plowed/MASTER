package com.example.wsebase;

import org.json.JSONObject;


import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class Home extends SQLDumperActivity {
	
	 protected final static int VERSION = 2;
	 protected final static String NOM = "database.db";
	 
	 protected DatabaseHandler dbh;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		dbh=new DatabaseHandler(this, NOM, null, VERSION);
		setSQLiteDatabase(dbh.getWritableDatabase());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

}
