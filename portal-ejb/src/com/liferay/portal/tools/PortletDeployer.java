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
import com.liferay.portal.util.Constants;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.SAXReaderFactory;
import com.liferay.util.FileUtil;
import com.liferay.util.Validator;
import com.liferay.util.xml.XMLFormatter;
import com.liferay.util.xml.XMLMerger;
import com.liferay.util.xml.descriptor.FacesXMLDescriptor;

import java.io.File;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * <a href="PortletDeployer.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Brian Myunghun Kim
 *
 */
public class PortletDeployer extends BaseDeployer {

	public static void main(String[] args) {
		List wars = new ArrayList();
		List jars = new ArrayList();

		for (int i = 0; i < args.length; i++) {
			if (args[i].endsWith(".war")) {
				wars.add(args[i]);
			}
			else if (args[i].endsWith(".jar")) {
				jars.add(args[i]);
			}
		}

		new PortletDeployer(wars, jars);
	}

	protected PortletDeployer() {
	}

	protected PortletDeployer(List wars, List jars) {
		super(wars, jars);
	}

	protected void checkArguments() {
		super.checkArguments();

		if (Validator.isNull(portletTaglibDTD)) {
			throw new IllegalArgumentException(
				"The system property deployer.portlet.taglib.dtd is not set");
		}
	}

	protected String getExtraContent(
			double webXmlVersion, File srcFile, String displayName)
		throws Exception {

		String extraContent = super.getExtraContent(
			webXmlVersion, srcFile, displayName);

		extraContent +=
			"<listener>" +
			"<listener-class>" +
			"com.liferay.portal.kernel.servlet.PortletContextListener" +
			"</listener-class>" +
			"</listener>";

		File facesXML = new File(srcFile + "/WEB-INF/faces-config.xml");
		File portletXML = new File(srcFile + "/WEB-INF/portlet.xml");
		File webXML = new File(srcFile + "/WEB-INF/web.xml");

		extraContent += _getServletContent(portletXML, webXML);

		_setupJSF(facesXML, portletXML);

		if (_sunFacesPortlet) {
			extraContent +=
				"<listener>" +
				"<listener-class>" +
				"com.liferay.util.jsf.sun.faces.config." +
					"LiferayConfigureListener" +
				"</listener-class>" +
				"</listener>";
		}

		return extraContent;
	}

