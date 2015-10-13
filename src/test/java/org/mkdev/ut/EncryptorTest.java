package org.mkdev.ut;

import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.util.encoders.Base64;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Michał Kołodziejski &lt;<I><A href="mailto:michal.kolodziejski@gmail.com">michal.kolodziejski@gmail.com</A></I>&gt;
 * @version 1.0
 * @license: GPLv3 (http://www.gnu.org/licenses/gpl-3.0.txt)
 * @since: 2014-10-31
 */
public class EncryptorTest {
	private static final String KEY = "4f41243847da693a4f356c0486114bc6"; //md5("deadbeef");
	private Encryptor encryptor;
	
	@Before
    public void setUp() {
		encryptor = new Encryptor(KEY);
	}
	
	@Test
	public void testEncryptString() throws CryptoException {
		System.out.println(new String(Base64.encode(encryptor.encryptString("alamakota"))));
	}

	@Test
	public void testDecryptString() {
	}
}
