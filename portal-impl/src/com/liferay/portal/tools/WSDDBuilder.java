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

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class WSDDBuilder {

	public static void main(String[] args) {
		Map<String, String> arguments = ArgumentsUtil.parseArguments(args);

		InitUtil.initWithSpring();

		String fileName = arguments.get("wsdd.input.file");
		String serviceNamespace = arguments.get("wsdd.service.namespace");
		String outputPath = arguments.get("wsdd.output.path");
		String serverConfigFileName = arguments.get("wsdd.server.config.file");

		new WSDDBuilder(
			fileName, serviceNamespace, outputPath, serverConfigFileName);
	}

	public WSDDBuilder(
		String fileName, String serviceNamespace, String outputPath,
		String serverConfigFileName) {

		try {
			_outputPath = outputPath;
			_serverConfigFileName = serverConfigFileName;
			_serviceNamespace = serviceNamespace;

			if (!FileUtil.exists(_serverConfigFileName)) {
				ClassLoader classLoader = getClass().getClassLoader();

				String serverConfigContent = StringUtil.read(
					classLoader,
					"com/liferay/portal/tools/dependencies/server-config.wsdd");

				FileUtil.write(_serverConfigFileName, serverConfigContent);
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

	private void _createServiceWSDD(String entityName) throws Exception {
		String className =
			_packagePath + ".service.http." + entityName + "ServiceSoap";

		String serviceName = StringUtil.replace(_portletShortName, " ", "_");


		if (!_portletShortName.equals("Portal")) {
			serviceName = _serviceNamespace + serviceName;
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
	private String _serviceNamespace;
	private String _portletShortName;
	private String _outputPath;
	private String _packagePath;

}