	private String _getServletContent(File portletXML, File webXML)
		throws Exception {

		StringMaker sm = new StringMaker();

		SAXReader reader = SAXReaderFactory.getInstance(false);

		// Add wrappers for portlets

		Document doc = reader.read(portletXML);

		Element root = doc.getRootElement();

		Iterator itr1 = root.elements("portlet").iterator();

		while (itr1.hasNext()) {
			Element portlet = (Element)itr1.next();

			String portletName = PortalUtil.getJsSafePortletName(
				portlet.elementText("portlet-name"));
			String portletClass = portlet.elementText("portlet-class");

			sm.append("<servlet>");
			sm.append("<servlet-name>");
			sm.append(portletName);
			sm.append("</servlet-name>");
			sm.append("<servlet-class>");
			sm.append("com.liferay.portal.kernel.servlet.PortletServlet");
			sm.append("</servlet-class>");
			sm.append("<init-param>");
			sm.append("<param-name>portlet-class</param-name>");
			sm.append("<param-value>");
			sm.append(portletClass);
			sm.append("</param-value>");
			sm.append("</init-param>");
			sm.append("<load-on-startup>0</load-on-startup>");
			sm.append("</servlet>");

			sm.append("<servlet-mapping>");
			sm.append("<servlet-name>");
			sm.append(portletName);
			sm.append("</servlet-name>");
			sm.append("<url-pattern>/");
			sm.append(portletName);
			sm.append("/*</url-pattern>");
			sm.append("</servlet-mapping>");
		}

		// Make sure there is a company id specified

		reader = SAXReaderFactory.getInstance(false);

		doc = reader.read(webXML);

		root = doc.getRootElement();

		// Remove deprecated references to SharedServletWrapper

		itr1 = root.elements("servlet").iterator();

		while (itr1.hasNext()) {
			Element servlet = (Element)itr1.next();

			String icon = servlet.elementText("icon");
			String servletName = servlet.elementText("servlet-name");
			String displayName = servlet.elementText("display-name");
			String description = servlet.elementText("description");
			String servletClass = servlet.elementText("servlet-class");
			List initParams = servlet.elements("init-param");
			String loadOnStartup = servlet.elementText("load-on-startup");
			String runAs = servlet.elementText("run-as");
			List securityRoleRefs = servlet.elements("security-role-ref");

			if ((servletClass != null) &&
				(servletClass.equals(
					"com.liferay.portal.servlet.SharedServletWrapper"))) {

				sm.append("<servlet>");

				if (icon != null) {
					sm.append("<icon>");
					sm.append(icon);
					sm.append("</icon>");
				}

				if (servletName != null) {
					sm.append("<servlet-name>");
					sm.append(servletName);
					sm.append("</servlet-name>");
				}

				if (displayName != null) {
					sm.append("<display-name>");
					sm.append(displayName);
					sm.append("</display-name>");
				}

				if (description != null) {
					sm.append("<description>");
					sm.append(description);
					sm.append("</description>");
				}

				Iterator itr2 = initParams.iterator();

				while (itr2.hasNext()) {
					Element initParam = (Element)itr2.next();

					String paramName = initParam.elementText("param-name");
					String paramValue = initParam.elementText("param-value");

					if ((paramName != null) &&
						(paramName.equals("servlet-class"))) {

						sm.append("<servlet-class>");
						sm.append(paramValue);
						sm.append("</servlet-class>");
					}
				}

				itr2 = initParams.iterator();

				while (itr2.hasNext()) {
					Element initParam = (Element)itr2.next();

					String paramName = initParam.elementText("param-name");
					String paramValue = initParam.elementText("param-value");
					String paramDesc = initParam.elementText("description");

					if ((paramName != null) &&
						(!paramName.equals("servlet-class"))) {

						sm.append("<init-param>");
						sm.append("<param-name>");
						sm.append(paramName);
						sm.append("</param-name>");

						if (paramValue != null) {
							sm.append("<param-value>");
							sm.append(paramValue);
							sm.append("</param-value>");
						}

						if (paramDesc != null) {
							sm.append("<description>");
							sm.append(paramDesc);
							sm.append("</description>");
						}

						sm.append("</init-param>");
					}
				}

				if (loadOnStartup != null) {
					sm.append("<load-on-startup>");
					sm.append(loadOnStartup);
					sm.append("</load-on-startup>");
				}

				if (runAs != null) {
					sm.append("<run-as>");
					sm.append(runAs);
					sm.append("</run-as>");
				}

				itr2 = securityRoleRefs.iterator();

				while (itr2.hasNext()) {
					Element roleRef = (Element)itr2.next();

					String roleDesc = roleRef.elementText("description");
					String roleName = roleRef.elementText("role-name");
					String roleLink = roleRef.elementText("role-link");

					sm.append("<security-role-ref>");

					if (roleDesc != null) {
						sm.append("<description>");
						sm.append(roleDesc);
						sm.append("</description>");
					}

					if (roleName != null) {
						sm.append("<role-name>");
						sm.append(roleName);
						sm.append("</role-name>");
					}

					if (roleLink != null) {
						sm.append("<role-link>");
						sm.append(roleLink);
						sm.append("</role-link>");
					}

					sm.append("</security-role-ref>");
				}

				sm.append("</servlet>");
			}
		}

		return sm.toString();
	}

	private void _setupJSF(File facesXML, File portletXML) throws Exception {
		_myFacesPortlet = false;
		_sunFacesPortlet = false;

		if (!facesXML.exists()) {
			return;
		}

		// portlet.xml

		SAXReader reader = SAXReaderFactory.getInstance();

		Document doc = reader.read(portletXML);

		Element root = doc.getRootElement();

		List elements = root.elements("portlet");

		Iterator itr = elements.iterator();

		while (itr.hasNext()) {
			Element portlet = (Element)itr.next();

			String portletClass = portlet.elementText("portlet-class");

			if (portletClass.equals(Constants.JSF_MYFACES)) {
				_myFacesPortlet = true;

				break;
			}
			else if (portletClass.equals(Constants.JSF_SUN)) {
				_sunFacesPortlet = true;

				break;
			}
		}

		// faces-config.xml

		reader = SAXReaderFactory.getInstance();

		doc = reader.read(facesXML);

		root = doc.getRootElement();

		Element factoryEl = root.element("factory");

		Element renderKitFactoryEl = null;
		Element facesContextFactoryEl = null;

		if (factoryEl == null) {
			factoryEl = root.addElement("factory");
		}

		renderKitFactoryEl = factoryEl.element("render-kit-factory");
		facesContextFactoryEl = factoryEl.element("faces-context-factory");

		if ((appServerType.equals("orion") && (_sunFacesPortlet) &&
			(renderKitFactoryEl == null))) {

			renderKitFactoryEl = factoryEl.addElement("render-kit-factory");

			renderKitFactoryEl.addText(Constants.LIFERAY_RENDER_KIT_FACTORY);
		}
		else if (_myFacesPortlet && (facesContextFactoryEl == null)) {
			facesContextFactoryEl =
				factoryEl.addElement("faces-context-factory");

			facesContextFactoryEl.addText(Constants.MYFACES_CONTEXT_FACTORY);
		}

		if (!appServerType.equals("orion") && (_sunFacesPortlet)) {
			factoryEl.detach();
		}

		XMLMerger merger = new XMLMerger(new FacesXMLDescriptor());

		merger.organizeXML(doc);

		FileUtil.write(facesXML, XMLFormatter.toString(doc), true);
	}

	private boolean _myFacesPortlet;
	private boolean _sunFacesPortlet;

}