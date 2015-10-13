package org.mkdev.ut;

import org.bouncycastle.crypto.*;
import org.bouncycastle.crypto.engines.*;
import org.bouncycastle.crypto.modes.*;
import org.bouncycastle.crypto.params.*;

/**
 * @author Michał Kołodziejski &lt;<I><A href="mailto:michal.kolodziejski@gmail.com">michal.kolodziejski@gmail.com</A></I>&gt;
 * @version 1.0
 * @license: GPLv3 (http://www.gnu.org/licenses/gpl-3.0.txt)
 * @since: 2014-10-31
 */
public class Encryptor {

    private BufferedBlockCipher cipher;
    private KeyParameter key;

    // Initialize the cryptographic engine.
    // The key array should be at least 8 bytes long.

    public Encryptor(byte[] key) {
        /*
        cipher = new PaddedBlockCipher(
                 new CBCBlockCipher(
                 new DESEngine() ) );
         */

        cipher = new PaddedBlockCipher(
                new CBCBlockCipher(
                        new BlowfishEngine()));

        this.key = new KeyParameter(key);
    }

    // Initialize the cryptographic engine.
    // The string should be at least 8 chars long.

    public Encryptor(String key) {
        this(key.getBytes());
    }

    // Private routine that does the gritty work.

    private byte[] callCipher(byte[] data) throws CryptoException {
        int size = cipher.getOutputSize(data.length);
        byte[] result = new byte[size];
        int olen = cipher.processBytes(data, 0, data.length, result, 0);
        olen += cipher.doFinal(result, olen);

        if (olen < size) {
            byte[] tmp = new byte[olen];
            System.arraycopy(
                    result, 0, tmp, 0, olen);
            result = tmp;
        }

        return result;
    }

    // Encrypt arbitrary byte array, returning the
    // encrypted data in a different byte array.

    public synchronized byte[] encrypt(byte[] data) throws CryptoException {
        if (data == null || data.length == 0) {
            return new byte[0];
        }

        cipher.init(true, key);
        return callCipher(data);
    }

    // Encrypts a string.

    public byte[] encryptString(String data) throws CryptoException {
        if (data == null || data.length() == 0) {
            return new byte[0];
        }

        return encrypt(data.getBytes());
    }

    // Decrypts arbitrary data.

    public synchronized byte[] decrypt(byte[] data) throws CryptoException {
        if (data == null || data.length == 0) {
            return new byte[0];
        }

        cipher.init(false, key);
        return callCipher(data);
    }

    // Decrypts a string that was previously encoded
    // using encryptString.

    public String decryptString(byte[] data) throws CryptoException {
        if (data == null || data.length == 0) {
            return "";
        }

        return new String(decrypt(data));
    }
}
