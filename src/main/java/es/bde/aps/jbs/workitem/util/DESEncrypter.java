package es.bde.aps.jbs.workitem.util;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class DESEncrypter {
	private String keyStr;

	private Key aesKey = null;
	private Cipher cipher = null;

	public DESEncrypter(String keyStr) throws Exception {
		this.keyStr = keyStr;
		init();
	}

	/**
	 * @throws Exception
	 */
	synchronized private void init() throws Exception {
		if (keyStr == null || keyStr.length() != 16) {
			throw new Exception("bad aes key configured");
		}
		if (aesKey == null) {
			aesKey = new SecretKeySpec(keyStr.getBytes(), "AES");
			cipher = Cipher.getInstance("AES");
		}
	}

	/**
	 * @param text
	 * @return
	 * @throws Exception
	 */
	synchronized public String encrypt(String text) throws Exception {
		cipher.init(Cipher.ENCRYPT_MODE, aesKey);
		return toHexString(cipher.doFinal(text.getBytes()));
	}

	/**
	 * @param doFinal
	 * @return
	 */
	private String toHexString(byte[] doFinal) {
		return DatatypeConverter.printHexBinary(doFinal);
	}

	/**
	 * @param text
	 * @return
	 * @throws Exception
	 */
	synchronized public String decrypt(String text) throws Exception {
		cipher.init(Cipher.DECRYPT_MODE, aesKey);
		return new String(cipher.doFinal(toByteArray(text)));
	}

	/**
	 * @param text
	 * @return
	 */
	private byte[] toByteArray(String text) {
		// TODO Auto-generated method stub
		return DatatypeConverter.parseHexBinary(text);
	}

	/**
	 * @param seed
	 * @return
	 * @throws Exception
	 */
	public static DESEncrypter instance(String seed) throws Exception {
		return new DESEncrypter(seed);

	}
}
