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
import com.liferay.util.StringUtil;

import java.io.File;

import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * <a href="EJBXMLBuilder.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
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
			_buildWebLogicXML(8.1);
			_buildWebLogicXML(9.2);
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
		StringMaker sm = new StringMaker();

		sm.append("<?xml version=\"1.0\"?>\n");
		sm.append("<!DOCTYPE ejb-jar PUBLIC \"-//Borland Software Corporation//DTD Enterprise JavaBeans 2.0//EN\" \"http://www.borland.com/devsupport/appserver/dtds/ejb-jar_2_0-borland.dtd\">\n");

		sm.append("\n<ejb-jar>\n");
		sm.append("\t<enterprise-beans>\n");

		SAXReader reader = SAXReaderFactory.getInstance();

		Document doc = reader.read(new File("classes/META-INF/ejb-jar.xml"));

		Iterator itr = doc.getRootElement().element("enterprise-beans").elements("session").iterator();

		while (itr.hasNext()) {
			Element entity = (Element)itr.next();

			sm.append("\t\t<session>\n");
			sm.append("\t\t\t<ejb-name>").append(entity.elementText("ejb-name")).append("</ejb-name>\n");

			String displayName = entity.elementText("display-name");

			if (displayName.endsWith("LocalServiceEJB")) {
				sm.append("\t\t\t<bean-local-home-name>ejb/liferay/").append(displayName.substring(0, displayName.length() - 3)).append("Home</bean-local-home-name>\n");
			}
			else {
				sm.append("\t\t\t<bean-home-name>").append(entity.elementText("ejb-name")).append("</bean-home-name>\n");
			}

			sm.append("\t\t</session>\n");
		}

		sm.append("\t</enterprise-beans>\n");
		sm.append("\t<property>\n");
		sm.append("\t\t<prop-name>ejb.default_transaction_attribute</prop-name>\n");
		sm.append("\t\t<prop-type>String</prop-type>\n");
		sm.append("\t\t<prop-value>Supports</prop-value>\n");
		sm.append("\t</property>\n");
		sm.append("</ejb-jar>");

		File outputFile = new File("classes/META-INF/ejb-borland.xml");

		if (!outputFile.exists() ||
			!FileUtil.read(outputFile).equals(sm.toString())) {

			FileUtil.write(outputFile, sm.toString());

			System.out.println(outputFile.toString());
		}
	}

	private void _buildJOnASXML() throws Exception {
		StringMaker sm = new StringMaker();

		sm.append("<?xml version=\"1.0\"?>\n");
		sm.append("<!DOCTYPE jonas-ejb-jar PUBLIC \"-//ObjectWeb//DTD JOnAS 3.2//EN\" \"http://www.objectweb.org/jonas/dtds/jonas-ejb-jar_3_2.dtd\">\n");

		sm.append("\n<jonas-ejb-jar>\n");

		SAXReader reader = SAXReaderFactory.getInstance();

		Document doc = reader.read(new File("classes/META-INF/ejb-jar.xml"));

		Iterator itr = doc.getRootElement().element("enterprise-beans").elements("session").iterator();

		while (itr.hasNext()) {
			Element entity = (Element)itr.next();

			sm.append("\t<jonas-session>\n");
			sm.append("\t\t<ejb-name>").append(entity.elementText("ejb-name")).append("</ejb-name>\n");

			String displayName = entity.elementText("display-name");

			if (displayName.endsWith("LocalServiceEJB")) {
				sm.append("\t\t<jndi-name>ejb/liferay/").append(displayName.substring(0, displayName.length() - 3)).append("Home</jndi-name>\n");
			}
			else {
				sm.append("\t\t<jndi-name>").append(entity.elementText("ejb-name")).append("</jndi-name>\n");
			}

			sm.append("\t\t<jonas-resource>\n");
			sm.append("\t\t\t<res-ref-name>jdbc/LiferayPool</res-ref-name>\n");
			sm.append("\t\t\t<jndi-name>jdbc/LiferayPool</jndi-name>\n");
			sm.append("\t\t</jonas-resource>\n");
			sm.append("\t\t<jonas-resource>\n");
			sm.append("\t\t\t<res-ref-name>mail/MailSession</res-ref-name>\n");
			sm.append("\t\t\t<jndi-name>mail/MailSession</jndi-name>\n");
			sm.append("\t\t</jonas-resource>\n");
			sm.append("\t</jonas-session>\n");
		}

		sm.append("</jonas-ejb-jar>");

		File outputFile = new File("classes/META-INF/jonas-ejb-jar.xml");

		if (!outputFile.exists() ||
			!FileUtil.read(outputFile).equals(sm.toString())) {

			FileUtil.write(outputFile, sm.toString());

			System.out.println(outputFile.toString());
		}
	}

	private void _buildJRunXML() throws Exception {
		StringMaker sm = new StringMaker();

		sm.append("<?xml version=\"1.0\"?>\n");
		sm.append("<!DOCTYPE jrun-ejb-jar PUBLIC \"-//Macromedia, Inc.//DTD jrun-ejb-jar 4.0//EN\" \"http://jrun.macromedia.com/dtds/jrun-ejb-jar.dtd\">\n");

		sm.append("\n<jrun-ejb-jar>\n");
		sm.append("\t<enterprise-beans>\n");

		SAXReader reader = SAXReaderFactory.getInstance();

		Document doc = reader.read(new File("classes/META-INF/ejb-jar.xml"));

		Iterator itr = doc.getRootElement().element("enterprise-beans").elements("session").iterator();

		while (itr.hasNext()) {
			Element entity = (Element)itr.next();

			sm.append("\t\t<session>\n");
			sm.append("\t\t\t<ejb-name>").append(entity.elementText("ejb-name")).append("</ejb-name>\n");

			String displayName = entity.elementText("display-name");

			if (displayName.endsWith("LocalServiceEJB")) {
				sm.append("\t\t\t<local-jndi-name>ejb/liferay/").append(displayName.substring(0, displayName.length() - 3)).append("Home</local-jndi-name>\n");
			}
			else {
				sm.append("\t\t\t<jndi-name>").append(entity.elementText("ejb-name")).append("</jndi-name>\n");
			}

			sm.append("\t\t\t<cluster-home>false</cluster-home>\n");
			sm.append("\t\t\t<cluster-object>false</cluster-object>\n");
			sm.append("\t\t\t<timeout>3000</timeout>\n");
			sm.append("\t\t</session>\n");
		}

		sm.append("\t</enterprise-beans>\n");
		sm.append("</jrun-ejb-jar>");

		File outputFile = new File("classes/META-INF/jrun-ejb-jar.xml");

		if (!outputFile.exists() ||
			!FileUtil.read(outputFile).equals(sm.toString())) {

			FileUtil.write(outputFile, sm.toString());

			System.out.println(outputFile.toString());
		}
	}

	private void _buildPramatiXML() throws Exception {
		StringMaker sm = new StringMaker();

		sm.append("<?xml version=\"1.0\"?>\n");
		sm.append("<!DOCTYPE pramati-j2ee-server PUBLIC \"-//Pramati Technologies //DTD Pramati J2ee Server 5.0//EN\" \"http://www.pramati.com/dtd/pramati-j2ee-server_5_0.dtd\">\n");

		sm.append("\n<pramati-j2ee-server>\n");
		sm.append("\t<vhost-name>default</vhost-name>\n");
		sm.append("\t<auto-start>TRUE</auto-start>\n");
		sm.append("\t<realm-name />\n");
		sm.append("\t<ejb-module>\n");
		sm.append("\t\t<name>").append(_jarFileName).append("</name>\n");

		SAXReader reader = SAXReaderFactory.getInstance();

		Document doc = reader.read(new File("classes/META-INF/ejb-jar.xml"));

		Iterator itr = doc.getRootElement().element("enterprise-beans").elements("session").iterator();

		while (itr.hasNext()) {
			Element entity = (Element)itr.next();

			sm.append("\t\t<ejb>\n");
			sm.append("\t\t\t<name>").append(entity.elementText("ejb-name")).append("</name>\n");
			sm.append("\t\t\t<max-pool-size>40</max-pool-size>\n");
			sm.append("\t\t\t<min-pool-size>20</min-pool-size>\n");
			sm.append("\t\t\t<enable-freepool>false</enable-freepool>\n");
			sm.append("\t\t\t<pool-waittimeout-millis>60000</pool-waittimeout-millis>\n");
			sm.append("\t\t\t<pk-waittimeout-millis/>\n");
			sm.append("\t\t\t<low-activity-interval>20</low-activity-interval>\n");
			sm.append("\t\t\t<is-secure>false</is-secure>\n");
			sm.append("\t\t\t<is-clustered>true</is-clustered>\n");

			String displayName = entity.elementText("display-name");

			if (displayName.endsWith("LocalServiceEJB")) {
				sm.append("\t\t\t<jndi-name>ejb/liferay/").append(displayName.substring(0, displayName.length() - 3)).append("Home</jndi-name>\n");
				sm.append("\t\t\t<local-jndi-name>").append(entity.elementText("ejb-name")).append("__PRAMATI_LOCAL").append("</local-jndi-name>\n");
			}
			else {
				sm.append("\t\t\t<jndi-name>").append(entity.elementText("ejb-name")).append("</jndi-name>\n");
			}

			sm.append(_buildPramatiXMLRefs(entity));

			sm.append("\t\t</ejb>\n");
		}

		sm.append("\t</ejb-module>\n");
		sm.append("</pramati-j2ee-server>");

		File outputFile = new File("classes/pramati-j2ee-server.xml");

		if (!outputFile.exists() ||
			!FileUtil.read(outputFile).equals(sm.toString())) {

			FileUtil.write(outputFile, sm.toString());

			System.out.println(outputFile.toString());
		}
	}

	private String _buildPramatiXMLRefs(Element entity) {
		StringMaker sm = new StringMaker();

		Iterator itr = entity.elements("ejb-ref").iterator();

		while (itr.hasNext()) {
			Element ejbRef = (Element)itr.next();

			sm.append("\t\t\t<ejb-ref>\n");
			sm.append("\t\t\t\t<ejb-ref-name>").append(ejbRef.elementText("ejb-ref-name")).append("</ejb-ref-name>\n");
			sm.append("\t\t\t\t<ejb-link>").append(ejbRef.elementText("ejb-link")).append("</ejb-link>\n");
			sm.append("\t\t\t</ejb-ref>\n");
		}

		itr = entity.elements("ejb-local-ref").iterator();

		while (itr.hasNext()) {
			Element ejbRef = (Element)itr.next();

			sm.append("\t\t\t<ejb-local-ref>\n");
			sm.append("\t\t\t\t<ejb-ref-name>").append(ejbRef.elementText("ejb-ref-name")).append("</ejb-ref-name>\n");
			sm.append("\t\t\t\t<ejb-link>").append(ejbRef.elementText("ejb-link")).append("__PRAMATI_LOCAL").append("</ejb-link>\n");
			sm.append("\t\t\t</ejb-local-ref>\n");
		}

		sm.append("\t\t\t<resource-mapping>\n");
		sm.append("\t\t\t\t<resource-name>jdbc/LiferayPool</resource-name>\n");
		sm.append("\t\t\t\t<resource-type>javax.sql.DataSource</resource-type>\n");
		sm.append("\t\t\t\t<resource-link>LiferayPool</resource-link>\n");
		sm.append("\t\t\t</resource-mapping>\n");

		sm.append("\t\t\t<resource-mapping>\n");
		sm.append("\t\t\t\t<resource-name>mail/MailSession</resource-name>\n");
		sm.append("\t\t\t\t<resource-type>javax.mail.Session</resource-type>\n");
		sm.append("\t\t\t\t<resource-link>mail/MailSession</resource-link>\n");
		sm.append("\t\t\t</resource-mapping>\n");

		return sm.toString();
	}

	private void _buildRexIPXML() throws Exception {
		StringMaker sm = new StringMaker();

		sm.append("<?xml version=\"1.0\"?>\n");

		sm.append("\n<rexip-ejb-jar>\n");
		sm.append("\t<enterprise-beans>\n");

		SAXReader reader = SAXReaderFactory.getInstance();

		Document doc = reader.read(new File("classes/META-INF/ejb-jar.xml"));

		Iterator itr = doc.getRootElement().element("enterprise-beans").elements("session").iterator();

		while (itr.hasNext()) {
			Element entity = (Element)itr.next();

			sm.append("\t\t<session>\n");
			sm.append("\t\t\t<ejb-name>").append(entity.elementText("ejb-name")).append("</ejb-name>\n");

			String displayName = entity.elementText("display-name");

			if (displayName.endsWith("LocalServiceEJB")) {
				sm.append("\t\t\t<local-jndi-name>ejb/liferay/").append(displayName.substring(0, displayName.length() - 3)).append("Home</local-jndi-name>\n");
			}
			else {
				sm.append("\t\t\t<jndi-name>").append(entity.elementText("ejb-name")).append("</jndi-name>\n");
			}

			sm.append("\t\t\t<clustered>true</clustered>\n");
			sm.append("\t\t\t<pool-size>20</pool-size>\n");
			sm.append("\t\t\t<cache-size>20</cache-size>\n");
			sm.append("\t\t</session>\n");
		}

		sm.append("\t</enterprise-beans>\n");
		sm.append("</rexip-ejb-jar>");

		File outputFile = new File("classes/META-INF/rexip-ejb-jar.xml");

		if (!outputFile.exists() ||
			!FileUtil.read(outputFile).equals(sm.toString())) {

			FileUtil.write(outputFile, sm.toString());

			System.out.println(outputFile.toString());
		}
	}

	private void _buildSunXML() throws Exception {
		StringMaker sm = new StringMaker();

		sm.append("<?xml version=\"1.0\"?>\n");
		sm.append("<!DOCTYPE sun-ejb-jar PUBLIC \"-//Sun Microsystems, Inc.//DTD Sun ONE Application Server 7.0 EJB 2.0//EN\" \"http://www.sun.com/software/sunone/appserver/dtds/sun-ejb-jar_2_0-0.dtd\">\n");

		sm.append("\n<sun-ejb-jar>\n");
		sm.append("\t<enterprise-beans>\n");

		SAXReader reader = SAXReaderFactory.getInstance();

		Document doc = reader.read(new File("classes/META-INF/ejb-jar.xml"));

		Iterator itr = doc.getRootElement().element("enterprise-beans").elements("session").iterator();

		while (itr.hasNext()) {
			Element entity = (Element)itr.next();

			sm.append("\t\t<ejb>\n");
			sm.append("\t\t\t<ejb-name>").append(entity.elementText("ejb-name")).append("</ejb-name>\n");

			String displayName = entity.elementText("display-name");

			if (displayName.endsWith("LocalServiceEJB")) {
				sm.append("\t\t\t<jndi-name>ejb/liferay/").append(displayName.substring(0, displayName.length() - 3)).append("Home</jndi-name>\n");
			}
			else {
				sm.append("\t\t\t<jndi-name>").append(entity.elementText("ejb-name")).append("</jndi-name>\n");
			}

			sm.append("\t\t\t<bean-pool>\n");
			sm.append("\t\t\t\t<steady-pool-size>0</steady-pool-size>\n");
			sm.append("\t\t\t\t<resize-quantity>60</resize-quantity>\n");
			sm.append("\t\t\t\t<max-pool-size>60</max-pool-size>\n");
			sm.append("\t\t\t\t<pool-idle-timeout-in-seconds>900</pool-idle-timeout-in-seconds>\n");
			sm.append("\t\t\t</bean-pool>\n");
			sm.append("\t\t</ejb>\n");
		}

		sm.append("\t</enterprise-beans>\n");
		sm.append("</sun-ejb-jar>");

		File outputFile = new File("classes/META-INF/sun-ejb-jar.xml");

		if (!outputFile.exists() ||
			!FileUtil.read(outputFile).equals(sm.toString())) {

			FileUtil.write(outputFile, sm.toString());

			System.out.println(outputFile.toString());
		}
	}

	private void _buildWebLogicXML(double version) throws Exception {
		StringMaker sm = new StringMaker();

		sm.append("<?xml version=\"1.0\"?>\n");

		if (version == 8.1) {
			sm.append("<!DOCTYPE weblogic-ejb-jar PUBLIC \"-//BEA Systems, Inc.//DTD WebLogic 8.1.0 EJB//EN\" \"http://www.bea.com/servers/wls810/dtd/weblogic-ejb-jar.dtd\">\n");

			sm.append("\n<weblogic-ejb-jar>\n");
		}
		else {
			sm.append("\n<weblogic-ejb-jar xmlns=\"http://www.bea.com/ns/weblogic/90\" xmlns:j2ee=\"http://java.sun.com/xml/ns/j2ee\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.bea.com/ns/weblogic/90 http://www.bea.com/ns/weblogic/90/weblogic-ejb-jar.xsd\">\n");
		}

		SAXReader reader = SAXReaderFactory.getInstance();

		Document doc = reader.read(new File("classes/META-INF/ejb-jar.xml"));

		Iterator itr = doc.getRootElement().element("enterprise-beans").elements("session").iterator();

		while (itr.hasNext()) {
			Element entity = (Element)itr.next();

			sm.append("\t<weblogic-enterprise-bean>\n");
			sm.append("\t\t<ejb-name>").append(entity.elementText("ejb-name")).append("</ejb-name>\n");

			String displayName = entity.elementText("display-name");

			if (version == 8.1) {
				sm.append("\t\t<reference-descriptor>\n");
				sm.append("\t\t\t<resource-description>\n");
				sm.append("\t\t\t\t<res-ref-name>mail/MailSession</res-ref-name>\n");
				sm.append("\t\t\t\t<jndi-name>mail/MailSession</jndi-name>\n");
				sm.append("\t\t\t</resource-description>\n");
				sm.append("\t\t</reference-descriptor>\n");
			}
			else {
				sm.append("\t\t<resource-description>\n");
				sm.append("\t\t\t<res-ref-name>mail/MailSession</res-ref-name>\n");
				sm.append("\t\t\t<jndi-name>mail/MailSession</jndi-name>\n");
				sm.append("\t\t</resource-description>\n");
			}

			sm.append("\t\t<enable-call-by-reference>true</enable-call-by-reference>\n");

			if (displayName.endsWith("LocalServiceEJB")) {
				sm.append("\t\t<local-jndi-name>ejb/liferay/").append(displayName.substring(0, displayName.length() - 3)).append("Home</local-jndi-name>\n");
			}
			else {
				sm.append("\t\t<jndi-name>").append(entity.elementText("ejb-name")).append("</jndi-name>\n");
			}

			sm.append("\t</weblogic-enterprise-bean>\n");
		}

		sm.append("</weblogic-ejb-jar>");

		File outputFile = null;
		
		if (version == 8.1) {
			outputFile = new File("classes/META-INF/weblogic-ejb-jar.xml.81");
		}
		else {
			outputFile = new File("classes/META-INF/weblogic-ejb-jar.xml");
		}

		if (!outputFile.exists() ||
			!FileUtil.read(outputFile).equals(sm.toString())) {

			FileUtil.write(outputFile, sm.toString());

			System.out.println(outputFile.toString());
		}
	}

	private void _updateEJBXML() throws Exception {
		File xmlFile = new File("classes/META-INF/ejb-jar.xml");

		StringMaker methodsSB = new StringMaker();

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

		StringMaker sm = new StringMaker();

		sm.append("\t<assembly-descriptor>\n");
		sm.append("\t\t<method-permission>\n");
		sm.append("\t\t\t<unchecked />\n");
		sm.append(methodsSB);
		sm.append("\t\t</method-permission>\n");
		/*sm.append("\t\t<container-transaction>\n");
		sm.append(methodsSB);
		sm.append("\t\t\t<trans-attribute>Required</trans-attribute>\n");
		sm.append("\t\t</container-transaction>\n");*/
		sm.append("\t</assembly-descriptor>\n");

		String content = FileUtil.read(xmlFile);

		int x = content.indexOf("<assembly-descriptor>") - 1;
		int y = content.indexOf("</assembly-descriptor>", x) + 23;

		if (x < 0) {
			x = content.indexOf("</ejb-jar>");
			y = x;
		}

		String newContent =
			content.substring(0, x) + sm.toString() +
			content.substring(y, content.length());

		if (!content.equals(newContent)) {
			FileUtil.write(xmlFile, newContent);

			System.out.println(xmlFile.toString());
		}
	}

	private void _updateRemotingXML() throws Exception {
		StringMaker sm = new StringMaker();

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
					serviceName, ".service.ejb.", ".service.");

				System.out.println("writing transaction spring remoting for " + serviceName);
				sm.append("\t<bean name=\"/").append(serviceMapping).append("-burlap\" class=\"org.springframework.remoting.caucho.BurlapServiceExporter\">\n");
				sm.append("\t\t<property name=\"service\" ref=\"").append(serviceName).append(".transaction\" />\n");
				sm.append("\t\t<property name=\"serviceInterface\" value=\"").append(serviceName).append("\" />\n");
				sm.append("\t</bean>\n");

				sm.append("\t<bean name=\"/").append(serviceMapping).append("-hessian\" class=\"org.springframework.remoting.caucho.HessianServiceExporter\">\n");
				sm.append("\t\t<property name=\"service\" ref=\"").append(serviceName).append(".transaction\" />\n");
				sm.append("\t\t<property name=\"serviceInterface\" value=\"").append(serviceName).append("\" />\n");
				sm.append("\t</bean>\n");

				sm.append("\t<bean name=\"/").append(serviceMapping).append("-http\" class=\"org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter\">\n");
				sm.append("\t\t<property name=\"service\" ref=\"").append(serviceName).append(".transaction\" />\n");
				sm.append("\t\t<property name=\"serviceInterface\" value=\"").append(serviceName).append("\" />\n");
				sm.append("\t</bean>\n");
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
				content.substring(0, x - 1) + sm.toString() +
					content.substring(y, content.length());
		}
		else {
			x = content.indexOf("</beans>");

			newContent =
				content.substring(0, x) + sm.toString() +
					content.substring(x, content.length());
		}

		System.out.println("writing transaction spring remoting");
		if (!content.equals(newContent)) {
			FileUtil.write(outputFile, newContent);

			System.out.println(outputFile.toString());
		}
	}

	private void _updateWebXML() throws Exception {
		StringMaker sm = new StringMaker();

		SAXReader reader = SAXReaderFactory.getInstance();

		Document doc = reader.read(new File("classes/META-INF/ejb-jar.xml"));

		// ejb-ref

		Iterator itr = doc.getRootElement().element("enterprise-beans").elements("session").iterator();

		while (itr.hasNext()) {
			Element entity = (Element)itr.next();

			String displayName = entity.elementText("display-name");

			if (!displayName.endsWith("LocalServiceEJB")) {
				sm.append("\t<ejb-ref>\n");
				sm.append("\t\t<ejb-ref-name>").append(entity.elementText("ejb-name")).append("</ejb-ref-name>\n");
				sm.append("\t\t<ejb-ref-type>Session</ejb-ref-type>\n");
				sm.append("\t\t<home>").append(entity.elementText("home")).append("</home>\n");
				sm.append("\t\t<remote>").append(entity.elementText("remote")).append("</remote>\n");
				sm.append("\t\t<ejb-link>").append(entity.elementText("ejb-name")).append("</ejb-link>\n");
				sm.append("\t</ejb-ref>\n");
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
				sm.append(content.substring(x - 1, y));
			}
		}

		// ejb-local-ref

		itr = doc.getRootElement().element("enterprise-beans").elements("session").iterator();

		while (itr.hasNext()) {
			Element entity = (Element)itr.next();

			String displayName = entity.elementText("display-name");

			if (displayName.endsWith("LocalServiceEJB")) {
				sm.append("\t<ejb-local-ref>\n");
				sm.append("\t\t<ejb-ref-name>ejb/liferay/").append(displayName.substring(0, displayName.length() - 3)).append("Home</ejb-ref-name>\n");
				sm.append("\t\t<ejb-ref-type>Session</ejb-ref-type>\n");
				sm.append("\t\t<local-home>").append(entity.elementText("local-home")).append("</local-home>\n");
				sm.append("\t\t<local>").append(entity.elementText("local")).append("</local>\n");
				sm.append("\t\t<ejb-link>").append(entity.elementText("ejb-name")).append("</ejb-link>\n");
				sm.append("\t</ejb-local-ref>\n");
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
				sm.append(content.substring(x - 1, y));
			}
		}

		// web.xml

		_updateWebXML("../portal-web/docroot/WEB-INF/web.xml", sm);
		_updateWebXML("../ext-web/docroot/WEB-INF/web.xml", sm);
		_updateWebXML("../tunnel-web/docroot/WEB-INF/web.xml", sm);
	}

	private void _updateWebXML(String fileName, StringMaker sm)
		throws Exception {

		File outputFile = new File(fileName);

		if (!outputFile.exists()) {
			return;
		}

		String content = FileUtil.read(outputFile);
		String newContent = content;

		int x = content.indexOf("<ejb-ref>");

		if (x == -1) {
			x = content.indexOf("<ejb-local-ref>");
		}

		int y = content.lastIndexOf("</ejb-local-ref>");

		if (y == -1) {
			y = content.lastIndexOf("</ejb-ref>") + 11;
		}
		else {
			y += 17;
		}

		if (x != -1) {
			newContent =
				content.substring(0, x - 1) + sm.toString() +
					content.substring(y, content.length());
		}
		else {
			x = content.indexOf("</web-app>");

			newContent =
				content.substring(0, x) + sm.toString() +
					content.substring(x, content.length());
		}

		if (!content.equals(newContent)) {
			FileUtil.write(outputFile, newContent);

			System.out.println(outputFile.toString());
		}
	}

	private String _jarFileName;

}