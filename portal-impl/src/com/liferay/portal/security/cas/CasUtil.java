package com.liferay.portal.security.cas;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * <p>
 * See http://issues.liferay.com/browse/LPS-8601.
 * </p>
 *
 * @author Barrie Selack

 */

public class CasUtil {

	private static final String PKIX = "PKIX";
	
	public static String checkUrlAvailability(String urlToCheck) {
		
		String urlAvailability = "cas-configuration-test-connected";

		try {
			URL url = new URL(urlToCheck);
			
			HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setConnectTimeout(3000);
			
			int responseCode = httpURLConnection.getResponseCode();
		} catch (MalformedURLException e) {
			urlAvailability = "cas-configuration-test-bad-url";
		} catch (IOException e) {
			if (e.getMessage().contains(PKIX)) {
				urlAvailability = "cas-configuration-test-ssl-issues";
			} else {
				urlAvailability = "cas-configuration-test-unreachable";
			}
		}
		
		return urlAvailability;
	}
}
