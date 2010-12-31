/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.tools;

import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.util.InitUtil;
import com.liferay.util.ant.Java2WsddTask;

import java.io.File;
import java.io.IOException;

import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class WSDDBuilder {

	public static void main(String[] args) {
		InitUtil.initWithSpring();

		if (args.length == 2) {
			new WSDDBuilder(args[0], args[1]);
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	public WSDDBuilder(String fileName, String serverConfigFileName) {
		try {
			_serverConfigFileName = serverConfigFileName;

			if (!FileUtil.exists(_serverConfigFileName)) {
				ClassLoader classLoader = getClass().getClassLoader();

				String serverConfigContent = StringUtil.read(
					classLoader,
					"com/liferay/portal/tools/dependencies/server-config.wsdd");

				FileUtil.write(_serverConfigFileName, serverConfigContent);
			}

			if (FileUtil.exists("docroot/WEB-INF/src/")) {
				_portalWsdd = false;
			}

			Document doc = SAXReaderUtil.read(new File(fileName), true);

			Element root = doc.getRootElement();

			String packagePath = root.attributeValue("package-path");

			Element portlet = root.element("portlet");
			Element namespace = root.element("namespace");

			if (portlet != null) {
				_portletShortName = portlet.attributeValue("short-name");
			}
			else {
				_portletShortName = namespace.getText();
			}

			if (_portalWsdd) {
				_outputPath = "src/";
			}
			else {
				_outputPath = "docroot/WEB-INF/src/";
			}

			_outputPath +=
				StringUtil.replace(packagePath, ".", "/") + "/service/http";

			_packagePath = packagePath;

			List<Element> entities = root.elements("entity");

			Iterator<Element> itr = entities.iterator();

			while (itr.hasNext()) {
				Element entity = itr.next();

				String entityName = entity.attributeValue("name");

				boolean remoteService = GetterUtil.getBoolean(
					entity.attributeValue("remote-service"), true);

				if (remoteService) {
					_createServiceWSDD(entityName);

					WSDDMerger.merge(
						_outputPath + "/" + entityName + "Service_deploy.wsdd",
						_serverConfigFileName);
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void _createServiceWSDD(String entityName) throws IOException {
		String className =
			_packagePath + ".service.http." + entityName + "ServiceSoap";

		String serviceName = StringUtil.replace(_portletShortName, " ", "_");

		if (!_portalWsdd) {
			serviceName = "Plugin_" + serviceName;
		}
		else {
			if (!_portletShortName.equals("Portal")) {
				serviceName = "Portlet_" + serviceName;
			}
		}

		serviceName += ("_" + entityName + "Service");

		String[] wsdds = Java2WsddTask.generateWsdd(className, serviceName);

		FileUtil.write(
			new File(_outputPath + "/" + entityName + "Service_deploy.wsdd"),
			wsdds[0], true);

		FileUtil.write(
			new File(_outputPath + "/" + entityName + "Service_undeploy.wsdd"),
			wsdds[1], true);
	}

	private String _serverConfigFileName;
	private boolean _portalWsdd = true;
	private String _portletShortName;
	private String _outputPath;
	private String _packagePath;

}