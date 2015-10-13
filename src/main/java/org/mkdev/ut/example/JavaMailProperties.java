package org.mkdev.ut.example;

import org.mkdev.ut.PropertiesEncryptedUtil;
import org.mkdev.ut.annotations.Encrypted;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Michał Kołodziejski &lt;<I><A href="mailto:michal.kolodziejski@gmail.com">michal.kolodziejski@gmail.com</A></I>&gt;
 * @version 1.0
 * @license: GPLv3 (http://www.gnu.org/licenses/gpl-3.0.txt)
 * @since: 2014-10-31
 */
public class JavaMailProperties extends PropertiesEncryptedUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(JavaMailProperties.class);

	@Encrypted
	public String host;
	@Encrypted
	public String port;

	@Encrypted
	public String username;
	@Encrypted
	public String password;

	public boolean useSSL;
	public boolean useTLS;
	
	public boolean useAuth;
	
	public boolean useAD;
	public String adDomain;
	public String adFlags;

	public boolean useProxy;
	public String proxyHost;
	public String proxyPort;
	
	public String encoding;
	
	public String timeout;
	public String connectionTimeout;
	
	public String xmailer;
	
	public String protocol;
	
	@Encrypted
	public String authorised_sender;
	
	public boolean useDebug;
	
	public boolean use8bitMime;
	public boolean useSendPartial;
	
	public boolean useSSLTrustAll;
	public boolean useSSLCheckServerIdentity;
	
	@Override
	public void initProperties()
	{
		host = resourceBundle.getProperty("host");
		port = resourceBundle.getProperty("port");
		username = resourceBundle.getProperty("username");
		password = resourceBundle.getProperty("password");
		useSSL = Boolean.parseBoolean(resourceBundle.getProperty("useSSL", "false"));
		useTLS = Boolean.parseBoolean(resourceBundle.getProperty("useTLS", "false"));
		
		useProxy =  Boolean.parseBoolean(resourceBundle.getProperty("useProxy", "false"));
		proxyHost = resourceBundle.getProperty("proxyHost");
		proxyPort = resourceBundle.getProperty("proxyPort");
		

		useAD =  Boolean.parseBoolean(resourceBundle.getProperty("useAD", "false"));
		adDomain = resourceBundle.getProperty("adDomain");
		adFlags = resourceBundle.getProperty("adFlags");
		
		encoding = resourceBundle.getProperty("encoding", "windows-1250");
		timeout = resourceBundle.getProperty("timeout", "5000");
		connectionTimeout = resourceBundle.getProperty("connectionTimeout", "5000");
		
		xmailer = resourceBundle.getProperty("xmailer");
		
		protocol = resourceBundle.getProperty("protocol", "smtp");
		
		authorised_sender = resourceBundle.getProperty("authorised_sender");
		
		useDebug = Boolean.parseBoolean(resourceBundle.getProperty("useDebug", "false"));
		
		use8bitMime = Boolean.parseBoolean(resourceBundle.getProperty("use8bitMime", "false"));
		useSendPartial = Boolean.parseBoolean(resourceBundle.getProperty("useSendPartial", "false"));
		
		useSSLTrustAll = Boolean.parseBoolean(resourceBundle.getProperty("useSSLTrustAll", "false"));
		useSSLCheckServerIdentity = Boolean.parseBoolean(resourceBundle.getProperty("useSSLCheckServerIdentity", "false"));
	}
	
	@Override
	public void reinitProperties()
	{
		resourceBundle.setProperty("host", host);
		resourceBundle.setProperty("port", port);
		resourceBundle.setProperty("username", username);
		resourceBundle.setProperty("password", password);
		resourceBundle.setProperty("useSSL", String.valueOf(useSSL));
		resourceBundle.setProperty("useTLS", String.valueOf(useTLS));
		
		if (useProxy) {
			resourceBundle.setProperty("useProxy", String.valueOf(useProxy));
			resourceBundle.setProperty("proxyHost", proxyHost);
			resourceBundle.setProperty("proxyPort", proxyPort);
		}
		
		if (useAD) {
			resourceBundle.setProperty("useAD", String.valueOf(useAD));
			resourceBundle.setProperty("adDomain", adDomain);
			resourceBundle.setProperty("adFlags", adFlags);
		}
		
		resourceBundle.setProperty("encoding", encoding);
		resourceBundle.setProperty("timeout", timeout);
		resourceBundle.setProperty("connectionTimeout", connectionTimeout);
		
		resourceBundle.setProperty("xmailer", xmailer);
		
		resourceBundle.setProperty("protocol", protocol);
		
		resourceBundle.setProperty("authorised_sender", authorised_sender);
		
		resourceBundle.setProperty("useDebug", String.valueOf(useDebug));
		
		resourceBundle.setProperty("use8bitMime", String.valueOf(use8bitMime));
		resourceBundle.setProperty("useSendPartial", String.valueOf(useSendPartial));
		
		resourceBundle.setProperty("useSSLCheckServerIdentity", String.valueOf(useSSLCheckServerIdentity));
		resourceBundle.setProperty("useSSLTrustAll", String.valueOf(useSSLTrustAll));
	}
	
	@Override
	public void debugProperties()
	{
		LOGGER.debug("host: {}", host);
		LOGGER.debug("port: {}", port);
		LOGGER.debug("username: {}",username);
		LOGGER.debug("password: {}",password);
		LOGGER.debug("useSSL: {}",useSSL);
		LOGGER.debug("useTLS: {}",useTLS);
		LOGGER.debug("useProxy: {}",useProxy);
		LOGGER.debug("proxyHost: {}",proxyHost);
		LOGGER.debug("proxyPort: {}",proxyPort);
		LOGGER.debug("encoding: {}",encoding);
		LOGGER.debug("timeout: {}",timeout);
		LOGGER.debug("connectionTimeout: {}",connectionTimeout);
		LOGGER.debug("xmailer: {}",xmailer);
		LOGGER.debug("protocol: {}",protocol);
		LOGGER.debug("authorised_sender: {}", authorised_sender);
		LOGGER.debug("useDebug: {}",useDebug);
		LOGGER.debug("use8bitMime: {}",use8bitMime);
		LOGGER.debug("useSendPartial: {}",useSendPartial);
		LOGGER.debug("useSSLTrustAll: {}",useSSLTrustAll);
		LOGGER.debug("useSSLCheckServerIdentity: {}",useSSLCheckServerIdentity);
	}
}
