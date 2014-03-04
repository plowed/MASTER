package com.example.wsebase;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Table {
	private String name;
	private List<Champs> champs;
	
	public Table(String name){
		this.name=name;
		champs=new ArrayList<Champs>();
	}
	
	public String getName(){
		return name;
	}
	
	public boolean addChamps(Champs champs){
		if(champs!=null){
			return this.champs.add(champs);
		}
		return false;
	}
	
	public boolean addAllChamps(List<Champs> champs){
		if(champs!=null){
			return this.champs.addAll(champs);
		}
		return false;
	}
	
	public JSONObject toJSONObject(){
		JSONObject json=new JSONObject();
		try {
			json.put("nomTable", getName());
			json.put("champs",champsToJSONArray());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}
	
	public void setForeignKey(Champs c){
		int len=champs.size();
		for(int i=0;i<len;i++){
			Champs cOld=champs.get(i);
			if(cOld.equals(c)){
				cOld.setFk(true);
				cOld.setTableFk(c.getTableFk());
				cOld.setChampsFk(c.getChampsFk());
				break;
			}
		}
	}
	
	protected JSONArray champsToJSONArray(){
		JSONArray jsonArray=new JSONArray();
		int nbChamps=champs.size();
		Champs c;
		for(int i=0;i<nbChamps;i++){
			c=champs.get(i);
			jsonArray.put(c.toJSONObject());
		}
		return jsonArray;
	}
}
