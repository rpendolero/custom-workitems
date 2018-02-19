/*
 * Creado el 24-oct-2007
 *
 * TODO Para cambiar la plantilla de este archivo generado, vaya a
 * Ventana - Preferencias - Java - Estilo de c�digo - Plantillas de c�digo
 */
package es.bde.aps.jbs.eaijava.util;

import es.bde.aps.jbs.eaijava.EAIConstants;

/**
 * @author infrpbx
 * 
 *         Clase que se utiliza para realizar la encriptaci�n de las contrase�as
 *         para acceso a las base de datos. Para poder generar una contrase�a
 *         encriptada se deber� ejecutar esta clase en modo "Stand-alone",
 *         debi�ndose pasar por par�metro el texto en claro de contrase�a y la
 *         clave con la que se quiere que se encripte.
 * 
 */
public class Encrypter {

	// Constante que guarda la semilla con la que se encripta las password.

	public static void main(String[] args) {

		String comando1 = "-c";
		String comando2 = "-d";

		if (args.length < 2) {
			System.out.println("uso: Encrypter {-c|-d} {texto}");
			System.exit(1);
		}

		// COMANDO 1 o COMANDO 2
		if ((comando1.equals(args[0])) || (comando2.equals(args[0]))) {

			// leer clave por teclado

			try {
				String texto = args[1];
				if (comando1.equals(args[0])) {
					System.out.println(encrypt(texto));
				} else if (comando2.equals(args[0])) {
					System.out.println(desencrypt(texto));

				}
			} catch (Exception e) {
				// TODO Bloque catch generado autom�ticamente
				e.printStackTrace();
				System.exit(1);
			}
		}
		System.exit(0);
	}

	private static String desencrypt(String passwordEncrypt) throws Exception {
		// TODO Ap�ndice de m�todo generado autom�ticamente
		DESEncrypter desencrypter = DESEncrypter.instance(EAIConstants.SEED);
		return desencrypter.decrypt(passwordEncrypt);

	}

	private static String encrypt(String password) throws Exception {
		DESEncrypter desencrypter = DESEncrypter.instance(EAIConstants.SEED);
		return desencrypter.encrypt(password);
	}

}
