package org.mkdev.ut;

import org.bouncycastle.util.encoders.Base64;
import org.mkdev.ut.annotations.Encrypted;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Michał Kołodziejski &lt;<I><A href="mailto:michal.kolodziejski@gmail.com">michal.kolodziejski@gmail.com</A></I>&gt;
 * @version 1.0
 * @license: GPLv3 (http://www.gnu.org/licenses/gpl-3.0.txt)
 * @since: 2014-10-31
 */
public abstract class PropertiesEncryptedUtil extends PropertiesUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesEncryptedUtil.class);

    private static final String KEY = "4f41243847da693a4f356c0486114bc6"; // md5("deadbeef");
    private Encryptor encryptor;

    private static final Pattern TO_ENCODE = Pattern.compile("<encode>(\\S+)</encode>");

    public PropertiesEncryptedUtil() {
        super();

        encryptor = new Encryptor(KEY);
    }

    public void encryptProperties() throws Exception {
        Class<?> d = this.getClass();

        Field fs[] = d.getFields();

        for (Field f : fs) {
            Annotation a = f.getAnnotation(Encrypted.class);

            if (a != null) {
                String fieldValue = (String) f.get(this);

                Matcher toEncode = TO_ENCODE.matcher(fieldValue);

                String stringToEncode = fieldValue;
                if (toEncode.find()) {
                    stringToEncode = toEncode.group(1);
                }

                f.set(this, stringToEncode);

                encryptPropertyValue(f);
            }
        }
    }

    private void encryptPropertyValue(Field field) throws Exception {
        String value = (String) field.get(this);

        String encryptedValue = new String(Base64.encode(encryptor.encryptString(value)));

		LOGGER.debug("key=[{}], value=[{}]", field.getName(), encryptedValue);

        field.set(this, encryptedValue);
    }

    private void decryptPropertyValue(Field field) throws Exception {
        String value = (String) field.get(this);

        String decryptedValue = new String(encryptor.decrypt(Base64.decode(value)));

        LOGGER.debug("key=[{}], value=[{}]", field.getName(), decryptedValue);

        field.set(this, decryptedValue);
    }

    public void decryptProperties() throws Exception {
        Class<?> d = this.getClass();

        Field fs[] = d.getFields();

        for (Field f : fs) {
            Annotation a = f.getAnnotation(Encrypted.class);

            if (a != null) {
                String fieldValue = (String) f.get(this);

                Matcher toEncode = TO_ENCODE.matcher(fieldValue);

                // skip open text values prepared for encryption
                if (toEncode.find()) {
                    return;
                }
                decryptPropertyValue(f);
            }
        }
    }

    @Override
    public PropertiesEncryptedUtil readProperties(String fileName) throws IOException {
        super.readProperties(fileName);

        try {
            decryptProperties();
            encryptProperties();
            writeProperties();
            decryptProperties();
        } catch (Exception x) {
            x.printStackTrace();
        }

        return this;
    }
}
