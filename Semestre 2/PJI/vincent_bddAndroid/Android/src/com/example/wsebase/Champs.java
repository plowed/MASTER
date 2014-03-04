package com.example.wsebase;

import org.json.JSONException;
import org.json.JSONObject;

public class Champs {
	
	protected String name,type;
	protected boolean pk,fk,notNull;
	protected String valDefaut;
	protected String tableFk, champsFk;

	public Champs(String name){
		this.name=name;
		pk=false;
		fk=false;
		notNull=false;
		valDefaut="";
		tableFk="";
	}
	
	public Champs(String name,String type,boolean pk){
		this.name=name;
		this.pk=pk;
		this.type=type;
		fk=false;
		notNull=false;
		valDefaut="";
		tableFk="";
		champsFk="";
	}
	
	public Champs(String name,String tableFk, String champsFk){
		this.name=name;
		pk=false;
		this.type="";
		fk=(tableFk != null && champsFk!= null && !tableFk.equals("") && !champsFk.equals(""));
		notNull=false;
		valDefaut="";
		if(fk){
			this.tableFk=tableFk;
			this.champsFk=champsFk;
		}
		else{
			this.tableFk="";
			this.champsFk="";
		}
	}

	public boolean isPk() {
		return pk;
	}

	public void setPk(boolean pk) {
		this.pk = pk;
	}

	public boolean isFk() {
		return fk;
	}

	public void setFk(boolean fk) {
		System.out.println("fk : "+fk);
		this.fk = fk;
	}

	public boolean isNotNull() {
		return notNull;
	}

	public void setNotNull(boolean notNull) {
		this.notNull = notNull;
	}

	public String getValDefaut() {
		return valDefaut;
	}

	public void setValDefaut(String valDefaut) {
		this.valDefaut = valDefaut;
	}
	
	public String getChampsFk(){
		return champsFk;
	}
	
	public void setChampsFk(String champsFk){
		this.champsFk=champsFk;
	}

	public String getTableFk() {
		return tableFk;
	}

	public void setTableFk(String tableFk) {
		this.tableFk = tableFk;
	}

	public String getName() {
		return name;
	}
	
	public String getType(){
		return type;
	}
	
	public boolean equals(Object o){
		if(o.getClass()==getClass()){
			String nameO=((Champs)o).getName();
			return nameO.equals(getName());
		}
		return false;
	}
	
	public JSONObject toJSONObject(){
		JSONObject json=new JSONObject();
		try {
			json.put("nameChamps", getName());
			json.put("type", getType());
			json.put("notNull", isNotNull());
			json.put("default", getValDefaut());
			json.put("pk",isPk());
			json.put("fk", isFk());
			if(isFk()){
				JSONObject ref=new JSONObject();
				ref.put("table", getTableFk());
				ref.put("champs", getChampsFk());
				json.put("reference", ref);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}
}
