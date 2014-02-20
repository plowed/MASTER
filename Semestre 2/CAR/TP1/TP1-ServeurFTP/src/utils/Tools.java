package utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;


/**
 * 
 * @author damien
 * Cette classe contient des metodes & variables utilises dans plusieurs classes
 * plutot que des les reecrire je les mets ici.
 */
public class Tools {

	//racine du serveur ftp
	public static String path;
	//adresse ip du serveur
	public static String ip="127,0,0,1";

	//port de depart
	public static int portDepart=1025;

	//nombre d'utilisateurs max : 100
	public static int portFin=1125;

	
	public static HashMap<String, String> userPass=new HashMap<String,String>(); //hashmap d'utilisateur/mot de passe


	/**
	 * Envoi un message a une socket client
	 * @author damien
	 * @param client une socket client
	 * @param message message qu'ont veux envoyer au client
	 * @throws IOException
	 * 
	 */
	public static void send(Socket client, String message) {

		OutputStream output;
		DataOutputStream data;

		try {

			output = client.getOutputStream();
			data = new DataOutputStream(output);
			System.out.println("<server> Envoi : "+message);
			//envoi
			data.writeBytes(message+"\n");
			

		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("<server> le message ("+message+") n'a pas pu etre envoye au client");
		}

	}

	/**
	 * test si le parametre est un string non vide
	 * @param parametre
	 * @return boolean
	 */
	public static boolean paramOK(String parametre){
		return (parametre!=null & parametre != "" & parametre != " ");
	}

	/**
	 * Envoi message par defaut en cas d'erreur inconnue au client
	 * @param client
	 */
	public static void sendErreurCommande(Socket client) {
		// TODO Auto-generated method stub
		send(client, "Commande non geree / improbable");

	}

	/**
	 * ferme la connexion avec une socket client
	 * @param client
	 */
	public static void fermerConnexion(Socket client){
		try {
			client.close();
			System.out.println("<server> connexion fermee");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("<server> impossible de fermer la connexion avec le client (deje fermee)");
		}
	}
	/**
	 * ferme la connexion d'une server socket
	 * @param client
	 */
	public static void fermerConnexion(ServerSocket server){
		try {
			server.close();
			server.setReuseAddress(true);
			System.out.println("<server> data server socket fermee");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("<server> impossible de fermer la data server socket");
		}
	}

	/**
	 * lis une ligne d'un buggered reader
	 * @param client socket client
	 * @param bufferedReader bufferedReader de la socket
	 * @return
	 */
	public static String lireLigne(Socket client, BufferedReader bufferedReader){
		String requete=null;

		try {

			requete = bufferedReader.readLine();
			if(requete != null)
				System.out.println("<server> Reception : "+requete);
			else
				fermerConnexion(client);


		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("<server> probleme lecture client");
			send(client, "un probleme est survenu lors de la lecture, veuillez vous reconnecter");
			fermerConnexion(client);
		}
		return requete;
	}

	/**
	 * test si un utilisateur existe
	 * @param user
	 * @return boolean
	 */
	public static boolean isInUserList(String user){
		return userPass.containsKey(user);
	}
	
	/**
	 * test si le mot de passe est bon
	 * @param user
	 * @param password
	 * @return boolean
	 */
	public static boolean isRightPassword(String user, String password){
		return (userPass.get(user).equals(password));
	}
}
