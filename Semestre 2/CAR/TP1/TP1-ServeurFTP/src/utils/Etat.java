package utils;

import java.net.Socket;

/**
 * Cette enumeration associe un etat  a deux messages, un pour la reussite
 * et un pour l'echec
 * @author damien
 */
public enum Etat {

	//Objets directement construits
	INIT("300 Bienvenue sur le serveur FTP", "Allez vous en !"),
	USER("331 Utilisateur reconnu, en attente du mot de passe", "430 Identifiant incorrecte"),
	PASS("230 mot de pass ok", "430 mot de passe incorrect"),
	IDENTIFIED("Utilisateur identifie, en attente de commande", "530 vous n'etes pas connecte"),
	PWD("257 ", "PWD erreur"),
	TYPE("200 Type OK", "TYPE Erreur"),
	
	PASV("227 Entering Passive Mode ("+Tools.ip+",", "425 Can't open data connection."),
	
	LIST("150 Files OK, open Data Connection in ASCII", "425 Can't open data connection. (list)"),
	LIST2("226 Closing data connection. Requested file action successful", "LIST2 erreur"),
	RETR("150 Files OK, open Data Connection in ASCII", "550 fichier inexistant"),
	RETR2("226 Closing data connection. Requested file action successful", "425 Can't open data connection. (retr)"),
	STOR("150 Files OK, open Data Connection in ASCII", "425 Can't open data connection. (stor)"),
	STOR2("226 Closing data connection. Requested file action successful", "STOR2 erreur"),
	
	CWD("250 CWD ok", "550 "),
	CDUP("250 CDUP ok", "550 racine"),
	QUIT("A bientot !", "");
	

	private String messageOK = "";
	private String messageError = "";

	/**
	 * Associe les messages a l'etat
	 * @param messageOK
	 * @param messageError
	 * @author damien
	 */
	Etat(String messageOK, String messageError){
		this.messageOK = messageOK;
		this.messageError = messageError;
	}

	/**
	 * envoi un message avec personnalisation apres le code de retour
	 * @param client la socket client a qui on envoi le message
	 * @param etat "OK" ou "KO"
	 * @param perso le message a mettre a la suite
	 */
	public void sendMessagePerso(Socket client, String etat , String perso) {

		switch(etat){
		case "OK":
			Tools.send(client, this.messageOK+perso);
			break;
		case "KO":
			Tools.send(client, this.messageError+perso);
			break;
		}


	}

	/**
	 * Envoi le message en cas de succes pour PSV
	 * @param client la socket client a qui on veut envoyer
	 * @param port on ajoute le port choisi
	 */
	public void sendMessageOK(Socket client, int port) {
		Tools.send(client, this.messageOK+port/256+","+port%256+")");
	}

	/**
	 * Envoi le message en cas de succes pour l'etat courant
	 * @param client le client a qui on veut envoyer le message
	 */
	public void sendMessageOK(Socket client) {
		Tools.send(client, this.messageOK);
	}
	
	/**
	 * Envoi le message en cas d'echec pour l'etat courant
	 * @param client le client a qui on veut envoyer le message
	 */
	public void sendMessageError(Socket client) {
		Tools.send(client, this.messageError);
	}

}
