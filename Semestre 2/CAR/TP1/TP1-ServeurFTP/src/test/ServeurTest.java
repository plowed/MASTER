package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import client.ClientSocket;

import serveur.Serveur;
import utils.Tools;

/**
 * Batterie de test dont je n'ai pas le temps de commenter
 * ils seront expliques a la seance de tp
 * @author damien
 */
public class ServeurTest {

	static Serveur serveur;
	ClientSocket client;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		serveur=new Serveur("/");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		client=new ClientSocket(1025);
	}

	@After
	public void tearDown() throws Exception {
		client.send("QUIT");
		client.close();
	}


	@Test
	public void testConnection() {

		String retour = client.receiv();
		assertEquals(retour, "300 Bienvenue sur le serveur FTP");

	}

	@Test
	public void testUserExists() {	

		assertEquals(client.receiv(), "300 Bienvenue sur le serveur FTP");
		client.send("USER toto");
		assertEquals(client.receiv(), "331 Utilisateur reconnu, en attente du mot de passe");

	}

	@Test
	public void testUserNotExists() {	

		assertEquals(client.receiv(), "300 Bienvenue sur le serveur FTP");
		client.send("USER bonjour");
		assertEquals(client.receiv(), "430 Identifiant incorrecte");

	}

	@Test
	public void testRightPassword() {	

		assertEquals(client.receiv(), "300 Bienvenue sur le serveur FTP");
		client.send("USER a");
		assertEquals(client.receiv(), "331 Utilisateur reconnu, en attente du mot de passe");
		client.send("PASS a");
		assertEquals(client.receiv(), "230 mot de pass ok");

	}
	//Commande non geree / improbable
	@Test
	public void testWrongPassword() {	

		assertEquals(client.receiv(), "300 Bienvenue sur le serveur FTP");
		client.send("USER a");
		assertEquals(client.receiv(), "331 Utilisateur reconnu, en attente du mot de passe");
		client.send("PASS b");
		assertEquals(client.receiv(), "430 mot de passe incorrect");

	}

	@Test
	public void testPWD() {	

		assertEquals(client.receiv(), "300 Bienvenue sur le serveur FTP");
		client.send("USER a");
		assertEquals(client.receiv(), "331 Utilisateur reconnu, en attente du mot de passe");
		client.send("PASS a");
		assertEquals(client.receiv(), "230 mot de pass ok");
		client.send("PWD");
		assertEquals(client.receiv(), "257 "+Tools.path);

	}

	@Test
	public void testTypeI() {	

		assertEquals(client.receiv(), "300 Bienvenue sur le serveur FTP");
		client.send("USER a");
		assertEquals(client.receiv(), "331 Utilisateur reconnu, en attente du mot de passe");
		client.send("PASS a");
		assertEquals(client.receiv(), "230 mot de pass ok");
		client.send("PWD");
		assertEquals(client.receiv(), "257 "+Tools.path);
		client.send("TYPE I");
		assertEquals(client.receiv(), "200 Type OK");

	}

	@Test
	public void testPASV() {	

		assertEquals(client.receiv(), "300 Bienvenue sur le serveur FTP");
		client.send("USER a");
		assertEquals(client.receiv(), "331 Utilisateur reconnu, en attente du mot de passe");
		client.send("PASS a");
		assertEquals(client.receiv(), "230 mot de pass ok");
		client.send("PWD");
		assertEquals(client.receiv(), "257 "+Tools.path);
		client.send("TYPE I");
		assertEquals(client.receiv(), "200 Type OK");
		client.send("PASV");
		assertEquals(client.receiv(), "227 Entering Passive Mode (127,0,0,1,4,2)");
		ClientSocket client2=new ClientSocket(1026);
		client2.close();

	}

	@Test
	public void testLISTOK() {	

		assertEquals(client.receiv(), "300 Bienvenue sur le serveur FTP");
		client.send("USER a");
		assertEquals(client.receiv(), "331 Utilisateur reconnu, en attente du mot de passe");
		client.send("PASS a");
		assertEquals(client.receiv(), "230 mot de pass ok");
		client.send("PWD");
		assertEquals(client.receiv(), "257 "+Tools.path);
		client.send("TYPE I");
		assertEquals(client.receiv(), "200 Type OK");
		client.send("PASV");
		assertEquals(client.receiv(), "227 Entering Passive Mode (127,0,0,1,4,2)");
		ClientSocket client2=new ClientSocket(1026);
		
		client.send("LIST");
		assertEquals(client.receiv(), "150 Files OK, open Data Connection in ASCII");
		assertEquals(client.receiv(), "226 Closing data connection. Requested file action successful");
		
		client2.close();
	}
	
	@Test
	public void testLISTEerreur() {	

		assertEquals(client.receiv(), "300 Bienvenue sur le serveur FTP");
		client.send("USER a");
		assertEquals(client.receiv(), "331 Utilisateur reconnu, en attente du mot de passe");
		client.send("PASS a");
		assertEquals(client.receiv(), "230 mot de pass ok");
		client.send("PWD");
		assertEquals(client.receiv(), "257 "+Tools.path);
		client.send("TYPE I");
		assertEquals(client.receiv(), "200 Type OK");
		client.send("PASV");
		assertEquals(client.receiv(), "227 Entering Passive Mode (127,0,0,1,4,2)");
		ClientSocket client2=new ClientSocket(1026);
		client2.close();//fermeture avant
		client.send("LIST");
		assertEquals(client.receiv(), "150 Files OK, open Data Connection in ASCII");
		assertEquals(client.receiv(), "425 Can't open data connection. (list)");
		
		
	}
	
	@Test
	public void testCWDOK() {	

		assertEquals(client.receiv(), "300 Bienvenue sur le serveur FTP");
		client.send("USER a");
		assertEquals(client.receiv(), "331 Utilisateur reconnu, en attente du mot de passe");
		client.send("PASS a");
		assertEquals(client.receiv(), "230 mot de pass ok");
		client.send("PWD");
		assertEquals(client.receiv(), "257 "+Tools.path);
		client.send("TYPE I");
		assertEquals(client.receiv(), "200 Type OK");
		client.send("PASV");
		assertEquals(client.receiv(), "227 Entering Passive Mode (127,0,0,1,4,2)");
		ClientSocket client2=new ClientSocket(1026);
		
		client.send("LIST");
		assertEquals(client.receiv(), "150 Files OK, open Data Connection in ASCII");
		assertEquals(client.receiv(), "226 Closing data connection. Requested file action successful");		
		client.send("CWD Users");
		assertEquals(client.receiv(), "250 CWD ok");
		
		client2.close();
		
	}
	
	@Test
	public void testCWDInexistant() {	

		assertEquals(client.receiv(), "300 Bienvenue sur le serveur FTP");
		client.send("USER a");
		assertEquals(client.receiv(), "331 Utilisateur reconnu, en attente du mot de passe");
		client.send("PASS a");
		assertEquals(client.receiv(), "230 mot de pass ok");
		client.send("PWD");
		assertEquals(client.receiv(), "257 "+Tools.path);
		client.send("TYPE I");
		assertEquals(client.receiv(), "200 Type OK");
		client.send("PASV");
		assertEquals(client.receiv(), "227 Entering Passive Mode (127,0,0,1,4,2)");
		ClientSocket client2=new ClientSocket(1026);
		
		client.send("LIST");
		assertEquals(client.receiv(), "150 Files OK, open Data Connection in ASCII");
		assertEquals(client.receiv(), "226 Closing data connection. Requested file action successful");
		
		client.send("CWD BONJOUR");
		assertEquals(client.receiv(), "550 le fichier n'existe pas");
		
		client2.close();
		
	}
	
	@Test
	public void testCDUPOK() {	

		assertEquals(client.receiv(), "300 Bienvenue sur le serveur FTP");
		client.send("USER a");
		assertEquals(client.receiv(), "331 Utilisateur reconnu, en attente du mot de passe");
		client.send("PASS a");
		assertEquals(client.receiv(), "230 mot de pass ok");
		client.send("PWD");
		assertEquals(client.receiv(), "257 "+Tools.path);
		client.send("TYPE I");
		assertEquals(client.receiv(), "200 Type OK");
		client.send("PASV");
		assertEquals(client.receiv(), "227 Entering Passive Mode (127,0,0,1,4,2)");
		ClientSocket client2=new ClientSocket(1026);
		
		client.send("LIST");
		assertEquals(client.receiv(), "150 Files OK, open Data Connection in ASCII");
		assertEquals(client.receiv(), "226 Closing data connection. Requested file action successful");
		
		client.send("CWD Users");
		assertEquals(client.receiv(), "250 CWD ok");
		
		client.send("CDUP");
		assertEquals(client.receiv(), "250 CDUP ok");
		
		client2.close();
		
	}
	
	@Test
	public void testCDUPErreur() {	

		assertEquals(client.receiv(), "300 Bienvenue sur le serveur FTP");
		client.send("USER a");
		assertEquals(client.receiv(), "331 Utilisateur reconnu, en attente du mot de passe");
		client.send("PASS a");
		assertEquals(client.receiv(), "230 mot de pass ok");
		client.send("PWD");
		assertEquals(client.receiv(), "257 "+Tools.path);
		client.send("TYPE I");
		assertEquals(client.receiv(), "200 Type OK");
		client.send("PASV");
		assertEquals(client.receiv(), "227 Entering Passive Mode (127,0,0,1,4,2)");
		ClientSocket client2=new ClientSocket(1026);
		
		client.send("LIST");
		assertEquals(client.receiv(), "150 Files OK, open Data Connection in ASCII");
		assertEquals(client.receiv(), "226 Closing data connection. Requested file action successful");
		
		client.send("CDUP");
		assertEquals(client.receiv(), "550 racine");
		
		client2.close();
		
	}
	
	@Test
	public void testRETROK() {	

		assertEquals(client.receiv(), "300 Bienvenue sur le serveur FTP");
		client.send("USER a");
		assertEquals(client.receiv(), "331 Utilisateur reconnu, en attente du mot de passe");
		client.send("PASS a");
		assertEquals(client.receiv(), "230 mot de pass ok");
		client.send("PWD");
		assertEquals(client.receiv(), "257 "+Tools.path);
		client.send("TYPE I");
		assertEquals(client.receiv(), "200 Type OK");
		
		client.send("PASV");
		assertEquals(client.receiv(), "227 Entering Passive Mode (127,0,0,1,4,2)");
		
		ClientSocket client2=new ClientSocket(1026);
		
		client.send("CWD Users");
		assertEquals(client.receiv(), "250 CWD ok");
		
		client.send("RETR test.txt");
		assertEquals(client.receiv(), "150 Files OK, open Data Connection in ASCII");
		assertEquals(client.receiv(), "226 Closing data connection. Requested file action successful");
		
		
		
		client2.close();
		
	}
	
	@Test
	public void testRETRInexistant() {	

		assertEquals(client.receiv(), "300 Bienvenue sur le serveur FTP");
		client.send("USER a");
		assertEquals(client.receiv(), "331 Utilisateur reconnu, en attente du mot de passe");
		client.send("PASS a");
		assertEquals(client.receiv(), "230 mot de pass ok");
		client.send("PWD");
		assertEquals(client.receiv(), "257 "+Tools.path);
		client.send("TYPE I");
		assertEquals(client.receiv(), "200 Type OK");
		
		client.send("PASV");
		assertEquals(client.receiv(), "227 Entering Passive Mode (127,0,0,1,4,2)");
		
		ClientSocket client2=new ClientSocket(1026);
		
		client.send("CWD Users");
		assertEquals(client.receiv(), "250 CWD ok");
		
		client.send("RETR bozo.txt");
		assertEquals(client.receiv(), "550 fichier inexistant");
		

		client2.close();
		
	}
	
	@Test
	public void testRETRDisconnect() {	

		assertEquals(client.receiv(), "300 Bienvenue sur le serveur FTP");
		client.send("USER a");
		assertEquals(client.receiv(), "331 Utilisateur reconnu, en attente du mot de passe");
		client.send("PASS a");
		assertEquals(client.receiv(), "230 mot de pass ok");
		client.send("PWD");
		assertEquals(client.receiv(), "257 "+Tools.path);
		client.send("TYPE I");
		assertEquals(client.receiv(), "200 Type OK");
		
		client.send("PASV");
		assertEquals(client.receiv(), "227 Entering Passive Mode (127,0,0,1,4,2)");
		
		ClientSocket client2=new ClientSocket(1026);
		
		client.send("CWD Users");
		assertEquals(client.receiv(), "250 CWD ok");
		
		client2.close();
		client.send("RETR test.txt");
		assertEquals(client.receiv(), "150 Files OK, open Data Connection in ASCII");
		assertEquals(client.receiv(), "425 Can't open data connection. (retr)");

	}

	@Test
	public void testQUIT() {	

		assertEquals(client.receiv(), "300 Bienvenue sur le serveur FTP");
		client.send("USER a");
		assertEquals(client.receiv(), "331 Utilisateur reconnu, en attente du mot de passe");
		client.send("PASS a");
		assertEquals(client.receiv(), "230 mot de pass ok");
		client.send("PWD");
		assertEquals(client.receiv(), "257 "+Tools.path);
		client.send("TYPE I");
		assertEquals(client.receiv(), "200 Type OK");
		
		client.send("PASV");
		assertEquals(client.receiv(), "227 Entering Passive Mode (127,0,0,1,4,2)");
		
		ClientSocket client2=new ClientSocket(1026);
		
		client.send("CWD Users");
		assertEquals(client.receiv(), "250 CWD ok");
		
		client.send("STOR test.txt");
		assertEquals(client.receiv(), "150 Files OK, open Data Connection in ASCII");
		assertEquals(client.receiv(), "425 Can't open data connection. (stor)");
		
		
		
		client2.close();
		
	}
	
	@Test
	public void commandeSansIdentification() {	

		assertEquals(client.receiv(), "300 Bienvenue sur le serveur FTP");
		
		client.send("PWD");
		assertEquals(client.receiv(), "530 vous n'etes pas connecte");
	
	}
	
	@Test
	public void testSTORKO() {	

		assertEquals(client.receiv(), "300 Bienvenue sur le serveur FTP");
		client.send("USER a");
		assertEquals(client.receiv(), "331 Utilisateur reconnu, en attente du mot de passe");
		client.send("PASS a");
		assertEquals(client.receiv(), "230 mot de pass ok");
		client.send("PWD");
		assertEquals(client.receiv(), "257 "+Tools.path);
		client.send("TYPE I");
		assertEquals(client.receiv(), "200 Type OK");
		
		client.send("PASV");
		assertEquals(client.receiv(), "227 Entering Passive Mode (127,0,0,1,4,2)");
		
		ClientSocket client2=new ClientSocket(1026);
		
		client.send("QUIT");
		assertEquals(client.receiv(), "A bientot !");
		
		
		
		client2.close();
		
	}
	
	
	
	
}
