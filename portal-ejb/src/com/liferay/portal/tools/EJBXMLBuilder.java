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

package com.liferay.portal.tools;

import com.liferay.portal.util.SAXReaderFactory;
import com.liferay.util.FileUtil;
import com.liferay.util.StringUtil;

import java.io.File;

import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * <a href="EJBXMLBuilder.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class EJBXMLBuilder {

	public static void main(String[] args) {
		if (args.length == 1) {
			new EJBXMLBuilder(args[0]);
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	public EJBXMLBuilder(String jarFileName) {
		_jarFileName = jarFileName;

		try {
			_buildBorlandXML();
			_buildJOnASXML();
			_buildJRunXML();
			_buildPramatiXML();
			_buildRexIPXML();
			_buildSunXML();
			_buildWebLogicXML();
			//_buildWebSphereXML();
			_updateEJBXML();
			_updateWebXML();
			_updateRemotingXML();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void _buildBorlandXML() throws Exception {
		StringBuffer sb = new StringBuffer();

		sb.append("<?xml version=\"1.0\"?>\n");
		sb.append("<!DOCTYPE ejb-jar PUBLIC \"-//Borland Software Corporation//DTD Enterprise JavaBeans 2.0//EN\" \"http://www.borland.com/devsupport/appserver/dtds/ejb-jar_2_0-borland.dtd\">\n");

		sb.append("\n<ejb-jar>\n");
		sb.append("\t<enterprise-beans>\n");

		SAXReader reader = SAXReaderFactory.getInstance();

		Document doc = reader.read(new File("classes/META-INF/ejb-jar.xml"));

		Iterator itr = doc.getRootElement().element("enterprise-beans").elements("session").iterator();

		while (itr.hasNext()) {
			Element entity = (Element)itr.next();

			sb.append("\t\t<session>\n");
			sb.append("\t\t\t<ejb-name>").append(entity.elementText("ejb-name")).append("</ejb-name>\n");

			String displayName = entity.elementText("display-name");

			if (displayName.endsWith("LocalServiceEJB")) {
				sb.append("\t\t\t<bean-local-home-name>ejb/liferay/").append(displayName.substring(0, displayName.length() - 3)).append("Home</bean-local-home-name>\n");
			}
			else {
				sb.append("\t\t\t<bean-home-name>").append(entity.elementText("ejb-name")).append("</bean-home-name>\n");
			}

			sb.append("\t\t</session>\n");
		}

		sb.append("\t</enterprise-beans>\n");
		sb.append("\t<property>\n");
		sb.append("\t\t<prop-name>ejb.default_transaction_attribute</prop-name>\n");
		sb.append("\t\t<prop-type>String</prop-type>\n");
		sb.append("\t\t<prop-value>Supports</prop-value>\n");
		sb.append("\t</property>\n");
		sb.append("</ejb-jar>");

		File outputFile = new File("classes/META-INF/ejb-borland.xml");

		if (!outputFile.exists() ||
			!FileUtil.read(outputFile).equals(sb.toString())) {

			FileUtil.write(outputFile, sb.toString());

			System.out.println(outputFile.toString());
		}
	}

	private void _buildJOnASXML() throws Exception {
		StringBuffer sb = new StringBuffer();

		sb.append("<?xml version=\"1.0\"?>\n");
		sb.append("<!DOCTYPE jonas-ejb-jar PUBLIC \"-//ObjectWeb//DTD JOnAS 3.2//EN\" \"http://www.objectweb.org/jonas/dtds/jonas-ejb-jar_3_2.dtd\">\n");

		sb.append("\n<jonas-ejb-jar>\n");

		SAXReader reader = SAXReaderFactory.getInstance();

		Document doc = reader.read(new File("classes/META-INF/ejb-jar.xml"));

		Iterator itr = doc.getRootElement().element("enterprise-beans").elements("session").iterator();

		while (itr.hasNext()) {
			Element entity = (Element)itr.next();

			sb.append("\t<jonas-session>\n");
			sb.append("\t\t<ejb-name>").append(entity.elementText("ejb-name")).append("</ejb-name>\n");

			String displayName = entity.elementText("display-name");

			if (displayName.endsWith("LocalServiceEJB")) {
				sb.append("\t\t<jndi-name>ejb/liferay/").append(displayName.substring(0, displayName.length() - 3)).append("Home</jndi-name>\n");
			}
			else {
				sb.append("\t\t<jndi-name>").append(entity.elementText("ejb-name")).append("</jndi-name>\n");
			}

			sb.append("\t\t<jonas-resource>\n");
			sb.append("\t\t\t<res-ref-name>jdbc/LiferayPool</res-ref-name>\n");
			sb.append("\t\t\t<jndi-name>jdbc/LiferayPool</jndi-name>\n");
			sb.append("\t\t</jonas-resource>\n");
			sb.append("\t\t<jonas-resource>\n");
			sb.append("\t\t\t<res-ref-name>mail/MailSession</res-ref-name>\n");
			sb.append("\t\t\t<jndi-name>mail/MailSession</jndi-name>\n");
			sb.append("\t\t</jonas-resource>\n");
			sb.append("\t</jonas-session>\n");
		}

		sb.append("</jonas-ejb-jar>");

		File outputFile = new File("classes/META-INF/jonas-ejb-jar.xml");

		if (!outputFile.exists() ||
			!FileUtil.read(outputFile).equals(sb.toString())) {

			FileUtil.write(outputFile, sb.toString());

			System.out.println(outputFile.toString());
		}
	}

	private void _buildJRunXML() throws Exception {
		StringBuffer sb = new StringBuffer();

		sb.append("<?xml version=\"1.0\"?>\n");
		sb.append("<!DOCTYPE jrun-ejb-jar PUBLIC \"-//Macromedia, Inc.//DTD jrun-ejb-jar 4.0//EN\" \"http://jrun.macromedia.com/dtds/jrun-ejb-jar.dtd\">\n");

		sb.append("\n<jrun-ejb-jar>\n");
		sb.append("\t<enterprise-beans>\n");

		SAXReader reader = SAXReaderFactory.getInstance();

		Document doc = reader.read(new File("classes/META-INF/ejb-jar.xml"));

		Iterator itr = doc.getRootElement().element("enterprise-beans").elements("session").iterator();

		while (itr.hasNext()) {
			Element entity = (Element)itr.next();

			sb.append("\t\t<session>\n");
			sb.append("\t\t\t<ejb-name>").append(entity.elementText("ejb-name")).append("</ejb-name>\n");

			String displayName = entity.elementText("display-name");

			if (displayName.endsWith("LocalServiceEJB")) {
				sb.append("\t\t\t<local-jndi-name>ejb/liferay/").append(displayName.substring(0, displayName.length() - 3)).append("Home</local-jndi-name>\n");
			}
			else {
				sb.append("\t\t\t<jndi-name>").append(entity.elementText("ejb-name")).append("</jndi-name>\n");
			}

			sb.append("\t\t\t<cluster-home>false</cluster-home>\n");
			sb.append("\t\t\t<cluster-object>false</cluster-object>\n");
			sb.append("\t\t\t<timeout>3000</timeout>\n");
			sb.append("\t\t</session>\n");
		}

		sb.append("\t</enterprise-beans>\n");
		sb.append("</jrun-ejb-jar>");

		File outputFile = new File("classes/META-INF/jrun-ejb-jar.xml");

		if (!outputFile.exists() ||
			!FileUtil.read(outputFile).equals(sb.toString())) {

			FileUtil.write(outputFile, sb.toString());

			System.out.println(outputFile.toString());
		}
	}

	private void _buildPramatiXML() throws Exception {
		StringBuffer sb = new StringBuffer();

		sb.append("<?xml version=\"1.0\"?>\n");
		sb.append("<!DOCTYPE pramati-j2ee-server PUBLIC \"-//Pramati Technologies //DTD Pramati J2ee Server 3.5 SP5//EN\" \"http://www.pramati.com/dtd/pramati-j2ee-server_3_5.dtd\">\n");

		sb.append("\n<pramati-j2ee-server>\n");
		sb.append("\t<vhost-name>default</vhost-name>\n");
		sb.append("\t<auto-start>TRUE</auto-start>\n");
		sb.append("\t<realm-name />\n");
		sb.append("\t<ejb-module>\n");
		sb.append("\t\t<name>").append(_jarFileName).append("</name>\n");

		SAXReader reader = SAXReaderFactory.getInstance();

		Document doc = reader.read(new File("classes/META-INF/ejb-jar.xml"));

		Iterator itr = doc.getRootElement().element("enterprise-beans").elements("session").iterator();

		while (itr.hasNext()) {
			Element entity = (Element)itr.next();

			sb.append("\t\t<ejb>\n");
			sb.append("\t\t\t<name>").append(entity.elementText("ejb-name")).append("</name>\n");
			sb.append("\t\t\t<max-pool-size>40</max-pool-size>\n");
			sb.append("\t\t\t<min-pool-size>20</min-pool-size>\n");
			sb.append("\t\t\t<enable-freepool>false</enable-freepool>\n");
			sb.append("\t\t\t<pool-waittimeout-millis>60000</pool-waittimeout-millis>\n");

			sb.append("\t\t\t<low-activity-interval>20</low-activity-interval>\n");
			sb.append("\t\t\t<is-secure>false</is-secure>\n");
			sb.append("\t\t\t<is-clustered>true</is-clustered>\n");

			String displayName = entity.elementText("display-name");

			if (displayName.endsWith("LocalServiceEJB")) {
				sb.append("\t\t\t<jndi-name>ejb/liferay/").append(displayName.substring(0, displayName.length() - 3)).append("Home</jndi-name>\n");
			}
			else {
				sb.append("\t\t\t<jndi-name>").append(entity.elementText("ejb-name")).append("</jndi-name>\n");
			}

			sb.append("\t\t\t<local-jndi-name>").append(entity.elementText("ejb-name")).append("__PRAMATI_LOCAL").append("</local-jndi-name>\n");

			sb.append(_buildPramatiXMLRefs(entity));

			sb.append("\t\t</ejb>\n");
		}

		sb.append("\t</ejb-module>\n");
		sb.append("</pramati-j2ee-server>");

		File outputFile = new File("classes/pramati-j2ee-server.xml");

		if (!outputFile.exists() ||
			!FileUtil.read(outputFile).equals(sb.toString())) {

			FileUtil.write(outputFile, sb.toString());

			System.out.println(outputFile.toString());
		}
	}

	private String _buildPramatiXMLRefs(Element entity) {
		StringBuffer sb = new StringBuffer();

		Iterator itr = entity.elements("ejb-local-ref").iterator();

		while (itr.hasNext()) {
			Element ejbRef = (Element)itr.next();

			sb.append("\t\t\t<ejb-local-ref>\n");
			sb.append("\t\t\t\t<ejb-ref-name>").append(ejbRef.elementText("ejb-ref-name")).append("</ejb-ref-name>\n");
			sb.append("\t\t\t\t<ejb-link>").append(ejbRef.elementText("ejb-ref-name")).append("__PRAMATI_LOCAL").append("</ejb-link>\n");
			sb.append("\t\t\t</ejb-local-ref>\n");
		}

		return sb.toString();
	}

	private void _buildRexIPXML() throws Exception {
		StringBuffer sb = new StringBuffer();

		sb.append("<?xml version=\"1.0\"?>\n");

		sb.append("\n<rexip-ejb-jar>\n");
		sb.append("\t<enterprise-beans>\n");

		SAXReader reader = SAXReaderFactory.getInstance();

		Document doc = reader.read(new File("classes/META-INF/ejb-jar.xml"));

		Iterator itr = doc.getRootElement().element("enterprise-beans").elements("session").iterator();

		while (itr.hasNext()) {
			Element entity = (Element)itr.next();

			sb.append("\t\t<session>\n");
			sb.append("\t\t\t<ejb-name>").append(entity.elementText("ejb-name")).append("</ejb-name>\n");

			String displayName = entity.elementText("display-name");

			if (displayName.endsWith("LocalServiceEJB")) {
				sb.append("\t\t\t<local-jndi-name>ejb/liferay/").append(displayName.substring(0, displayName.length() - 3)).append("Home</local-jndi-name>\n");
			}
			else {
				sb.append("\t\t\t<jndi-name>").append(entity.elementText("ejb-name")).append("</jndi-name>\n");
			}

			sb.append("\t\t\t<clustered>true</clustered>\n");
			sb.append("\t\t\t<pool-size>20</pool-size>\n");
			sb.append("\t\t\t<cache-size>20</cache-size>\n");
			sb.append("\t\t</session>\n");
		}

		sb.append("\t</enterprise-beans>\n");
		sb.append("</rexip-ejb-jar>");

		File outputFile = new File("classes/META-INF/rexip-ejb-jar.xml");

		if (!outputFile.exists() ||
			!FileUtil.read(outputFile).equals(sb.toString())) {

			FileUtil.write(outputFile, sb.toString());

			System.out.println(outputFile.toString());
		}
	}

	private void _buildSunXML() throws Exception {
		StringBuffer sb = new StringBuffer();

		sb.append("<?xml version=\"1.0\"?>\n");
		sb.append("<!DOCTYPE sun-ejb-jar PUBLIC \"-//Sun Microsystems, Inc.//DTD Sun ONE Application Server 7.0 EJB 2.0//EN\" \"http://www.sun.com/software/sunone/appserver/dtds/sun-ejb-jar_2_0-0.dtd\">\n");

		sb.append("\n<sun-ejb-jar>\n");
		sb.append("\t<enterprise-beans>\n");

		SAXReader reader = SAXReaderFactory.getInstance();

		Document doc = reader.read(new File("classes/META-INF/ejb-jar.xml"));

		Iterator itr = doc.getRootElement().element("enterprise-beans").elements("session").iterator();

		while (itr.hasNext()) {
			Element entity = (Element)itr.next();

			sb.append("\t\t<ejb>\n");
			sb.append("\t\t\t<ejb-name>").append(entity.elementText("ejb-name")).append("</ejb-name>\n");

			String displayName = entity.elementText("display-name");

			if (displayName.endsWith("LocalServiceEJB")) {
				sb.append("\t\t\t<jndi-name>ejb/liferay/").append(displayName.substring(0, displayName.length() - 3)).append("Home</jndi-name>\n");
			}
			else {
				sb.append("\t\t\t<jndi-name>").append(entity.elementText("ejb-name")).append("</jndi-name>\n");
			}

			sb.append("\t\t\t<bean-pool>\n");
			sb.append("\t\t\t\t<steady-pool-size>0</steady-pool-size>\n");
			sb.append("\t\t\t\t<resize-quantity>60</resize-quantity>\n");
			sb.append("\t\t\t\t<max-pool-size>60</max-pool-size>\n");
			sb.append("\t\t\t\t<pool-idle-timeout-in-seconds>900</pool-idle-timeout-in-seconds>\n");
			sb.append("\t\t\t</bean-pool>\n");
			sb.append("\t\t</ejb>\n");
		}

		sb.append("\t</enterprise-beans>\n");
		sb.append("</sun-ejb-jar>");

		File outputFile = new File("classes/META-INF/sun-ejb-jar.xml");

		if (!outputFile.exists() ||
			!FileUtil.read(outputFile).equals(sb.toString())) {

			FileUtil.write(outputFile, sb.toString());

			System.out.println(outputFile.toString());
		}
	}

	private void _buildWebLogicXML() throws Exception {
		StringBuffer sb = new StringBuffer();

		sb.append("<?xml version=\"1.0\"?>\n");
		sb.append("<!DOCTYPE weblogic-ejb-jar PUBLIC \"-//BEA Systems, Inc.//DTD WebLogic 7.0.0 EJB//EN\" \"http://www.bea.com/servers/wls700/dtd/weblogic-ejb-jar.dtd\">\n");

		sb.append("\n<weblogic-ejb-jar>\n");

		SAXReader reader = SAXReaderFactory.getInstance();

		Document doc = reader.read(new File("classes/META-INF/ejb-jar.xml"));

		Iterator itr = doc.getRootElement().element("enterprise-beans").elements("session").iterator();

		while (itr.hasNext()) {
			Element entity = (Element)itr.next();

			sb.append("\t<weblogic-enterprise-bean>\n");
			sb.append("\t\t<ejb-name>").append(entity.elementText("ejb-name")).append("</ejb-name>\n");

			String displayName = entity.elementText("display-name");

			if (displayName.endsWith("LocalServiceEJB")) {
				sb.append("\t\t<local-jndi-name>ejb/liferay/").append(displayName.substring(0, displayName.length() - 3)).append("Home</local-jndi-name>\n");
			}
			else {
				sb.append("\t\t<jndi-name>").append(entity.elementText("ejb-name")).append("</jndi-name>\n");
			}

			sb.append("\t</weblogic-enterprise-bean>\n");
		}

		sb.append("</weblogic-ejb-jar>");

		File outputFile = new File("classes/META-INF/weblogic-ejb-jar.xml");

		if (!outputFile.exists() ||
			!FileUtil.read(outputFile).equals(sb.toString())) {

			FileUtil.write(outputFile, sb.toString());

			System.out.println(outputFile.toString());
		}
	}

	private void _updateEJBXML() throws Exception {
		File xmlFile = new File("classes/META-INF/ejb-jar.xml");

		StringBuffer methodsSB = new StringBuffer();

		SAXReader reader = SAXReaderFactory.getInstance();

		Document doc = reader.read(xmlFile);

		Iterator itr = doc.getRootElement().element("enterprise-beans").elements("entity").iterator();

		while (itr.hasNext()) {
			Element entity = (Element)itr.next();

			methodsSB.append("\t\t\t<method>\n");
			methodsSB.append("\t\t\t\t<ejb-name>" + entity.elementText("ejb-name") + "</ejb-name>\n");
			methodsSB.append("\t\t\t\t<method-name>*</method-name>\n");
			methodsSB.append("\t\t\t</method>\n");
		}

		itr = doc.getRootElement().element("enterprise-beans").elements("session").iterator();

		while (itr.hasNext()) {
			Element entity = (Element)itr.next();

			methodsSB.append("\t\t\t<method>\n");
			methodsSB.append("\t\t\t\t<ejb-name>" + entity.elementText("ejb-name") + "</ejb-name>\n");
			methodsSB.append("\t\t\t\t<method-name>*</method-name>\n");
			methodsSB.append("\t\t\t</method>\n");
		}

		StringBuffer sb = new StringBuffer();

		sb.append("\t<assembly-descriptor>\n");
		sb.append("\t\t<method-permission>\n");
		sb.append("\t\t\t<unchecked />\n");
		sb.append(methodsSB);
		sb.append("\t\t</method-permission>\n");
		/*sb.append("\t\t<container-transaction>\n");
		sb.append(methodsSB);
		sb.append("\t\t\t<trans-attribute>Required</trans-attribute>\n");
		sb.append("\t\t</container-transaction>\n");*/
		sb.append("\t</assembly-descriptor>\n");

		String content = FileUtil.read(xmlFile);

		int x = content.indexOf("<assembly-descriptor>") - 1;
		int y = content.indexOf("</assembly-descriptor>", x) + 23;

		if (x < 0) {
			x = content.indexOf("</ejb-jar>");
			y = x;
		}

		String newContent =
			content.substring(0, x) + sb.toString() +
			content.substring(y, content.length());

		if (!content.equals(newContent)) {
			FileUtil.write(xmlFile, newContent);

			System.out.println(xmlFile.toString());
		}
	}

	private void _updateWebXML() throws Exception {
		StringBuffer sb = new StringBuffer();

		SAXReader reader = SAXReaderFactory.getInstance();

		Document doc = reader.read(new File("classes/META-INF/ejb-jar.xml"));

		// ejb-ref

		Iterator itr = doc.getRootElement().element("enterprise-beans").elements("session").iterator();

		while (itr.hasNext()) {
			Element entity = (Element)itr.next();

			String displayName = entity.elementText("display-name");

			if (!displayName.endsWith("LocalServiceEJB")) {
				sb.append("\t<ejb-ref>\n");
				sb.append("\t\t<ejb-ref-name>").append(entity.elementText("ejb-name")).append("</ejb-ref-name>\n");
				sb.append("\t\t<ejb-ref-type>Session</ejb-ref-type>\n");
				sb.append("\t\t<home>").append(entity.elementText("home")).append("</home>\n");
				sb.append("\t\t<remote>").append(entity.elementText("remote")).append("</remote>\n");
				sb.append("\t\t<ejb-link>").append(entity.elementText("ejb-name")).append("</ejb-link>\n");
				sb.append("\t</ejb-ref>\n");
			}
		}

		// web-ejb-ref.xml

		File manualFile = new File(
			"../portal-web/docroot/WEB-INF/web-ejb-ref.xml");

		if (!manualFile.exists()) {
			manualFile = new File("../ext-web/docroot/WEB-INF/web-ejb-ref.xml");
		}

		if (manualFile.exists()) {
			String content = FileUtil.read(manualFile);

			int x = content.indexOf("<ejb-ref>");
			int y = content.lastIndexOf("</ejb-ref>") + 11;

			if (x != -1) {
				sb.append(content.substring(x - 1, y));
			}
		}

		// ejb-local-ref

		itr = doc.getRootElement().element("enterprise-beans").elements("session").iterator();

		while (itr.hasNext()) {
			Element entity = (Element)itr.next();

			String displayName = entity.elementText("display-name");

			if (displayName.endsWith("LocalServiceEJB")) {
				sb.append("\t<ejb-local-ref>\n");
				sb.append("\t\t<ejb-ref-name>ejb/liferay/").append(displayName.substring(0, displayName.length() - 3)).append("Home</ejb-ref-name>\n");
				sb.append("\t\t<ejb-ref-type>Session</ejb-ref-type>\n");
				sb.append("\t\t<local-home>").append(entity.elementText("local-home")).append("</local-home>\n");
				sb.append("\t\t<local>").append(entity.elementText("local")).append("</local>\n");
				sb.append("\t\t<ejb-link>").append(entity.elementText("ejb-name")).append("</ejb-link>\n");
				sb.append("\t</ejb-local-ref>\n");
			}
		}

		// web-ejb-local-ref.xml

		manualFile = new File(
			"../portal-web/docroot/WEB-INF/web-ejb-local-ref.xml");

		if (!manualFile.exists()) {
			manualFile = new File(
				"../ext-web/docroot/WEB-INF/web-ejb-local-ref.xml");
		}

		if (manualFile.exists()) {
			String content = FileUtil.read(manualFile);

			int x = content.indexOf("<ejb-local-ref>");
			int y = content.lastIndexOf("</ejb-local-ref>") + 17;

			if (x != -1) {
				sb.append(content.substring(x - 1, y));
			}
		}

		// web.xml

		File outputFile = new File("../portal-web/docroot/WEB-INF/web.xml");

		if (!outputFile.exists()) {
			outputFile = new File("../ext-web/docroot/WEB-INF/web.xml");
		}

		String content = FileUtil.read(outputFile);
		String newContent = content;

		int x = content.indexOf("<ejb-ref>");
		
		int y = content.lastIndexOf("</ejb-local-ref>") + 17;

		if (y == -1) {
			y = content.lastIndexOf("</ejb-ref>") + 11;
		}

		if (x != -1) {
			newContent =
				content.substring(0, x - 1) + sb.toString() +
					content.substring(y, content.length());
		}
		else {
			x = content.indexOf("</web-app>");

			newContent =
				content.substring(0, x) + sb.toString() +
					content.substring(x, content.length());
		}

		if (!content.equals(newContent)) {
			FileUtil.write(outputFile, newContent);

			System.out.println(outputFile.toString());
		}
	}

	private void _updateRemotingXML() throws Exception {
		StringBuffer sb = new StringBuffer();

		SAXReader reader = SAXReaderFactory.getInstance();

		Document doc = reader.read(new File("classes/META-INF/ejb-jar.xml"));

		Iterator itr = doc.getRootElement().element("enterprise-beans").elements("session").iterator();

		while (itr.hasNext()) {
			Element entity = (Element)itr.next();

			String displayName = entity.elementText("display-name");

			if (!displayName.endsWith("LocalServiceEJB") &&
				!displayName.endsWith("RemoteServiceEJB")) {

				String serviceMapping = entity.elementText("ejb-name");
				serviceMapping = serviceMapping.substring(
					0, serviceMapping.length() - 3);
				serviceMapping = StringUtil.replace(
					serviceMapping, "_service_ejb_", "_service_spring_");

				String serviceName = entity.elementText("ejb-class");
				serviceName = serviceName.substring(
					0, serviceName.length() - 7);
				serviceName = StringUtil.replace(
					serviceName, ".service.ejb.", ".service.spring.");

				sb.append("\t<bean name=\"/").append(serviceMapping).append("-burlap\" class=\"org.springframework.remoting.caucho.BurlapServiceExporter\">\n");
				sb.append("\t\t<property name=\"service\" ref=\"").append(serviceName).append(".professional\" />\n");
				sb.append("\t\t<property name=\"serviceInterface\" value=\"").append(serviceName).append("\" />\n");
				sb.append("\t</bean>\n");

				sb.append("\t<bean name=\"/").append(serviceMapping).append("-hessian\" class=\"org.springframework.remoting.caucho.HessianServiceExporter\">\n");
				sb.append("\t\t<property name=\"service\" ref=\"").append(serviceName).append(".professional\" />\n");
				sb.append("\t\t<property name=\"serviceInterface\" value=\"").append(serviceName).append("\" />\n");
				sb.append("\t</bean>\n");

				sb.append("\t<bean name=\"/").append(serviceMapping).append("-http\" class=\"org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter\">\n");
				sb.append("\t\t<property name=\"service\" ref=\"").append(serviceName).append(".professional\" />\n");
				sb.append("\t\t<property name=\"serviceInterface\" value=\"").append(serviceName).append("\" />\n");
				sb.append("\t</bean>\n");
			}
		}

		File outputFile = new File(
			"../tunnel-web/docroot/WEB-INF/remoting-servlet.xml");
		if (!outputFile.exists()) {
			outputFile = new File(
				"../ext-web/docroot/WEB-INF/remoting-servlet-ext.xml");
		}

		String content = FileUtil.read(outputFile);
		String newContent = content;

		int x = content.indexOf("<bean ");
		int y = content.lastIndexOf("</bean>") + 8;

		if (x != -1) {
			newContent =
				content.substring(0, x - 1) + sb.toString() +
					content.substring(y, content.length());
		}
		else {
			x = content.indexOf("</beans>");

			newContent =
				content.substring(0, x) + sb.toString() +
					content.substring(x, content.length());
		}

		if (!content.equals(newContent)) {
			FileUtil.write(outputFile, newContent);

			System.out.println(outputFile.toString());
		}
	}

	private String _jarFileName;

}