/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.tools;

import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.util.FileUtil;
import com.liferay.util.GetterUtil;
import com.liferay.util.StringUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * <a href="WebSiteBuilder.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class WebSiteBuilder {

	public static void main(String[] args) {
		if (args.length == 3) {
			new WebSiteBuilder(args[0], args[1], args[2]);
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	public static List getWebSites() throws Exception {
		File file = new File("../web-sites/web-sites.xml");

		SAXReader reader = new SAXReader();

		Document doc = null;

		try {
			doc = reader.read(file);
		}
		catch (DocumentException de) {
			de.printStackTrace();
		}

		Element root = doc.getRootElement();

		List webSites = new ArrayList();

		Iterator itr = root.elements("web-site").iterator();

		while (itr.hasNext()) {
			Element webSite = (Element)itr.next();

			String id = webSite.attributeValue("id");
			boolean httpEnabled = GetterUtil.getBoolean(
				webSite.attributeValue("http-enabled"), true);
			String keystore = GetterUtil.getString(
				webSite.attributeValue("keystore"));
			String keystorePassword = GetterUtil.getString(
				webSite.attributeValue("keystore-password"));
			String virtualHosts = GetterUtil.getString(
				webSite.attributeValue("virtual-hosts"));
			String forwardURL = GetterUtil.getString(
				webSite.attributeValue("forward-url"), "/c");

			webSites.add(
				new WebSite(
					id, httpEnabled, keystore, keystorePassword, virtualHosts,
					forwardURL));
		}

		return webSites;
	}

	public WebSiteBuilder(String portalExtProperties, String availableWebApps,
						  String orionConfigDir) {

		try {
			_orionWebXmlDevelopment = System.getProperty(
				"app.server.orion.web.xml.development");
			_orionWebXmlJspCheck = System.getProperty(
				"app.server.orion.web.xml.jsp.check");

			_portalExtProperties = portalExtProperties;
			_availableWebApps = availableWebApps;
			_orionConfigDir = orionConfigDir;

			List webSites = getWebSites();

			_buildOrionASP(webSites);
			_buildWebSites(webSites);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void _buildOrionASP(List webSites) throws Exception {
		if (_portalExtProperties.startsWith("${") ||
			_availableWebApps.startsWith("${") ||
			_orionConfigDir.startsWith("${")) {

			return;
		}

		// portal-ext.properties

		BufferedReader br = new BufferedReader(new FileReader(
			_portalExtProperties));

		StringMaker sm = new StringMaker();
		String line = null;

		while ((line = br.readLine()) != null) {
			if (line.startsWith("portal.instances")) {
				sm.append("portal.instances=" + webSites.size());
			}
			else {
				sm.append(line);
			}

			sm.append("\n");
		}

		br.close();

		FileUtil.write(_portalExtProperties, sm.toString());

		// /orion/config/application.xml

		sm = new StringMaker();

		Iterator itr = webSites.iterator();

		while (itr.hasNext()) {
			WebSite webSite = (WebSite)itr.next();

			if (webSite.isHttpEnabled() || webSite.isHttpsEnabled()) {
				sm.append("\t<web-module id=\"");
				sm.append(webSite.getId());
				sm.append("-web\" ");
				sm.append("path=\"../applications/");
				sm.append(webSite.getId());
				sm.append("-web.war\" />\n");
			}
		}

		File file = new File(_orionConfigDir + "/application.xml");

		String content = FileUtil.read(file);

		int x = content.indexOf("<!-- Begin ASP -->");
		int y = content.indexOf("<!-- End ASP -->");

		content =
			content.substring(0, x  + 20) + sm.toString() +
				content.substring(y - 2, content.length());

		FileUtil.write(file, content);

		// /orion/config/server.xml

		sm = new StringMaker();

		itr = webSites.iterator();

		while (itr.hasNext()) {
			WebSite webSite = (WebSite)itr.next();

			if (webSite.isHttpEnabled()) {
				sm.append("\t<web-site path=\"./web-sites/");
				sm.append(webSite.getId());
				sm.append("-web.xml\" />\n");
			}

			if (webSite.isHttpsEnabled()) {
				sm.append("\t<web-site path=\"./web-sites/");
				sm.append(webSite.getId());
				sm.append("-web-secure.xml\" />\n");
			}
		}

		file = new File(_orionConfigDir + "/server.xml");

		content = FileUtil.read(file);

		x = content.indexOf("<!-- Begin ASP -->");
		y = content.indexOf("<!-- End ASP -->");

		content =
			content.substring(0, x  + 20) + sm.toString() +
				content.substring(y - 2, content.length());

		FileUtil.write(file, content);

		// /orion/config/web-sites/liferay.com-web.xml

		itr = webSites.iterator();

		while (itr.hasNext()) {
			WebSite webSite = (WebSite)itr.next();

			if (webSite.isHttpEnabled()) {
				_buildOrionASP(webSite, false);
			}

			if (webSite.isHttpsEnabled()) {
				_buildOrionASP(webSite, true);
			}
		}
	}

	private void _buildOrionASP(WebSite webSite, boolean secure)
		throws Exception {

		String[] webApps = StringUtil.split(_availableWebApps);

		int httpPort = 8080;
		int httpsPort = 8443;

		int port = httpPort;

		if (secure) {
			port = httpsPort;
		}

		String xml =
			"<?xml version=\"1.0\"?>\n" +
			"<!DOCTYPE web-site PUBLIC \"Orion Web-site\" " +
				"\"http://www.orionserver.com/dtds/web-site.dtd\">\n" +
			"\n" +
			"<web-site port=\"" + port + "\" " +
				(secure ? "secure=\"true\" " : "") + "virtual-hosts=\"" +
					webSite.getVirtualHosts() + "\">\n" +
			"\t<default-web-app application=\"default\" name=\"" +
				webSite.getId() + "-web\" load-on-startup=\"true\" />\n";

		for (int i = 0; i < webApps.length; i++) {
			xml +=
				"\t<web-app application=\"default\" name=\"" + webApps[i] +
					"-web\" " + "root=\"/" + webApps[i] +
						"\" load-on-startup=\"true\" />\n";
		}

		xml +=
			"\t<access-log path=\"../../log/" + webSite.getId() + "-web" +
				(secure ? "-secure" : "") + "-access.log\" split=\"day\" />\n";

		if (secure) {
			xml +=
				"\t<ssl-config keystore=\"" + webSite.getKeystore() +
					"\" keystore-password=\"" +
						webSite.getKeystorePassword() + "\" />\n";
		}

		xml += "</web-site>";

		FileUtil.write(
			_orionConfigDir + "/web-sites/" + webSite.getId() + "-web" +
				(secure ? "-secure" : "") + ".xml",
			xml);
	}

	private void _buildWebSites(List webSites) throws Exception {

		// Session timeout

		Properties props = new Properties();
		props.load(
			new FileInputStream("../portal-ejb/classes/portal.properties"));

		String sessionTimeout =
			GetterUtil.getString(props.getProperty("session.timeout"), "30");

		// web-sites

		Iterator itr = webSites.iterator();

		while (itr.hasNext()) {
			WebSite webSite = (WebSite)itr.next();

			String id = webSite.getId();
			String forwardURL = webSite.getForwardURL();

			// /docroot/index.html

			String indexHTML =
				"<html>\n" +
				"<head>\n" +
				"\t<title></title>\n" +
				"\t<meta content=\"0; url=" + forwardURL +
					"\" http-equiv=\"refresh\">\n" +
				"</head>\n" +
				"\n" +
				"<body onLoad=\"javascript:location.replace('" +
					forwardURL + "')\">\n" +
				"\n" +
				"</body>\n" +
				"\n" +
				"</html>";

			File indexHTMLFile = new File(
				"../web-sites/" + id + "-web/docroot/index.html");

			FileUtil.write(indexHTMLFile, indexHTML);

			// /docroot/WEB-INF/orion-web.xml

			String orionWebXML =
				"<?xml version=\"1.0\"?>\n" +
				"<!DOCTYPE orion-web-app PUBLIC " +
					"\"-//Evermind//DTD Orion Web Application 2.3//EN\" " +
					"\"http://www.orionserver.com/dtds/orion-web.dtd\">\n" +
				"\n" +
				"<orion-web-app>\n" +
				"</orion-web-app>";

			File orionWebXMLFile = new File(
				"../web-sites/" + id + "-web/docroot/WEB-INF/orion-web.xml");

			FileUtil.write(orionWebXMLFile, orionWebXML);

			// /docroot/WEB-INF/web.xml

			String webXML = StringUtil.replace(
				FileUtil.read(
					"../portal-ejb/src/com/liferay/portal/tools/tmpl/" +
						"web.xml.tmpl"),
				new String[] {"[$COMPANY_ID$]", "[$SESSION_TIMEOUT$]"},
				new String[] {id, sessionTimeout});

			File webXMLFile = new File(
				"../web-sites/" + id + "-web/docroot/WEB-INF/web.xml");

			FileUtil.write(webXMLFile, webXML);

			// /docroot/WEB-INF/lib/util-taglib.jar

			FileUtil.copyFile(
				"../portal-web/docroot/WEB-INF/lib/util-taglib.jar",
				"../web-sites/" + id +
					"-web/docroot/WEB-INF/lib/util-taglib.jar");

			// /docroot/WEB-INF/tld/liferay-portlet.tld

			FileUtil.copyFile(
				"../portal-web/docroot/WEB-INF/tld/liferay-portlet.tld",
				"../web-sites/" + id +
					"-web/docroot/WEB-INF/tld/liferay-portlet.tld");

			// /docroot/WEB-INF/tld/liferay-portletext.tld

			FileUtil.copyFile(
				"../portal-web/docroot/WEB-INF/tld/liferay-portletext.tld",
				"../web-sites/" + id +
					"-web/docroot/WEB-INF/tld/liferay-portletext.tld");

			// /docroot/WEB-INF/tld/liferay-security.tld

			FileUtil.copyFile(
				"../portal-web/docroot/WEB-INF/tld/liferay-security.tld",
				"../web-sites/" + id +
					"-web/docroot/WEB-INF/tld/liferay-security.tld");

			// /docroot/WEB-INF/tld/liferay-theme.tld

			FileUtil.copyFile(
				"../portal-web/docroot/WEB-INF/tld/liferay-theme.tld",
				"../web-sites/" + id +
					"-web/docroot/WEB-INF/tld/liferay-theme.tld");

			// /docroot/WEB-INF/tld/liferay-ui.tld

			FileUtil.copyFile(
				"../portal-web/docroot/WEB-INF/tld/liferay-ui.tld",
				"../web-sites/" + id +
					"-web/docroot/WEB-INF/tld/liferay-ui.tld");

			// /docroot/WEB-INF/tld/liferay-util.tld

			FileUtil.copyFile(
				"../portal-web/docroot/WEB-INF/tld/liferay-util.tld",
				"../web-sites/" + id +
					"-web/docroot/WEB-INF/tld/liferay-util.tld");
		}
	}

	private String _orionWebXmlDevelopment = null;
	private String _orionWebXmlJspCheck = null;
	private String _portalExtProperties = null;
	private String _availableWebApps = null;
	private String _orionConfigDir = null;

}