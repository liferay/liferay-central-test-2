/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.jbi.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.URL;
import java.net.URLConnection;

import java.util.Iterator;

import javax.jbi.messaging.MessageExchange;
import javax.jbi.messaging.MessagingException;
import javax.jbi.messaging.NormalizedMessage;

import org.servicemix.components.util.TransformComponentSupport;
import org.servicemix.jbi.jaxp.StringSource;

/**
 * <a href="URLTransformComponent.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Charles May
 *
 */
public class URLTransformComponent extends TransformComponentSupport {

	public String getUrl() {
		return _url;
	}

	public void setUrl(String url) {
		_url = url;
	}

	public String getUrlResult(NormalizedMessage in) throws MessagingException {
		StringBuffer url = new StringBuffer();

		url.append(_url);

		if (_url.indexOf("?") == -1) {
			url.append("?");
		}

		Iterator itr = in.getPropertyNames().iterator();

		while (itr.hasNext()) {
			String key = (String)itr.next();

			String value = (String)in.getProperty(key);

			url.append("&");
			url.append(key);
			url.append("=");
			url.append(value);
		}

		StringBuffer result = new StringBuffer();

		try {
	        URL urlObj = new URL(url.toString());

			URLConnection con = urlObj.openConnection();

			BufferedReader br = new BufferedReader(
				new InputStreamReader(con.getInputStream()));

	        String s = null;

			while ((s = br.readLine()) != null) {
				result.append(s);
			}

	        br.close();
		}
		catch (IOException ioe) {
			throw new MessagingException(ioe.getMessage());
		}

        return result.toString();
	}

	public boolean transform(
			MessageExchange exchange, NormalizedMessage in,
			NormalizedMessage out)
		throws MessagingException {

		out.setContent(new StringSource(getUrlResult(in)));

		return true;
	}

	private String _url;

}