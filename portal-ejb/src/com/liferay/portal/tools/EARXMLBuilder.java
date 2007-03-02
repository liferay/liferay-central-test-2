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
import com.liferay.portal.util.SAXReaderFactory;
import com.liferay.util.FileUtil;

import java.io.File;
import java.io.IOException;

import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * <a href="EARXMLBuilder.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class EARXMLBuilder {

	public static String[] EJB_PATHS = {
		"../counter-ejb", "../documentlibrary-ejb", "../lock-ejb",
		"../mail-ejb", "../portal-ejb"
	};

	public static String[] WEB_PATHS = {
		"../cms-web", "../laszlo-web", "../portal-web", "../tunnel-web"
	};

	public static void main(String[] args) {
		new EARXMLBuilder();
	}

	public EARXMLBuilder() {
		try {
			_buildGeronimoXML();
			_buildPramatiXML();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void _buildGeronimoXML() throws Exception {
		StringMaker sm = new StringMaker();

		SAXReader reader = SAXReaderFactory.getInstance();

		Document doc = reader.read(new File("classes/META-INF/ejb-jar.xml"));

		Iterator itr = doc.getRootElement().element("enterprise-beans").elements("session").iterator();

		while (itr.hasNext()) {
			Element entity = (Element)itr.next();

			String displayName = entity.elementText("display-name");

			sm.append("\t\t\t\t<session>\n");
			sm.append("\t\t\t\t\t<ejb-name>").append(entity.elementText("ejb-name")).append("</ejb-name>\n");

			if (displayName.endsWith("LocalServiceEJB")) {
				sm.append("\t\t\t\t\t<jndi-name>ejb/liferay/").append(displayName.substring(0, displayName.length() - 3)).append("Home</jndi-name>\n");
			}
			else {
				sm.append("\t\t\t\t\t<jndi-name>").append(entity.elementText("ejb-name")).append("</jndi-name>\n");
			}

			sm.append("\t\t\t\t\t<resource-ref>\n");
			sm.append("\t\t\t\t\t\t<ref-name>jdbc/LiferayPool</ref-name>\n");
			sm.append("\t\t\t\t\t\t<resource-link>LiferayPool</resource-link>\n");
			sm.append("\t\t\t\t\t</resource-ref>\n");
			sm.append("\t\t\t\t\t<resource-ref>\n");
			sm.append("\t\t\t\t\t\t<ref-name>mail/MailSession</ref-name>\n");
			sm.append("\t\t\t\t\t\t<resource-link>LiferayMailSession</resource-link>\n");
			sm.append("\t\t\t\t\t</resource-ref>\n");
			sm.append("\t\t\t\t</session>\n");
		}

		// geronimo-application.xml

		File outputFile = new File("../portal-ear/modules/META-INF/geronimo-application.xml");

		String content = FileUtil.read(outputFile);
		String newContent = content;

		int x = content.indexOf("portal-ejb.jar");

		x = content.indexOf("<enterprise-beans>", x) + 20;

		int y = content.indexOf("</enterprise-beans>", x) - 3;

		newContent =
			content.substring(0, x - 1) + sm.toString() +
				content.substring(y, content.length());

		if (!content.equals(newContent)) {
			FileUtil.write(outputFile, newContent);

			System.out.println(outputFile.toString());
		}
	}

	private void _buildPramatiXML() throws Exception {

		// pramati-j2ee-server.xml

		StringMaker sm = new StringMaker();

		sm.append("<?xml version=\"1.0\"?>\n");
		sm.append("<!DOCTYPE pramati-j2ee-server PUBLIC \"-//Pramati Technologies //DTD Pramati J2ee Server 5.0//EN\" \"http://www.pramati.com/dtd/pramati-j2ee-server_5_0.dtd\">\n");

		sm.append("\n<pramati-j2ee-server>\n");
		sm.append("\t<vhost-name>default</vhost-name>\n");
		sm.append("\t<auto-start>TRUE</auto-start>\n");
		sm.append("\t<realm-name>PortalRealm</realm-name>\n");
		sm.append("\t<deployment-properties app-versioning=\"true\" delete-previous-versions=\"false\" forced-deployment=\"false\">\n");
		sm.append("\t\t<jsp-files pre-compilation=\"true\" />\n");
		sm.append("\t\t<ejb-files retain-generated-code=\"false\" />\n");
		sm.append("\t\t<validation>\n");
		sm.append("\t\t\t<app-validation on-prepare=\"true\" on-start=\"true\" />\n");
		sm.append("\t\t</validation>\n");
		sm.append("\t</deployment-properties>\n");

		for (int i = 0; i < EJB_PATHS.length; i++) {
			sm.append(_buildPramatiXMLEJBModule(EJB_PATHS[i]));
		}

		for (int i = 0; i < WEB_PATHS.length; i++) {
			sm.append(_buildPramatiXMLWebModule(WEB_PATHS[i]));
		}

		for (int i = 0; i < EJB_PATHS.length; i++) {
			sm.append(_buildPramatiXMLRoleMapping(EJB_PATHS[i], "jar"));
		}

		for (int i = 0; i < WEB_PATHS.length; i++) {
			sm.append(_buildPramatiXMLRoleMapping(WEB_PATHS[i], "war"));
		}

		sm.append("</pramati-j2ee-server>");

		File outputFile = new File("../portal-ear/modules/pramati-j2ee-server.xml");

		if (!outputFile.exists() ||
			!FileUtil.read(outputFile).equals(sm.toString())) {

			FileUtil.write(outputFile, sm.toString());

			System.out.println(outputFile.toString());
		}
	}

	private String _buildPramatiXMLEJBModule(String path)
		throws IOException {

		File file = new File(path + "/classes/pramati-j2ee-server.xml");

		if (file.exists()) {
			String content = FileUtil.read(file);

			int x = content.indexOf("<ejb-module>");
			int y = content.indexOf("</ejb-module>");

			if (x != -1 && y != -1) {
				return content.substring(x - 1, y + 14);
			}
		}

		return "";
	}

	private String _buildPramatiXMLRoleMapping(String path, String extension)
		throws IOException {

		StringMaker sm = new StringMaker();

		sm.append("\t<role-mapping>\n");
		sm.append("\t\t<module-name>").append(path.substring(3, path.length())).append(".").append(extension).append("</module-name>\n");
		sm.append("\t\t<role-name>users</role-name>\n");
		sm.append("\t\t<role-link>everybody</role-link>\n");
		sm.append("\t</role-mapping>\n");

		return sm.toString();
	}

	private String _buildPramatiXMLWebModule(String path)
		throws DocumentException, IOException {

		String contextRoot = path.substring(2, path.length() - 4);
		String filePath = path + "/docroot/WEB-INF/web.xml";

		if (path.endsWith("portal-web")) {
			contextRoot = "/";
		}

		StringMaker sm = new StringMaker();

		sm.append("\t<web-module>\n");
		sm.append("\t\t<name>").append(contextRoot).append("</name>\n");

		sm.append("\t\t<module-name>");

		sm.append(path.substring(3, path.length())).append(".war</module-name>\n");

		SAXReader reader = SAXReaderFactory.getInstance(false);

		Document doc = reader.read(new File(filePath));

		Iterator itr = doc.getRootElement().elements("ejb-ref").iterator();

		while (itr.hasNext()) {
			Element ejbLocalRef = (Element)itr.next();

			sm.append("\t\t<ejb-ref>\n");
			sm.append("\t\t\t<ejb-ref-name>").append(ejbLocalRef.elementText("ejb-ref-name")).append("</ejb-ref-name>\n");
			sm.append("\t\t\t<ejb-link>").append(ejbLocalRef.elementText("ejb-link")).append("__PRAMATI_LOCAL</ejb-link>\n");
			sm.append("\t\t</ejb-ref>\n");
		}

		itr = doc.getRootElement().elements("ejb-local-ref").iterator();

		while (itr.hasNext()) {
			Element ejbLocalRef = (Element)itr.next();

			sm.append("\t\t<ejb-local-ref>\n");
			sm.append("\t\t\t<ejb-ref-name>").append(ejbLocalRef.elementText("ejb-ref-name")).append("</ejb-ref-name>\n");
			sm.append("\t\t\t<ejb-link>").append(ejbLocalRef.elementText("ejb-link")).append("__PRAMATI_LOCAL</ejb-link>\n");
			sm.append("\t\t</ejb-local-ref>\n");
		}

		itr = doc.getRootElement().elements("resource-ref").iterator();

		while (itr.hasNext()) {
			Element resourceRef = (Element)itr.next();

			sm.append("\t\t<resource-mapping>\n");
			sm.append("\t\t\t<resource-name>").append(resourceRef.elementText("res-ref-name")).append("</resource-name>\n");
			sm.append("\t\t\t<resource-type>").append(resourceRef.elementText("res-type")).append("</resource-type>\n");
			sm.append("\t\t\t<resource-link>").append(resourceRef.elementText("res-ref-name")).append("</resource-link>\n");
			sm.append("\t\t</resource-mapping>\n");
		}

		sm.append("\t</web-module>\n");

		return sm.toString();
	}

}