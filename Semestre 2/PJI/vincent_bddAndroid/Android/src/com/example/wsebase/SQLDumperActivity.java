package com.example.wsebase;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.TextView;

import com.example.wsebase.WseActivity;


public class SQLDumperActivity extends WseActivity {
	
	private SQLiteDatabase db=null;
	
	public void setSQLiteDatabase(SQLiteDatabase db){
		this.db=db;
	}
	
	public SQLiteDatabase getSQLiteDatabase(){
		return db;
	}
	
	protected List<Table> getListeTable(){
		ArrayList<Table> listTable=new ArrayList<Table>();
		Table table;
		String sql="SELECT name FROM sqlite_master WHERE type='table'" +
				" UNION ALL " +
				"SELECT name FROM sqlite_temp_master WHERE  type='table'";
		Cursor cur=db.rawQuery(sql, null);
		while(cur.moveToNext()){
			table=new Table(cur.getString(0));
			listTable.add(table);
			table.addAllChamps(getListeChamps(table));
			setForeignKey(table);
		}
		cur.close();
		return listTable;
	}
	
	protected List<Champs> getListeChamps(Table table){
		ArrayList<Champs> listChamps=new ArrayList<Champs>();
		Champs c;
		boolean pk;
		String sql="PRAGMA table_info("+table.getName()+")";
		Cursor cur=db.rawQuery(sql, null);
		while(cur.moveToNext()){
			pk=cur.getInt(5)==1;
			c=new Champs(cur.getString(1),cur.getString(2), pk);
			c.setNotNull(cur.getInt(3)==1);
			c.setValDefaut(cur.getString(4));
			listChamps.add(c);
		}
		cur.close();
		return listChamps;
	}
	
	protected void setForeignKey(Table table){
		Champs c;
		String sql="PRAGMA foreign_key_list("+table.getName()+")";
		/*
		 * Champs de la requétes :
		 * id, seq, table, from, to, on_update, on_delete, match
		 */
		Cursor cur=db.rawQuery(sql, null);
		while(cur.moveToNext()){
			//Récupération des champs
			c=new Champs(cur.getString(4), cur.getString(2), cur.getString(3));
			table.setForeignKey(c);
		}
		cur.close();
	}
	
	
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		List<Table> table=getListeTable();
		JSONObject json=new JSONObject();
		JSONArray js2=new JSONArray();
		for(int i=0;i<table.size();i++){
			js2.put(table.get(i).toJSONObject());
		}
		try {
			json.put("action", "structureBDD");
			json.put("table", js2);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MyApplication.getInstance().sendMessageOnWse(json);
	}

	// On est obligé d'implémenter cette méthode
	// si on ne veut rien faire à l'arrivé de message
	// laisser la méthode vide
	@Override
	public void newMessageReceived(final JSONObject message) {

					
	}

}
