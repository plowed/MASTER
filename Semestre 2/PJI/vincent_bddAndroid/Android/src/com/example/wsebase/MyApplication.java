package com.example.wsebase;

import java.net.ConnectException;

import org.json.JSONException;
import org.json.JSONObject;

import wse.Listener;
import android.app.Activity;
import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class MyApplication extends Application {

	private static MyApplication instance;

	wse.Bus bus = null;
	String url = "http://www.lifl.fr/miny/WSE/traceSession.php";
	String session = "BDDandroid2";

	// Sur quelle activité doit-on appeler newMessageReceived en cas de msg reçu
	// c'est la variable d'en dessous qui s'en occupe
	WseActivity currentActivity = null;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		instance = this;
	}

	// ***************************
	//
	// QUELQUES GET/SET
	//
	// ***************************

	public static MyApplication getInstance() {
		return instance;
	}

	public WseActivity getCurrentActivity() {
		return currentActivity;
	}

	public void setCurrentActivity(WseActivity currentActivity) {
		this.currentActivity = currentActivity;
	}

	// ***************************
	//
	// LES METHODES POUR ENVOYER DES MSG SUR WSE
	//
	// ***************************

	public void sendMessageOnWse(String text) {
		JSONObject message = new JSONObject();
		try {
			message.put("message", text);
		} catch (JSONException e) {
			errorMessage("A JSON problem occured about construction text message "
					+ text);
		}
		sendMessageOnWse(message);
	}

	public void sendMessageOnWse(JSONObject message) {
		new SendMessageOnWse().execute(message);
	}

	// ***************************
	//
	// ACCÈS AU BUS
	//
	// ***************************

	public wse.Bus getBus() {
		if (bus == null) {
			try {
				bus = new wse.Bus(url, session);
				startListening();
			} catch (java.net.ConnectException ex) {
				errorMessage("A network problem occured : cannot connect to the bus ");
			}
		}
		return bus;
	}

	// ***************************
	//
	// ERROR MESSAGE : PRATIQUE POUR INDIQUER UNE EXCEPTION
	//
	// sans rapport avec WSE
	//
	// ***************************

	public void errorMessage(String message) {
		Log.e("WSE", message);
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}

	// ***************************
	//
	// CLASSE INCLUSE NÉCESSAIRE POUR L'ENVOIE DE MSG WSE EN TACHE DE FOND
	//
	// ***************************

	private class SendMessageOnWse extends AsyncTask<JSONObject, Integer, Long> {

		@Override
		protected Long doInBackground(JSONObject... params) {
			for (int i = 0; i < params.length; i++) {
				JSONObject message = params[0];
				sendMessageOnWse(message);
			}
			return null;
		}

		public void sendMessageOnWse(JSONObject message) {
			wse.Bus bus = getBus();
			if (bus != null) {
				try {
					bus.sendBusMessage(message);
				} catch (ConnectException e) {
					errorMessage("A network problem occured : cannot send message "
							+ message + " to the bus ");
				} catch (JSONException e) {
					errorMessage("A JSON problem occured about message "
							+ message);
				}
			}
		}

	}

	// ***************************
	//
	// METHODE ET CLASSE INCLUSE POUR L'ÉCOUTE DES MESSAGES SUR WSE
	//
	// ***************************

	public void startListening() {
		new StartListeningOnWSE().execute(new Listener() {

			@Override
			public void newMessageReceive(String source,
					final JSONObject messageObject) {
				WseActivity currentActivity = MyApplication.getInstance()
						.getCurrentActivity();
				if (currentActivity != null) {
					Log.v("MyAPPLICATION", currentActivity + "");
					currentActivity.newMessageReceived(messageObject);
				}
			}
		});

	}

	public void startAndAddListener(wse.Listener listener) {
		new StartListeningOnWSE().execute(listener);
	}

	private class StartListeningOnWSE extends
			AsyncTask<wse.Listener, Integer, Long> {

		@Override
		protected Long doInBackground(Listener... params) {
			wse.Bus bus = getBus();
			if (bus != null) {
				if (params.length > 0 && params[0] != null)
					bus.addListener(params[0]);
			}
			if (!bus.isAlive()) {
				bus.start();
			}
			return null;
		}

	}

}
