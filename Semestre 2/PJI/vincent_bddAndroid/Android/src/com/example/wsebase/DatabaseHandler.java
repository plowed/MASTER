package com.example.wsebase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {
	
	private static final String TABLE_PERSONNE="personne";
	private static final String TABLE_NUMERO="numero";
	
	private static final String SQL_PERSONNE="CREATE TABLE "+TABLE_PERSONNE+"(" +
										"id_personne INTEGER AUTO_INCREMENT PRIMARY KEY," +
										"nom VARCHAR(50) NOT NULL," +
										"prenom VARCHAR(50) NOT NULL)";
	private static final String SQL_NUMERO="CREATE TABLE "+TABLE_NUMERO+"(" +
										"numero CHAR(14) PRIMARY KEY," +
										"id_personne INTERGER NOT NULL," +
										"FOREIGN KEY (id_personne) REFERENCES "+TABLE_PERSONNE+"(id_personne))";

	public DatabaseHandler(Context context, String name, CursorFactory factory,	int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
		getReadableDatabase().execSQL("PRAGMA foreign_keys = ON");
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(SQL_PERSONNE);
		db.execSQL(SQL_NUMERO);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}
