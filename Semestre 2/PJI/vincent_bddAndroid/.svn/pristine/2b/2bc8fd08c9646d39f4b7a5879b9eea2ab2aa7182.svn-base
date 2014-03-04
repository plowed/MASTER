package com.example.wsebase;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;

public abstract class WseActivity extends Activity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// quand l'activité est créée
		// on envoie un message sur le bus (session Android -> fixée dans la classe Application)
		// C'est un exemple
		// Vous pouvez le supprimer
		
		JSONObject msg = new JSONObject();
		try {
			msg.put("event", "activityCreate");
			msg.put("className", this.getLocalClassName());
			MyApplication.getInstance().sendMessageOnWse(msg);

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	// quand une activité est démarrée
	// on indique à l'application que si il y a un message qui arrive sur WSE
	// il faudra invoquer la méthode newMessageReceived sur cette activité
	protected void onResume() {
		super.onResume();
		MyApplication.getInstance().setCurrentActivity(this);
	}

	// quand l'activité est en pause
	// on indique à l'application que ce n'est plus la peine
	// d'invoquer une méthode newMessageReceived sur une qconque activité
	protected void onPause() {
		super.onPause();
		MyApplication.getInstance().setCurrentActivity(null);
	}

	// à implémenter dans les classes filles
	public abstract void newMessageReceived(JSONObject message);
}
