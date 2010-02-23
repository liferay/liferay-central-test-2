/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.blogs.util;

import com.liferay.ibm.icu.util.Calendar;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.xmlrpc.XmlRpcClient;
import com.liferay.portal.xmlrpc.XmlRpcException;
import com.liferay.portal.xmlrpc.response.Response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import net.htmlparser.jericho.Source;
import net.htmlparser.jericho.StartTag;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.HeadMethod;

/**
 * <a href="LinkbackProducerUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 */
public class LinkbackProducerUtil {

	public static void sendPingback(String sourceUri, String targetUri)
		throws Exception {

		String serverUri = _discoverPingbackServer(targetUri);

		if (Validator.isNull(serverUri)) {
			return;
		}

		_pingbackQueue.add(
			new Tuple(new Date(), serverUri, sourceUri, targetUri));
	}

	public static synchronized void sendQueuedPingbacks()
		throws XmlRpcException {

		Calendar cal = Calendar.getInstance();

		cal.add(Calendar.MINUTE, -1);

		Date expiration = cal.getTime();

		while (!_pingbackQueue.isEmpty()) {
			Tuple tuple = _pingbackQueue.get(0);

			Date time = (Date)tuple.getObject(0);

			if (time.before(expiration)) {
				_pingbackQueue.remove(0);

				String serverUri = (String)tuple.getObject(1);
				String sourceUri = (String)tuple.getObject(2);
				String targetUri = (String)tuple.getObject(3);

				if (_log.isInfoEnabled()) {
					_log.info(
						"XML-RPC pingback " + serverUri + ", source " +
							sourceUri + ", target " + targetUri);
				}

				XmlRpcClient client = new XmlRpcClient(serverUri);

				Response response = client.executeMethod(
					"pingback.ping", new Object[] { sourceUri, targetUri });

				if (_log.isInfoEnabled()) {
					_log.info(response.toString());
				}
			}
			else {
				break;
			}
		}
	}

	public static boolean sendTrackback(
			String trackback, Map<String, String> parts)
		throws Exception {

		if (_log.isInfoEnabled()) {
			_log.info("Pinging trackback " + trackback);
		}

		Http.Options options = new Http.Options();

		options.setLocation(trackback);
		options.setParts(parts);
		options.setPost(true);

		String xml = HttpUtil.URLtoString(options);

		if (_log.isInfoEnabled()) {
			_log.info(xml);
		}

		XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();

		XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(
			new UnsyncStringReader(xml));

		String error = xml;

		try {
			xmlStreamReader.nextTag();
			xmlStreamReader.nextTag();

			String name = xmlStreamReader.getLocalName();

			if (name.equals("error")) {
				int status = GetterUtil.getInteger(
					xmlStreamReader.getElementText(), 1);

				if (status == 0) {
					return true;
				}

				xmlStreamReader.nextTag();

				name = xmlStreamReader.getLocalName();

				if (name.equals("message")) {
					error = xmlStreamReader.getElementText();
				}
			}
		}
		finally {
			if (xmlStreamReader != null) {
				try {
					xmlStreamReader.close();
				}
				catch (Exception e) {
				}
			}
		}

		_log.error(
			"Error while pinging trackback at " + trackback + ": " + error);

		return false;
	}

	private static String _discoverPingbackServer(String targetUri) {
		String serverUri = null;

		try {
			HttpClient client = new HttpClient();

			HttpMethodBase method = new HeadMethod(targetUri);

			client.executeMethod(method);

			Header header = method.getResponseHeader("X-Pingback");

			if (header != null) {
				serverUri = header.getValue();
			}
		}
		catch (Exception e) {
			_log.error("Unable to call HEAD of " + targetUri, e);
		}

		if (Validator.isNull(serverUri)) {
			try {
				HttpClient client = new HttpClient();

				HttpMethodBase method = new GetMethod(targetUri);

				client.executeMethod(method);

				Source clientSource =
					new Source(method.getResponseBodyAsString());

				List<StartTag> tags = clientSource.getAllStartTags("link");

				for (StartTag tag : tags) {
					String rel = tag.getAttributeValue("rel");

					if (rel.equalsIgnoreCase("pingback")) {
						String href = tag.getAttributeValue("href");

						serverUri = HtmlUtil.escape(href);

						break;
					}
				}
			}
			catch (Exception e) {
				_log.error("Unable to call GET of " + targetUri, e);
			}
		}

		return serverUri;
	}

	private static List<Tuple> _pingbackQueue =
		Collections.synchronizedList(new ArrayList<Tuple>());

	private static Log _log = LogFactoryUtil.getLog(LinkbackProducerUtil.class);

}