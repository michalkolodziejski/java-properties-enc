package org.mkdev.ut;

import org.mkdev.ut.exceptions.InvalidConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.CodeSource;
import java.util.Properties;

/**
 * @author Michał Kołodziejski &lt;<I><A href="mailto:michal.kolodziejski@gmail.com">michal.kolodziejski@gmail.com</A></I>&gt;
 * @version 1.0
 * @license: GPLv3 (http://www.gnu.org/licenses/gpl-3.0.txt)
 * @since: 2014-10-31
 */
public abstract class PropertiesUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesUtil.class);

	protected Properties resourceBundle;

	private String fileName;

	public PropertiesUtil() {
		resourceBundle = new Properties();
	}

	public PropertiesUtil readProperties(String aFileName) throws IOException {
//        File file = new File(aFileName);
//        FileInputStream fileInput = new FileInputStream(file);
//
////		InputStream stream = PropertiesUtil.class.getClassLoader().getResourceAsStream(aFileName);
//
//		try {
//			resourceBundle.load(fileInput);
//		} catch (IOException e) {
//			throw new FileNotFoundException("Property file '" + aFileName + "' not found on classpath.");
//		}

        CodeSource src = super.getClass().getProtectionDomain().getCodeSource();
        if (src != null) {
            try {
                URL url = new URL(src.getLocation(), "config.properties");

                LOGGER.info(" * reading properties from file [{}]", url.getPath());

                resourceBundle = new Properties();
                try {
                    resourceBundle.load(new FileInputStream(new File(url.toURI())));
                } catch (IOException e) {
					throw new InvalidConfigurationException(e);
				} catch (URISyntaxException e) {
                    throw new InvalidConfigurationException(e);
                }

                this.fileName = url.getPath();
            } catch (MalformedURLException e) {
                throw new InvalidConfigurationException(e);
            }
        }
        else {
            throw new InvalidConfigurationException("UNKNOWN ERROR");
        }

		initProperties();
		debugProperties();

		return this;
	}

	public void writeProperties() {
		reinitProperties();

		LOGGER.info(" * writing properties to file [{}]", fileName);
		FileOutputStream os = null;
		try {
//			URL url = PropertiesUtil.class.getClassLoader().getResource(fileName);
//			File file = null;
//			if (url != null) {
//				file = new File(url.toURI().getPath());
//			}

            File file = new File(fileName);

			os = new FileOutputStream(file);
			resourceBundle.store(os, null);

			debugProperties();
		} catch (IOException e) {
			LOGGER.error("Exception", e);
		} finally {
			if (os!=null) {
				try {
					os.close();
				} catch (IOException e) {
					LOGGER.error("Error while writing resourceBundle {}", e);
				}
			}
		}
	}

	public abstract void initProperties();

	public abstract void reinitProperties();

	public abstract void debugProperties();
}
